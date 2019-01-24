/**
 * 
 */
package controller;

import java.io.IOException;

import boundary.LoadGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author shalev
 *
 */
public class LibrarianMenu {

	@FXML
	private Button createSubscriberButton;
	
	
	/** listener for the button press of createSubscriber 
	 * @throws IOException */
	public void createSubscriber() throws IOException {
		LoadGUI.loadFXML("Create.fxml",createSubscriberButton);
	}
}
