package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.User.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**search book by bookID, then librarian can insert new details and update them
 * @author omri braymok
 *
 */


public class UpdateBookController implements Initializable{

	@FXML private Button searchBookButton;
	@FXML private Button saveChangeButton;
	@FXML private Button returnButton;

	@FXML private Text bookStatusLabel;
	
	@FXML private Hyperlink bookIDLabel;
	@FXML private Hyperlink pdfLink;
	
	@FXML private TextField serialNumberTextField;
	@FXML private TextField titleTextField;
	@FXML private TextField authorTextField;
	@FXML private TextField freeTextField;
	@FXML private TextField quantityTextField;
	@FXML private TextField locationTextField;
	
	@FXML private ChoiceBox<String> subjectChoiceBox;
	@FXML private ChoiceBox<String> demandChoiceBox;
	
	public static Book resultBook = null;
	
	 //initialize the ChoiceBox
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> subjectList1 = FXCollections.observableArrayList
				("Action","Fantasy","Comedy","Software","Horror");
		subjectChoiceBox.setItems(subjectList1);	
		ObservableList<String> subjectList2 = FXCollections.observableArrayList
				("not wanted","wanted");
		demandChoiceBox.setItems(subjectList2);	
	}
    
    /**
     * search book by bookID, if book found uploads the book's details
     * @param event
	 * @throws InterruptedException
	 * @author Omri braymok
	*/
	@FXML
	public void searchBookButtonAction(ActionEvent event) throws InterruptedException {
    	Book book = new Book(serialNumberTextField.getText(), null, null, null, null);
    	MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_UPDATE_BOOK, book);
    	MainClient.client.accept(message);
    	Thread.sleep(1500);
    	if(resultBook == null)
		{
        	//Alert java
    		Alert alert = new Alert(AlertType.ERROR, "book don't found", ButtonType.CANCEL);
    		alert.showAndWait();
		}
		else
		{
			//Uploads the details of the book found
			bookStatusLabel.setText(resultBook.getAvailable());
			bookIDLabel.setText(resultBook.getBookID());
			titleTextField.setText(resultBook.getBookTitle());
			authorTextField.setText(resultBook.getAuthorName());
			freeTextField.setText(resultBook.getBookDescription());
			String Quanity = Integer.toString(resultBook.getOriginalBookQuantity()); 
			quantityTextField.setText(Quanity);
			locationTextField.setText(resultBook.getShelfLocation());
			subjectChoiceBox.setValue(resultBook.getBookGenre());
			demandChoiceBox.setValue(resultBook.getWantedLevel());
			pdfLink.setText(resultBook.getBookTableOfContents());
		}
	}
	
	 /** 
     * clears all data of the last book found 
     * @param event
     */
	@FXML
	public void resetButtonAction(ActionEvent event){
		bookStatusLabel.setText("[status]");
		pdfLink.setText("[View PDF]");
		serialNumberTextField.setText("Serial number");
		bookIDLabel.setText("[num]");
		titleTextField.setText("");
		authorTextField.setText("");
		freeTextField.setText(""); 
		quantityTextField.setText("");
		locationTextField.setText("");
		subjectChoiceBox.setValue(null);
		demandChoiceBox.setValue(null);
		pdfLink.setText("[View PDF]");
	}
	
	/**
     * save the book's new details who inserted by user
     * @param event
	 * @throws InterruptedException
	 * @author Omri braymok
	*/
	@FXML
	public void saveChangeButtonAction(ActionEvent event) throws InterruptedException {
		if(bookIDLabel.getText().equals("[num]"))
			{
			//Alert java
    		Alert alert = new Alert(AlertType.ERROR, "empty field", ButtonType.CANCEL);
    		alert.showAndWait();
			}
		else
			{
			Book book = new Book(bookIDLabel.getText(),bookStatusLabel.getText(),titleTextField.getText(),authorTextField.getText(),
					subjectChoiceBox.getValue(),freeTextField.getText(),
					Integer.parseInt(quantityTextField.getText()),locationTextField.getText(),demandChoiceBox.getValue(),resultBook.getBookTableOfContents());
			MessageCS message = new MessageCS(MessageType.UPDATE_BOOK, book);
	    	MainClient.client.accept(message);
	    	Thread.sleep(1500);
			}
	}
	
	/**
	 * Return to Librarian Menu or Manager Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {
		 if(LoginController.userResult.getRole()==Role.LIBRARIAN)
		 {
			 LoadGUI.loadFXML("LibrarianMenu.fxml",returnButton); 
		 }
		 else
	    	{
	    		LoadGUI.loadFXML("ManagerMenu.fxml",returnButton);
	    	}
	 }
}
