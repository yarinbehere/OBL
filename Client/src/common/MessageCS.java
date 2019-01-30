package common;

import java.io.Serializable;


import entity.Book;
import entity.BorrowedBook;
import entity.Subscriber;
import entity.User;

public class MessageCS implements Serializable {

	/**
	 * MessageType
	 * enum that differ between screens
	 * @author rami
	 */
	public enum MessageType {
		LOGIN,SEARCH_BOOK,SEARCH_SUBSCRIBER,ORDER_BOOK,SEARCH_BOOK_FOR_BORROW,BORROW,BORROW1;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	private User user;
	private Book book;
	private Subscriber subscriber;
	private BorrowedBook borrowedbook;


	/**
	 * user wants to login to the system
	 * @param messageType - messageType will be login
	 * @param user 
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, User user)
	{
		this.messageType = messageType;
		this.setUser(user);
	}

	/**
	 * user wants to search for a book
	 * @param messageType will be search book
	 * @param book the book wishing to look for
	 */
	
	public MessageCS(MessageType messageType, Book book)
	{
		this.messageType = messageType;
		this.setBook(book);
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
		this.setSubscriber(subscriber);
	}
	/**
	 * subscriber wants to borrow a book
	 * @param messageType - messageType will be borrow book
	 * @param subscriber 
	 * @author Hai
	 */
	public MessageCS(MessageType messageType, BorrowedBook borrowedbook)
	{
		this.messageType = messageType;
		this.setBorrowedBook(borrowedbook);
	}

	public BorrowedBook getBorrowedBook() {
		return borrowedbook;
	}
	public void setBorrowedBook(BorrowedBook borrowedbook) {
		this.borrowedbook = borrowedbook;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
