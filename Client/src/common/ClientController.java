package common;

import java.util.ArrayList;

import controller.ActivityLogController;
import controller.BorrowBookController;
import controller.LoginController;
import entity.ActivityLog;
import entity.User;

public class ClientController {
	
	
	
	/**
	 * message send back to the client (from the server) and has analyze
	 * what sort of message has been returned
	 * @param msg
	 */
	public static void messageAnalyze(Object msg) 
	{
		{
			MessageCS message = (MessageCS)msg;
			switch(message.messageType) {
			case LOGIN:
				LoginController.userResult = message.getUser();
				break;
			case SEARCH_SUBSCRIBER:
				LoginController.subscriberResult = message.getSubscriber();
				BorrowBookController.resultSubscriber = message.getSubscriber();
				break;
			case SEARCH_BOOK_FOR_BORROW:
				BorrowBookController.resultBook = message.getBook();
			case BORROW:
				BorrowBookController.resultBorrowedBook = message.getBorrowedBook();
			case BORROW1:
				BorrowBookController.cancel_borrow=message.getBook();
				break;	
			case ACTIVITY_LOG:
				 	ActivityLogController.finalSubscriberActivity=  message.getActivityLog();
				 	break;
			default:
				break;
			}	
		}
		
	}
}
