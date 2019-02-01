package entity;

import java.io.Serializable;

public class Subscriber implements Serializable 
{
	private String userName;
	private String password;
	private String mobileNumber;
	private String email;
	private String subscriberStatus;
	private String subscriberDetails;
	private String subscriberID;
	private String firstName;
	private String lastName;
	
	public Subscriber(String subscriberNumber,String userName,String firstName,String lastName,String mobileNumber,String email,String subscriberStatus,String subscriberDetails )
	{
		this.subscriberID=subscriberNumber;
		this.userName=userName;
		this.firstName=firstName;
		this.lastName=lastName;
		this.mobileNumber=mobileNumber;
		this.email=email;
		this.subscriberStatus=subscriberStatus;
		this.subscriberDetails=subscriberDetails;
	}
	public Subscriber(String subscriberNumber,String mobileNumber,String email,String subscriberStatus)
	{
		
		this.subscriberID=subscriberNumber;
		this.mobileNumber=mobileNumber;
		this.email=email;
		this.subscriberStatus=subscriberStatus;
	}

	public Subscriber(String subscriberDetails)
	{
		this.setSubscriberDetails(subscriberDetails);
	}
	
	public Subscriber(String subscriberID, String subscriberStatus) 
	{ 
		this.subscriberID = subscriberID;
		this.subscriberStatus = subscriberStatus;
	}
	
	//set function


	public void setUserName(String userName) 
	{
		
		this.userName = userName;
	}
	public void setMobileNumber(String mobileNumber) 
	{
		
		this.mobileNumber = mobileNumber;
	}
	public void setEmail(String email) 
	{
		
		this.email = email;
	}
	public void setSubscriberStatus(String subscriberStatus) 
	{
		
		this.subscriberStatus = subscriberStatus;
	}
	
	//get function 
	
	public String getUserName() 
	{
		
		return userName;
	}
	public String getMobileNumber() 
	{
		
		return mobileNumber;
	}
	public String getEmail() 
	{
		
		return email;
	}
	public String getSubscriberStatus() 
	{
		
		return subscriberStatus;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
