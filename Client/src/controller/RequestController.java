/**
 * 
 */
package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.BorrowedBook;
import entity.BorrowsExt;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * @author shalev kubi
 *
 */
public class RequestController {
	public static ArrayList<BorrowsExt> bookBorrowResults;
	@FXML
	private Button requestExtensionButton;
	@FXML
	private Button returnButton;
	// table-view fields
	@FXML
	private TableView<BorrowsExt> borrowedBooksTable;
	@FXML
	private TableColumn<BorrowsExt, String> subscriptionNumber;
	@FXML
	private TableColumn<BorrowsExt, String> firstName;
	@FXML
	private TableColumn<BorrowsExt, String> bookId;
	@FXML
	private TableColumn<BorrowsExt, String> bookTitle;
	@FXML
	private TableColumn<BorrowsExt, String> borrowDate;
	@FXML
	private TableColumn<BorrowsExt, String> returnDate;
	@FXML
	private Label requestFeedback;
	public static String requestAnswer;
	private ObservableList<BorrowsExt> data;

	@FXML
	/** initializes the controller table view and populates it with user borrows */
	private void initialize() throws InterruptedException {
		String userName = LoginController.userResult.getUserName();
		String password = LoginController.userResult.getPassword();
		// sends the server a new subscriber initialized with the above state
		User newUser = new User(userName, password);
		MessageCS message = new MessageCS(MessageType.REQUEST_EXTENSION_INIT, newUser);
		MainClient.client.accept(message);
		// wait for server processing
		Thread.sleep(400);
		// set properties to columns
		subscriptionNumber.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("SubscriptionNumber"));
		firstName.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("FirstName"));
		bookId.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("BookID"));
		bookTitle.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("BookTitle"));
		borrowDate.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("BorrowDate"));
		returnDate.setCellValueFactory(new PropertyValueFactory<BorrowsExt, String>("ReturnDate"));
		// set the items in the table list
		if (bookBorrowResults != null) {
			ObservableList<BorrowsExt> data = FXCollections.observableArrayList(bookBorrowResults);
			borrowedBooksTable.setItems(data);
		}
	}

	@FXML
	/** tries to extend the currently selected row in the table view by a week */
	void requestExtension() throws InterruptedException, IOException {
		requestExtensionButton.setDisable(true);// if user pressed search again, disable the button
		BorrowsExt borrowsExtCurrent = borrowedBooksTable.getSelectionModel().getSelectedItem();

		// check if it is a week or less from borrow return date
		if (ChronoUnit.DAYS.between(LocalDate.now(), borrowsExtCurrent.getReturnDate()) > 7)
			requestFeedback.setText("Failed, the return date is still more than a week away (or has passed)");
		else {
			// in case there are no book orders and some copies are in the library
			// sends the server a borrow to extend initialized with the above state
			String subscriptionNumber = borrowsExtCurrent.getSubscriptionNumber();
			String bookId = borrowsExtCurrent.getBookID();
			LocalDate returnDate = borrowsExtCurrent.getReturnDate();
			LocalDate borrowDate = borrowsExtCurrent.getBorrowDate();
			
			BorrowedBook borrowedBook = new BorrowedBook(subscriptionNumber, bookId, returnDate, borrowDate, -1);
			MessageCS message = new MessageCS(MessageType.REQUEST_EXTENSION_CHECK, borrowedBook);
			MainClient.client.accept(message);
			Thread.sleep(400);
			// checking servers answer
			if (requestAnswer.equals("orders exist")) {
				requestFeedback.setText("There are orders on this book, can't let you extend");
				requestExtensionButton.setDisable(false);
			} else {
				borrowsExtCurrent.setReturnDate(LocalDate.from(borrowsExtCurrent.getReturnDate().plusDays(7)));
				borrowedBooksTable.refresh();
				requestFeedback.setText("extension granted");
			}
		}
	}

	/**
	 * a row in TableView has been clicked
	 * 
	 * @param event
	 * @author Roman
	 */
	@FXML
	void chooseUserBorrow(MouseEvent event) {
		BorrowsExt borrowsExt = borrowedBooksTable.getSelectionModel().getSelectedItem();
		if (borrowsExt != null) {
			requestExtensionButton.setDisable(false);
		}
	}
	
	/**
	 * Return to Subscriber Menu
	 * @param event
	 * @throws IOException
	 */
	 @FXML
	 public void returnAction(ActionEvent event) throws IOException {

				LoadGUI.loadFXML("SubscriberMenu.fxml",returnButton);
	 }
}
