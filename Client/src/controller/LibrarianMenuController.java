/**
 * 
 */
package controller;

import java.io.IOException;

import boundary.LoadGUI;
import common.MainClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * @author omri braymok
 *
 */
public class LibrarianMenuController {
	MainClient main;
	VBox mainLayout;
	
	@FXML
	private Button borrowBookButton;
	
	@FXML
	void borrowBookAction(ActionEvent event) throws IOException 
	{
		LoadGUI.loadFXML("Borrow.fxml",borrowBookButton);
	}
	

}
