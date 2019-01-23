package common;

import java.io.Serializable;
import entity.User;

public class MessageCS implements Serializable {

	/* MessageType
	 * enum that differ between screens
	 */
	public enum MessageType {
		LOGIN, SEARCH_A_BOOK, CREATE_SUBSCRIBER;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	User user;

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

	

}
