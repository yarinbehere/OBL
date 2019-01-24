package controller;

import java.io.IOException;

import boundary.LoadGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class LibrarianMenuController {
	
    @FXML
    private Button borrowBookButton;

    @FXML
    private Button manageInventoryButton;

    @FXML
    private Button returnBookButton;

    @FXML
    private Button reviewSubscriberButton;

    @FXML
    private Button searchBookButton;

    @FXML
    private Button createSubscriberButton;

    @FXML
    private Text pathLabel;

    @FXML
    private Button helpButton;

    @FXML
    private Button logoutButton;

    @FXML
    private Text firstnameLabel;

    @FXML
    void openBorrowBookPage(ActionEvent event) throws IOException {
    	LoadGUI.loadFXML("BorrowBook.fxml", borrowBookButton);
    }


}
