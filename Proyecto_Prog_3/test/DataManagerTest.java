import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import monopoly.data.DataManager;
import monopoly.objects.User;

public class DataManagerTest {

	@Before
	public void createDataManager() {
		List<User> exampleList = new ArrayList<>();
		exampleList.add(new User());
		exampleList.add(new User("name1", "mail1"));
		for (User user: exampleList) {
			DataManager.getManager().saveUser(user);
		}
	}
	
	@Test
	public void addAndRemoveUsers(){
		DataManager.getManager().saveUser(new User("name2", "mail2", "password2", "alias2"));
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name2", "mail2", "password2", "alias2")));
		DataManager.getManager().deleteUser(new User("name2", "mail2", "password2", "alias2"));
		assertTrue(!DataManager.getManager().getRegisteredUsers().containsObject(new User("name2", "mail2", "password2", "alias2")));
	}

	@Test
	public void getUsers() {
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User()));
		assertTrue(DataManager.getManager().getRegisteredUsers().containsObject(new User("name1", "mail1")));
	}
}
