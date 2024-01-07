package monopoly.objects;


import java.io.File;
import java.io.Serializable;
import java.util.Set;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String Alias;
	private String Name;
	private String Email; //ID
	private String Password;
	private File Image;
	private Set<Achievement> achievements;
//	private final String path1 = "/monopoly/data/UserFile.dat";
//	private final URL UserURL = getClass().getResource(path1);

	public User() {
		setAlias("alias");
		setName("name");
		setEmail("email");
		setPassword("password");
	}

	public User(String name, String email) {
		this.Name = name;
		this.Email = email;
		this.Alias = "alias";
		this.Password = "password";
	}

	public User(String name,String email, String password, String alias) {
		this(name, email);
		this.Alias = alias;
		this.Email = email;
		this.Password = password;
	}

	public User(String name,String email, String password, String alias, File ImageUser) {
		this(name, email, password, alias);
		this.Image = ImageUser;
	}

	public User(String name,String email, String password, String alias, Set<Achievement> achievements) {
		this(name, email, password, alias);
		this.achievements = achievements;
	}

	public User(String name,String email, String password, String alias, File ImageUser, Set<Achievement> achievements) {
		this(name, email, password, alias, ImageUser);
		this.achievements = achievements;
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

	public Set<Achievement> getAchievements() {
		return achievements;
	}

	public void setAchievements(Set<Achievement> achievements) {
		this.achievements = achievements;
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

}
