
package controller;

import java.io.IOException;

import boundary.LoadGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ManagerMenuController {

	@FXML private Button borrowBookButton;
	@FXML private Button addNewBookButton;
	@FXML private Button returnBookButton;
	@FXML private Button reviewSubscriberButton;
	@FXML private Button searchBookButton;
	@FXML private Button createSubscriberButton;
	@FXML private Button updateBookButton;
	@FXML private Button deleteBookButton;

	@FXML private Button generateReportButton;
	@FXML private Button viewDatabaseButton;


	@FXML private Text pathLabel;
	@FXML private Button logoutButton;
	@FXML private Text firstnameLabel;
	
	@FXML
    void searchBook(ActionEvent event) throws IOException {
		LoadGUI.loadFXML("SearchBook.fxml", searchBookButton);
    } 
	
    @FXML
    void borrowBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("BorrowBook.fxml", borrowBookButton);
    }
    
    @FXML
    void returnBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("BookReturn.fxml", returnBookButton);
    }
    
    @FXML
    void addNewBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("AddNewBook.fxml", addNewBookButton);
    }
    
    @FXML
    void updateBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("UpdateBook.fxml", updateBookButton);
    }
    

    @FXML
    void deleteBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("DeleteBook.fxml", deleteBookButton);
    }
    

    @FXML
    void createSubscriber(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("CreateSubscriber.fxml", createSubscriberButton);
    }
    
    @FXML
    void reviewSubscriber(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("ReviewSubscriber.fxml", reviewSubscriberButton);
    }
    @FXML
    void viewDataBase(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("ViewDatabase.fxml", viewDatabaseButton);
    }
    
    @FXML
    public void logout(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("ReaderMenu.fxml", logoutButton);
    }

    /**
     * @author Yarin
     */
    @FXML
    void generateReport(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("GenerateReports.fxml", generateReportButton);
    }

}
