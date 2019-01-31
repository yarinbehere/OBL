
package controller;
import java.io.IOException;
import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import common.ThreadTest;
import common.ThreadTest.ThreadTest2;
import entity.Subscriber;
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
	    @FXML private Button searchBookButton;
	    
	    public static Role userRole = null;
		public static User userResult;
		public static Subscriber subscriberResult;
	    
	    @FXML
	    void loginToOBL(ActionEvent event) throws IOException, InterruptedException {
	    	User user = new User(usernameTextField.getText(),passwordTextField.getText());
	    	MessageCS message = new MessageCS(MessageType.LOGIN,user);
	    	MainClient.client.accept(message);
	    //	new ThreadTest.ThreadTest2(message).run();
	    	Thread.sleep(3000);
	    	//load the page for the specific user
	    	if(userResult.getRole() == Role.SUBSCRIBER)
	    	{
	    		Subscriber subscriber = new Subscriber(userResult.getUserName());
	    		message = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
	    		MainClient.client.accept(message);
	    		Thread.sleep(3000);
	    		System.out.println(subscriberResult.getSubscriberDetails());
	    		LoadGUI.loadFXML("SubscriberMenu.fxml",loginButton);
	    	}
	    	else if(userResult.getRole() == Role.LIBRARIAN)
	    	{
	    		LoadGUI.loadFXML("LibrarianMenu.fxml",loginButton); 
	    	}
	    	else
	    	{
	    		LoadGUI.loadFXML("ManagerMenu.fxml", loginButton);
	    	}
	    	
	    }
	    
	    @FXML
		void returnPreviousPage(ActionEvent event) throws IOException
	   	{
	   		LoadGUI.loadFXML("ReaderMenu.fxml", returnButton);
	   	}
	    
	    @FXML
		void returnMainMenuPage(ActionEvent event) throws IOException
	   	{
	   		LoadGUI.loadFXML("ReaderMenu.fxml", mainMenuButton);
	   	}
	}


