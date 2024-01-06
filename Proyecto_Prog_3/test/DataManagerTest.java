import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class DataManagerTest {
	
	@Before
	public void createDataManager() {
		List<User> exampleList = new ArrayList<>();
		exampleList.add(new User());
		exampleList.add(new User("name1", "mail1"));
	}
	
	@Test
	public void getUsers() {
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User()));
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name1", "mail1")));
	}

	@Test
	public void addAndRemoveUsers(){
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name2", "mail2", "password2", "alias2")));
		
	}
}
