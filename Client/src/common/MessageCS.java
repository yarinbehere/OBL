package common;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

import entity.Book;
import entity.FileTransfer;
import entity.GenerateReport;
import entity.User;

public class MessageCS implements Serializable {

	/**
	 * MessageType
	 * enum that differ between screens
	 * @author Roman
	 */
	public enum MessageType {
		LOGIN, SEARCH_BOOK,TABLE_OF_CONTENT,SEARCH_BOOK_FOR_ADDNEWBOOK,
		ADD_NEW_BOOK,SEARCH_BOOK_FOR_DELETEBOOK,DELETE_BOOK,UPLOAD_NEW_PDF, ACTIVITY_REPORT;
	}
	
	
	MessageType messageType; 
	
	/* Entities */
	private User user;
	private Book book;
	private ArrayList<Book> books;
	private FileTransfer tableOfContent;

	private GenerateReport generateReport;
	
	//private String dates;
	
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
	
	
	
	public MessageCS(MessageType messageType, GenerateReport generateReport) {
		this.messageType = messageType;
		this.setGenerateReport(generateReport);
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
	public GenerateReport getGenerateReport() {
		return generateReport;
	}
	public void setGenerateReport(GenerateReport generateReport) {
		this.generateReport = generateReport;
	}
	
}
