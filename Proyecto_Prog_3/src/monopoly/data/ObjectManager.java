package monopoly.data;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import monopoly.objects.Match;
import monopoly.objects.User;
/**
 * Designed to handle any kind data of type T, similar to a HashSet
 * @author KaRLiToS3.0
 */
public class ObjectManager <T> implements Iterable<T>, Serializable{
	private static final long serialVersionUID = 2488139304577784175L;
	
	private Set<T> registeredData = new HashSet<>();
	
	public T getObjectByKey(String key) {
		for(T obj : registeredData) {
			if(obj instanceof User) {
				User usr = (User) obj;
				if(usr.getEmail().equals(key)) return obj;
			} else if (obj instanceof Match) {
				Match match = (Match) obj;
				if(match.getDateAsString().equals(key)) return obj;
			}
		}
		return null;
	}

	public void addObject(T object){
		if (!registeredData.add(object));
	}
	
	public void removeObject(T object){
		if(!registeredData.remove(object));
	}
	
	public boolean containsObject(T object) {
		return registeredData.contains(object);
	}
	
	public void addDataCollection(Collection <? extends T> dataCollection) {
		registeredData.addAll(dataCollection);
	}
	
	public void addObjectManager(ObjectManager<T> dataCollection) {
		registeredData.addAll(dataCollection.getRegisteredData());
	}
	
	public boolean isEmpty() {
		return registeredData.isEmpty();
	}
	
	public int size() {
		return registeredData.size();
	}

	@Override
	public Iterator<T> iterator() {
		return registeredData.iterator();
	}

	public Set<T> getRegisteredData() {
		return registeredData;
	}
	
	public Set<T> setRegisteredData(Set<T> set) {
		return set;
	}
}
