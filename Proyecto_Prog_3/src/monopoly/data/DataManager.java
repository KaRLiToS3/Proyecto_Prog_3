package monopoly.data;

import java.sql.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.logging.Level;

import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences.PRINT_SCALING;

import monopoly.objects.Match;
import monopoly.objects.User;

/**
 * Designed to manage all the data of the application
 * @author KaRLiToS3.0
 */
public class DataManager {
	private static DataManager dataManager = null;
	private ObjectManager<ObjectManager<?>> allData= new ObjectManager<>();
	private ObjectManager<User> registeredUsers = new ObjectManager<>();
	private ObjectManager<Match> registeredMatches = new ObjectManager<>();
	private LogRecorder logger = new LogRecorder(this.getClass());
		
	private static String driver = "org.sqlite.JDBC";
	private String filePath = Paths.get("data/Data.dat").toAbsolutePath().toString();
	
	private DataManager() {
		//TODO Load all data from Database
		loadAllDataFromFile();
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
			registeredUsers = new ObjectManager<>();
			Connection conn = DriverManager.getConnection("jdbc:sqlite:data/GeneralDatabase.bd");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM User");
			while(rs.next()) {
				String Alias = rs.getString("Alias");
				String Name = rs.getString("Name");
				String Email = rs.getString("Email");
				String Password = rs.getString("Password");
				registeredUsers.addObject(new User(Name, Email, Password, Alias));
			}
			rs.close();
			conn.close();
		}catch (SQLException e) {
			// TODO: handle exception
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
			String sqlDelete = "DELETE FROM User";
			PreparedStatement deleteStmt = conn.prepareStatement(sqlDelete);
			deleteStmt.executeUpdate();
			for (User user:registeredUsers) {
				String sqlInsert = "INSERT INTO User(Alias, Name, Email, Password) VALUES (?, ?, ?, ?)";
				PreparedStatement prepStmt = conn.prepareStatement(sqlInsert);
				prepStmt.setString(1,user.getAlias());
				prepStmt.setString(2,user.getName());
				prepStmt.setString(3,user.getEmail());
				prepStmt.setString(4,user.getPassword());
				int rows = prepStmt.executeUpdate();
				System.out.println("Updated: " + rows);
			}
			conn.close();
		}catch (SQLException e) {
			// TODO: handle exception
			logger.log(Level.SEVERE, "Error saving data");
			e.printStackTrace();
		}
		
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
