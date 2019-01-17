package common;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainClient extends Application{
	private VBox mainLayout;
	private Stage primaryStage;
	public static ClientConsole client;
	
	 @FXML
	    private Button loginButton;

	    @FXML
	    private Button searchBookButton;

	    @FXML
	    private Button helpButton;

	    @FXML
	    private Button exitButton;

	
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
	public void init() {
		String host = "";
	    int port = 0;  //The port number
	    try
	    {
	    	Parameters params = getParameters();                    
	    	List<String> commandLineList = params.getRaw();  
	//    	host = commandLineList.get(0);
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	      host = "localhost";
	    }
	     this.client= new ClientConsole(host, 5555);
		 
	}
	
	/**
	 * opens the main page of OBL
	 * @throws IOException
	 * @author Roman
	 */
	public void showLoginForm() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainClient.class.getResource("/boundary/fxml/ReaderMenu.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public ClientConsole getClient() {
		return client;
		
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}
