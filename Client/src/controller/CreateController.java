package controller;

import java.io.IOException;
import java.util.Random;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
import entity.User.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
//import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author shalev
 *
 */
public class CreateController {
	@FXML
	private TextField firstnameTextField;
	@FXML
	private TextField lastnameTextField;
	@FXML
	private TextField idTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private Button createButton;
	@FXML
	private Button cancelButton;
	@FXML
	public TextArea requestInfoTextArea;
	public static Subscriber foundSubscriber;

	// assumption: input is valid
	@FXML
	/** creates a new subscriber in the system with a randomly generated password, based on 
	 * the details provided by the librarian/manager */
	void create(ActionEvent event) throws InterruptedException {
		// generates a password for the new subscriber
		Random r = new Random();
		char c1 = (char) (r.nextInt(26) + 'a');
		char c2 = (char) (r.nextInt(26) + 'a');
		int fourdigitNum = 1000 + r.nextInt(10000 - 1000);
		// assigns the nodes values to meaningful named locals
		String id = idTextField.getText();
		String userName = usernameTextField.getText();
		String firstName = firstnameTextField.getText();
		String lastName = lastnameTextField.getText();
		String phone = /* phoneChoiceBox.getValue() + */ phoneTextField.getText();
		String email = emailTextField.getText();
		String password = "" + c1 + c2 + String.valueOf(fourdigitNum);

		// sends the server a new subscriber initialized with the above state
		Subscriber newSubscriber = new Subscriber(id, userName, firstName, lastName, phone, email, password);
		MessageCS message = new MessageCS(MessageType.CREATE_SUBSCRIBER, newSubscriber);
		MainClient.client.accept(message);
		// wait for server processing
		Thread.sleep(1500);
		// informs the librarian that the subscriber was added successfully
		if (foundSubscriber == null) {
			requestInfoTextArea.setText("Subscriber already exists");
		} else {
			requestInfoTextArea.setText(
					foundSubscriber.getFirstName() + " " + foundSubscriber.getLastName() + " was created succesfully");
		}
	}
	
	/**
	 * Return to Librarian Menu or Manager Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {
		 if(LoginController.userResult.getRole()==Role.LIBRARIAN)
		 {
			 LoadGUI.loadFXML("LibrarianMenu_UPDATED.fxml",cancelButton); 
		 }
		 else
	    	{
	    		LoadGUI.loadFXML("ManagerMenu.fxml",cancelButton);
	    	}
	 }
}
