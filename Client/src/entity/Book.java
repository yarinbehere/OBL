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
	private int queue;
	private LocalDate dateOrder;
	private String subscriptionNumber;
	private Date returnDate;
	private Date borrowDate;
	private String pdfPath;
	private int originalQuantity;
	private int amountOfBooks;
	private String bookDemand; 


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

	public Book(String bookSerialNumber, String wantedLevel,int CurrentBookQuanity,int OriginalBookQuanity,String bookDetails ) 
	{
		this.setBookSerialNumber(bookSerialNumber);
		this.setWantedLevel(wantedLevel);
		this.setCurrentBookQuanity(CurrentBookQuanity);
		this.setOriginalBookQuanity(OriginalBookQuanity);
		this.setBookDetails(bookDetails);
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
		this.editionNumber = editionNumber+"";
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

	
	public String getEditionNumber() {
			return editionNumber;
		}

		public void setEditionNumber(String editionNumber) {
			this.editionNumber = editionNumber;
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

		public int getOriginalQuantity() {
			return originalQuantity;
		}

		public void setOriginalQuantity(int originalQuantity) {
			this.originalQuantity = originalQuantity;
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

	/***
	 * Add completely new book with all attributes of Book
	 * @author Yarin
	 * @return
	 */
	public Book(String bookID, String bookTitle, String authorName, String editionNumber, String bookGenre,
			String bookDescription, int originalQuantity, String publishedDate, String cataologNumber,
			String datePurchase, String shelfLocation, String pdfPath, int amountOfBooks, String available,
			Date soonestReturn,String bookDemand) {
		super();
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.editionNumber = editionNumber+"";
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
	

	public Book(String bookID, String wantedLevel,int currentBookQuanity) 
	{
		this.bookID=bookID;
		this.wantedLevel=wantedLevel;
		this.currentBookQuanity=currentBookQuanity;
	}

	public Book(String bookID, String bookTitle, LocalDate dateOrder,int queue)
	{
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.setDateOrder(dateOrder);
		this.setQueue(queue);
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

	public Book(String bookID, String subscriptionNumber) {
		this.bookID = bookID;
		this.setSubscriptionNumber(subscriptionNumber);
	}

	public Book(String bookTitle, LocalDate dateOrder, int queue) {
		this.bookTitle = bookTitle;
		this.setDateOrder(dateOrder);
		this.setQueue(queue);
	}

	public Book(Date borrowDate, Date returnDate) {
		this.setBorrowDate(borrowDate);
		this.setReturnDate(returnDate);
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

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}

	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}

	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public LocalDate getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(LocalDate dateOrder) {
		this.dateOrder = dateOrder;
	}


}
