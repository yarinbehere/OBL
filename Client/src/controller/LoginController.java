package controller;

import java.io.IOException;
import java.util.ArrayList;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
import entity.User;
import entity.User.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	@FXML private Button returnButton;
	@FXML private Button mainMenuButton;
	@FXML private Button searchBookButton;

	public static Role userRole = null;
	public static User userResult;
	public static Subscriber subscriberResult;
	public static ArrayList<String> finalSubscriberResult;
	public static String message;
	public String listOfLateReturnsSubscribers="There are several subscribers with 3 or more delays in returning a book. \n The Subscribers are:\n\n";

	@FXML
	void loginToOBL(ActionEvent event) throws IOException, InterruptedException {
		User user = new User(usernameTextField.getText(),passwordTextField.getText());
		MessageCS message = new MessageCS(MessageType.LOGIN,user);
		MainClient.client.accept(message);
		//	new ThreadTest.ThreadTest2(message).run();
		Thread.sleep(400);
		//load the page for the specific user
		if(userResult==null) 
		{
			//Alert java
			Alert alert = new Alert(AlertType.ERROR, LoginController.message, ButtonType.CANCEL);
			alert.showAndWait();
		}
		else
		{
			if(userResult.getRole() == Role.SUBSCRIBER)
			{
				Subscriber subscriber = new Subscriber(userResult.getUserName());
				message = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
				MainClient.client.accept(message);
				Thread.sleep(400);
				LoadGUI.loadFXML("SubscriberMenu.fxml",loginButton);
			}
			else if(userResult.getRole() == Role.LIBRARIAN)
			{
				LoadGUI.loadFXML("LibrarianMenu.fxml",loginButton); 
			}
			else
			{
				LoadGUI.loadFXML("ManagerMenu.fxml", loginButton);
				Alert alert=new Alert(Alert.AlertType.INFORMATION);
				//if there are subscribers with 3 or more late return book

				if(finalSubscriberResult.size()>0)
				{
					for (int i = 0; i < finalSubscriberResult.size(); i++) 
					{
						listOfLateReturnsSubscribers=listOfLateReturnsSubscribers + finalSubscriberResult.get(i);
						listOfLateReturnsSubscribers=listOfLateReturnsSubscribers+"\n";
					}
					alert.setTitle("Subscribers Late returns");
					alert.setContentText(listOfLateReturnsSubscribers);
					alert.showAndWait();
				}
			}
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


