package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchBookController implements Initializable{
	
    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
   
    @FXML private Button searchButton;
    @FXML private Button clearButton;
    @FXML private Button helpButton;
    @FXML private Button closeButton;
    @FXML private Button selectBookButton;
    
    @FXML private ChoiceBox<String> subjectChoiceBox;
    @FXML private TextField freeTextField;

    @FXML private ListView<?> searchResult;

    
    //initialize the choicebox with genre options
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> subjectList = FXCollections.observableArrayList
				("Action","Fantasy","Comedy","Software","Horror");
		subjectChoiceBox.setValue("Action");//make the first value chosen as "Action"
		subjectChoiceBox.setItems(subjectList);

	}
}
