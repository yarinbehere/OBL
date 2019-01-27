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
	int amountOfBooks;
	int quanity;
	private String available;
	private Date soonestReturn; 
	

	public Book(String bookTitle, String authorName, String bookGenre, String bookDescription)
	{
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
	}
	
	public Book(String bookID, String bookTitle, String shelfLocation, String available, Date soonestReturn)
	{
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.shelfLocation = shelfLocation;
		this.available = available;
		this.setSoonestReturn(soonestReturn);
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
	
	
}
