package common;

import controller.LoginController;
import controller.OrderBookController;
import controller.SearchBookController;
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
		case CHECK_AVAILABLE_ORDER:
			OrderBookController.bookResult.add(message.getBook());
			System.out.println(message.getBook().getLocalDate());
			System.out.println("2: " + OrderBookController.bookResult.get(1).getLocalDate());
			break;
		case ERROR_MESSAGE:
			OrderBookController.messageBookOrder = message.getError();
			break;
		case LIST_OF_ORDERS:
			OrderBookController.bookResult = message.getBooks();
		default:
			break; 
		}	

	}
}
