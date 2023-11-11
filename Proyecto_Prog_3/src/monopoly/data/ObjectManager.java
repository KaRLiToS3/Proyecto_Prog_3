package monopoly.data;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ObjectManager <T> implements Iterable<T>, Serializable{
	private static final long serialVersionUID = 2488139304577784175L;
	
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
	
	@Override
	public Iterator<T> iterator() {
		return registeredData.iterator();
	}
}
