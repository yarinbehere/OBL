package common;

import controller.BookReturnController;
import controller.LoginController;
import controller.OrderBookController;
import controller.SearchBookController;

public class ClientController {



	/**
	 * message send back to the client (from the server) and has analyze
	 * what sort of message has been returned
	 * @param msg
	 */
	public static void messageAnalyze(Object msg) 
	{
		MessageCS message = (MessageCS)msg;
		switch(message.getMessageType()) 
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
			OrderBookController.wantedBook = message.getBook();
			break;
		case ORDER_BOOK:
			OrderBookController.bookForOrder = message.getBook();
			break;
		case ERROR_MESSAGE:
			OrderBookController.messageBookOrder = message.getError();
			break;
		case LIST_OF_ORDERS:
			OrderBookController.bookResult = message.getBooks();
			break;
		case SEARCH_SUBSCRIBER_FOR_OPTIONS:
			BookReturnController.resultSubscriber = message.getSubscriber();
			break;
		case SEARCH_BOOK_FOR_RETURN:
			BookReturnController.resultBook = message.getBook();
		default:
			break; 
		}	

	}
}
