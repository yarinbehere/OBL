package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Librarian or Manager can add books to the inventory of the library.
 * Accessible through the main menu.
 * 
 * @author Yarin
 * @version 1.0
 * 
 */

public class AddNewBookController implements Initializable{

	@FXML private TextField titleTextField;
	@FXML private TextField authorTextField;
	@FXML private TextField serialNumberTextField;
	@FXML private TextField subjectsTextField;
	@FXML private TextField freeTextField;
	@FXML private TextField quantityTextField;
	@FXML private TextField locationTextField;
	@FXML private TextField pdfPathTextField;
	@FXML private Button addNewBookButton;
	@FXML private Button clearButton;
	@FXML private Button returnButton;
	@FXML private Button helpButton;
	@FXML private ChoiceBox<String> demandChoiceBox;

	
	public static Book resultBookForAddNewBook;
	//public static ArrayList<Book> bookResult;
	public static FileTransfer tableOfContent;
	//private ObservableList<Book> listOfBooks;

	/*
	 * Initialize the DemandChoiceBox 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		ObservableList<String> demandList=FXCollections.observableArrayList("Normal","Wanted");
		demandChoiceBox.setValue("Normal"); // Default value is Normal
		demandChoiceBox.setItems(demandList);
	
	}
	
	@FXML
	void addNewBook(ActionEvent event) throws InterruptedException{
		
		// Vars init
		ArrayList<String> errorsList=new ArrayList<>();
		Alert errorAlert=new Alert(Alert.AlertType.ERROR);
		
		//Check fields (all are necessary)		
		if(titleTextField.getText().isEmpty()) {
			errorsList.add("Title");
		}
		if(authorTextField.getText().isEmpty()) {
			errorsList.add("Author");
		}
		if(serialNumberTextField.getText().isEmpty()) {
			errorsList.add("Serial number");
		}
		if(subjectsTextField.getText().isEmpty()) {
			errorsList.add("Subject");
		}
		if(freeTextField.getText().isEmpty()) {
			errorsList.add("Description");
		}
		if((quantityTextField.getText().isEmpty())||(!quantityTextField.getText().matches("[0-9]*"))) {
			errorsList.add("Quantity");
		}
		if(locationTextField.getText().isEmpty()) {
			errorsList.add("Location");
		}
		if(pdfPathTextField.getText().isEmpty()) {
			errorsList.add("PDF Path");
		}
		
		// Error detected in field(s)
		if(errorsList.size()>0) {
			// Show error
			errorAlert.setTitle("Failed");
    		errorAlert.setContentText(errorsList.toString());
    		errorAlert.showAndWait();
			return;
		}
		
		//Date temp=new Date(0);
		//Book book=new Book(serialNumberTextField.getText(), titleTextField.getText(), locationTextField.getText(), "Yes", temp);
		Book tempBook=new Book(serialNumberTextField.getText());
		
		MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_ADDNEWBOOK,tempBook);
    	MainClient.client.accept(message);
    	Thread.sleep(100);
    	System.out.println("This is working");
    	// Check if book already exists.
    	if (!(resultBookForAddNewBook == null)) {
    		errorAlert.setTitle("Failed");
    		errorAlert.setContentText("Book already exists in inventory.");
    		errorAlert.showAndWait(); 
  			return;
    	}
    	else {
    	}
    	
    	
    	return;
    	//if(resultBook.g)
		// TODO construct Book (after merge)
		//Book book = new Book(bookID, bookTitle, shelfLocation, available, soonestReturn)
	}

	/*
	 * clears all data inserted by the user from the fields when button is pressed
	 */
	@FXML
	void clearFields(ActionEvent event) 
	{
		titleTextField.setText("");
		authorTextField.setText("");
		serialNumberTextField.setText("");
		subjectsTextField.setText("");
		freeTextField.setText("");
		quantityTextField.setText("");
		locationTextField.setText("");
		pdfPathTextField.setText("");
		demandChoiceBox.setValue("Normal");
		
	}



}
