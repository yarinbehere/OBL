package controller;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

}
