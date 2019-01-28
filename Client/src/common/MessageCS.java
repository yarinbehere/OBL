package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Subscriber;
import entity.User;

public class MessageCS implements Serializable {

	/* MessageType
	 * enum that differ between screens
	 */
	public enum MessageType {
		LOGIN, SEARCH_A_BOOK,SEARCH_SUBSCRIBER;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	User user;
	private Subscriber subscriber;
	private ArrayList<Subscriber> arrSubscriber=null;

	/**
	 * user wants to login to the system
	 * @param messageType - messageType will be login
	 * @param user 
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, User user)
	{
		this.user = user;
		this.messageType = messageType;
	}
	
	/**
	 * librarian want to search a subscriber
	 * @param messageType - messageType will be SEARCH_SUBSCRIBER
	 * @param subscriber 
	 * @author Omri
	 */
	public MessageCS(MessageType messageType, Subscriber subscriber)
	{
		this.subscriber=subscriber;
		this.messageType=messageType;
	}

	/**
	 * @return the subscriber
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber the subscriber to set
	 */
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * @return the arrSubscriber
	 */
	public ArrayList<Subscriber> getArrSubscriber() {
		return arrSubscriber;
	}

	/**
	 * @param arrSubscriber the arrSubscriber to set
	 */
	public void setArrSubscriber(ArrayList<Subscriber> arrSubscriber) {
		this.arrSubscriber = arrSubscriber;
	}
	

}
