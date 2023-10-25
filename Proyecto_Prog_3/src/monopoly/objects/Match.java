package monopoly.objects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Match {
	//							Time of the change - Amount of cash
	private Map<User, TreeMap<Long, Integer>> turnCurrencyPerUser; //Used for statistics
	private List<User> users = new ArrayList<>();
	private String name;
	
	public Match(String name, Map<User, TreeMap<Long, Integer>> turnCurrencyPerUser) {
		this.name = name;
		this.turnCurrencyPerUser = turnCurrencyPerUser;
	}
	
	//Test data model
	public Match() {
		name = "Test Match";
		turnCurrencyPerUser = new HashMap<User, TreeMap<Long, Integer>>();
		TreeMap<Long, Integer> map1 = new TreeMap<>();
		map1.put((long) 60000, 100);
		map1.put((long) 100000, 200);
		map1.put((long) 180000, 500);
		map1.put((long) 220000, 900);
		TreeMap<Long, Integer> map2 = new TreeMap<>();
		map2.put((long) 60000, 100);
		map2.put((long) 110000, 300);
		map2.put((long) 130000, 500);
		map2.put((long) 230000, 100);
		TreeMap<Long, Integer> map3 = new TreeMap<>();
		map3.put((long) 60000, 100);
		map3.put((long) 120000, 600);
		map3.put((long) 150000, 200);
		map3.put((long) 240000, 1000);
		
		User usr1 = new User("Paco", "dvkajenlkaenñfa");
		User usr2 = new User("Juan", "thaehaerhaerhaeha");
		User usr3 = new User("Damian", "rgagaerhaehaerh");
		
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

	public Map<User, TreeMap<Long, Integer>> getTurnCurrencyPerUser() {
		return turnCurrencyPerUser;
	}

	public void setTurnCurrencyPerUser(Map<User, TreeMap<Long, Integer>> turnCurrencyPerUser) {
		this.turnCurrencyPerUser = turnCurrencyPerUser;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
