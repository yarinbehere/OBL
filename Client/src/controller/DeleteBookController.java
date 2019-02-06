package controller;

import java.net.URL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

/**
 * Librarian or Manager can delete books from the inventory of the library.
 * Accessible through the main menu.
 * @author Yarin
 *
 */
public class DeleteBookController {

	@FXML private TextField serialNumberTextField1;
	@FXML private TextField serialNumberTextField2;
	@FXML private TextField titleTextField;
	@FXML private TextField authorTextField;
	@FXML private TextField editionTextField;
	@FXML private TextField subjectsTextField;
	@FXML private TextField freeTextField;
	@FXML private TextField quantityTextField;
	@FXML private TextField locationTextField;
	@FXML private TextField pdfPathTextField;
	@FXML private Button deleteBookButton;
	@FXML private Button clearButton;
	@FXML private Button returnButton;
	@FXML private Button helpButton;
	@FXML private Button openPdfButton;
	@FXML private TextField demandTextField;
	
	public static Book resultBookForDeleteBook;
	
	/*
	 * Search Book for Delete Book
	 */
	@FXML
	void searchBook(ActionEvent event) throws InterruptedException {
		// Vars init
		ArrayList<String> errorsList=new ArrayList<>();
		Alert errorAlert=new Alert(Alert.AlertType.ERROR);
		Alert successAlert=new Alert(AlertType.CONFIRMATION);
		Book tempBook;
		Date tempDate=new Date(0);
		int quantity,edition;
		
		if(serialNumberTextField1.getText().isEmpty()) {
			// Show error
			errorAlert.setTitle("Failed");
			errorAlert.setContentText("Please enter Serial Number.");
			errorAlert.showAndWait();
			return;
		}
		
		tempBook=new Book(serialNumberTextField1.getText());
		
		// Search DB for a book with this serial number
		MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_DELETEBOOK,tempBook);
		MainClient.client.accept(message);
		Thread.sleep(1500);
		// Check if book not exists.
    	if (resultBookForDeleteBook == null) {
    		errorAlert.setTitle("Failed");
    		errorAlert.setContentText("Book is not exists in library's inventory,or the book is borrowed or ordered by subscriber!");
    		errorAlert.showAndWait(); 
  			return;
    	}
    	
    	// Book found in library, show it's information
    	titleTextField.setText(resultBookForDeleteBook.getBookTitle());
		authorTextField.setText(resultBookForDeleteBook.getAuthorName());
		serialNumberTextField2.setText(resultBookForDeleteBook.getBookID());
		editionTextField.setText(String.valueOf(resultBookForDeleteBook.getEditionNumber()));
		subjectsTextField.setText(resultBookForDeleteBook.getBookGenre());
		freeTextField.setText(resultBookForDeleteBook.getBookDescription());
		quantityTextField.setText(String.valueOf(resultBookForDeleteBook.getOriginalQuantity()));
		locationTextField.setText(resultBookForDeleteBook.getShelfLocation());
		pdfPathTextField.setText(resultBookForDeleteBook.getPdfPath());
		demandTextField.setText(resultBookForDeleteBook.getBookDemand());

	}
	
	/*
	 * Delete book
	 */
	@FXML
	void deleteBook(ActionEvent action) {
		Alert errorAlert=new Alert(Alert.AlertType.ERROR);
		Alert successAlert=new Alert(AlertType.CONFIRMATION);
		Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
		
		confirmAlert.setTitle("Confirm");
		confirmAlert.setHeaderText("Are you sure?");
		confirmAlert.setContentText("Delete a book cannot be undone.");

		Optional<ButtonType> result = confirmAlert.showAndWait();
		if (result.get() == ButtonType.OK){
		    // ... user chose OK
			MessageCS message = new MessageCS(MessageType.DELETE_BOOK,resultBookForDeleteBook);
			try {
				MainClient.client.accept(message);
				Thread.sleep(1500);
				// deletes Table of Content
				
			}
			
			catch (Exception e) { 
				errorAlert.setTitle("Failed");
				errorAlert.setContentText("Delete book failed.");
				errorAlert.showAndWait(); 
				return;
			}
			successAlert.setTitle("Succcess");
			successAlert.setContentText("Book successfully deleted.");
			successAlert.showAndWait(); 
			
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
	}
	
	
	/*
	 * clears all data inserted by the user from the fields when button is pressed
	 */
	@FXML
	void clearFields(ActionEvent event) 
	{
		titleTextField.setText("");
		authorTextField.setText("");
		serialNumberTextField1.setText("");
		serialNumberTextField2.setText("");
		editionTextField.setText("");
		subjectsTextField.setText("");
		freeTextField.setText("");
		quantityTextField.setText("");
		locationTextField.setText("");
		pdfPathTextField.setText("");
		demandTextField.setText("");
	}

	
	
}
