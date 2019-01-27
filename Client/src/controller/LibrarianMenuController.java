package controller;

import java.io.IOException;

import boundary.LoadGUI;
import common.DataBaseManager;
import common.EchoServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LibrarianMenuController {
	

    @FXML private Button borrowBookButton;
    @FXML private Button manageInventoryButton;
    @FXML private Button returnBookButton;
    @FXML private Button reviewSubscriberButton;
    @FXML private Button searchBookButton;
    @FXML private Button createSubscriberButton;
    @FXML private Button helpButton;
    @FXML private Button logoutButton;
    @FXML private Text pathLabel;
    @FXML private Text firstnameLabel;
    
    
    @FXML
    public void searchBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("SearchBook.fxml", searchBookButton);
    }
    
    
    @FXML
    public void borrowBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("BorrowBook.fxml", borrowBookButton);
    }
  
    @FXML
    public void bookReturn(ActionEvent event) throws IOException
    {
    	LoadGUI.loadFXML("BookReturn.fxml", returnBookButton);
    }
    
    @FXML
    public void createSubscriber(ActionEvent event) throws IOException
    {
    	LoadGUI.loadFXML("CreateSubscriber", createSubscriberButton);
    }
    
    
    
    
}
