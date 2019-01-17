package controller;

import java.io.IOException;

import boundary.LoadGUI;
import common.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ReaderMenuController {
	MainClient main;
	VBox mainLayout;
	
	 @FXML
	    private Button loginButton;

	    @FXML
	    private Button searchBookButton;

	    @FXML
	    private Button helpButton;

	    @FXML
	    private Button exitButton;

	/**
	 * moving to the login page when login button has been pressed
	 * @param event
	 * @throws IOException
	 * @author Roman
	 */
	   @FXML
	    void loginAccount(ActionEvent event) throws IOException {
		    LoadGUI.loadFXML("Login.fxml", loginButton); 
	    }
	   
}
