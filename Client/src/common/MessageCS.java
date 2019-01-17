package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.User;

public class MessageCS implements Serializable {

	/* MessageType
	 * enum that differ between screens
	 */
	public enum MessageType {
		LOGIN;
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
