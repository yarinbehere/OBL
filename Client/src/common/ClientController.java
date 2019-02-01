package common;

import controller.LoginController;
import controller.PersonalInformationController;
import controller.SearchBookController;
import controller.UpdateBookController;
import controller.ViewDatabaseController;
import controller.ReviewSubscriberController;
import controller.BorrowBookController;
import controller.CreateController;
import entity.Subscriber;
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
		case SEARCH_BOOK:
			SearchBookController.bookResult = message.getBooks();
			break;
		case TABLE_OF_CONTENT:
			SearchBookController.tableOfContent = message.getTableOfContent();
			break;
			case CREATE_SUBSCRIBER:
				if (message.getSubscriber() == null) {
					CreateController.foundSubscriber = null;
				} else {
					CreateController.foundSubscriber = message.getSubscriber();
				}
				break;
			case REVIEW_SUBSCRIBER_SEARCH:
			case REVIEW_SUBSCRIBER_UPDATE:
				if (message.getSubscriber() == null) {
					ReviewSubscriberController.foundSubscriber = null;
				} else {
					ReviewSubscriberController.foundSubscriber = message.getSubscriber();
				}
				break;
				case SEARCH_SUBSCRIBER:
				BorrowBookController.resultSubscriber = message.getSubscriber();
				break;
			case SEARCH_BOOK_FOR_BORROW:
				BorrowBookController.resultBook = message.getBook();
				break;
			case BORROW:
				BorrowBookController.resultBorrowedBook = message.getBorrowedbook();
			case BORROW1:
				BorrowBookController.cancel_borrow=message.getBook();
				break;
			case SEARCH_BOOK_FOR_UPDATE_BOOK:
				UpdateBookController.resultBook = message.getBook();
				break;
			case PERSONAL_INFORMATION_RESULT:
				PersonalInformationController.user=message.getUser();
				PersonalInformationController.subscriber=message.getSubscriber();
				break;
			case SEARCH_ALL_FOR_VIEW_DATABASE:
				ViewDatabaseController.subscriberResult=message.getSubscribers();
				ViewDatabaseController.librarianResult=message.getLibrarians();
				break;
		default:
			break;
			
		}	
		
	}
}
