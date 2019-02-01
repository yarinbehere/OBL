/**
 * 
 */
package entity;

import java.io.Serializable;

/**
 * @author omri braymok
 *
 */
public class Librarian implements Serializable {
	
	private String workerNumber;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String role;
	private String phoneNumber;
	
	/**
	 * @param workerNumber
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param role
	 * @param phoneNumber
	 */
	public Librarian(String workerNumber, String userName, String firstName, String lastName, String email, String role,
			String phoneNumber) {
		this.workerNumber = workerNumber;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return the workerNumber
	 */
	public String getWorkerNumber() {
		return workerNumber;
	}
	/**
	 * @param workerNumber the workerNumber to set
	 */
	public void setWorkerNumber(String workerNumber) {
		this.workerNumber = workerNumber;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * @return the role
	 */
	public String getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


}
