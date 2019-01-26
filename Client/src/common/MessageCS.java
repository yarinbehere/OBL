package common;

import java.io.Serializable;


import entity.Book;
import entity.Subscriber;
import entity.User;

public class MessageCS implements Serializable {

	/**
	 * MessageType
	 * enum that differ between screens
	 * @author rami
	 */
	public enum MessageType {
		LOGIN,SEARCH_BOOK,SEARCH_SUBSCRIBER,ORDER_BOOK,SEARCH_BOOK_FOR_OPTIONS;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	User user;
	Book book;
	Subscriber subscriber;


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
	/**
	 * subscriber wants to borrow a book
	 * @param messageType - messageType will be borrow book
	 * @param subscriber 
	 * @author Hai
	 */
	public MessageCS(MessageType messageType, Subscriber subscriber)
	{
		this.messageType = messageType;
		this.subscriber = subscriber;
	}
	
}
