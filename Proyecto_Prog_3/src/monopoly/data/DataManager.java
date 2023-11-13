package monopoly.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;

import monopoly.objects.LogRecorder;
import monopoly.objects.Match;
import monopoly.objects.User;

public class DataManager {
	private static DataManager dataManager = null;
	private ObjectManager<User> registeredUsers = new ObjectManager<>();
	private ObjectManager<Match> registeredMatches = new ObjectManager<>();
	private LogRecorder logger = new LogRecorder(this.getClass());
	
	private String filePath = "/monopoly/data/UserFile.dat";
	private URL fPath = getClass().getResource(filePath);
	
	private DataManager() {
		//TODO Load all data from Database
		loadUsers();
	}
	
	public static DataManager getManager() {
		if(dataManager == null) {
			dataManager = new DataManager();
		} 
		return DataManager.dataManager;			
	}
	
	public void saveUser(User usr){
		registeredUsers.addObject(usr);
	}
	
	public ObjectManager<User> getRegisteredUsers() {
		return registeredUsers;
	}
	
	public ObjectManager<Match> getRegisteredMatches() {
		return registeredMatches;
	}
	
	@SuppressWarnings("unchecked")
	private void loadUsers() {
		try (ObjectInputStream UsersInput = new ObjectInputStream(getClass().getResourceAsStream(filePath))) {
			ObjectManager<User> list = (ObjectManager<User>) UsersInput.readObject();
			registeredUsers = list;
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
		//Users
		try (ObjectOutputStream forFile = new ObjectOutputStream(new FileOutputStream(fPath.getPath()))) {
			forFile.reset();
			forFile.writeObject(registeredUsers);
			logger.log(Level.INFO, "Users saved");
		} catch (IOException e) {
			logger.log(Level.WARNING, "The address to add the file was not found");
			e.printStackTrace();
		}	
	}
}
