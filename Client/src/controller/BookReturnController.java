package controller;

import java.net.URL;
import java.sql.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BookReturnController implements Initializable{
	@FXML private TextField subscriberTextField;
	@FXML private Button searchSubscriberButton;

	@FXML private Button helpButton;
	@FXML private Button returnBook;
	@FXML private Button searchBookButton;
	@FXML private Text subscriberStatusLabel;
	@FXML private DatePicker borrowDate;
	@FXML private DatePicker returnDate;
	@FXML private Button returnBookButton;
	@FXML private DatePicker returnedDate;
	@FXML private Text lateReturnLabel;

	@FXML private Text labelPath;
	@FXML private TextField bookTextField;
	@FXML private Label subscriberIDLabel;

	public static Subscriber resultSubscriber = null;
	public static Book resultBook;
	private Alert alert;
	private Subscriber subscriber;
	private MessageCS message;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		borrowDate.setDisable(true);
		borrowDate.setStyle("-fx-opacity: 1");
		borrowDate.getEditor().setStyle("-fx-opacity: 1");
		returnDate.setDisable(true);
		returnDate.setStyle("-fx-opacity: 1");
		returnDate.getEditor().setStyle("-fx-opacity: 1");
		returnedDate.setDisable(true);
		returnedDate.setStyle("-fx-opacity: 1");
		returnedDate.getEditor().setStyle("-fx-opacity: 1");
		returnedDate.setValue(LocalDate.now());
		lateReturnLabel.setVisible(false);
		subscriberIDLabel.setVisible(false);
		subscriberStatusLabel.setVisible(false);
		returnBookButton.setDisable(true);
	}
	

	@FXML
	void searchSubscriber(ActionEvent event) throws InterruptedException
	{
		alert =new Alert(Alert.AlertType.INFORMATION); 
    	if((subscriberTextField.getText().equals("Serial number, name")) || subscriberTextField.getText().equals("") )
 		 {
 			alert.setTitle("Empty Fields");
 			alert.setContentText("please fill all the requierd fields! ");
 			alert.showAndWait();
 			returnBookButton.setDisable(true);
 			return;
 		 }
		
		subscriber = new Subscriber(subscriberTextField.getText());
		message = new MessageCS(MessageType.SEARCH_SUBSCRIBER_FOR_OPTIONS,subscriber);
		MainClient.client.accept(message);
		Thread.sleep(400);
    	//if the subscriber does not exist
		alert = new Alert(AlertType.ERROR);
    	if(resultSubscriber.getSubscriberDetails().equals("null"))
    	{
    		alert.setContentText("subscriber doesnt exist!");
			alert.showAndWait();
			returnBookButton.setDisable(true);
			return;
    	}
		subscriberIDLabel.setText(resultSubscriber.getSubscriberID());
		subscriberStatusLabel.setText(resultSubscriber.getSubscriberStatus());
		//if the subscriber exist
	       if( resultSubscriber.getSubscriberStatus().equals("Locked"))
	    	   subscriberStatusLabel.setFill(Color.RED);
	       if( resultSubscriber.getSubscriberStatus().equals("Frozen"))
	    	   subscriberStatusLabel.setFill(Color.BLUE);
	       if( resultSubscriber.getSubscriberStatus().equals("Active"))
	    	   subscriberStatusLabel.setFill(Color.GREEN);
	    subscriberIDLabel.setVisible(true);
		subscriberStatusLabel.setVisible(true);
	}
	
    @FXML
    void searchBook(ActionEvent event) throws InterruptedException {
    	alert =new Alert(Alert.AlertType.INFORMATION);
    	if((bookTextField.getText().equals("Serial number, name")) || bookTextField.getText().equals("") )
 		 {
 			alert.setTitle("Empty Fields");
 			alert.setContentText("please fill all the requierd fields! ");
 			alert.showAndWait();
 			returnBookButton.setDisable(true);
 			return;
 		 }
    	//no reason to look for a book if subscriber fields are empty
    	alert =new Alert(Alert.AlertType.INFORMATION);
    	if((subscriberTextField.getText().equals("Serial number, name")) || subscriberTextField.getText().equals("") )
 		 {
 			alert.setTitle("Empty Fields");
 			alert.setContentText("please fill all the requierd fields! ");
 			alert.showAndWait();
 			returnBookButton.setDisable(true);
 			return;
 		 }
    	subscriber = new Subscriber(subscriberTextField.getText());
		message = new MessageCS(MessageType.SEARCH_SUBSCRIBER_FOR_OPTIONS,subscriber);
		MainClient.client.accept(message);
		Thread.sleep(400);
    	//no reason to look for a book if subscriber doesn't exist
		alert = new Alert(AlertType.ERROR);
		if(resultSubscriber.getSubscriberDetails().equals("null"))
    	{
    		alert.setContentText("subscriber doesnt exist!");
			alert.showAndWait();
			returnBookButton.setDisable(true);
			return;
    	}
		//Book book = new Book(bookID, subscriptionNumber)
    	Book book = new Book(bookTextField.getText(),subscriberTextField.getText());
    	message = new MessageCS(MessageType.SEARCH_BOOK_FOR_RETURN,book);
    	MainClient.client.accept(message);
    	Thread.sleep(100);
    	
    	alert = new Alert(AlertType.ERROR);
    	if(resultBook == null)
    	{
    		alert.setContentText("subscriber doesnt have this book!");
			alert.showAndWait();
			returnBookButton.setDisable(true);
			return;
    	}
    	//convert Date to String
    	Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    	String format = formatter.format(resultBook.getBorrowDate());
    	//insert the converted LocalDate value
    	borrowDate.setValue(LocalDate.parse(format));
    	
    	format = formatter.format(resultBook.getReturnDate());
    	returnDate.setValue(LocalDate.parse(format));
    	//if there is a negative gap between today's date and the return date
    	if(ChronoUnit.DAYS.between(returnedDate.getValue(), returnDate.getValue()) < 0)
    	{
    		//print and convert the gap to positive value
    		lateReturnLabel.setText("The subscriber is late by " + 
    	Math.abs(ChronoUnit.DAYS.between(returnDate.getValue(), returnedDate.getValue()))+" Days");
    		lateReturnLabel.setVisible(true);
    	}
    	returnBookButton.setDisable(false);
    }
    
    @FXML
	void returnBook(ActionEvent event) throws InterruptedException { 
    	Book book = new Book(bookTextField.getText());
		message = new MessageCS(MessageType.RETURN_BOOK,resultSubscriber,book);
		MainClient.client.accept(message);
		Thread.sleep(100);
		alert =new Alert(Alert.AlertType.INFORMATION);
		alert.setContentText("Succeed to return the book");
		alert.showAndWait();
		returnBookButton.setDisable(true);
	}

	
}
