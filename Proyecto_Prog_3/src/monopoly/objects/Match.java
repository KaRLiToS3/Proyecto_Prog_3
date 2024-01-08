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
	//		       User email					Turn - Amount of cash
	private Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser; //Used for statistics
	private List<String> usersEmails;
	private String name;
	private Date date;
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	
	public Match(Date date, String name, Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser) {
		this.name = name.strip();
		this.date = date;
		this.turnCurrencyPerUser = turnCurrencyPerUser;
		usersEmails = new ArrayList<>(turnCurrencyPerUser.keySet());
	}
	
	//Test data model
	public Match() {
		date = new Date();
		name = "Test Match";
		turnCurrencyPerUser = new HashMap<String, TreeMap<Integer, Integer>>();
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
		
		User usr1 = new User("Paco", "Paco");
		User usr2 = new User("Juan", "Juan");
		User usr3 = new User("Damian", "Damian");
		
		usersEmails = new ArrayList<>();
		usersEmails.add(usr1.getEmail());
		usersEmails.add(usr2.getEmail());
		usersEmails.add(usr3.getEmail());
		
		turnCurrencyPerUser.put(usr1.getEmail(), map1);
		turnCurrencyPerUser.put(usr2.getEmail(), map2);
		turnCurrencyPerUser.put(usr3.getEmail(), map3);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public List<String> getUsersEmails() {
		return usersEmails;
	}

	public void setUsersEmails(ArrayList<String> users) {
		this.usersEmails = users;
	}

	public Map<String, TreeMap<Integer, Integer>> getTurnCurrencyPerUser() {
		return turnCurrencyPerUser;
	}

	public static SimpleDateFormat getFormat() {
		return format;
	}

	public void setTurnCurrencyPerUser(Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser) {
		this.turnCurrencyPerUser = turnCurrencyPerUser;
	}
	
	public String getDateAsString() {
		return format.format(date);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Match) {
			Match match = (Match) obj;
			return this.getDateAsString().equals(match.getDateAsString());
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
