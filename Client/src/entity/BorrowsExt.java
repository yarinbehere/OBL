/**
 * 
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author shalev kubi
 *
 */
public class BorrowsExt implements Serializable {
	private static final long serialVersionUID = 1L; // just to turn off warning
	private String subscriptionNumber;
	private String firstName;
	private String bookID;
	private String bookTitle;
	private LocalDate borrowDate;
	private LocalDate returnDate;

	/**
	 * @param subscriptionNumber
	 * @param firstName
	 * @param bookID
	 * @param bookTitle
	 * @param borrowDate
	 * @param returnDate
	 */
	public BorrowsExt(String subscriptionNumber, String firstName, String bookID, String bookTitle,
			LocalDate borrowDate, LocalDate returnDate) {
		this.subscriptionNumber = subscriptionNumber;
		this.firstName = firstName;
		this.bookID = bookID;
		this.bookTitle = bookTitle;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
	}

	/**
	 * @return the bookID
	 */
	public String getBookID() {
		return bookID;
	}

	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
	 * @return the borrowDate
	 */
	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	/**
	 * @return the returnDate
	 */
	public LocalDate getReturnDate() {
		return returnDate;
	}

	/**
	 * @return the subscriptionNumber
	 */
	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param bookID the bookID to set
	 */
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	/**
	 * @param borrowDate the borrowDate to set
	 */
	public void setBorrowDate(LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	/**
	 * @param returnDate the returnDate to set
	 */
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	/**
	 * @param subscriptionNumber the subscriptionNumber to set
	 */
	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setfirstName(String userName) {
		this.firstName = userName;
	}
}
