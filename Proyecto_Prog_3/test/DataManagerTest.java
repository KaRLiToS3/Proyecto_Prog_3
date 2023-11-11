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
		DataManager.getManager().getRegisteredUsers().addDataCollection(exampleList);
	}
	
	@Test
	public void getUsers() {
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User()));
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name1", "mail1")));
	}

	@Test
	public void addAndRemoveUsers(){
		DataManager.getManager().getRegisteredUsers().addObject(new User("name2", "mail2", "password2", "alias2"));
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name2", "mail2", "password2", "alias2")));
		DataManager.getManager().getRegisteredUsers().removeObject(new User("name2", "mail2", "password2", "alias2"));
		
		assertThrows(InvalidParameterException.class, () -> DataManager.getManager().getRegisteredUsers().addObject(new User()));
	}
}
