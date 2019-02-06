package controller;

import java.net.URL;

import java.sql.Date;
import java.util.ArrayList;
import java.util.ResourceBundle;

import boundary.LoadGUI;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Desktop;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import entity.User.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
	@FXML private TextField editionTextField;
	@FXML private TextField subjectsTextField;
	@FXML private TextField freeTextField;
	@FXML private TextField quantityTextField;
	@FXML private TextField locationTextField;
	@FXML private TextField pdfPathTextField;
	@FXML private Button addNewBookButton;
	@FXML private Button clearButton;
	@FXML private Button returnButton;
	@FXML private Button helpButton;
	@FXML private Button uploadPdfButton;
	@FXML private ChoiceBox<String> demandChoiceBox;

	private boolean pdfUploadedFlag;
	
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
		this.pdfUploadedFlag=false;
	}
	
	/*
	 * Add new book to the inventory
	 */
	@FXML
	void addNewBook(ActionEvent event) throws InterruptedException{
		
		// Vars init
		ArrayList<String> errorsList=new ArrayList<>();
		Alert errorAlert=new Alert(Alert.AlertType.ERROR);
		Alert successAlert=new Alert(AlertType.CONFIRMATION);
		Book tempBook;
		Date tempDate;
		int quantity,edition;
		
		/* Check all field are neccesery */
	
		if(titleTextField.getText().isEmpty()) {
			errorsList.add("Title");
		}
		if(authorTextField.getText().isEmpty()) {
			errorsList.add("Author");
		}
		if(serialNumberTextField.getText().isEmpty()) {
			errorsList.add("Serial number");
		}
		if(editionTextField.getText().isEmpty()) {
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
    		errorAlert.setContentText("Please enter "+errorsList.toString()+", and try again.");
    		errorAlert.showAndWait();
			return;
		}
		
		
		// Check if it's avaliable to open the PDF file & check the Flag
		if (pdfUploadedFlag==false) {
			// Show error
			errorAlert.setTitle("Failed");
			errorAlert.setContentText("Please upload PDF file and try again.");
			errorAlert.showAndWait();
			return;
		}
		
		
		/* Continue by creating temporary book Object and validate it in the DB */
		
		tempBook=new Book(serialNumberTextField.getText());
		tempDate=new Date(0);
		quantity=Integer.parseInt(quantityTextField.getText());
		String editionString=editionTextField.getText();
		
		// Search DB for a book with this serial number
		MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_ADDNEWBOOK,tempBook);
    	MainClient.client.accept(message);
    	Thread.sleep(1500);
    	
    	// Check if book already exists.
    	if (!(resultBookForAddNewBook == null)) {
    		errorAlert.setTitle("Failed");
    		errorAlert.setContentText("Book already exists in inventory: "+resultBookForAddNewBook.getBookTitle());
    		errorAlert.showAndWait(); 
  			return;
    	}
    	
    	// Continue by creating a new Book object
    	tempBook=new Book(serialNumberTextField.getText(), titleTextField.getText(), authorTextField.getText(), editionString,
    			subjectsTextField.getText(), freeTextField.getText(), quantity, tempDate.toString(), serialNumberTextField.getText(), "", locationTextField.getText(), pdfPathTextField.getText(),
    			quantity, demandChoiceBox.getSelectionModel().getSelectedItem(), tempDate,demandChoiceBox.getSelectionModel().getSelectedItem());

    	// Add this Book object to DB
    	message = new MessageCS(MessageType.ADD_NEW_BOOK,tempBook);
    	MainClient.client.accept(message); 
    	Thread.sleep(1500);
    	successAlert.setTitle("Add New Book");
    	successAlert.setContentText("Book "+tempBook.getBookID()+": "+tempBook.getBookTitle()+" successfully added to inventory.");
    	successAlert.showAndWait();
		
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
		editionTextField.setText("");
		subjectsTextField.setText("");
		freeTextField.setText("");
		quantityTextField.setText("");
		locationTextField.setText("");
		pdfPathTextField.setText("");
		demandChoiceBox.setValue("Normal");
		pdfUploadedFlag=false;
	}

	
	/* Click on Upload PDF */
	@FXML
    void uploadPDF(ActionEvent event) throws IOException {
		Alert successAlert=new Alert(AlertType.INFORMATION);
		Alert errorAlert=new Alert(Alert.AlertType.ERROR);
		
		// Check PDF file path
		if(pdfPathTextField.getText().isEmpty()) {
			errorAlert.setTitle("Failed");
			errorAlert.setContentText("Please enter PDF file name, same as the Title of the book.");
			errorAlert.showAndWait();
			return;
		}
		
		// Check if title match to PDF file name
		if(titleTextField.getText().equals(pdfPathTextField.getText())==false)
		{
			// Show error if not
			errorAlert.setTitle("Failed");
    		errorAlert.setContentText("Please enter matching Title and PDF File Name.");
    		errorAlert.showAndWait();
    		return;
		}
		// Check if valid
		try
		{
			FileTransfer tableOfContent = new FileTransfer(titleTextField.getText());//initialize the entity FileTransfer with the title book
			File newFile = new File ("C:\\Client\\pdf\\" + pdfPathTextField.getText() +".pdf");//get the file and it's location
			byte [] mybytearray  = new byte [(int)newFile.length()];
			FileInputStream fis = new FileInputStream(newFile);
			BufferedInputStream bis = new BufferedInputStream(fis);			 
			tableOfContent.initArray(mybytearray.length);
			tableOfContent.setSize(mybytearray.length);
			bis.read(tableOfContent.getMybytearray(),0,mybytearray.length);
			MessageCS file = new MessageCS(MessageType.UPLOAD_NEW_PDF,tableOfContent);
			MainClient.client.accept(file);
			bis.close();
			
		}
		catch(FileNotFoundException e)
		{
			// Show error
			errorAlert.setTitle("Failed");
			errorAlert.setContentText("Could not upload file.");
			errorAlert.showAndWait();
			return;
		}
		
		// Turn flag ON
		this.pdfUploadedFlag=true;
		successAlert.setTitle("Add New Book");
		successAlert.setContentText("Table of content PDF has been successfully uploaded to the system.");
		successAlert.showAndWait();
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
			 LoadGUI.loadFXML("LibrarianMenu.fxml", returnButton); 
		 }
		 else
	    	{
	    		LoadGUI.loadFXML("ManagerMenu.fxml", returnButton);
	    	}
	 }



}
