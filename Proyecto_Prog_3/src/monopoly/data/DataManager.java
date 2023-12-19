package monopoly.data;

import java.sql.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences.PRINT_SCALING;

import monopoly.objects.Achievement;
import monopoly.objects.Match;
import monopoly.objects.User;
import monopoly.windows.MainMenu;
import monopoly.windows.WarningPanel;

/**
 * Designed to manage all the data of the application
 * @author KaRLiToS3.0
 */
public class DataManager {
	private static DataManager dataManager = null;
	private static Properties initializer = null;
	private static final String propertyFile = Paths.get("data/configuration.properties").toAbsolutePath().toString();
	private ObjectManager<ObjectManager<?>> allData= new ObjectManager<>();
	private ObjectManager<User> registeredUsers = new ObjectManager<>();
	private ObjectManager<Match> registeredMatches = new ObjectManager<>();
	private static LogRecorder logger = new LogRecorder(DataManager.class);
		
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
	
	public ObjectManager<User> getRegisteredUsers() {
		return registeredUsers;
	}
	
	public ObjectManager<Match> getRegisteredMatches() {
		return registeredMatches;
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
	public void uploadDataFromDB() {
		//Load the driver
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to load the driver " + driver);
		}
		//TODO
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/GeneralDatabase.bd");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER");
			while(rs.next()) {
				String Alias = rs.getString("ALIAS");
				String Name = rs.getString("NAME");
				String Email = rs.getString("EMAIL");
				String Password = rs.getString("PASSWORD");
				String Achievements = rs.getString("ACHIEVEMENTS");
				Set<Achievement> setAch = convertStringToAchievementSet(Achievements);
				registeredUsers.addObject(new User(Name, Email, Password, Alias, setAch));
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch (SQLException e) {
			// TODO: handle exception
			new WarningPanel("There was a problem while loading the data, please \nmake sure all files remain in their corresponding folder");
			logger.log(Level.SEVERE, "Error uploading data");
			e.printStackTrace();
		};
	}
	
	public void saveDataInDB() {
		//Load the driver
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE, "Unable to load the driver " + driver);
		}
		//TODO
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/GeneralDatabase.bd");
			String sqlDelete = "DELETE FROM USER";
			PreparedStatement deleteStmt = conn.prepareStatement(sqlDelete);
			deleteStmt.executeUpdate();
			for (User user : registeredUsers) {
				//The order in the database is now EMAIL, NAME, ALIAS, PASSWORD, IMAGE, ACHIEVEMENTS
				String sqlInsert = "INSERT INTO USER (EMAIL, NAME, ALIAS, PASSWORD, ACHIEVEMENTS) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement prepStmt = conn.prepareStatement(sqlInsert);
				prepStmt.setString(1,user.getEmail());
				prepStmt.setString(2,user.getName());
				prepStmt.setString(3,user.getAlias());
				prepStmt.setString(4,user.getPassword());
				prepStmt.setString(5, convertAchievementSetToString(user.getAchievements()));
				prepStmt.executeUpdate();
			}
			deleteStmt.close();
			conn.close();
		}catch (SQLException e) {
			new WarningPanel("The conection with the local database is not working\nplease restart the program, if the problem persists \nthere might be a problem with the file directory");
			logger.log(Level.SEVERE, "Error saving data");
			e.printStackTrace();
		}
	}
	
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
		for(int i = 0; i < line.length; i++) {
			String[] achievement = line[i].split("/");
			for(Achievement.Type type : Achievement.Type.values()){
				if(type.toString().equals(achievement[0])) {
					res.add(new Achievement(type, Integer.parseInt(achievement[1])));
				}
			}
		}
		return res;
	}
	
	/**The output convention will have this example format: <strong>"MVP/2;CHEAPSKATE/4;BEGGINER/1"</strong>
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
	
	//IN CASE A FILE IS USED
	@SuppressWarnings("unchecked")
	private void loadAllDataFromFile() {
		try (ObjectInputStream UsersInput = new ObjectInputStream(new FileInputStream(filePath))) {
			allData = (ObjectManager<ObjectManager<?>>) UsersInput.readObject();
			for(ObjectManager<?> obj : allData) {
				if(obj instanceof ObjectManager) {
					ObjectManager<?> objectManager = (ObjectManager<?>) obj;
					if(!objectManager.isEmpty()) {						
						if(objectManager.iterator().next() instanceof User) { //iterator is an objects that iterates over the data, with the next method we get the first object and we use instanceof User to check it
							registeredUsers = (ObjectManager<User>) objectManager;
							logger.log(Level.INFO, "All users were properly loaded");
						} else if(objectManager.iterator().next() instanceof Match) {
							registeredMatches = (ObjectManager<Match>) objectManager;
							logger.log(Level.INFO, "All matches were properly loaded");
						}else logger.log(Level.WARNING, "Failed to load part of the data, beacuse it doesn't match the type");
					}
				} else logger.log(Level.WARNING, "Failed to load the data, beacuse it doesn't match the type");
			}
		} catch (FileNotFoundException e) {
			logger.log(Level.WARNING, "File for load users not found");
			new WarningPanel("The data file is missing in the location "+ filePath);
			e.printStackTrace();
		} catch (IOException e) {
			logger.log(Level.WARNING, "User loading failed");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			logger.log(Level.WARNING, "Incorrect cast to User");
			e.printStackTrace();
		}
	}
	
	public void saveAllDataToFile() {
		try (ObjectOutputStream forFile = new ObjectOutputStream(new FileOutputStream(filePath))) {
			forFile.reset();
			if(allData.isEmpty()) {
				try {			//REMOVE THIS PART
					allData.addObject(registeredUsers);
					allData.addObject(registeredMatches);
					System.out.println("The file was configured, the next time it should work perfectly fine");
				} catch (InvalidParameterException e) {
				}
			}
			forFile.writeObject(allData);
			logger.log(Level.INFO, "Users saved");
		} catch (IOException e) {
			logger.log(Level.WARNING, "The address to add the file was not found");
			e.printStackTrace();
		}	
	}
}
