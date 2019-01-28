/**
 * 
 */
package entity;

import java.io.Serializable;

/**
 * @author omri braymok
 *
 */
public class Subscriber implements Serializable{

	private String username;
	private String id;
	private String firstname;
	private String email;
	private String phone;
	private String lastname;
	private String status;
	
	/**
	 * @param username
	 * @param id
	 * @param firstname
	 * @param email
	 * @param phone
	 * @param lastname
	 * @param status 
	 */
	public Subscriber(String username, String id, String firstname, String email, String phone,
			String lastname, String status) {
		this.username = username;
		this.id = id;
		this.firstname = firstname;
		this.email = email;
		this.phone = phone;
		this.lastname = lastname;
		this.status=status;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}





}
