package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.BorrowedBook;
import entity.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class BorrowBookController implements Initializable{ 

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
    public static BorrowedBook resultBorrowedBook;
    public static Book cancel_borrow;
   String checkSubscriberStatus;

   /**
    * When the Librarian wants to borrow a book to subscriber, first he need to search the subscriber
    * Accessible through the librarian menu.
    * (action made by librarian)
    * @throws InterruptedException
    * @author Hai
    * 
    */
    
    @FXML
    void searchSubscriber(ActionEvent event) throws InterruptedException
    {
   	 Alert alert1=new Alert(Alert.AlertType.INFORMATION);
   	if((subscriberEditLabel.getText().equals("Username, email, ID")) || subscriberEditLabel.getText().equals("") )
		 {
			alert1.setTitle("Empty Fields");
			alert1.setContentText("please fill all the requierd fields! ");
			alert1.showAndWait();
			return;
		 }
    	Subscriber subscriber = new Subscriber(subscriberEditLabel.getText());
    	MessageCS message = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
    	MainClient.client.accept(message);
    	Thread.sleep(1000);
    	//if the subscriber does not exist
    	if(resultSubscriber.getSubscriberDetails().equals("null"))
    	{
    		alert1.setContentText("subscriber doesnt exist!");
			alert1.showAndWait();
			return;
    	}
		
     	//if the subscriber exist
       if( resultSubscriber.getSubscriberStatus().equals("Locked"))
    	   subscriberStatusLabel.setFill(Color.RED);
       if( resultSubscriber.getSubscriberStatus().equals("Frozen"))
    	   subscriberStatusLabel.setFill(Color.BLUE);
       if( resultSubscriber.getSubscriberStatus().equals("Active"))
    	   subscriberStatusLabel.setFill(Color.GREEN);
    	subscriberStatusLabel.setText(resultSubscriber.getSubscriberStatus());
    	subscriberIDLabel.setText(resultSubscriber.getSubscriberID());
    	searchBookButton.setDisable(false);
    }
    
    /**
     * When the Librarian wants to borrow a book to subscriber, first he need to search the book he wants to borrow.
     * Accessible through the librarian menu.
     * (action made by librarian)
     * @throws InterruptedException
     * @author Hai
     * 
     */

    @FXML
    void searchBook(ActionEvent event) throws InterruptedException
    {
      	 Alert alert2=new Alert(Alert.AlertType.INFORMATION);
        	if((bookLabel.getText().equals("Serial number, name"))||bookLabel.getText().equals("") )
     		 {
     			alert2.setTitle("Empty Fields");
     			alert2.setContentText("please fill all the requierd fields! ");
     			alert2.showAndWait();
     			return;
     		 }
    	Book book = new Book(bookLabel.getText());
    	MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_BORROW,book);
    	MainClient.client.accept(message);
    	Thread.sleep(1000);
    	//if the book does not exist in the system
    	if(resultBook.getBookDetails().equals("null"))
    	{
    		alert2.setContentText("Book doesn't exist!");
    		alert2.showAndWait();
			return;
    	}
    	//if the book exist in the system
    	else 
    	{
    	   if( resultBook.getWantedLevel().equals("wanted"))
    		   bookStatusLabel.setFill(Color.RED);
    	   if( resultBook.getWantedLevel().equals("not wanted"))
    		   bookStatusLabel.setFill(Color.GREEN);
    	bookStatusLabel.setText(resultBook.getWantedLevel());
    	bookIDLabel.setText(resultBook.getBookSerialNumber());

    	}
    	borrowButton.setDisable(false);
    }
    
    /**
     * checking the requirement of the subscriber to borrow a book and let's him to borrow a book 
     * (action made by librarian)
     * @param event
     * @throws InterruptedException
     * @author Hai
     */
    @FXML
    void borrowBook(ActionEvent event) throws InterruptedException
    {
     	
    	int checkCurrentBookQuanity;
    	Alert alert4=new Alert(Alert.AlertType.ERROR);
    	Alert alert5=new Alert(Alert.AlertType.INFORMATION);
    	
    	if(borrowDate.getValue() == null||returnDate.getValue()==null||bookLabel.getText().equals("Serial number, name")||subscriberEditLabel.getText().equals("")||subscriberEditLabel.getText().equals("Username, email, ID") || subscriberEditLabel.getText().equals(""))
    	{
  			alert4.setTitle("Empty Fields");
  			alert4.setContentText("please fill all the requierd fields! ");
  			alert4.showAndWait();
  			return;
    	}
    	else
    	{
        	checkCurrentBookQuanity=resultBook.getCurrentBookQuanity();
        	checkSubscriberStatus=resultSubscriber.getSubscriberStatus();
        	LocalDate BorrowDate = borrowDate.getValue();
        	LocalDate ReturnDate = returnDate.getValue();
        	long borrow_period =ChronoUnit.DAYS.between(BorrowDate, ReturnDate);
        	//check if the subscriber status isn't "Active"
        	if(!(checkSubscriberStatus.equals("Active")))
        	{
        		alert4.setTitle("Subscriber Status");
        		alert4.setHeaderText(null);
        		alert4.setContentText("Subscriber status isn't 'active ");
        		alert4.showAndWait();
        		return;
        	}
        	//check if the wanted book quantity isn't available
        	if(checkCurrentBookQuanity<=0)
        	{
        		alert4.setTitle("Current Book Quanity");
        		alert4.setContentText("The wanted book quantity isn't available!");
        		alert4.showAndWait();
        		return;
        	}
        	//checks if the borrow date is further than the return date
        	if (borrow_period < 0)
        	{
        		alert4.setTitle("Borrow Period");
        		alert4.setContentText("The borrow date is further than the return date!");
        		alert4.showAndWait();
        		return;
        	}
        	//checks if the status of the book is "wanted", and the borrow period is three days or more
        	if(resultBook.getWantedLevel().equals("wanted") && borrow_period > 3)
        	{
        		alert4.setTitle("Borrow Period");
        		alert4.setContentText("'Wanted' book cant be borrowed for more than 3 days!");
        		alert4.showAndWait();
        		return;
        	}
        	//check if the borrow period is more than two weeks
        	if(resultBook.getWantedLevel().equals("not wanted") && borrow_period > 14)
        	{
        		alert4.setTitle("Borrow Period");
        		alert4.setContentText("'The borrow period is more than two weeks!");
        		alert4.showAndWait();
        		return;
        	}
        	
        	BorrowedBook borrowedbook = new BorrowedBook(resultSubscriber.getSubscriberID(),resultBook.getBookSerialNumber(),ReturnDate,BorrowDate,0);
        	MessageCS message = new MessageCS(MessageType.BORROW,borrowedbook);
        	MainClient.client.accept(message); 
        	Thread.sleep(1500);
        	 
        	if(cancel_borrow.getBookDetails().equals("subscriber already borrowed this book"))
        	{
        		alert4.setTitle("information");
        		alert4.setContentText("subscriber already borrowed this book!");
        		alert4.showAndWait();
        		cancel_borrow.setBookDetails(bookLabel.getText());
        		return;
        	}
        
        	alert5.setTitle("Borrowed book");
        	alert5.setContentText("Book successfully borrowed");
    		alert5.showAndWait();
    		resultBook.setCurrentBookQuanity(resultBook.getCurrentBookQuanity()-1);
    		return;
    	}

    }
    /**
     * initialize the buttons to be off, and gives the option to perform borrow only when searching first the subscriber and the book.
     * @author Hai
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchBookButton.setDisable(true);
		borrowButton.setDisable(true);
		
	}

	

}
