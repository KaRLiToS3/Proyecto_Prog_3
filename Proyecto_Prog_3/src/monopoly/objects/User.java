package monopoly.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String Alias;
	String Name;
	String Email; //ID
	String Password;
	File Image;
	private final String path1 = "data/UserFile.dat";
	public User() {
		setAlias("");
		setName("");
		setEmail("");
		setPassword("");
	}
	
	public User(String name, String email) {
		this.Name = name;
		this.Email = email;
	}
	
	public User(String alias,String name,String email, String password, File image) {
		setAlias(alias);
		setName(name);
		setEmail(email);
		setPassword(password);
		setImage(image);
	}

	public String getAlias() {
		return Alias;
	}

	public void setAlias(String alias) {
		Alias = alias;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}
	

	public File getImage() {
		return Image;
	}

	public void setImage(File image) {
		Image = image;
	}

	@Override
	public String toString() {
		return "User alias: " + Alias + " - " + "User name: " + Name;
	}

	@Override
	public boolean equals(Object user) {
		if(user instanceof User) {
			User us = (User) user;
			return this.getEmail().equals(us.getEmail()) || this.getAlias().equals(us.getAlias());
		}else return false;
	}

	@Override
	public int hashCode() {
		return this.getEmail().hashCode();
	}
	
	/**
	 * Save User method
	 * @return ArrayList<User>
	 */
	public void saveUser() {
		ArrayList<User> UserList = loadUsers();
		UserList.add(User.this);
		try {
			ObjectOutputStream forFile = new ObjectOutputStream(new FileOutputStream(path1));
			forFile.writeObject(UserList);
			System.out.println("New User saved");
			forFile.close();
		} catch (IOException e) {
			System.err.println("The address to add the file was not found");
			e.printStackTrace();
		}	
	}
	
	/**
	 * User loading method
	 * @return ArrayList<User>
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<User> loadUsers(){
		try (ObjectInputStream UsersInput = new ObjectInputStream(new FileInputStream(path1))) {
			return (ArrayList<User>) UsersInput.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("File for load users not found");
			e.printStackTrace();
		} catch (IOException e) {
			//System.err.println("User loading failed");
			//e.printStackTrace();
			System.out.println("No user registered yet");
		} catch (ClassNotFoundException e) {
			System.err.println("Incorrect cast to User");
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
