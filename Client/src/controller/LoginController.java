package controller;
import java.io.IOException;
import java.util.ArrayList;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.User;
import entity.User.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

	public class LoginController 
	{
		@FXML private TextField usernameTextField;
		@FXML private PasswordField passwordTextField;
		@FXML private Text pathLabel;
	    
		@FXML private Hyperlink forgotLink;
	    
		@FXML private Button loginButton;
	    @FXML private Button helpButton;
	    @FXML private Button returnButton;
	    @FXML private Button mainMenuButton;
	    MainClient main;
	    
	    public static Role userRole = null;
	    
	    @FXML
	    void loginToOBL(ActionEvent event) throws IOException {
	    	User user = new User(usernameTextField.getText(),passwordTextField.getText());
	    	MessageCS message = new MessageCS(MessageType.LOGIN,user);
	    	MainClient.client.accept(message);
	    	if(userRole == Role.SUBSCRIBER)
	    	{
	    		System.out.println("fail");
	    		System.out.println(userRole);
	    		LoadGUI.loadFXML("SubscriberMenu.fxml",loginButton);
	    	}
	    	else if(userRole == Role.LIBRARIAN)
	    	{
	    		LoadGUI.loadFXML("LibrarianMenu.fxml",loginButton);
	    	}
	    	else
	    	{
	    		
	    	}
	    	
	    }

	}



