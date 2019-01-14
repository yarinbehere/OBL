package common.gui.fxml;

import java.io.IOException;

import application.Main;
import common.ChatClient;
import common.ClientConsole;
import common.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReaderMenuController {
	Main main;
	MainClient mainClient;
	ClientConsole client;
	VBox mainLayout;
	
	 @FXML
	    private Button loginButton;

	    @FXML
	    private Button searchBookButton;

	    @FXML
	    private Button helpButton;

	    @FXML
	    private Button exitButton;

	
	   @FXML
	    void loginAccount(ActionEvent event) throws IOException {
		    Stage stage = (Stage)loginButton.getScene().getWindow();
	        Parent root = FXMLLoader.load(getClass().getResource("/common/gui/login/Login.fxml"));
	        Scene scene=new Scene(root);
	        stage.setScene(scene);
	        stage.show();   
	    }
	   
	   

}
