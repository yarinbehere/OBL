package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class OrderBookController implements Initializable{

	@FXML private TextField bookNameTextField;
	@FXML private Button searchBookButton;
	@FXML private Button placeOrderButton;
	@FXML private Text subscriberStatusLabel;
	@FXML private Hyperlink bookCodeLabel;
	@FXML private Button helpButton;
	@FXML private Button returnButton;
	@FXML private Text pathLabel;
	@FXML private Button cancelButton;
	@FXML private DatePicker abc;
	
	@FXML private TableView<Book> ordersTableView;
	@FXML private TableColumn<Book, String> tableColumnTitle;
    @FXML private TableColumn<Book, String> tableColumnOrderDate;
    @FXML private TableColumn<Book, String> tableColumnQueue;
	public static Book resultBook = null;
	public static ArrayList<Book> bookResult = null;
	private ObservableList<Book> listOfBooks;
	

	@FXML
	void searchBook(ActionEvent event) throws InterruptedException {
		if(bookNameTextField.getText() == null)
			System.out.println("empty field");
		else
		{
			Book book = new Book(bookNameTextField.getText());
			MessageCS message = new MessageCS(MessageType.SEARCH_BOOK_FOR_ORDER,book);
			MainClient.client.accept(message);
			Thread.sleep(400);
			//LocalDate localDateConvert = resultBook.getSoonestReturn().toLocalDate();//convert from Date to LocalDate
			//abc.setValue(localDateConvert);
			if(resultBook == null)
			{
				placeOrderButton.setDisable(true);
				subscriberStatusLabel.setText("Book can't be ordered")	;
			}
			else
			{
				placeOrderButton.setDisable(false);
				subscriberStatusLabel.setText(resultBook.getSoonestReturn().toString());
			}
				
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	//	abc.getEditor().setEditable(false);
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


}
