package common;

import controller.LoginController;
import controller.OrderBookController;
import controller.PersonalInformationController;
import controller.RequestController;
import controller.SearchBookController;
import controller.UpdateBookController;
import controller.ViewDatabaseController;
import controller.ReviewSubscriberController;

import java.util.ArrayList;

import controller.ActivityLogController;
import controller.AddNewBookController;
import controller.BookReturnController;
import controller.BorrowBookController;
import controller.CreateController;
import controller.DeleteBookController;
import controller.GenerateReportController;
import entity.ActivityLog;
import entity.Book;

public class ClientController {



	/**
	 * message send back to the client (from the server) and has analyze
	 * what sort of message has been returned
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public static void messageAnalyze(Object msg) 
	{
		MessageCS message = (MessageCS)msg;
		switch(message.messageType) 
		{
		case LOGIN:
			LoginController.userResult = message.getUser();
			break;
		case SEARCH_BOOK:
			SearchBookController.bookResult = (ArrayList<Book>) message.getArrayList();
			break;
		case TABLE_OF_CONTENT:
			SearchBookController.tableOfContent = message.getTableOfContent();
			break;
		case CREATE_SUBSCRIBER:
			if (message.getSubscriber() == null) {
				CreateController.foundSubscriber = null;
			} 
			else
			{
				CreateController.foundSubscriber = message.getSubscriber();
			}
			break;
		case REVIEW_SUBSCRIBER_SEARCH:
		case REVIEW_SUBSCRIBER_UPDATE:
			if (message.getSubscriber() == null) {
				ReviewSubscriberController.foundSubscriber = null;
			} 
			else 
			{
				ReviewSubscriberController.foundSubscriber = message.getSubscriber();
			}
			break;
		case SEARCH_SUBSCRIBER:
			LoginController.subscriberResult = message.getSubscriber();
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
		case ACTIVITY_LOG:
			ActivityLogController.finalSubscriberActivity=  (ArrayList<ActivityLog>) message.getArrayList();
			break;
		case SEARCH_ALL_FOR_VIEW_DATABASE:
			ViewDatabaseController.subscriberResult=message.getSubscribers();
			ViewDatabaseController.librarianResult=message.getLibrarians();
			break;
		case REQUEST_EXTENSION_INIT:
			RequestController.bookBorrowResults = message.getUsersBorrows();
			break;
		case REQUEST_EXTENSION_CHECK:
			RequestController.requestAnswer = message.getTextMessage();
			break;
		case SEARCH_BOOK_FOR_ORDER:
			OrderBookController.wantedBook = message.getBook();
			break;
		case ORDER_BOOK:
			OrderBookController.bookForOrder = message.getBook();
			break;
		case ERROR_MESSAGE:
			LoginController.message=message.getTextMessage();
			break;
	/*	case ERROR_MESSAGE2:
			OrderBookController.messageBookOrder = message.getTextMessage();*/
			//break;
		case LIST_OF_ORDERS:
			OrderBookController.bookResult = (ArrayList<Book>) message.getArrayList();
			break;
		case SEARCH_SUBSCRIBER_FOR_OPTIONS:
			BookReturnController.resultSubscriber = message.getSubscriber();
			break;
		case SEARCH_BOOK_FOR_RETURN:
			BookReturnController.resultBook = message.getBook();
			break;
		case LATE_RETURNS:
			LoginController.finalSubscriberResult=(ArrayList<String>) message.getArrayList();
			break;
				
		/**
		 * Search book for Add New Book
		 * @author Yarin	
		 */
		case SEARCH_BOOK_FOR_ADDNEWBOOK:
			AddNewBookController.resultBookForAddNewBook=message.getBook();
			break;
			
		/**
		 * Add New Book
		 * @author Yarin	
		 */
		case ADD_NEW_BOOK:
			AddNewBookController.resultBookForAddNewBook=message.getBook();
			break;
		
		/**
		 * Search book for Delete Book
		 * @author Yarin
		 */
		case SEARCH_BOOK_FOR_DELETEBOOK:
			DeleteBookController.resultBookForDeleteBook=message.getBook();
			break;
			
		/**
		 * Delete Book
		 * @author Yarin
		 */
		case DELETE_BOOK:
			DeleteBookController.resultBookForDeleteBook=message.getBook();
			break;
			
		/**
		 * Return PDF of table of content after uploading it
		 * @author Yarin
		 */
		case UPLOAD_NEW_PDF:
			AddNewBookController.tableOfContent=message.getTableOfContent();
			break;
			
		case ACTIVITY_REPORT:
			GenerateReportController.reportActivityResult=message.getActivityReport();
			GenerateReportController.reportBorrowResult=message.getBorrowReport();
			
			break;
		default:
			break;

		}	

	}
}
