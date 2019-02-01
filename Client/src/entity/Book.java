package entity;

import java.io.Serializable;
import java.sql.Date;

public class Book implements Serializable{
	private String bookID;
	private String bookTitle;
	private String authorName;
	private int editionNumber;
	private String bookGenre;
	private String bookDescription;
	private int originalQuantity;
	private String publishedDate;
	private String cataologNumber; // Yarin: WTF is this?
	private String datePurchase; // Yarin: Unnecessary?
	private String shelfLocation;
	private String pdfPath; // Yarin: Added
	private int amountOfBooks; // Yarin: currentQuantity
	private String available;
	private Date soonestReturn; 
	public String bookDemand;
	

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
	
	/***
	 * Search by ID only within the window.
	 * @author Yarin
	 */
	public Book(String bookID) {
		this.bookID=bookID;
	}
	
	/**
	 * Add new book with data for columns in DB
	 * @author Yarin
	 */
	public Book(String bookID, String bookTitle, String authorName, int editionNumber, String publishedDate,
			String bookGenre, String bookDescription, int originalQuantity, String datePurchase, String shelfLocation,
			String pdfPath, int amountOfBooks, String available) {
		super();
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.editionNumber = editionNumber;
		this.publishedDate = publishedDate;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
		this.originalQuantity = originalQuantity;
		this.datePurchase = datePurchase;
		this.shelfLocation = shelfLocation;
		this.pdfPath = pdfPath;
		this.amountOfBooks = amountOfBooks;
		this.bookDemand = available;
	}

	
	/***
	 * Add completely new book with all attributes of Book
	 * @author Yarin
	 * @return
	 */
	public Book(String bookID, String bookTitle, String authorName, int editionNumber, String bookGenre,
			String bookDescription, int originalQuantity, String publishedDate, String cataologNumber,
			String datePurchase, String shelfLocation, String pdfPath, int amountOfBooks, String available,
			Date soonestReturn,String bookDemand) {
		super();
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.editionNumber = editionNumber;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
		this.originalQuantity = originalQuantity;
		this.publishedDate = publishedDate;
		this.cataologNumber = cataologNumber;
		this.datePurchase = datePurchase;
		this.shelfLocation = shelfLocation;
		this.pdfPath = pdfPath;
		this.amountOfBooks = amountOfBooks;
		this.available = available;
		this.soonestReturn = soonestReturn;
		this.bookDemand=bookDemand;
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

	public int getEditionNumber() {
		return editionNumber;
	}

	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}

	public int getOriginalQuantity() {
		return originalQuantity;
	}

	public void setOriginalQuantity(int originalQuantity) {
		this.originalQuantity = originalQuantity;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getCataologNumber() {
		return cataologNumber;
	}

	public void setCataologNumber(String cataologNumber) {
		this.cataologNumber = cataologNumber;
	}

	public String getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(String datePurchase) {
		this.datePurchase = datePurchase;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}

	public int getAmountOfBooks() {
		return amountOfBooks;
	}

	public void setAmountOfBooks(int amountOfBooks) {
		this.amountOfBooks = amountOfBooks;
	}

	public String getBookDemand() {
		return bookDemand;
	}

	public void setBookDemand(String bookDemand) {
		this.bookDemand = bookDemand;
	}
	
	
	
}
