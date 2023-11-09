package monopoly.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataManager {
	private static DataManager dataManager = new DataManager();
	private ObjectManager<User> registeredUsers = new ObjectManager<>();
	private ObjectManager<Match> registeredMatches = new ObjectManager<>();
	
	public DataManager() {
		//TODO Load all data from Database
	}
	
	public static DataManager getManager() {
		return DataManager.dataManager;
	}
	
	public ObjectManager<User> getRegisteredUsers() {
		return registeredUsers;
	}
	
	public ObjectManager<Match> getRegisteredMatches() {
		return registeredMatches;
	}
}
