package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Book;
import entity.BookOrder;
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
		LOGIN, SEARCH_BOOK,TABLE_OF_CONTENT,SEARCH_BOOK_FOR_ORDER, SEARCH_BOOK_FOR_BORROW,
		SEARCH_SUBSCRIBER,LIST_OF_ORDERS,CHECK_AVAILABLE_ORDER,ORDER_A_BOOK, 
		ERROR_MESSAGE, SEARCH_SUBSCRIBER_FOR_OPTIONS; 
	}
	
	
	private MessageType messageType; 
	
	/* Entities */
	private User user;
	private Book book;
	private ArrayList<Book> books;
	private FileTransfer tableOfContent;
	private Subscriber subscriber;
	private String Error;
	private BookOrder bookOrder;
	

	/**
	 * user wants to login to the system
	 * @param messageType - messageType will be login
	 * @param user 
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, User user)
	{
		this.setMessageType(messageType);
		this.setUser(user);
	}
	/**
	 * user wants to search for a book, a specification of the book he desired to search for
	 * @param messageType will be search book
	 * @param book the book wishing to look for
	 * @author Roman
	 */
	
	public MessageCS(MessageType messageType, Book book)
	{
		this.setMessageType(messageType);
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
		this.setMessageType(messageType);
		this.setBooks(books);
	}

	/**constructor for the table of content that we would like to open
	 * @param tableOfContent saving the file we want to open
	 * @author Roman
	 */
	
	public MessageCS(MessageType messageType, FileTransfer tableOfContent)
	{
		this.setMessageType(messageType);
		this.setTableOfContent(tableOfContent);
	}
	
	
	public MessageCS(MessageType messageType, Subscriber subscriber) {
		this.setMessageType(messageType);
		this.setSubscriber(subscriber);	
	}
	/**
	 * Will return a message error
	 * @param checkAvailableOrder
	 * @param Error
	 */
	public MessageCS(MessageType messageType, String Error) {
		this.setMessageType(messageType);
		this.setError(Error);
	}
	public MessageCS(MessageType messageType, BookOrder bookOrder) {
		this.setMessageType(messageType);
		this.setBookOrder(bookOrder);
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
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public String getError() {
		return Error;
	}
	public void setError(String error) {
		Error = error;
	}
	public BookOrder getBookOrder() {
		return bookOrder;
	}
	public void setBookOrder(BookOrder bookOrder) {
		this.bookOrder = bookOrder;
	}
	public MessageType getMessageType() {
		return messageType;
	}
	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
