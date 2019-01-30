package entity;

import java.io.Serializable;

public class User implements Serializable{
	
	public enum Role{
		SUBSCRIBER,LIBRARIAN,MANAGER;
	}
	private String userName;
	private String password;
	private Role role;
	

	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public void setPassword(String password) 
	{
		
		this.password = password;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public String getUserName() 
	{
		return userName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
