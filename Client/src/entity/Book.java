package entity;

import java.io.Serializable;

public class Book implements Serializable{
	String bookTitle;
	String authorName;
	String editionNumber;
	String publishedDate;
	String bookDescription;
	String bookGenre;
	String cataologNumber;
	String datePurchase;
	String shelfLocation;
	int amountOfBooks;
	int quanity;

	public Book(String bookTitle, String authorName, String bookGenre, String bookDescription)
	{
		this.bookTitle = bookTitle;
		this.authorName = authorName;
		this.bookGenre = bookGenre;
		this.bookDescription = bookDescription;
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
	
	
}
