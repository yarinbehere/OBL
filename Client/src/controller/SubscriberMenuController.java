package controller;

import java.io.IOException;

import boundary.LoadGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class SubscriberMenuController {
	
    @FXML private Button searchBookButton;
    @FXML private Button extensionRequestButton;
    @FXML private Button actvityLogButton;
    @FXML private Button orderBookButton;
    @FXML private Button personalInformationButton;
    @FXML private Text pathLabel;
    @FXML private Button helpButton;
    @FXML private Button logoutButton;
    @FXML private Text firstNameLabel;
    
    @FXML
    void searchBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("SearchBook.fxml", searchBookButton);
    }
    
    @FXML
    void orderBook(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("OrderBook.fxml", orderBookButton);
    }

}
