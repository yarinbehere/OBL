package common.boundary;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoadGUI {
	
	public static void loadFXML(String path,Button button) throws IOException
	{
		String tempPath = "/common/gui/login/";
		Stage stage = (Stage)button.getScene().getWindow();
        Parent root = FXMLLoader.load(Main.class.getResource(tempPath + path));
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show(); 
	}

}
