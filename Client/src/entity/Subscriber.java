
package entity;

import java.io.Serializable;

public class Subscriber implements Serializable  {
	
	private String userName;
	private String password;
	private String mobileNumber;
	private String email;
	private String subscriberStatus;
	private String subscriberDetails;
	private String subscriberID;
	private String firstName;
	private String lastName;

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
	public Subscriber(String subscriberID, String userName, String firstName, String lastName, String mobileNumber, String email, String password) {
		this.setUserName(userName);
		this.setPassword(password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.subscriberID = subscriberID;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}
	
	public Subscriber(String subscriberNumber,String userName,String firstName,String lastName,String mobileNumber,String email,String subscriberStatus,String subscriberDetails )
	{
		this.setSubscriberID(subscriberNumber);
		this.setUserName(userName);
		this.firstName=firstName;
		this.lastName=lastName;
		this.setMobileNumber(mobileNumber);
		this.email=email;
		this.subscriberStatus=subscriberStatus;
		this.setSubscriberDetails(subscriberDetails);
	}
	public Subscriber(String subscriberNumber,String mobileNumber,String email,String subscriberStatus)
	{
		
		this.setSubscriberID(subscriberNumber);
		this.setMobileNumber(mobileNumber);
		this.email=email;
		this.subscriberStatus=subscriberStatus;
	}

	public Subscriber(String subscriberDetails)
	{
		this.setSubscriberDetails(subscriberDetails);
	}
	
	public Subscriber(String subscriberID, String subscriberStatus) 
	{ 
		this.setSubscriberID(subscriberID);
		this.subscriberStatus = subscriberStatus;
	}
	
	/**find and update the user and subscriber details
	 * @author Omri Braymok
	 * @param subscriberID
	 * @param firstName
	 * @param lastName
	 * @param mobileNumber
	 * @param email
	 * @param subscriberStatus
	 */
	public Subscriber(String subscriberID, String firstName, String lastName, String mobileNumber,
			String email, String subscriberStatus) {
		this.subscriberID=subscriberID;
		this.firstName=firstName;
		this.lastName=lastName;
		this.mobileNumber=mobileNumber;
		this.email=email;
		this.subscriberStatus=subscriberStatus;
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
		return subscriberID;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String subscriberID) {
		this.subscriberID = subscriberID;
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
		return mobileNumber;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	public String getSubscriberDetails() {
		return subscriberDetails;
	}

	public void setSubscriberDetails(String subscriberDetails) {
		this.subscriberDetails = subscriberDetails;
	}

	public String getSubscriberID() {
		return subscriberID;
	}

	public void setSubscriberID(String subscriberID) {
		this.subscriberID = subscriberID;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
