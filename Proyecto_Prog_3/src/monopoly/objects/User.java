package monopoly.objects;

public class User{
	private static final long serialVersionUID = 1L;
	String Alias;
	String Name;
	String Email; //ID
	String Password;
	
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
		setAlias(alias);
		setName(name);
		setEmail(email);
		setPassword(password);
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
