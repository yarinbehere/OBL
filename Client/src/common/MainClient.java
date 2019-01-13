package common;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.application.Application;
import javafx.application.Application.Parameters;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainClient extends Application implements Initializable{
	private VBox mainLayout;
	private Stage primaryStage;
	
	public static void main(String[] args) 
	{
	   launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showLoginForm();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String host = "";
	    int port = 0;  //The port number
	    try
	    {
	   	Parameters params = getParameters();                    
	    	List<String> commandLineList = params.getRaw();  
	    	host = commandLineList.get(0);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	    }
	}
	
	public void showLoginForm() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("/common/gui/fxml/ReaderMenu.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
