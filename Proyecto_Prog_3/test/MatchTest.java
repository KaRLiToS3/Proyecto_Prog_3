import static org.junit.Assert.*;

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
	private List<User>users;

	@Before
	public void loadDataStructure() {
		Map<User, TreeMap<Integer, Integer>> data = loadDataExampe1();
		match1 = new Match(new Date(),"match1", data);
		match2 = new Match();
	}
	
	@Test
	public void test() {
		assertEquals("match1", match1.getName());
		assertEquals(users, match1.getUsers());
		assertEquals(loadDataExampe1(), match1.getTurnCurrencyPerUser());
		assertNotEquals(match1, match2);
		
		assertEquals("Test Match", match2.getName());
		assertEquals(users, match2.getUsers());
		assertEquals(loadDataExampe2(), match2.getTurnCurrencyPerUser());
	}

	private Map<User, TreeMap<Integer, Integer>> loadDataExampe1(){
		Map<User, TreeMap<Integer, Integer>> turnCurrencyPerUser = new HashMap<User, TreeMap<Integer, Integer>>();
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
		users.add(usr1);
		users.add(usr2);
		users.add(usr3);
		
		turnCurrencyPerUser.put(usr1, map1);
		turnCurrencyPerUser.put(usr2, map2);
		turnCurrencyPerUser.put(usr3, map3);
		
		return turnCurrencyPerUser;
	}
	
	private Map<User, TreeMap<Integer, Integer>> loadDataExampe2(){
		Map<User, TreeMap<Integer, Integer>> turnCurrencyPerUser = new HashMap<User, TreeMap<Integer, Integer>>();
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
		
		User usr1 = new User("Paco", "dvkajenlkaenñfa");
		User usr2 = new User("Juan", "thaehaerhaerhaeha");
		User usr3 = new User("Damian", "rgagaerhaehaerh");
		
		users = new ArrayList<>();
		users.add(usr1);
		users.add(usr2);
		users.add(usr3);
		
		turnCurrencyPerUser.put(usr1, map1);
		turnCurrencyPerUser.put(usr2, map2);
		turnCurrencyPerUser.put(usr3, map3);
		
		return turnCurrencyPerUser;
	}
}
