package entity;

import java.io.Serializable;
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
	////
	private String bookSerialNumber;
	private String bookDetails;
	private String wantedLevel;
	private int OriginalBookQuanity;
	private int CurrentBookQuanity;
	//אולי צריך לשים ב"יישות" חדשה
	private LocalDate BorrowDate;
	private LocalDate ReturnDate;
	////
	public Book(String bookTitle, String authorName, String bookGenre, String bookDescription)
	{
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
	}
	//contractor for borrowing book
	public Book(LocalDate BorrowDate,LocalDate ReturnDate)
	{
		this.setBorrowDate(BorrowDate);
		this.setReturnDate(ReturnDate);

	}
	public Book(String bookSerialNumber, String wantedLevel,int CurrentBookQuanity) 
	{
		this.bookSerialNumber=bookSerialNumber;
		this.wantedLevel=wantedLevel;
		this.CurrentBookQuanity=CurrentBookQuanity;
	}
	
	//
	public Book(String bookDetails) 
	{
		this.bookDetails = bookDetails;
	}
	
	public String getwantedLevel() {
		return wantedLevel;
	}
	public String getbookDetails() {
		return bookDetails;
	}
	public String getBookTitle() {
		return bookTitle;
	}
	public String getbookSerialNumber() {
		return bookSerialNumber;
	}
	public void setbookSerialNumber(String bookSerialNumber) {
		this.bookSerialNumber = bookSerialNumber;
	}
	public void setwantedLevel(String wantedLevel) {
		this.wantedLevel = wantedLevel;
	}
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}
	public void setbookDetails(String bookDetails) {
		this.bookDetails = bookDetails;
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
	
	public void setOriginalBookQuanity(int OriginalBookQuanity) {
		this.OriginalBookQuanity = OriginalBookQuanity;
	}
	public int getOriginalBookQuanity() {
		return OriginalBookQuanity;
	}
	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}
	public void setCurrentBookQuanity(int CurrentBookQuanity) {
		this.CurrentBookQuanity = CurrentBookQuanity;
	}
	public int getCurrentBookQuanity() {
		return CurrentBookQuanity;
	}
	public LocalDate getReturnDate() {
		return ReturnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		ReturnDate = returnDate;
	}
	public LocalDate getBorrowDate() {
		return BorrowDate;
	}
	public void setBorrowDate(LocalDate borrowDate) {
		BorrowDate = borrowDate;
	}
	
}
