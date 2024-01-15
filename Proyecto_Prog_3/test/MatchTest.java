import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;

import monopoly.objects.Match;
import monopoly.objects.User;

public class MatchTest {
	private Match match1;
	private Match match2;
	private List<String>users;

	@Before
	public void loadDataStructure() {
		Map<String, TreeMap<Integer, Integer>> data = loadDataExampe1();
		match1 = new Match(new Date(),"match1", data);
		match2 = new Match();
	}

	@Test
	public void test() {
		assertEquals("match1", match1.getName());
		assertEquals(users, match1.getUsersEmails());
		assertEquals(loadDataExampe1(), match1.getTurnCurrencyPerUser());
		assertEquals("Test Match", match2.getName());
		assertEquals(loadDataExampe2(), match2.getTurnCurrencyPerUser());
		assertEquals(users, match2.getUsersEmails());
	}

	private Map<String, TreeMap<Integer, Integer>> loadDataExampe1(){
		Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser = new HashMap<>();
		TreeMap<Integer, Integer> map1 = new TreeMap<>();
		map1.put(1, 100);
		map1.put(2, 200);
		map1.put(3, 300);
		map1.put(4, 900);
		TreeMap<Integer, Integer> map2 = new TreeMap<>();
		map2.put(1, 100);
		map2.put(2, 500);
		map2.put(3, 600);
		map2.put(4, 100);
		TreeMap<Integer, Integer> map3 = new TreeMap<>();
		map3.put(1, 100);
		map3.put(2, 200);
		map3.put(3, 800);
		map3.put(4, 1000);

		User usr1 = new User("Dave", "Filoni");
		User usr2 = new User("John", "Favreau");
		User usr3 = new User("Pedro", "Pascal");

		users = new ArrayList<>();
		users.add(usr1.getEmail());
		users.add(usr2.getEmail());
		users.add(usr3.getEmail());

		turnCurrencyPerUser.put(usr1.getEmail(), map1);
		turnCurrencyPerUser.put(usr2.getEmail(), map2);
		turnCurrencyPerUser.put(usr3.getEmail(), map3);

		return turnCurrencyPerUser;
	}

	private Map<String, TreeMap<Integer, Integer>> loadDataExampe2(){
		Map<String, TreeMap<Integer, Integer>> turnCurrencyPerUser = new HashMap<>();
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
		
		users = new ArrayList<>();
		users.add(usr1.getEmail());
		users.add(usr2.getEmail());
		users.add(usr3.getEmail());

		turnCurrencyPerUser.put(usr1.getEmail(), map1);
		turnCurrencyPerUser.put(usr2.getEmail(), map2);
		turnCurrencyPerUser.put(usr3.getEmail(), map3);

		return turnCurrencyPerUser;
	}
}
