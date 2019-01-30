/**
 * 
 */
package entity;

/**
 * @author shalev
 *
 */
public class Subscriber extends User {
	/** a field for serializable */
	private static final long serialVersionUID = 1L;
	String userName;
	String password;
	String firstName;
	String lastName;
	String id;
	String email;
	String phone;
	String subscriberStatus;

	/**
	 * @param userName
	 * @param password
	 * @param userName2
	 * @param password2
	 * @param firstName
	 * @param lastName
	 * @param id
	 * @param email
	 * @param phone
	 */
	public Subscriber(String id, String userName, String firstName, String lastName, String phone, String email, String password) {
		super(userName, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id;
		this.email = email;
		this.phone = phone;
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
	 * @return the subscriberStatus
	 */
	public String getSubscriberStatus() {
		return subscriberStatus;
	}

	/**
	 * @param subscriberStatus the subscriberStatus to set
	 */
	public void setSubscriberStatus(String subscriberStatus) {
		this.subscriberStatus = subscriberStatus;
	}

}
