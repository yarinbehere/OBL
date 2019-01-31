package entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

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
	private int currentBookQuanity;
	private String available;
	private Date soonestReturn;
	private String bookDetails;
	private String bookSerialNumber;
	private String wantedLevel;
	private int originalBookQuantity;
	private String bookTableOfContents; 
	

	public Book(String bookTitle, String authorName, String bookGenre, String bookDescription)
	{
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
	}

	public Book(String bookSerialNumber, String wantedLevel,int CurrentBookQuanity,int OriginalBookQuanity,String bookDetails ) 
	{
		this.setBookSerialNumber(bookSerialNumber);
		this.setWantedLevel(wantedLevel);
		this.setCurrentBookQuanity(CurrentBookQuanity);
		this.setOriginalBookQuanity(OriginalBookQuanity);
		this.setBookDetails(bookDetails);
	}
	
	public Book(String bookID, String bookTitle, String shelfLocation, String available, Date soonestReturn)
	{
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.shelfLocation = shelfLocation;
		this.available = available;
		this.setSoonestReturn(soonestReturn);
	}
	
	/**find and update the books for update book's details
	 * @author Omri Braymok
	 * @param ID
	 * @param available
	 * @param Title
	 * @param authorName
	 * @param Genre
	 * @param bookDescription
	 * @param originalBookQuantity
	 * @param shelfLocation
	 * @param wantedLevel
	 * @param bookTableOfContents
	 */
	public Book(String bookID,String available, String bookTitle,String authorName,String bookGenre,String bookDescription,
			int originalBookQuantity,String shelfLocation,String wantedLevel,String bookTableOfContents) {
		this.bookID=bookID;
		this.available=available;
		this.bookTitle=bookTitle;
		this.authorName=authorName;
		this.bookGenre=bookGenre;
		this.bookDescription=bookDescription;
		this.setOriginalBookQuantity(originalBookQuantity);
		this.shelfLocation=shelfLocation;
		this.wantedLevel=wantedLevel;
		this.setBookTableOfContents(bookTableOfContents);
		
	}
	
	public Book(String bookDetails) 
	{
		this.setBookDetails(bookDetails);
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

	public String getWantedLevel() {
		return wantedLevel;
	}

	public void setWantedLevel(String wantedLevel) {
		this.wantedLevel = wantedLevel;
	}

	public String getBookSerialNumber() {
		return bookSerialNumber;
	}

	public void setBookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}

	public int getCurrentBookQuanity() {
		return currentBookQuanity;
	}

	public void setCurrentBookQuanity(int currentBookQuanity) {
		this.currentBookQuanity = currentBookQuanity;
	}

	public int getOriginalBookQuanity() {
		return originalBookQuantity;
	}

	public void setOriginalBookQuanity(int originalBookQuanity) {
		this.originalBookQuantity = originalBookQuanity;
	}

	public String getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}

	/**
	 * @return the originalBookQuantity
	 */
	public int getOriginalBookQuantity() {
		return originalBookQuantity;
	}

	/**
	 * @param originalBookQuantity the originalBookQuantity to set
	 */
	public void setOriginalBookQuantity(int originalBookQuantity) {
		this.originalBookQuantity = originalBookQuantity;
	}

	/**
	 * @return the bookTableOfContents
	 */
	public String getBookTableOfContents() {
		return bookTableOfContents;
	}

	/**
	 * @param bookTableOfContents the bookTableOfContents to set
	 */
	public void setBookTableOfContents(String bookTableOfContents) {
		this.bookTableOfContents = bookTableOfContents;
	}
	
	
}
