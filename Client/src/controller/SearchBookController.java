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

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.FileTransfer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class SearchBookController implements Initializable {

	@FXML
	private TextField titleTextField;
	@FXML
	private TextField authorTextField;
	@FXML
	private TextField freeTextField;

	@FXML
	private Button searchButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button helpButton;
	@FXML
	private Button closeButton;
	@FXML
	private Button selectBookButton;

	@FXML
	private ChoiceBox<String> subjectChoiceBox;

	@FXML
	private TableView<Book> searchResultTable;
	@FXML
	private TableColumn<Book, String> tableColumnTitle;
	@FXML
	private TableColumn<Book, String> tableColumnAvailable;
	@FXML
	private TableColumn<Book, String> tableColumnShelfLocation;
	@FXML
	private TableColumn<Book, String> tableColumnSoonestReturn;

	public static ArrayList<Book> bookResult;
	public static FileTransfer tableOfContent;
	private ObservableList<Book> listOfBooks;

	// initialize the ChoiceBox and TableView
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> subjectList = FXCollections.observableArrayList("Action", "Fantasy", "Comedy",
				"Software", "Horror");
		subjectChoiceBox.setValue("Action");// make the first value chosen as "Action"
		subjectChoiceBox.setItems(subjectList);
		// initialize the TableView
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
		tableColumnAvailable.setCellValueFactory(new PropertyValueFactory<>("Available"));
		tableColumnShelfLocation.setCellValueFactory(new PropertyValueFactory<>("ShelfLocation"));
		tableColumnSoonestReturn.setCellValueFactory(new PropertyValueFactory<>("SoonestReturn"));

	}

	/**
	 * clears all data inserted by the user from the fields when button is pressed
	 * 
	 * @param event
	 */
	@FXML
	void clearFields(ActionEvent event) {
		titleTextField.setText("");
		authorTextField.setText("");
		freeTextField.setText("");
		searchResultTable.getItems().clear();
		selectBookButton.setDisable(true);
	}

	@FXML
	void searchBook(ActionEvent event) throws InterruptedException {
		selectBookButton.setDisable(true);// if user pressed search again, disable the button
		Book book = new Book // build an entity of type book with parameters inserted from user
		(titleTextField.getText(), authorTextField.getText(), subjectChoiceBox.getSelectionModel().getSelectedItem(),
				freeTextField.getText());
		MessageCS message = new MessageCS(MessageType.SEARCH_BOOK, book); // build the type of message we want the
																			// server to perfrom
		MainClient.client.accept(message);// send message to the client
		Thread.sleep(400);// need to fix this with real threads instead of making main thread sleep
		// set the items in the table list
		listOfBooks = FXCollections.observableArrayList(bookResult);// insert those items first in the collection
		searchResultTable.setItems(listOfBooks);
		searchResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		searchResultTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

	}

	/**
	 * a row in TableView has been clicked
	 * 
	 * @param event
	 * @author Roman
	 */
	@FXML
	void chooseBookFromTableViwe(MouseEvent event) {
		if (searchResultTable.hasProperties())
			selectBookButton.setDisable(false);

	}

	/**
	 * opening the table of content of the chosen book by it's serial number
	 * 
	 * @param event
	 * @throws InterruptedException
	 * @throws IOException
	 * @author Roman
	 */

	@FXML
	void showTableOfContent(ActionEvent event) throws InterruptedException, IOException {
		Book book = searchResultTable.getSelectionModel().getSelectedItem();
		MessageCS message = new MessageCS(MessageType.TABLE_OF_CONTENT, book);
		MainClient.client.accept(message);// send message to the client
		Thread.sleep(400);
		// String path = "/Resources/" + tableOfContent.getFileName() + ".pdf";
		File newFile = new File(tableOfContent.getFileName() + "1.pdf");// write the file to location and added "1" to
																		// differ from main file
		FileOutputStream fos = new FileOutputStream(newFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bos.write(tableOfContent.getMybytearray(), 0, tableOfContent.getSize());
		bos.flush();
		bos.close();
		Desktop desktop = Desktop.getDesktop();
		desktop.open(newFile);// open the file after it finished writing it
	}

}