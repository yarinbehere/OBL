package common;

import controller.LoginController;
import controller.OrderBookController;
import controller.SearchBookController;
import controller.UpdateBookController;
import entity.User;

public class ClientController {



	/**
	 * message send back to the client (from the server) and has analyze
	 * what sort of message has been returned
	 * @param msg
	 */
	public static void messageAnalyze(Object msg) 
	{
		MessageCS message = (MessageCS)msg;
		switch(message.messageType) 
		{
		case LOGIN:
			LoginController.userResult = message.getUser();
			break;
		case SEARCH_SUBSCRIBER:
			LoginController.subscriberResult = message.getSubscriber();
			break;
		case SEARCH_BOOK:
			SearchBookController.bookResult = message.getBooks();
			break;
		case TABLE_OF_CONTENT:
			SearchBookController.tableOfContent = message.getTableOfContent();
			break;
		case SEARCH_BOOK_FOR_ORDER:
			OrderBookController.resultBook = message.getBook();
			break;
		case LIST_OF_ORDERS:
			OrderBookController.bookResult = message.getBooks();
		case SEARCH_BOOK_FOR_UPDATE_BOOK:
			UpdateBookController.resultBook = message.getBook();
		default:
			break; 
		}	

	}
}
