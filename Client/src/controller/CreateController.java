package controller;

import java.io.IOException;
import java.util.Random;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
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
//	@FXML
//	private ChoiceBox<String> phoneChoiceBox; TODO: restore it when learned how to use
	@FXML
	private TextField phoneTextField;
	@FXML
	private Button createButton;
	@FXML
	public TextArea requestInfoTextArea;

	public static String serverAnser;

	// assumption: input is valid
	@FXML
	void create(ActionEvent event) throws IOException, InterruptedException {
		// generates a password for the new subscriber
		Random r = new Random();
		char c1 = (char) (r.nextInt(26) + 'a');
		char c2 = (char) (r.nextInt(26) + 'a');
		int fourdigitNum = 1000 + r.nextInt(10000 - 1000);
		// assigns the nodes values to meaningful named locals
//		String userName = "208";
//		String password = "" + c1 + c2 + String.valueOf(fourdigitNum);
//		String firstName = "moshe";
//		String lastName = "peretz";
//		int id = Integer.parseInt("208");
//		String email = "moshe@gmail.com";
//		String phone = "0502979234";
		
		String userName = usernameTextField.getText();
		String password = "" + c1 + c2 + String.valueOf(fourdigitNum);
		String firstName = firstnameTextField.getText();
		String lastName = lastnameTextField.getText();
		int id = Integer.parseInt(idTextField.getText());
		String email = emailTextField.getText();
		String phone = /* phoneChoiceBox.getValue() + */ phoneTextField.getText();

		// sends the server a new subscriber initialized with the above state
		Subscriber newSubscriber = new Subscriber(userName, password, firstName, lastName, id, email, phone);
		MessageCS message = new MessageCS(MessageType.CREATE_SUBSCRIBER, newSubscriber);
		MainClient.client.accept(message);
		// wait for server processing
		Thread.sleep(100);
		// informs the librarian that the subscriber was added succesfully
		requestInfoTextArea.setText(serverAnser);
	}
}
