package common;

import java.io.Serializable;


import entity.Book;
import entity.User;

public class MessageCS implements Serializable {

	/**
	 * MessageType
	 * enum that differ between screens
	 * @author rami
	 */
	public enum MessageType {

		LOGIN,SEARCH_BOOK,ORDER_BOOK;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	User user;
	Book book;


	/**
	 * user wants to login to the system
	 * @param messageType - messageType will be login
	 * @param user 
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, User user)
	{
		this.messageType = messageType;
		this.user = user;
	}

	/**
	 * user wants to search for a book
	 * @param messageType will be search book
	 * @param book the book wishing to look for
	 */
	
	public MessageCS(MessageType messageType, Book book)
	{
		this.messageType = messageType;
		this.book = book;
	}

}
