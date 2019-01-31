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
    @FXML private Button helpButton;
    @FXML private Button logoutButton;
    
    @FXML private Text pathLabel;
   
    @FXML private Text firstNameLabel;

    @FXML
    void openOrderBookPage(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("OrderBook.fxml", orderBookButton);
    }
    
    @FXML
    void viewActvityLog(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("ActivityLog.FXML", actvityLogButton);
    }
}
