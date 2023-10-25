package monopoly.objects;

public class User {
	String Alias;
	String Name;
	String Email;
	String Password;
	
	public User() {
		setAlias("");
		setName("");
		setEmail("");
		setPassword("");
	}
	
	public User(String name) {
		this.Name = name;
	}
	
	public User(String alias,String name,String email, String password) {
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

	public boolean equals(User user) {
		boolean equal;
		if (this.getEmail().equals(user.getEmail()) || this.getAlias().equals(user.getAlias())) {
			equal = true;
		} else {
			equal = false;
		}
		return equal;
	}
	
	
}
