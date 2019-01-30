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
	private int OriginalBookQuanity;
	private int CurrentBookQuanity;
	private String available;
	private Date soonestReturn;
	private String bookDetails;
	private String bookSerialNumber;
	private String wantedLevel; 
	

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
		return CurrentBookQuanity;
	}

	public void setCurrentBookQuanity(int currentBookQuanity) {
		CurrentBookQuanity = currentBookQuanity;
	}

	public int getOriginalBookQuanity() {
		return OriginalBookQuanity;
	}

	public void setOriginalBookQuanity(int originalBookQuanity) {
		OriginalBookQuanity = originalBookQuanity;
	}

	public String getBookDetails() {
		return bookDetails;
	}

	public void setBookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
	}
	
	
}
