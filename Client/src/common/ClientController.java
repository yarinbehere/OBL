package common;

import controller.AddNewBookController;
import controller.DeleteBookController;
import controller.GenerateReportController;
import controller.LoginController;
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
			GenerateReportController.reportResult=message.getGenerateReport();
			
			break;
			
		case TABLE_OF_CONTENT:
			SearchBookController.tableOfContent = message.getTableOfContent();
		default:
			break; 
		}	
		
	}
}
