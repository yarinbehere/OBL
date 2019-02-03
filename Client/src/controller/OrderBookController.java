package controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import boundary.LoadGUI;
import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import entity.BookOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderBookController implements Initializable{

	@FXML private TextField bookNameTextField;
	@FXML private Button searchBookButton;
	@FXML private Button placeOrderButton;
	@FXML private Button helpButton;
	@FXML private Button returnButton;
	@FXML private Text pathLabel;
	@FXML private Button cancelButton;
	@FXML private Label bookCodeLabel;
	@FXML private Text soonestReturnLabel;

	@FXML private TableView<Book> ordersTableView;
	@FXML private TableColumn<Book, String> tableColumnTitle;
	@FXML private TableColumn<Book, LocalDate> tableColumnOrderDate;
	@FXML private TableColumn<Book, String> tableColumnQueue;

	public static Book wantedBook = null;
	public static ArrayList<Book> bookResult = null;
	private ObservableList<Book> listOfBooks = null;
	public static String messageBookOrder = null;
	public static Book bookForOrder;


	@FXML 
	void searchBook(ActionEvent event) throws InterruptedException {
		Alert alert = new Alert(AlertType.INFORMATION);
		if(bookNameTextField.getText() == null)
			System.out.println("empty field");
		else
		{
			Book book = new Book(bookNameTextField.getText());
			MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_ORDER,book);
			MainClient.client.accept(message);
			Thread.sleep(400);
			if(wantedBook == null)
			{
				soonestReturnLabel.setVisible(false);
				bookCodeLabel.setVisible(false);
				placeOrderButton.setDisable(true);
				soonestReturnLabel.setText("Book can't be found");
				alert.setTitle("Book Can't be ordered");
				alert.setHeaderText("Book Can't be ordered");
				alert.setContentText("Either the book title is wrong or you can find it in the library");
				alert.showAndWait();
			}
			else
			{
				placeOrderButton.setDisable(false);
				soonestReturnLabel.setText(wantedBook.getSoonestReturn().toString());
				bookCodeLabel.setText(wantedBook.getBookID());
				soonestReturnLabel.setVisible(true);
				bookCodeLabel.setVisible(true);
			}

		}
	}

	//initialize the page: all componenets that needed to be disabled and invisible
	//initialize also the TableView
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bookCodeLabel.setVisible(false);
		soonestReturnLabel.setVisible(false);
		placeOrderButton.setDisable(true);

		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("BookTitle"));
		tableColumnOrderDate.setCellValueFactory(new PropertyValueFactory<>("DateOrder"));
		tableColumnQueue.setCellValueFactory(new PropertyValueFactory<>("Queue"));

		MessageCS message = new MessageCS(MessageType.LIST_OF_ORDERS,LoginController.subscriberResult);
		MainClient.client.accept(message);
		try {
			Thread.sleep(400);
			listOfBooks = FXCollections.observableArrayList(bookResult);//insert those items first in the collection
			ordersTableView.setItems(listOfBooks);
		} catch (InterruptedException e) {
			e.printStackTrace();
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

	@FXML
	void placeOrder(ActionEvent event) throws InterruptedException, ParseException {
		Alert alert = new Alert(AlertType.INFORMATION);
		if(wantedBook != null)
		{

			if(listOfBooks != null)
			{
				for(int i = 0;i<listOfBooks.size();i++)
				{
					if(listOfBooks.get(i).getBookID().equals(wantedBook.getBookID()))
					{
						alert.setTitle("Book Exists");
						alert.setHeaderText("Book Exists");
						alert.setContentText("You have this book already in your list");
						alert.showAndWait();
						placeOrderButton.setDisable(true);
						return;
					}
				}
			}		
		}
		BookOrder bookOrder = new BookOrder(bookNameTextField.getText(),LoginController.subscriberResult.getSubscriberDetails());
		MessageCS message = new MessageCS(MessageType.ORDER_BOOK,bookOrder);
		MainClient.client.accept(message);
		Thread.sleep(100);
		//returned queue 0 from server which means the queue is full
		if(bookForOrder == null)
		{
			alert.setTitle("Full queue");
			alert.setHeaderText("The queue is full");
			alert.setContentText("The queue is full please try again next time!");
			alert.showAndWait();
			placeOrderButton.setDisable(true);
			return;
		}
		//the book can be ordered
		else
		{
			bookResult.add(bookForOrder);
			//set the date from the controller to the last item of the ArrayList 
			bookResult.get(bookResult.size()-1).setDateOrder(bookResult.get(bookResult.size()-1).getDateOrder());

			listOfBooks = FXCollections.observableArrayList(bookResult);//insert those items first in the collection

			ordersTableView.setItems(listOfBooks);
			Thread.sleep(100);

			alert.setTitle("Success");
			alert.setHeaderText("Success");
			alert.setContentText("You have ordered "+ bookResult.get(bookResult.size()-1).getBookTitle()+ " successfully");
			alert.showAndWait();

		}
		//disable the button, so won't be able to make 2nd attempt to order
		placeOrderButton.setDisable(true);
	}

	/**
	 * a row in TableView has been clicked
	 * @param event
	 * @author Roman
	 */
	@FXML
	void chooseBookFromTableView(MouseEvent event) 
	{
		if(ordersTableView.hasProperties())
			cancelButton.setDisable(false);
		if(ordersTableView.getSelectionModel().getSelectedItem() == null)
			cancelButton.setDisable(true);

	}

	@FXML
	void cancelOrder(ActionEvent event) throws InterruptedException {
		//get the bookID from the table
		BookOrder bookOrder = new BookOrder(ordersTableView.
				getSelectionModel().getSelectedItem().getBookID(),
				LoginController.subscriberResult.getSubscriberDetails());
		MessageCS message = new MessageCS(MessageType.CANCEL_ORDER,bookOrder);
		MainClient.client.accept(message);
		Book selectedItem = ordersTableView.getSelectionModel().getSelectedItem();
		ordersTableView.getItems().remove(selectedItem);
		bookResult.remove(selectedItem);
		listOfBooks.remove(selectedItem); 
		//disable the button every time deleted item just in case the last item is deleted
		//won't be able to press on it
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Book deletion");
		alert.setHeaderText("book deleted");
		alert.setContentText("The book has been deleted");
		alert.showAndWait();
		cancelButton.setDisable(true);
	}

}
