/**
 * 
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

/**Controller checking all personal information of the user and let's update them
 * @author Omri Braymok
 *
 */


public class PersonalInformationController implements Initializable{
	@FXML private TextField userPasswordTextField;
	@FXML private TextField emailTextField;
	@FXML private TextField firstnameTextField;
	@FXML private TextField lastnameTextField;
	@FXML private TextField phoneTextField;
	
	@FXML private Text idLabel; 
	@FXML private Text statusLabel;
	@FXML private Text userNameLabe;
	
	@FXML private ChoiceBox<String> phoneChoiceBox;
	
	@FXML private Button saveChangeButton;
	@FXML private Button resetButton;
	
	public static Subscriber subscriber = null;
	public static User user = null;
	
	
	 /**
     * search user by userResult from LoginController, if user found uploads the user's details
	 * @author Omri braymok
	*/
	 @Override
		public void initialize(URL location, ResourceBundle resources) {
			//User user = new User("203",null);
	    	MessageCS message = new MessageCS(MessageType.PERSONAL_INFORMATION,LoginController.userResult);
	    	MainClient.client.accept(message);
	    	try {
				Thread.sleep(400);
				ObservableList<String> subjectList = FXCollections.observableArrayList
						("050","051","052","053","054","055");
				if(subscriber==null||user==null)
					System.out.println("ERROR");
				else 
					{
					phoneChoiceBox.setItems(subjectList);
					phoneChoiceBox.setValue(subscriber.getMobileNumber().substring(0, 3));
					userNameLabe.setText(user.getUserName());
					emailTextField.setText(subscriber.getEmail());
					firstnameTextField.setText(subscriber.getFirstName());
					lastnameTextField.setText(subscriber.getLastName());
					phoneTextField.setText(subscriber.getMobileNumber().substring(3, 10));
					statusLabel.setText(subscriber.getSubscriberStatus());
					userPasswordTextField.setText(PersonalInformationController.user.getPassword());
					idLabel.setText(subscriber.getSubscriberID());
					}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
	 
	 /**
	  * save the user's new details who inserted by user, if one or more field empty show alert message
	  * @param event
	  * @throws InterruptedException
	  * @author Omri braymok
	  */
	 @FXML
		public void saveChangeButtonAction(ActionEvent event) throws InterruptedException {
		 if(userPasswordTextField.getText().equals("")||
			emailTextField.getText().equals("")||
			firstnameTextField.getText().equals("")||
			lastnameTextField.getText().equals("")||
			phoneTextField.getText().equals("")||
			statusLabel.getText().equals(""))
			{
			//Alert java
			 Alert alert = new Alert(AlertType.ERROR, "empty field-Please do not leave empty fields before saving", 
					 ButtonType.CANCEL);
			 alert.showAndWait();
			}
		else
			{
			user.setPassword(userPasswordTextField.getText());
			subscriber.setEmail(emailTextField.getText());
			subscriber.setFirstName(firstnameTextField.getText());
			subscriber.setLastName(lastnameTextField.getText());
			subscriber.setMobileNumber(phoneChoiceBox.getValue()+phoneTextField.getText());
			MessageCS message = new MessageCS(MessageType.UPDATE_PERSONAL_INFORMATION, user,subscriber);
	    	MainClient.client.accept(message);
	    	Thread.sleep(400);
			}
	 }
	 
	 /** 
	  * reset all user's data 
	  * @param event
	  */ 
	 @FXML
		public void resetButtonAction(ActionEvent event){
		 	phoneChoiceBox.setValue(subscriber.getMobileNumber().substring(0, 3));
			userPasswordTextField.setText(user.getPassword());
			emailTextField.setText(subscriber.getEmail());
			firstnameTextField.setText(subscriber.getFirstName());
			lastnameTextField.setText(subscriber.getLastName());
			phoneTextField.setText(subscriber.getMobileNumber().substring(3, 10));
		}
}
