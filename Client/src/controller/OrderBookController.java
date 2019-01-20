package controller;

import common.MainClient;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.OrderBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class OrderBookController
{
    @FXML
    private TextField bookTextField;

    @FXML
    private Button searchBookButton;

    @FXML
    private Button placeOrderButton;

    @FXML
    private Text subscriberStatusLabel;

    @FXML
    private Hyperlink bookCodeLabel;

    @FXML
    private Button helpButton;

    @FXML
    private Button returnButton;

    @FXML
    private Text pathLabel;

    @FXML
    private Button cancelButton;

    @FXML
    void searchBook(ActionEvent event) 
    {
    	String=
    	OrderBook orderBook = new OrderBook(bookTextField.get);
    	MessageCS message = new MessageCS(MessageType.ORDER_BOOK,orderBook);
    	MainClient.client.accept(message);

    }
}
