package common;

import java.io.Serializable;

import entity.Subscriber;
import entity.User;

public class MessageCS implements Serializable {

// just to satisfy warning problem
	private static final long serialVersionUID = 1L;

	/*
	 * MessageType enum that differ between screens
	 */
	public enum MessageType {
		LOGIN, CREATE_SUBSCRIBER;
	}

	MessageType messageType;

	/* Entities */
	User user;
	Subscriber subscriber;

	/**
	 * user wants to login to the system
	 * 
	 * @param messageType - messageType will be login
	 * @param user
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, User user) {
		this.user = user;
		this.messageType = messageType;
	}

	/**
	 * @param subscriber
	 */
	public MessageCS(MessageType messageType,Subscriber subscriber) {
		this.subscriber = subscriber;
		this.messageType=messageType;
	}

}
