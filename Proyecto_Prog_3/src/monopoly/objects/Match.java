package monopoly.objects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Match {
	private Date date;
	private long duration;
	//							Time of the change - Amount of cash
	private Map<User, TreeMap<Long, Integer>> usersCurrency; //Used for statistics
	private ArrayList<User> users = new ArrayList<>();
	private int numUsers;
	private String name;
	
	public Match(String name, int numUsers) {
		this.numUsers = numUsers;
		this.name = name;
		users.add(new User("Paco"));
		users.add(new User("Juan"));
		users.add(new User("Fabian"));
	}

	public int getNumUsers() {
		return numUsers;
	}

	public void setNumUsers(int numUsers) {
		this.numUsers = numUsers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
