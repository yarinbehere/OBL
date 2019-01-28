package entity;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable{
	private String bookTitle;
	private String authorName;
	private String editionNumber;
	private String publishedDate;
	private String bookDescription;
	private String bookGenre;
	private String cataologNumber;
	private String datePurchase;
	private String shelfLocation;
	private String bookID;
	private int amountOfBooks;
	private int quanity;
	private String available;
	private Date soonestReturn;
	private String bookDetails;
	private String wantedLevel;
	private int CurrentBookQuanity;
	private Date dateOrder;
	private int queue;
	
	/**constructor when a message to server has been sent, to look for the book with these parameters
	 * @author Roman
	 * @param bookTitle
	 * @param authorName
	 * @param bookGenre
	 * @param @bookDescription
	 */
	public Book(String bookTitle, String authorName, String bookGenre, String bookDescription)
	{
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
	}
	/** Constructor send message back to the client with the information should appear when user searching
	 * for a book
	 * @author Roman
	 * @param bookID
	 * @param bookTitle
	 * @param shelfLocation
	 * @param available
	 * @param soonestReturn
	 */
	public Book(String bookID, String bookTitle, String shelfLocation, String available, Date soonestReturn)
	{
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.shelfLocation = shelfLocation;
		this.available = available;
		this.setSoonestReturn(soonestReturn);
	}
	
	public Book(String bookID, String bookTitle, String wantedLevel,int CurrentBookQuanity,Date soonestReturn) 
	{
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.wantedLevel = wantedLevel;
		this.CurrentBookQuanity = CurrentBookQuanity;
		this.soonestReturn = soonestReturn;
	}
	public Book(String bookID, String wantedLevel,int CurrentBookQuanity) 
	{
		this.bookID=bookID;
		this.wantedLevel=wantedLevel;
		this.CurrentBookQuanity=CurrentBookQuanity;
	}
	
	/**find the books a certain subscriber has ordered
	 * @author Roman
	 * @param title
	 * @param dateOrder
	 * @param queue
	 */
	
	public Book(String bookTitle, Date dateOrder,int queue)
	{
		this.bookTitle = bookTitle;
		this.dateOrder = dateOrder;
		this.queue = queue;
	}
	
	public Book(String bookID, Date soonestReturn)
	{
		this.bookID = bookID;
		this.soonestReturn = soonestReturn;
	}
	
	public Book(String bookDetails) 
	{ 
		this.bookDetails = bookDetails;
	}
	
	public String getBookTitle() {
		return bookTitle;
	}
	
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	} 
	
	public String getAuthorName() {
		return authorName;
	}
	
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public String getBookGenre() {
		return bookGenre;
	}
	
	public void setBookGenre(String bookGenre) {
		this.bookGenre = bookGenre;
	}
	
	public String getBookDescription() {
		return bookDescription;
	}
	
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getShelfLocation() {
		return shelfLocation;
	}

	public void setShelfLocation(String shelfLocation) {
		this.shelfLocation = shelfLocation;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public Date getSoonestReturn() {
		return soonestReturn;
	}

	public void setSoonestReturn(Date soonestReturn) {
		this.soonestReturn = soonestReturn;
	}

	public String getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}
	
	public String getWantedLevel() {
		return wantedLevel;
	}
	public void setWantedLevel(String wantedLevel) {
		this.wantedLevel = wantedLevel;
	}
	public int getCurrentBookQuanity() {
		return CurrentBookQuanity;
	}
	public void setCurrentBookQuanity(int currentBookQuanity) {
		CurrentBookQuanity = currentBookQuanity;
	}
	public Date getDateOrder() {
		return dateOrder;
	}
	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}
	public int getQueue() {
		return queue;
	}
	public void setQueue(int queue) {
		this.queue = queue;
	}
	 
	
}
