package common;

import controller.LoginController;
import controller.SearchBookController;
import controller.ReviewSubscriberController;
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
			//go to main menu of subscriber
			if(message.getUser().getRole() == User.Role.SUBSCRIBER)
			{
				LoginController.userRole = User.Role.SUBSCRIBER;
			}
			//go to main menu of librarian
			else if(message.getUser().getRole() == User.Role.LIBRARIAN)
			{
				LoginController.userRole = User.Role.LIBRARIAN;
			}
			//go to main menu of manager
			else if(message.getUser().getRole() == User.Role.LIBRARIAN)
			{
				LoginController.userRole = User.Role.MANAGER;
			}
			break;
		case SEARCH_BOOK:
			SearchBookController.bookResult = message.getBooks();
			break;
		case TABLE_OF_CONTENT:
			SearchBookController.tableOfContent = message.getTableOfContent();
			break;
			case CREATE_SUBSCRIBER:
				if (message.subscriber == null) {
					CreateController.foundSubscriber = null;
				} else {
					CreateController.foundSubscriber = message.subscriber;
				}
				break;
			case REVIEW_SUBSCRIBER_SEARCH:
			case REVIEW_SUBSCRIBER_UPDATE:
				if (message.subscriber == null) {
					ReviewSubscriberController.foundSubscriber = null;
				} else {
					ReviewSubscriberController.foundSubscriber = message.subscriber;
				}
				break;
		default:
			break;
			
		}	
		
	}
}
