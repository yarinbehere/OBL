package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class BorrowBookController { 

    @FXML private TextField subscriberEditLabel;
    @FXML private Button searchSubscriberButton;
    @FXML private TextField bookLabel;
    @FXML private Button searchBookButton;
    @FXML private Text subscriberStatusLabel;
    @FXML private Hyperlink subscriberIDLabel; 
    @FXML private Text bookStatusLabel;
    @FXML private Hyperlink bookIDLabel;
    @FXML private DatePicker borrowDate;
    @FXML private DatePicker returnDate;
    @FXML private Button borrowButton;
    @FXML private Button helpButton;
    @FXML private Button returnButton;
    @FXML private Text pathLabel;
    
    public static Subscriber resultSubscriber;
    public static Book resultBook;
 /*  
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	//borrowButton.setDisable(true);
	}
 */
    @FXML
    void searchSubscriber(ActionEvent event) throws InterruptedException
    {
    	Subscriber subscriber = new Subscriber(subscriberEditLabel.getText());
    	MessageCS message = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
    	MainClient.client.accept(message);
    	Thread.sleep(100);
    	subscriberStatusLabel.setText(resultSubscriber.getSubscriberStatus());
    	subscriberIDLabel.setText(resultSubscriber.getSubscriberID());
    }

    @FXML
    void searchBook(ActionEvent event) throws InterruptedException
    {
    	Book book = new Book(bookLabel.getText());
    	System.out.println(book.getbookDetails());
    	System.out.println(bookLabel.getText());
    	MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_OPTIONS,book);
    	MainClient.client.accept(message);
    	Thread.sleep(100);
    	bookStatusLabel.setText(resultBook.getwantedLevel());
    	bookIDLabel.setText(resultBook.getbookSerialNumber());
    }
    
    @FXML
    void borrowBook(ActionEvent event)
    {
    	String checkSubscriberStatus;
    	int checkCurrentBookQuanity;
    	checkCurrentBookQuanity=resultBook.getCurrentBookQuanity();
    	checkSubscriberStatus=resultSubscriber.getSubscriberStatus();
    	LocalDate date1 = borrowDate.getValue();
    	LocalDate date2 = returnDate.getValue();
    	long borrow_period =ChronoUnit.DAYS.between(date1, date2);
    	
    	//check if the subscriber status isn't "Active"
    	if(!(checkSubscriberStatus.equals("Active")))
    	{
    		//alert
    	}
    	//check if the wanted book quantity is 1 or more
    	if(checkCurrentBookQuanity<=0)
    	{
    		//alert
    	}
    	//checks if the borrow date is further than the return date
    	if (borrow_period < 0)
    	{
    		//alert
    	}
    	//checks if the status of the book is "wanted", and the borrow period is three days or more
    	if(checkSubscriberStatus.equals("Active") && borrow_period > 3)
    	{
    		//alert
    	}
    }

	

}
