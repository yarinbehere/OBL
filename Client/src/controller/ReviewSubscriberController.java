/**
 * 
 */
package controller;

import java.io.IOException;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Subscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author shalev kubi
 *
 */
public class ReviewSubscriberController {
	// first rectangle
	@FXML
	private TextField subscriberEdittxt;
	@FXML
	private Button searchSubscriberButton;
	// second rectangle
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField idTextField;
	@FXML
	private TextField firstnameTextField;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField phoneTextField;
	@FXML
	private TextField lastnameTextField;
	@FXML
	private ChoiceBox<String> statusChoiceBox;
	@FXML
	private Button saveChangesButton;
	@FXML
	private Button resetButton;
	@FXML
	private Label feedbackLabel;
	public static Subscriber foundSubscriber;

	@FXML
	/** Initializing the choice box */
	public void initialize() {
		// initializes status choice box
		statusChoiceBox.getItems().add("Active");
		statusChoiceBox.getItems().add("Frozen");
		statusChoiceBox.getItems().add("Locked");
	}

	/** populates the labels with the requested users details from the database */
	@FXML
	public void searchSubscriber(ActionEvent event) throws IOException, InterruptedException {
		String subscriberId = subscriberEdittxt.getText();
		MessageCS message = new MessageCS(MessageType.REVIEW_SUBSCRIBER_SEARCH, subscriberId);
		MainClient.client.accept(message);
		// wait for server processing
		Thread.sleep(400);
		if (foundSubscriber == null) {
			subscriberEdittxt.setText("not valid, try again!");
		} else {
			// showing the subscriber on screen
			usernameTextField.setText(foundSubscriber.getUserName());
			idTextField.setText(foundSubscriber.getId());
			firstnameTextField.setText(foundSubscriber.getFirstName());
			emailTextField.setText(foundSubscriber.getEmail());
			phoneTextField.setText(foundSubscriber.getPhone());
			lastnameTextField.setText(foundSubscriber.getLastName());
			statusChoiceBox.setValue(foundSubscriber.getSubscriberStatus());
		}
	}

	/** updates the subscriber in the database */
	@FXML
	public void saveChanges(ActionEvent event) throws IOException, InterruptedException {
		if (foundSubscriber == null) {
			subscriberEdittxt.setText("not valid, try again!");
		} else {
			String id = idTextField.getText();
			String userName = usernameTextField.getText();
			String firstName = firstnameTextField.getText();
			String lastName = lastnameTextField.getText();
			String phone = phoneTextField.getText();
			String email = emailTextField.getText();

			Subscriber subscriber = new Subscriber(id, null, firstName, lastName, phone, email, null);
			subscriber.setSubscriberStatus(statusChoiceBox.getValue());
			MessageCS message = new MessageCS(MessageType.REVIEW_SUBSCRIBER_UPDATE, subscriber);
			MainClient.client.accept(message);
			// wait for server processing
			Thread.sleep(400);
			if (foundSubscriber == null) {
				feedbackLabel.setText("Updating subscriber failed");
			} else {
				feedbackLabel.setText(foundSubscriber.getFirstName() + " " + foundSubscriber.getLastName()
						+ " was updated succesfully");
			}
		}
	}
	/** resets all fields in the lower rectangle */
	@FXML
	public void resetFields(ActionEvent event) throws IOException, InterruptedException{
		idTextField.setText("");
		usernameTextField.setText("");
		firstnameTextField.setText("");
		lastnameTextField.setText("");
		phoneTextField.setText("");
		emailTextField.setText("");
	}
}