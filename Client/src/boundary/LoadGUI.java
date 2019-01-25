package boundary;

import java.io.IOException;

import common.MainClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoadGUI {
	
	public static void loadFXML(String fxmlType,Button button) throws IOException
	{
		String path = "/boundary/fxml/";
		Stage stage = (Stage)button.getScene().getWindow();
        Parent root = FXMLLoader.load(MainClient.class.getResource(path + fxmlType));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show(); 
	} 

}
