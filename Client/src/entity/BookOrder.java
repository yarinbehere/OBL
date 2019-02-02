package entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class BookOrder implements Serializable{
	

	private String subscriptionNumber;
	private String bookID;
	private String bookTitle;
	private Date dateOrder;
	private int queue;
	private LocalDate localDate;

	public BookOrder(String bookID, String subscriptionNumber) {
		this.setBookID(bookID);
		this.setSubscriptionNumber(subscriptionNumber);
	}
	
	/**find the books a certain subscriber has ordered
	 * @author Roman
	 * @param title
	 * @param dateOrder
	 * @param queue
	 */
	
	public BookOrder(String bookTitle, Date dateOrder,int queue)
	{
		this.bookTitle = bookTitle;
		this.dateOrder = dateOrder;
		this.queue = queue;
	}
	

	public String getSubscriptionNumber() {
		return subscriptionNumber;
	}

	public void setSubscriptionNumber(String subscriptionNumber) {
		this.subscriptionNumber = subscriptionNumber;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
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

	public LocalDate getLocalDate() {
		return localDate;
	}

	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	

}
