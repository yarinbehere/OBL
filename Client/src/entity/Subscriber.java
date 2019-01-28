package entity;

import java.io.Serializable;

public class Subscriber implements Serializable 
{
	private String userName;
	private String password;
	private String subscriberNumber;
	private String mobileNumber;
	private String email;
	private String subscriberStatus;
	private String subscriberDetails;
	private String subscriberID;
	//borrow history
	//Late return
	//Losing a book
	
	public Subscriber(String subscriberNumber,String mobileNumber,String email,String subscriberStatus)
	{
		
		this.subscriberNumber=subscriberNumber;
		this.mobileNumber=mobileNumber;
		this.email=email;
		this.subscriberStatus=subscriberStatus;
	}

	public Subscriber(String subscriberDetails) 
	{
		this.subscriberDetails = subscriberDetails;
	}
	
	public Subscriber(String subscriberID, String subscriberStatus) 
	{ 
		this.subscriberID = subscriberID;
		this.subscriberStatus = subscriberStatus;
	}
	
	//set function

	

	public void setSubscriberNumber(String subscriberNumber) 
	{
		
		this.subscriberNumber = subscriberNumber;
	}
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
	
	public String getSubscriberNumber() 
	{
		
		return subscriberNumber;
	}
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
	
}