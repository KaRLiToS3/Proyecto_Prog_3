package monopoly.objects;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ObjectManager {
	private static Set<User> registeredUsers = new HashSet<>();
	private static Set<Match> registeredMatches = new HashSet<>();
	
	public static void addUser(User usr) throws InvalidParameterException{
		if (!registeredUsers.add(usr)) throw new InvalidParameterException();
	}
	
	public static void addMatch(Match match) throws InvalidParameterException{
		if (!registeredMatches.add(match)) throw new InvalidParameterException();
	}
}
