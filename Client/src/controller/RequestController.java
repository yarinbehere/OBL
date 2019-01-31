/**
 * 
 */
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * @author shalev kubi
 *
 */
public class RequestController {
	@FXML
	private TableView<String> borrowdBooks;
	@FXML
	private Button requestExtensionButton;
	@FXML
	private TableView<Book> searchResultTable;
	@FXML
	private TableColumn<Book, String> tableColumnsubscriberId;
	@FXML
	private TableColumn<Book, String> tableColumnfirstName;
	@FXML
	private TableColumn<Book, String> tableColumnBookId;
	@FXML
	private TableColumn<Book, String> tableColumnTitle;
	@FXML
	private TableColumn<Book, String> tableColumnBorrowDate;
	@FXML
	private TableColumn<Book, String> tableColumnReturnDate;
	@FXML
	private void initialize() {
		ObservableList<String> headingsList = FXCollections.observableArrayList("subscriptionNumber", "firstName",
				"bookId", "title", "borrowDate", "returnDate");
	}

	@FXML
	void requestExtensionButton() {

	}

}
