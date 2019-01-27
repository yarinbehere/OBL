/**
 * 
 */
package controller;

import java.io.IOException;

import boundary.LoadGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



/**
 * @author שלו קובי
 *
 */
public class ManagerMenuController {
	@FXML
	private Button reviewSubscriberButton;

	@FXML
	void reviewSubscriber(ActionEvent event) throws IOException {
		LoadGUI.loadFXML("ReviewSubscriber.fxml", reviewSubscriberButton);
	}
}
