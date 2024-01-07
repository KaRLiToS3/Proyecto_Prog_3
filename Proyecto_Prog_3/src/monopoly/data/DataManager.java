package monopoly.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import monopoly.objects.Achievement;
import monopoly.objects.Achievement.Type;
import monopoly.objects.Match;
import monopoly.objects.User;
import monopoly.windows.MasterFrame;
import monopoly.windows.WarningPanel;

/**
 * Designed to manage all the data of the application
 * @author KaRLiToS3.0 with some contributions from Fauldave
 */
public class DataManager{

	private static DataManager dataManager = null;
	private static Properties initializer = null;
	private static final String propertyFile = Paths.get("data/configuration.properties").toAbsolutePath().toString();
	private ImmutableList<User> registeredUsers = new ImmutableList<>();
	private ImmutableList<Match> registeredMatches = new ImmutableList<>();
	private static LogRecorder logger = new LogRecorder(DataManager.class);

	private Connection conn;
	private static int userChoiceToContinue = JOptionPane.YES_OPTION;
	private static String driver = getInitializer().getProperty("driver");
	private String filePath = Paths.get("data/Data.dat").toAbsolutePath().toString();

	private DataManager() {
		uploadDataFromDB();
	}

	public static DataManager getManager() {
		if(dataManager == null) {
			dataManager = new DataManager();
		}
		return DataManager.dataManager;
	}

	public void saveUser(User usr){
		try {
			registeredUsers.addObject(usr);
		} catch (InvalidParameterException e) {
			logger.log(Level.WARNING, "The user " + usr.getName() + " was already on the list");
		}
	}

	public void saveMatch(Match match) {
		try {
			registeredMatches.addObject(match);
		} catch (InvalidParameterException e) {
			logger.log(Level.WARNING, "The match " + match.getName() + " was already on the list");
		}
	}

	public ImmutableList<User> getRegisteredUsers() {
		return registeredUsers;
	}

	public ImmutableList<Match> getRegisteredMatches() {
		return registeredMatches;
	}

	/**This method adds a {@code Achievement} and a {@code Type} to a {@code User} and in case the type of achievement was already
	 * added it increments the {@code times} parameter in the {@code Achievement} class.
	 * @param usr
	 * @param type
	 */
	public void incrementAchivementOrAddIfAbsent(User usr, Type type) {
		if(!usr.getAchievements().add(new Achievement(type))) {
			for(Achievement ach : usr.getAchievements()) {
				if(ach.getType().equals(type)) ach.setTimes(ach.getTimes() + 1);
			}
		}
		MasterFrame.triggerDataUpdate();
	}

	//JAVA PROPERTY FILE
	public static Properties getInitializer() {
		if(initializer == null) {
			initializer = new Properties();
		}
		try{
			initializer.load(new FileInputStream(new File(propertyFile)));
		} catch (FileNotFoundException e) {
			new WarningPanel("We were unable to find the file connection.properties\nplease make sure it's in the folder 'data' ");
			logger.log(Level.SEVERE, "Unable to find the .properties file in the location");
			e.printStackTrace();
		} catch (IOException e) {
			new WarningPanel("A major problem occured while loading, please restart");
			logger.log(Level.SEVERE, "A major problem occured while loading");
			e.printStackTrace();
		}
		return DataManager.initializer;
	}

	//DATA BASE

	private void connect() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection("jdbc:sqlite:data/GeneralDatabase.bd");
		} catch (ClassNotFoundException | SQLException e) {
			logger.log(Level.SEVERE, "Unable to load the driver " + driver);
		}
	}

	private void disconnect() throws SQLException {
		conn.close();
	}

	public boolean checkForUserChoice() {
		if(userChoiceToContinue == JOptionPane.NO_OPTION) return false;
		else return true;
	}

	/**This method creates the database from scratch in case the original file is lost
	 * @throws SQLException
	 */
	private void createDataBase() throws SQLException{
		Statement stmt = conn.createStatement();
		stmt.executeUpdate("CREATE TABLE USER ("
				+ "    EMAIL TEXT NOT NULL,"
				+ "    NAME TEXT NOT NULL,"
				+ "    ALIAS TEXT,"
				+ "    PASSWORD TEXT NOT NULL,"
				+ "    IMAGE TEXT,"
				+ "    ACHIEVEMENTS TEXT,"
				+ "    PRIMARY KEY (EMAIL)"
				+ ");");
		stmt.executeUpdate("CREATE TABLE MATCH ("
				+ "	DAT TEXT PRIMARY KEY,"
				+ "	NAME TEXT NOT NULL"
				+ ");");
		stmt.executeUpdate("CREATE TABLE MATCHMAP ("
				+ "	ID_MAP INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "	MATCH_DAT TEXT NOT NULL,"
				+ "	USER_EMAIL TEXT NOT NULL,"
				+ "	TURN INT,"
				+ "	CURRENCY INT,"
				+ "	FOREIGN KEY (MATCH_DAT) REFERENCES MATCH(DAT)"
				+ ");");
		stmt.close();
	}

	public void deleteUser(User usr) {
		registeredUsers.removeObject(usr);
		connect();
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM USER WHERE EMAIL = ?");
			stmt.setString(1, usr.getEmail());
			stmt.executeUpdate();
			stmt.close();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteMatch(Match match) {
		registeredMatches.removeObject(match);
		connect();
		try {
			deleteMapAssociated(match);
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM MATCH WHERE DAT = ?");
			stmt.setString(1, match.getDateAsString());
			stmt.executeUpdate();
			stmt.close();
			disconnect();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteMapAssociated(Match match) throws SQLException{
		PreparedStatement stmt = conn.prepareStatement("DELETE FROM MATCHMAP WHERE MATCH_DAT = ?");
		stmt.setString(1, match.getDateAsString());
		stmt.executeUpdate();
		stmt.close();
	}

	/**
	 * Loads all the data from the database to the lists of objects, in case the database is lost
	 * there will appear a <strong>WarningPanel</strong> to explain that a new database
	 * will be created
	 */
	public void uploadDataFromDB() {
		connect();
		try {
			uploadUsers();
			uploadMatches();
			saveMatch(new Match());
			disconnect();
		}catch (SQLException e) {
			userChoiceToContinue = JOptionPane.showConfirmDialog(null,
					"There was a problem while loading the data, please "
					+ "\nmake sure all files remain in their corresponding folder.\n"
					+ "Should we try other means to retrieve the data?\n"
					+ "We will use a local copy of the data, any changes will be\n"
					+ "saved and we will create a new database, in case you\n"
					+ "retrieve the old one you can change them, all new changes\n"
					+ "will prevail", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			logger.log(Level.SEVERE, "Error uploading data, we will atempt to read from a serialized file");
			if(userChoiceToContinue == JOptionPane.YES_OPTION) {
				try {
					createDataBase();
					logger.log(Level.INFO, "A new database has been created");
				} catch (SQLException e1) {
					logger.log(Level.WARNING, "Failed to create a new database");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			synchronizeFileWithDataBase();
		}
	}

	private void uploadUsers() throws SQLException{
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
		while(rs.next()) {
			String Alias = rs.getString("ALIAS");
			String Name = rs.getString("NAME");
			String Email = rs.getString("EMAIL");
			String Password = rs.getString("PASSWORD");
			String Achievements = rs.getString("ACHIEVEMENTS");
			Set<Achievement> setAch = convertStringToAchievementSet(Achievements);
			User usr = new User(Name, Email, Password, Alias, setAch);
			registeredUsers.addObject(usr);
		}
		stmt.close();
		rs.close();
	}

	private void uploadMatches() throws SQLException{
		String getMatches = "SELECT * FROM MATCH";
		String getMap = "SELECT USER_EMAIL, TURN, CURRENCY FROM MATCHMAP WHERE MATCH_DAT = ?";

		Statement stmt = conn.createStatement();
		ResultSet res = stmt.executeQuery(getMatches);
		while(res.next()) {
			String dat = res.getString("DAT");
			String name = res.getString("NAME");

			PreparedStatement mapStmt = conn.prepareStatement(getMap);
			mapStmt.setString(1, dat);
			ResultSet map = mapStmt.executeQuery();

			Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser = new HashMap<>();
			while (map.next()) {
				TreeMap<Integer, Integer> turnsPerCurrency = new TreeMap<>();
				String email = map.getString("USER_EMAIL");
				Integer turn = map.getInt("TURN");
				Integer currency = map.getInt("CURRENCY");

				if(!turnCurrencyPerUser.containsKey(email)) {
					turnsPerCurrency.put(turn, currency);
					turnCurrencyPerUser.put(email, turnsPerCurrency);
				} else {
					turnCurrencyPerUser.get(email).put(turn, currency);
				}
			}
			try {
				registeredMatches.addObject(new Match(Match.getFormat().parse(dat), name, turnCurrencyPerUser));
			} catch (ParseException e) {
				logger.log(Level.SEVERE, "Wrong date format for the match, conversion not possible");
			}
			mapStmt.close();
			map.close();
		}
		stmt.close();
		res.close();
	}

	public void saveDataInDB() {
		connect();
		try {
			try {
				saveUsers();
			}catch (SQLException e) {
				logger.log(Level.SEVERE, "Error saving users");
			}
			try {
				saveMatches();
			}catch (SQLException e) {
				logger.log(Level.SEVERE, "Error saving matches");
			}
			disconnect();
		}catch (SQLException e) {
			new WarningPanel("The conection with the local database is not working\nplease restart the program, if the problem persists \nthere might be a problem with the file directory");
			logger.log(Level.SEVERE, "Error saving data");
			e.printStackTrace();
		} finally {
			saveAllDataToFile();
		}
	}

	private void saveUsers() throws SQLException {
		String checkUser = "SELECT * FROM USER WHERE EMAIL = ?";
		String updateUser = "UPDATE USER SET NAME = ?, ALIAS = ?, PASSWORD = ?, IMAGE = ?, ACHIEVEMENTS = ? WHERE EMAIL = ?";
		String sqlInsert = "INSERT INTO USER (EMAIL, NAME, ALIAS, PASSWORD, IMAGE, ACHIEVEMENTS) VALUES (?, ?, ?, ?, ?, ?)";

		for (User user : registeredUsers) {
			PreparedStatement chkStmt = conn.prepareStatement(checkUser);
			chkStmt.setString(1, user.getEmail());
			ResultSet check =chkStmt.executeQuery();
			if(check.next()) {
				PreparedStatement updateStmt = conn.prepareStatement(updateUser);
				updateStmt.setString(1, user.getName());
				updateStmt.setString(2, user.getAlias());
				updateStmt.setString(3, user.getPassword());
				updateStmt.setString(4, convertAchievementSetToString(user.getAchievements()));
				updateStmt.setString(5, user.getEmail());
				updateStmt.executeUpdate();
				updateStmt.close();
			} else {
				//The order in the database is now EMAIL, NAME, ALIAS, PASSWORD, IMAGE, ACHIEVEMENTS
				PreparedStatement prepStmt = conn.prepareStatement(sqlInsert);
				prepStmt.setString(1, user.getEmail());
				prepStmt.setString(2, user.getName());
				prepStmt.setString(3, user.getAlias());
				prepStmt.setString(4, user.getPassword());
				if (user.getImage() != null) {
					prepStmt.setString(5, user.getImage().toString());
				} else {
					prepStmt.setString(5, null);
				}
				prepStmt.setString(6, convertAchievementSetToString(user.getAchievements()));
				prepStmt.executeUpdate();
				prepStmt.close();
			}
			chkStmt.close();
			chkStmt.close();
		}
	}

	private void saveMatches() throws SQLException {
		String checkMatch = "SELECT * FROM MATCH WHERE DAT = ?";
		String updateMatch = "UPDATE MATCH SET NAME = ? WHERE DAT = ?";
		String insertMatch = "INSERT INTO MATCH (DAT, NAME) VALUES (?, ?)";

		for(Match match : registeredMatches) {
			PreparedStatement chkStmt = conn.prepareStatement(checkMatch);
			chkStmt.setString(1, match.getDateAsString());
			ResultSet check = chkStmt.executeQuery();

			if(check.next()) {
				PreparedStatement updateStmt = conn.prepareStatement(updateMatch);
				updateStmt.setString(1, match.getName());
				updateStmt.setString(2, match.getDateAsString());
				updateStmt.executeUpdate();
				updateStmt.close();
			} else {
				PreparedStatement insertStmt = conn.prepareStatement(insertMatch);
				insertStmt.setString(1, match.getDateAsString());
				insertStmt.setString(2, match.getName());
				insertStmt.executeUpdate();
				insertStmt.close();
				insertMapAssocieted(match);
			}
			chkStmt.close();
			check.close();
		}
	}

	private void insertMapAssocieted(Match match) throws SQLException{
		String insertStatement = "INSERT INTO MATCHMAP (MATCH_DAT, USER_EMAIL, TURN, CURRENCY) VALUES (?, ?, ?, ?)";

		for(String userEmail : match.getTurnCurrencyPerUser().keySet()) {
			TreeMap<Integer, Integer> turnAndCurrency = match.getTurnCurrencyPerUser().get(userEmail);
			for(Integer turn : turnAndCurrency.keySet()) {
				PreparedStatement insertStmt = conn.prepareStatement(insertStatement);
				insertStmt.setString(1, match.getDateAsString());
				insertStmt.setString(2, userEmail);
				insertStmt.setInt(3, turn);
				insertStmt.setInt(4, turnAndCurrency.get(turn));
				insertStmt.executeUpdate();
				insertStmt.close();
			}
		}
	}

	//ACHIEVEMENT DECODIFICATION

	/**The input convention should have this example format: <strong>"MVP/2;CHEAPSKATE/4;BEGGINER/1"</strong>
	 * The <strong>slash /</strong> divides the {@code Type} selection and the {@code times} from the {@code Achievement} class.
	 * The <strong> ;</strong> divides the Achievements
	 * @param input
	 * @return {@code Set<Achievement>} object
	 */
	private static Set<Achievement> convertStringToAchievementSet(String input){
		Set<Achievement> res = new HashSet<>();
		if(input == null) return res;

		String[] line = input.split(";");
		for (String element : line) {
			String[] achievement = element.split("/");
			for(Achievement.Type type : Achievement.Type.values()){
				if(type.toString().equals(achievement[0])) {
					res.add(new Achievement(type, Integer.parseInt(achievement[1])));
				}
			}
		}
		return res;
	}

	/**The output convention will have this example format: <strong>"MVP/2;CHEAPSKATE/4;BEGINNER/1"</strong>
	 * The <strong>slash /</strong> divides the {@code Type} selection and the {@code times} from the {@code Achievement} class.
	 * The <strong> ;</strong> divides the Achievements
	 * @param data
	 * @return {@code String} with the proper format before described
	 */
	private static String convertAchievementSetToString(Set<Achievement> data) {
		String str = "";
		int count = 0;
		for(Achievement ach : data) {
			count++;
			if(count != data.size()) str = str + ach.toString() + ";";
			else str = str + ach.toString();
		}
		return str;
	}

	/**
	 * This method will <strong>always</strong> synchronize the database
	 * using the file as a safety measure. The database is the primary source
	 * when not available a file will be used, and then that data will be loaded
	 * again into the database. No data will be lost unless they are both lost.
	 */
	@SuppressWarnings("unchecked")
	private void synchronizeFileWithDataBase() {
		List<Set<?>> allData = loadAllDataFromFile();
		if(allData != null) {
			registeredUsers.addDataSet((Set<User>) allData.get(0));
			registeredMatches.addDataSet((Set<Match>)allData.get(1));
		}
	}

	@SuppressWarnings("unchecked")
	private List<Set<?>> loadAllDataFromFile() {
		File f = new File(filePath);
		try {
			if(f.createNewFile()) logger.log(Level.INFO, "A new Data.dat file was created for it was missing");
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(filePath))) {
			return (List<Set<?>>) input.readObject();
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "File for load users not found");
			new WarningPanel("The data file is missing in the location "+ filePath + "\nWe cannot launch the game with saved data."
					+"\nThis will change when new data is loaded");
		} catch (IOException e) {
			logger.log(Level.WARNING, "User loading failed");
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARNING, "Incorrect cast to User");
		}
		return null;
	}

	/**
	 * Saves all data to a serialized file to sync with the database in case of necessity
	 */
	public void saveAllDataToFile() {
		try (ObjectOutputStream forFile = new ObjectOutputStream(new FileOutputStream(filePath))) {
			forFile.reset();
			List<Set<?>> allData = new ArrayList<>();
			allData.add(registeredUsers.getRegisteredData());
			allData.add(registeredMatches.getRegisteredData());
			forFile.writeObject(allData);
			logger.log(Level.INFO, "All data was saved to the file successfully");
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to save data to file");
			e.printStackTrace();
		}
	}

	//RECURSIVITY
	public User getUserByEmail(String email) {
	    List<User> list = new ArrayList<>(registeredUsers.getRegisteredData());
	    return recursiveFunction(list, email);
	}

	private User recursiveFunction(List<User> list, String email) {
	    if(list.size() < 1) return null;
	    int breakPoint = list.size()/2;
	    User usr = list.get(breakPoint);
	    if(usr.getEmail().equals(email)) {
	        return usr;
	    }
	    User foundUser = recursiveFunction(list.subList(0, breakPoint), email);
	    if(foundUser != null) return foundUser;
	    return recursiveFunction(list.subList(breakPoint + 1, list.size()), email);
	}

	///////////////////////////////////////////////////
	//**************** IMMUTABLE LIST *********************
	///////////////////////////////////////////////////

	/**
	 * Designed to handle any kind data of type T, similar to a HashSet
	 * but ensuring only visualization of the data, only the DataManager is
	 * allowed to perform modifications in these objects
	 * @author KaRLiToS3.0
	 */
	public class ImmutableList <T> implements Iterable<T>{

		private Set<T> registeredData = new HashSet<>();

		private void addObject(T object){
			registeredData.add(object);
		}

		private void removeObject(T object){
			registeredData.remove(object);
		}

		private void addImmutableList(ImmutableList<T> dataCollection) {
			registeredData.addAll(dataCollection.getRegisteredData());
		}

		private void addDataSet(Set<T> data) {
			registeredData.addAll(data);
		}

		public boolean containsObject(T object) {
			return registeredData.contains(object);
		}

		public boolean isEmpty() {
			return registeredData.isEmpty();
		}

		public int size() {
			return registeredData.size();
		}

		@Override
		public Iterator<T> iterator() {
			return registeredData.iterator();
		}

		/**This method returns a copy of the data, so any changes made in this list
		 * will not prevail in the database or in the files
		 * @return A copy of the data
		 */
		public Set<T> getRegisteredData() {
			return new HashSet<>(registeredData);
		}
	}
}
