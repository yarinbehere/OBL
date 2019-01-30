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
public class SubscriberMenuController {
	@FXML
	private Button extensionRequestButton;
	
	@FXML
	void extensionRequest(ActionEvent event)  {
		try {
			LoadGUI.loadFXML("Request.fxml", extensionRequestButton);
		} catch (IOException e) {
			System.out.println("runtime error ioexception: while opening 'Request.fxml'");
		}
	}
}
