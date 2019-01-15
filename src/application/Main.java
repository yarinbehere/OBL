package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	Stage primaryStage;
	VBox mainLayout;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
		    showLoginForm();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showLoginForm() throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ReaderMenu.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
