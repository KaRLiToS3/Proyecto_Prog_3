package monopoly.data;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/**
 * Designed to handle any kind data of type T, similar to a HashSet
 * @author KaRLiToS3.0
 */
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
	
	public boolean isEmpty() {
		return registeredData.isEmpty();
	}
	
	public int size() {
		return registeredData.size();
	}
	
	public Set<T> getRegisteredData() {
		return registeredData;
	}

	@Override
	public Iterator<T> iterator() {
		return registeredData.iterator();
	}
}
