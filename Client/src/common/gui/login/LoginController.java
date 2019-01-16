package common.gui.login;
import java.util.ArrayList;


import application.Main;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import common.user.entity.User;
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
	    Main main;
	    
	    @FXML
	    void loginToOBL(ActionEvent event) {
	    	User user = new User(usernameTextField.getText(),passwordTextField.getText());
	    	MessageCS message = new MessageCS(MessageType.LOGIN,user);
	    	MainClient.client.accept(message);
	    	
	    }

	}



