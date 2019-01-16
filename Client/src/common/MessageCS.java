package common;

import java.io.Serializable;
import java.util.ArrayList;

import common.user.entity.User;

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
	 * @author roman
	 * user wants to login to the system
	 * @param messageType - messageType will be login
	 * @param user 
	 */
	public MessageCS(MessageType messageType, User user)
	{
		this.user = user;
		this.messageType = messageType;
	}

	

}
