import static org.junit.Assert.*;

import java.awt.Window.Type;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import monopoly.objects.Achievement;
import monopoly.objects.User;

public class UserTest {

	@Test
	public void test() {
		User[] users = {new User(), new User("name", "email 1"), new User("name", "email2", "password", "alias")};
		
		for (int i = 0; i < users.length; i++) {
			assertEquals("name", users[i].getName());
			assertEquals("alias", users[i].getAlias());
			assertEquals("password", users[i].getPassword());
			if(i > 0) assertNotEquals(users[i-1].getEmail(), users[i].getEmail());
			
			users[i].setName("testName");
			users[i].setAlias("testAlias");
			users[i].setEmail("testEmail");
			users[i].setPassword("testPassword");
			
			assertEquals("testName", users[i].getName());
			assertEquals("testAlias", users[i].getAlias());
			assertEquals("testPassword", users[i].getPassword());
			assertEquals("testEmail", users[i].getEmail());
		}
	}
}
