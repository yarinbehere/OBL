package controller;

import java.awt.Desktop;
import java.awt.Scrollbar;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import entity.User.Role;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SearchBookController implements Initializable{
	
    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private TextField freeTextField;
    
    @FXML private Button searchButton;
    @FXML private Button clearButton;
    @FXML private Button closeButton;
    @FXML private Button selectBookButton;
    @FXML private Button returnButton;
    
    @FXML private ChoiceBox<String> subjectChoiceBox;
    
    @FXML private TableView<Book> searchResultTable;
    @FXML private TableColumn<Book, String> tableColumnTitle;
    @FXML private TableColumn<Book, String> tableColumnAvailable;
    @FXML private TableColumn<Book, String> tableColumnShelfLocation;
    @FXML private TableColumn<Book, String> tableColumnSoonestReturn;
    
    public static ArrayList<Book> bookResult;
    public static FileTransfer tableOfContent;
    private ObservableList<Book> listOfBooks;

    
    //initialize the ChoiceBox and TableView
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> subjectList = FXCollections.observableArrayList
				("","Action","Fantasy","Comedy","Software","Horror");
		subjectChoiceBox.setValue("");
		subjectChoiceBox.setItems(subjectList);
		//initialize the TableView
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
		tableColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
		tableColumnShelfLocation.setCellValueFactory(new PropertyValueFactory<>("ShelfLocation"));
		tableColumnSoonestReturn.setCellValueFactory(new PropertyValueFactory<>("SoonestReturn"));

	}
    /** 
     * clears all data inserted by the user from the fields when button is pressed
     * @param event
     */
    @FXML
    void clearFields(ActionEvent event) 
    {
    	titleTextField.setText("");
    	authorTextField.setText("");
    	freeTextField.setText("");
    	subjectChoiceBox.setValue("");
    	searchResultTable.getItems().clear();
    	selectBookButton.setDisable(true); 
    }

    @FXML
    void searchBook(ActionEvent event) throws InterruptedException {
    	if((titleTextField.getText().equals(""))&&(authorTextField.getText().equals(""))&&
    			((subjectChoiceBox.getValue()).equals(""))&&(freeTextField.getText().equals(""))) {
    		//Alert java
    		Alert alert = new Alert(AlertType.ERROR, "empty fields", ButtonType.CANCEL);
    		alert.showAndWait();
    	}
    	else {
    	selectBookButton.setDisable(true);//if user pressed search again, disable the button
    	Book book = new Book //build an entity of type book with parameters inserted from user
    			(titleTextField.getText(), authorTextField.getText(), 
    					subjectChoiceBox.getSelectionModel().getSelectedItem(), freeTextField.getText());
    	MessageCS message = new MessageCS(MessageType.SEARCH_BOOK, book); //build the type of message we want the server to perfrom
    	MainClient.client.accept(message);//send message to the client
    	Thread.sleep(1500);//need to fix this with real threads instead of making main thread sleep
    	if(bookResult.isEmpty()) {
    		//Alert java
    		Alert alert = new Alert(AlertType.ERROR, "No book found", ButtonType.CANCEL);
    		alert.showAndWait();
    	}
    	else {
    	//set the items in the table list
    	listOfBooks = FXCollections.observableArrayList(bookResult);//insert those items first in the collection
    	searchResultTable.setItems(listOfBooks);
    	searchResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
    	}
    	}
    }
    /**
     * a row in TableView has been clicked
     * @param event
     * @author Roman
     */
    @FXML
    void chooseBookFromTableViwe(MouseEvent event) 
    {
    	if(searchResultTable.hasProperties())
    		selectBookButton.setDisable(false);
    	if(searchResultTable.getSelectionModel().getSelectedItem() == null)
    		selectBookButton.setDisable(true);
    	
    }
    
    /**
     * opening the table of content of the chosen book by it's serial number
     * @param event
     * @throws InterruptedException
     * @throws IOException
     * @author Roman
     */
    
    @FXML
    void showTableOfContent(ActionEvent event) throws InterruptedException, IOException {
    	Book book = searchResultTable.getSelectionModel().getSelectedItem();
    	MessageCS message = new MessageCS(MessageType.TABLE_OF_CONTENT,book);
    	MainClient.client.accept(message);//send message to the client
    	Thread.sleep(1500);
    	//String path = "/Resources/" + tableOfContent.getFileName() + ".pdf";
    	File newFile = new File ("C:\\Client\\pdf\\" +tableOfContent.getFileName()+ ".pdf");//write the file to location and added "1" to differ from main file
	    FileOutputStream fos = new FileOutputStream(newFile);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);			  
	    bos.write(tableOfContent.getMybytearray(),0,tableOfContent.getSize());
	    bos.flush();
    	bos.close();
    	Desktop desktop = Desktop.getDesktop();
    	desktop.open(newFile);//open the file after it finished writing it
    }
    
    /**
	 * Return to Librarian Menu, Manager Menu, Subscriber Menu or Reader Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {
		 if(LoginController.userResult==null)
			 LoadGUI.loadFXML("ReaderMenu.fxml",returnButton);
		 else if(LoginController.userResult.getRole()==Role.MANAGER)
	    	 LoadGUI.loadFXML("ManagerMenu.fxml", returnButton);
		 else if(LoginController.userResult.getRole()==Role.SUBSCRIBER)
			 LoadGUI.loadFXML("SubscriberMenu.fxml",returnButton);
		 else  LoadGUI.loadFXML("LibrarianMenu.fxml", returnButton); 
		 
	 }

}
