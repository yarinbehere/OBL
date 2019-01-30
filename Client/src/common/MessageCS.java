package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Book;
import entity.FileTransfer;
import entity.Subscriber;
import entity.User;

public class MessageCS implements Serializable {

	/**
	 * MessageType
	 * enum that differ between screens
	 * @author Roman
	 */
	public enum MessageType {
		LOGIN, SEARCH_BOOK, TABLE_OF_CONTENT, CREATE_SUBSCRIBER, REVIEW_SUBSCRIBER_SEARCH, 
		REVIEW_SUBSCRIBER_UPDATE;
	}

	MessageType messageType;

	/* Entities */
	private User user;
	private Book book;
	private ArrayList<Book> books;
	private FileTransfer tableOfContent;
	Subscriber subscriber;
	String textMessage;


	/**
	 * user wants to login to the system
	 * 
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
	 * user wants to search for a book, a specification of the book he desired to search for
	 * @param messageType will be search book
	 * @param book the book wishing to look for
	 * @author Roman
	 */
	
	public MessageCS(MessageType messageType, Book book)
	{
		this.messageType = messageType;
		this.setBook(book);
	}
	
	/**
	 * returning search result.. a list of books that have been searched
	 * @param messageType
	 * @param books 
	 * @author Roman
	 */
	
	public MessageCS(MessageType messageType, ArrayList<Book> books)
	{
		this.messageType = messageType;
		this.setBooks(books);
	}

	/**constructor for the table of content that we would like to open
	 * @param tableOfContent saving the file we want to open
	 * @author Roman
	 */
	
	public MessageCS(MessageType messageType, FileTransfer tableOfContent)
	{
		this.messageType = messageType;
		this.setTableOfContent(tableOfContent);
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public ArrayList<Book> getBooks() {
		return books;
	}
	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}
	public FileTransfer getTableOfContent() {
		return tableOfContent;
	}
	public void setTableOfContent(FileTransfer tableOfContent) {
		this.tableOfContent = tableOfContent;
	}
	/**
	 * @param subscriber
	 */
	public MessageCS(MessageType messageType, Subscriber subscriber) {
		this.messageType = messageType;
		this.subscriber = subscriber;
	}

	/**
	 * @param textMessage
	 */
	public MessageCS(MessageType messageType, String textMessage) {
		this.messageType = messageType;
		this.textMessage = textMessage;
	}
}
