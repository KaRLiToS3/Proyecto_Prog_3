package monopoly.data;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ObjectManager <T> {
	private Set<T> registeredData = new HashSet<>();

	public void addObject(T object) throws InvalidParameterException{
		if (!registeredData.add(object)) throw new InvalidParameterException("This object was already added");
	}
	
	public void removeObject(T object) throws InvalidParameterException{
		if(!registeredData.remove(object)) throw new InvalidParameterException("This object doesn't exist");
	}
	
	public boolean containsObject(T object) {
		return registeredData.contains(object);
	}
	
	public void addDataCollection(Collection<T> dataCollection) {
		registeredData.addAll(dataCollection);
	}
}
