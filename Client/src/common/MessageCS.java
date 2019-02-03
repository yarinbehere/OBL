package common;

import java.io.Serializable;
import java.util.ArrayList;

import entity.Book;
import entity.BookOrder;
import entity.BorrowedBook;
import entity.BorrowsExt;
import entity.FileTransfer;
import entity.Librarian;
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
		REVIEW_SUBSCRIBER_UPDATE,SEARCH_SUBSCRIBER,SEARCH_BOOK_FOR_BORROW,BORROW,BORROW1,
		SEARCH_BOOK_FOR_UPDATE_BOOK, UPDATE_BOOK, PERSONAL_INFORMATION,
		PERSONAL_INFORMATION_RESULT, UPDATE_PERSONAL_INFORMATION, SEARCH_BOOK_FOR_ORDER, LIST_OF_ORDERS,
		ACTIVITY_LOG,SEARCH_ALL_FOR_VIEW_DATABASE, REQUEST_EXTENSION_CHECK, REQUEST_EXTENSION_INIT,
		ERROR_MESSAGE, SEARCH_SUBSCRIBER_FOR_OPTIONS, SEARCH_BOOK_FOR_RETURN, ORDER_BOOK, RETURN_BOOK, CANCEL_ORDER, 
		LATE_RETURNS; 
	}

	MessageType messageType;

	/* Entities */
	private User user;
	private Book book;
	private FileTransfer tableOfContent;
	private BorrowedBook borrowedbook;
	private Subscriber subscriber;
	private ArrayList<Subscriber> subscribers;
	private ArrayList<Librarian>Librarians;
	private String textMessage;
	private ArrayList<BorrowsExt> usersBorrows;
	private ArrayList<?> arrayList;
	private BookOrder bookOrder;


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
	 * 1st scenario: subscriber wants to watch in his activity log
	 * 2nd scenario: user wants to search for books
	 * @param messageType - messageType will be activityLog
	 * @param subscriber 
	 * @author Hai
	 * @author Roman
	 */
	public MessageCS(MessageType messageType, ArrayList<?> arrayList)
	{
		this.messageType = messageType;
		this.setArrayList(arrayList);
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

	/**constructor for the table of content that we would like to open
	 * @param tableOfContent saving the file we want to open
	 * @author Roman
	 */

	public MessageCS(MessageType messageType, FileTransfer tableOfContent)
	{
		this.messageType = messageType;
		this.setTableOfContent(tableOfContent);
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
		this.setBorrowedbook(borrowedbook);
	}

	/**constructor for the update Personal Information
	 * @param messageType
	 * @param user
	 * @param subscriber
	 * @author Omri Braymok
	 */
	public MessageCS(MessageType messageType, User user, Subscriber subscriber) {
		this.messageType = messageType;
		this.setUser(user);
		this.setSubscriber(subscriber);	
	}

	/**Constructor for find result to ViewDatabase in DB 
	 * @param textMessage
	 * @param subscribers
	 * @param librarians
	 * @author Omri Braymok
	 */
	public MessageCS(MessageType messageType, ArrayList<Subscriber> subscribers,ArrayList<Librarian> librarians) {
		this.messageType = messageType;
		this.setSubscribers(subscribers);
		this.setLibrarians(librarians);
	}

	/**Constructor for find result to ViewDatabase in DB 
	 * @param textMessage
	 * @author Omri Braymok
	 */
	public MessageCS(MessageType messageType) {
		this.messageType = messageType;
	}

	public MessageCS(MessageType messageType, BookOrder bookOrder) 
	{
		this.messageType = messageType;
		this.setBookOrder(bookOrder);
	}
	public MessageCS(MessageType messageType, Subscriber subscriber, Book book) {
		this.messageType = messageType;
		this.subscriber = subscriber;
		this.book = book;
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

	public FileTransfer getTableOfContent() {
		return tableOfContent;
	}
	public void setTableOfContent(FileTransfer tableOfContent) {
		this.tableOfContent = tableOfContent;
	}

	/**
	 * @param textMessage
	 */
	public MessageCS(MessageType messageType, String textMessage) {
		this.messageType = messageType;
		this.setTextMessage(textMessage);
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public BorrowedBook getBorrowedbook() {
		return borrowedbook;
	}
	public void setBorrowedbook(BorrowedBook borrowedbook) {
		this.borrowedbook = borrowedbook;
	}
	public ArrayList<?> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<?> arrayList) {
		this.arrayList = arrayList;
	}
	/**
	 * @return the librarians
	 */
	public ArrayList<Librarian> getLibrarians() {
		return Librarians;
	}
	/**
	 * @param librarians the librarians to set
	 */
	public void setLibrarians(ArrayList<Librarian> librarians) {
		Librarians = librarians;
	}
	/**
	 * @return the subscribers
	 */
	public ArrayList<Subscriber> getSubscribers() {
		return subscribers;
	}
	/**
	 * @param subscribers the subscribers to set
	 */
	public void setSubscribers(ArrayList<Subscriber> subscribers) {
		this.subscribers = subscribers;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
	/**
	 * @return the usersBorrows
	 */
	public ArrayList<BorrowsExt> getUsersBorrows() {
		return usersBorrows;
	}
	/**
	 * @param usersBorrows the usersBorrows to set
	 */
	public void setUsersBorrows(ArrayList<BorrowsExt> usersBorrows) {
		this.usersBorrows = usersBorrows;
	}
	public BookOrder getBookOrder() {
		return bookOrder;
	}
	public void setBookOrder(BookOrder bookOrder) {
		this.bookOrder = bookOrder;
	}
}
