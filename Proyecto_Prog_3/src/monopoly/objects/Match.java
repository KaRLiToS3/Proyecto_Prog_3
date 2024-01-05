package monopoly.objects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Match implements Serializable{
	private static final long serialVersionUID = 3317059767741645648L;
	//												Turn - Amount of cash
	private Map<User, TreeMap<Integer, Integer>> turnCurrencyPerUser; //Used for statistics
	private List<User> users;
	private String name;
	private Date date;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

	public Match(Date date, String name, Map<User, TreeMap<Integer, Integer>> turnCurrencyPerUser) {
		this.name = name;
		this.date = date;
		this.turnCurrencyPerUser = turnCurrencyPerUser;
		users = new ArrayList<>(turnCurrencyPerUser.keySet());
	}
	
	//Test data model
	public Match() {
		date = new Date();
		name = "Test Match";
		turnCurrencyPerUser = new HashMap<User, TreeMap<Integer, Integer>>();
		TreeMap<Integer, Integer> map1 = new TreeMap<>();
		map1.put(1, 100);
		map1.put(2, 200);
		map1.put(3, 500);
		map1.put(4, 900);
		TreeMap<Integer, Integer> map2 = new TreeMap<>();
		map2.put(1, 100);
		map2.put(2, 300);
		map2.put(3, 500);
		map2.put(4, 100);
		TreeMap<Integer, Integer> map3 = new TreeMap<>();
		map3.put(1, 100);
		map3.put(2, 600);
		map3.put(3, 200);
		map3.put(4, 1000);
		
		User usr1 = new User("a", "a");
		User usr2 = new User("b", "b");
		User usr3 = new User("c", "c");
		
		users = new ArrayList<>();
		users.add(usr1);
		users.add(usr2);
		users.add(usr3);
		
		turnCurrencyPerUser.put(usr1, map1);
		turnCurrencyPerUser.put(usr2, map2);
		turnCurrencyPerUser.put(usr3, map3);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

	public Map<User, TreeMap<Integer, Integer>> getTurnCurrencyPerUser() {
		return turnCurrencyPerUser;
	}

	public static SimpleDateFormat getFormat() {
		return format;
	}

	public void setTurnCurrencyPerUser(Map<User, TreeMap<Integer, Integer>> turnCurrencyPerUser) {
		this.turnCurrencyPerUser = turnCurrencyPerUser;
	}
	
	public String getDateAsString() {
		return format.format(date);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Match) {
			Match match = (Match) obj;
			return this.getName().equals(match.getName());
		} else return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name + "    " + format.format(date);
	}
	
}
