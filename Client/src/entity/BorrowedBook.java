package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class BorrowedBook implements Serializable
{
	private String subscriptionNumber;
	private String bookId;
	private LocalDate returnDate;
	private LocalDate borrowDate;
	private int lostBook;
	
	
	public BorrowedBook(String subscriptionNumber,String bookId,LocalDate returnDate,LocalDate borrowDate,int lostBook)
	{
		this.subscriptionNumber=subscriptionNumber;
		this.bookId=bookId;
		this.returnDate=returnDate;
		this.borrowDate=borrowDate;
		this.lostBook=lostBook;
	}
	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}
	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public LocalDate getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}
	public int getLostBook() {
		return lostBook;
	}
	public void setLostBook(int lostBook) {
		this.lostBook = lostBook;
	}
}
