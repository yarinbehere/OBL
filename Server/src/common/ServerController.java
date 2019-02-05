package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import common.MessageCS.MessageType;
import common.ocsf.server.ConnectionToClient;
import entity.ActivityLog;
import entity.Book;
import entity.BorrowsExt;
import entity.FileTransfer;
import entity.GenerateReport;
import entity.Librarian;
import entity.Subscriber;
import entity.User;
import entity.User.Role;

public class ServerController {
	DataBaseManager dbmanager = EchoServer.getDbManager();

	public void messageReceivedFromClient(Object msg, ConnectionToClient client, Connection conn) {
		MessageCS message = (MessageCS) msg;
		String query;
		Statement stmt;
		ResultSet rset;
		Book book = null;
		User user = null;
		Subscriber subscriber = null;
		Librarian librarian = null;
		try {
			stmt = conn.createStatement();
			switch (message.messageType) {
			case LOGIN:
				ArrayList<String> librarianFrozen = new ArrayList<>();
				// Query to find user in DB
				query = "SELECT * FROM user WHERE userName = '" + message.getUser().getUserName() + "'" + ";";
				rset = stmt.executeQuery(query);
				// If user is exists in DB
				if (rset.next() == true) {
					// If password is match
					if (rset.getString("Password").equals(message.getUser().getPassword())) {
						// Go to relevant main menu
						switch (rset.getString("Role"))
						{
						case "Subscriber":
							message.getUser().setRole(Role.SUBSCRIBER);
							break;
						case "Librarian":
							message.getUser().setRole(Role.LIBRARIAN);
							query= "SELECT * FROM borrowedbook b, subscriber s WHERE b.returnDate < \"" + LocalDate.now() + "\"AND"
									+ " b.subscriptionNumber = s.subscriberId AND s.subscriberStatus = \"Active\";";
							rset=stmt.executeQuery(query);
							while(rset.next()==true)
							{
								librarianFrozen.add(rset.getString("subscriptionNumber"));
							}
							for(int i=0;i<librarianFrozen.size();i++)
							{
								query = "UPDATE subscriber SET ";
								query += "subscriberStatus" +  "=" + "'";
								query += "Frozen";
								query += "',";
								query += "lateReturn" + "=" + "'";
								query += 1;
								query += "'";
								query += " WHERE ";
								query += "subscriberId" + "=" + "'";
								query += librarianFrozen.get(i);
								query += "';";
								stmt.executeUpdate(query);
							}
							break;
						case "Manager":
							message.getUser().setRole(Role.MANAGER);
							ArrayList<String> subscribersLateRetuens=new ArrayList<>();
							Boolean flagOfHavingSubscriberWithLateReturn=false;
							MessageCS subscriberLate;

							query="SELECT * FROM subscriber WHERE lateReturn = \"" + 3 + "\" OR lateReturn >" + "\"" + 3 + "\"" + ";";
							rset=stmt.executeQuery(query);
							///if the subscriber have more than 3 late returns
							while(rset.next() == true)
							{
								subscribersLateRetuens.add(rset.getString("subscriberId"));
								flagOfHavingSubscriberWithLateReturn=true;
							}
							//if there isn't subscriber with late return
							if(flagOfHavingSubscriberWithLateReturn==true)
							{
								subscriberLate = new MessageCS(MessageType.LATE_RETURNS,subscribersLateRetuens);
								client.sendToClient(subscriberLate);
							}
							///////////////////////////////////////////////////////////////////////////////////////////////////////////
							break;

						}
						// sending message back with the relevant user
						client.sendToClient(message);

					} else {
						MessageCS errorMessage = new MessageCS (MessageType.ERROR_MESSAGE,"Password inserted is incorrect!!");
						// password is wrong
						client.sendToClient(errorMessage);
					}

				} else {
					MessageCS errorMessage = new MessageCS (MessageType.ERROR_MESSAGE,"User can't be found");
					// Error message: user not found
					client.sendToClient(errorMessage);
				}
				break;
			case SEARCH_BOOK:
				ArrayList<Book> bookList = new ArrayList<>();
				book = null;
				// query to find title (substring), author(substring), category and
				// description(substring) in the database
				query = "SELECT * FROM book WHERE title LIKE \"%" + message.getBook().getBookTitle() + "%\" "
						+ "AND author LIKE \"%" + message.getBook().getAuthorName() + "%\" " + "AND category LIKE \"%"
						+ message.getBook().getBookGenre() + "%\"AND description LIKE \"%"
						+ message.getBook().getBookDescription() + "%\";";
				Statement stmtFindSoonestReturn = conn.createStatement();
				rset = stmt.executeQuery(query);
				// save the values of the book in ArrayList of type Book
				while (rset.next()) {
					// if there are no book available
					if (rset.getInt("currentQuantity") == 0) {
						// find the unavailable books and arrange them in ascended return dates so we
						// know the first list will be the soonest return date
						String tempQuery;
						tempQuery = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 and b.bookID = \""
								+ rset.getString("bookID") + "\" ORDER BY returnDate";
						// must create new ResultSet for inner checking
						ResultSet rsetFindSoonestReturn = stmtFindSoonestReturn.executeQuery(tempQuery);
						// creating an entity with the first book in the list with soonest return date
						// with quantity 0
						if (rsetFindSoonestReturn.next()) {
							book = new Book(rsetFindSoonestReturn.getString("bookID"),
									rsetFindSoonestReturn.getString("title"),
									rsetFindSoonestReturn.getString("location"), "No",
									rsetFindSoonestReturn.getDate("returnDate"));
						}
						rsetFindSoonestReturn.close();
					}
					// create an entity with the existing books in the library
					else {
						book = new Book(rset.getString("bookID"), rset.getString("title"), rset.getString("location"),
								"Yes", null);
					}
					bookList.add(book);
				}
				// create a new message of messageCS type with the result of the search of books
				// (going to the wanted constructor)
				MessageCS books = new MessageCS(MessageType.SEARCH_BOOK, bookList);
				client.sendToClient(books);// send message back to the client
				break;
			case TABLE_OF_CONTENT:
				// choose book by it's serial number (since there might be 2 books with same
				// names but different serial number)
				query = "SELECT * FROM book WHERE bookID = " + message.getBook().getBookID() + " ;";
				rset = stmt.executeQuery(query);
				if (rset.next()) {

					FileTransfer tableOfContent = new FileTransfer(rset.getString("title"));// initialize the entity
					// FileTransfer with the
					// title book
					String path = "C:\\Server\\pdf\\" + rset.getString("pdf") + ".pdf";// temporary (need to change it)
					File newFile = new File(path);// get the file and it's location
					byte[] mybytearray = new byte[(int) newFile.length()];
					FileInputStream fis = new FileInputStream(newFile);
					BufferedInputStream bis = new BufferedInputStream(fis);
					tableOfContent.initArray(mybytearray.length);
					tableOfContent.setSize(mybytearray.length);
					bis.read(tableOfContent.getMybytearray(), 0, mybytearray.length);
					MessageCS file = new MessageCS(MessageType.TABLE_OF_CONTENT, tableOfContent);
					client.sendToClient(file);
					bis.close();
				}
				// will never get to that else because we are sending a known book that exists
				// in the list
				else
					System.out.println("no such a book");
				break;
			case CREATE_SUBSCRIBER:
				insertSubscriber(message.getSubscriber());
				// sending the new subscriber to the client
				client.sendToClient(message);
				break;
			case REVIEW_SUBSCRIBER_SEARCH:
				String subscribersId = message.getTextMessage();
				message.setSubscriber(selectSubscriber(subscribersId));
				client.sendToClient(message);
				break;
			case REVIEW_SUBSCRIBER_UPDATE:
				updateSubscriber(message.getSubscriber());
				// sending confirmation message to the client
				client.sendToClient(message);
				break;
			case SEARCH_SUBSCRIBER:
				subscriber = null; 
				query = "SELECT * FROM subscriber r, user u WHERE u.userName = r.userName AND (r.userName = \""+ 
						message.getSubscriber().getSubscriberDetails() + "\" OR r.email = \"" + message.getSubscriber().getSubscriberDetails() + 
						"\" OR r.subscriberID = \"" + message.getSubscriber().getSubscriberDetails() + "\");";
				rset=stmt.executeQuery(query);
				message.getSubscriber().setSubscriberDetails(message.getSubscriber().getSubscriberDetails());
				//if the subscriber does not exist in the system
				if(rset.next() == false)
				{
					subscriber = new Subscriber("null");
				}
				//if the subscriber exist in the system
				else
				{
					subscriber = new Subscriber(rset.getString("subscriberID"),rset.getString("userName"),rset.getString("firstName"),rset.getString("lastName"),rset.getString("phoneNumber"),rset.getString("email"),rset.getString("subscriberStatus"),message.getSubscriber().getSubscriberDetails());
				} 
				MessageCS resultSearchSubscriber = new MessageCS(MessageType.SEARCH_SUBSCRIBER, subscriber);
				client.sendToClient(resultSearchSubscriber);
				break;
			case SEARCH_BOOK_FOR_BORROW:
				book = null;
				query = "SELECT * FROM book WHERE bookId= \"" + message.getBook().getBookDetails()  + "\";";
				rset=stmt.executeQuery(query);
				//if the book does not exist in the system
				if(rset.next() == false)
				{
					book = new Book("null");
				}
				//if the book exist in the system
				else
				{
					book = new Book(rset.getString("bookId"),rset.getString("wanted"),rset.getInt("currentQuantity"),rset.getInt("originalQuantity"),message.getBook().getBookDetails());
				}
				MessageCS resultBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_BORROW,book);
				client.sendToClient(resultBook);
				break;
			case BORROW:
				boolean flag=true;
				query = "SELECT * FROM borrowedbook WHERE bookId= \"" + message.getBorrowedbook().getBookId()  + "\";";
				rset=stmt.executeQuery(query);
				String borrowingDescription="Borrowed a book";
				book = null;
				while(flag)
				{
					if(rset.next() == true)
					{
						if(rset.getString("subscriptionNumber").equals(message.getBorrowedbook().getSubscriptionNumber()))
						{
							flag=false;
							book=new Book("subscriber already borrowed this book");
							MessageCS cancel_borrow = new MessageCS(MessageType.BORROW1,book);
							client.sendToClient(cancel_borrow);
							return;
						}
					}
					else
						flag=false;
				}
				query = "INSERT INTO borrowedbook VALUES (\"";
				query += message.getBorrowedbook().getSubscriptionNumber();
				query += "\",\"";
				query += message.getBorrowedbook().getBookId(); 
				query += "\",\"";
				query += message.getBorrowedbook().getReturnDate();
				query += "\",\"";
				query += message.getBorrowedbook().getBorrowDate();
				query += "\",\"";
				query += message.getBorrowedbook().getLostBook();
				query += "\");";
				stmt.executeUpdate(query);
				query = "SELECT * FROM book WHERE bookId= \"" + message.getBorrowedbook().getBookId()  + "\";";
				rset=stmt.executeQuery(query);
				if(rset.next() == true)
				{
					query = "UPDATE book SET ";
					query += "currentQuantity" + "=" + "\"";
					query += rset.getInt("currentQuantity")-1;
					query += "\"";
					query += " WHERE ";
					query += "bookId" + "=" + "\"";
					query += rset.getString("bookId");
					query += "\";";
					stmt.executeUpdate(query);
					//insert to activity log
					query = "INSERT INTO activitylog VALUES (\"";
					query += message.getBorrowedbook().getBorrowDate();
					query += "\",\"";
					query += borrowingDescription; 
					query += "\",\"";
					query += message.getBorrowedbook().getSubscriptionNumber();
					query += "\");";
					stmt.executeUpdate(query);
				}
				break;
			case SEARCH_BOOK_FOR_UPDATE_BOOK:
				//Query to find book in DB by bookID
				query = "SELECT * FROM book WHERE bookID= \""+message.getBook().getBookID()+ "\";";
				//result of the query
				rset = stmt.executeQuery(query);
				//if book fund 
				if(rset.next())
				{
					//check if the book is available according to current Quantity
					String available;
					if(rset.getInt("currentQuantity")>0)
						available = "available";
					else 
						available = "Not available";
					//get book's details from the result
					book = new Book(rset.getString("bookId"),available, rset.getString("title"), rset.getString("author"),
							rset.getString("category"),rset.getString("description"),rset.getInt("originalQuantity"),
							rset.getString("location"),rset.getString("wanted"),rset.getString("pdf")); 
				}
				else
					book = null;
				//create a new message of messageCS type with the result of the search of books (going to the wanted constructor)
				MessageCS resultReturn = new MessageCS(MessageType.SEARCH_BOOK_FOR_UPDATE_BOOK,book);
				client.sendToClient(resultReturn);
				break;
			case UPDATE_BOOK:
				//Query to update book's details in DB 
				query = "UPDATE book SET title= \""+message.getBook().getBookTitle()+
				"\",author=\""+message.getBook().getAuthorName()+
				"\",category=\""+message.getBook().getBookGenre()+
				"\",description=\""+message.getBook().getBookDescription()+
				"\",originalQuantity="+message.getBook().getOriginalBookQuantity()+
				",location=\""+message.getBook().getShelfLocation()+
				"\",wanted=\""+message.getBook().getWantedLevel()+
				"\"WHERE bookID= \""+message.getBook().getBookID()+ "\";";
				//send Query to DB
				stmt.executeUpdate(query);
				break;
			case PERSONAL_INFORMATION:
				//Query to find user in DB
				query = "SELECT * FROM user WHERE userName = \"" +  message.getUser().getUserName() + "\"" + ";";
				rset = stmt.executeQuery(query);
				// If user is exists in DB 
				if(rset.next() == true)
					user = new User (message.getUser().getUserName(),rset.getString("Password"));
				else 
					user = null;
				//Query to find user in DB ,table subscriber by userName
				query = "SELECT * FROM subscriber WHERE userName = \"" +  message.getUser().getUserName() + "\"" + ";";
				rset = stmt.executeQuery(query);
				// If user is exists in DB 
				if(rset.next() == true)
					subscriber = new Subscriber(rset.getString("subscriberId"), rset.getString("firstName"),
							rset.getString("lastName"), rset.getString("phoneNumber"),
							rset.getString("email"), rset.getString("subscriberStatus"));
				else 
					subscriber = null;
				MessageCS informationReturn = new MessageCS(MessageType.PERSONAL_INFORMATION_RESULT,user,subscriber);
				client.sendToClient(informationReturn);
				break;
			case UPDATE_PERSONAL_INFORMATION:
				//Query to update user's details in DB 
				query = "UPDATE user SET password= \""+message.getUser().getPassword()+
				"\"WHERE userName= \""+message.getUser().getUserName()+ "\";";
				//send Query to DB
				stmt.executeUpdate(query);
				//Query to update subscriber's details in DB 
				query = "UPDATE subscriber SET firstName= \""+message.getSubscriber().getFirstName()+
						"\",lastName=\""+message.getSubscriber().getLastName()+
						"\",phoneNumber=\""+message.getSubscriber().getMobileNumber()+
						"\",email=\""+message.getSubscriber().getEmail()+
						"\"WHERE userName= \""+message.getUser().getUserName()+"\";";
				//send Query to DB
				stmt.executeUpdate(query);
				//get date
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date date = new Date();  
				//Query to for registration action in activitylog
				query = "INSERT INTO activitylog (actionDate, actionDescription, subscriberNumber) VALUES (\""+
						dateFormat.format(date)+"\", \"update personal information\",\""+
						message.getSubscriber().getSubscriberID()+"\");";
				//send Query to DB
				stmt.executeUpdate(query);
				break;
			case SEARCH_ALL_FOR_VIEW_DATABASE:
				ArrayList<Subscriber> subscribersList = new ArrayList<>();
				ArrayList<Librarian> librariansList = new ArrayList<>();
				//Query to find subscriber in DB
				query = "SELECT * FROM subscriber;";
				rset = stmt.executeQuery(query);
				while(rset.next() == true) {
					subscriber = new Subscriber(rset.getString("subscriberId"),
							rset.getString("userName"), rset.getString("firstName"),
							rset.getString("lastName"), rset.getString("phoneNumber"),
							rset.getString("email"), rset.getString("subscriberStatus"),null);

					subscribersList.add(subscriber);
				}
				//Query to find Librarian in DB
				query = "SELECT * FROM librarian;";
				rset = stmt.executeQuery(query);
				while(rset.next() == true) {
					librarian = new Librarian(rset.getString("workerNumber"), 
							rset.getString("userName"), rset.getString("firstName"),
							rset.getString("lastName"), rset.getString("email"),
							rset.getString("role"), rset.getString("phone"));

					librariansList.add(librarian);
				}
				MessageCS resultReturn1 = new MessageCS(MessageType.SEARCH_ALL_FOR_VIEW_DATABASE,subscribersList,librariansList);
				client.sendToClient(resultReturn1);
				break;
			case ACTIVITY_LOG:
				ArrayList<ActivityLog> subscriberActivity = new ArrayList<>();
				ActivityLog subscriberActivity2;
				String subscriberNumberA="";
				query = "SELECT * FROM subscriber r, user u WHERE u.userName = r.userName AND (r.userName = \""+ 
						message.getSubscriber().getSubscriberDetails() + "\" OR r.email = \"" + message.getSubscriber().getSubscriberDetails() + 
						"\" OR r.subscriberID = \"" + message.getSubscriber().getSubscriberDetails() + "\");";
				rset=stmt.executeQuery(query);
				while(rset.next() == true)
				{
					subscriberNumberA=rset.getString("subscriberId");
				}
				query="SELECT * FROM activitylog WHERE subscriberNumber= \"" + subscriberNumberA + "\";";
				rset=stmt.executeQuery(query);
				///if the subscriber exist
				while(rset.next() == true)
				{
					subscriberActivity2=new ActivityLog(rset.getString("actionDate"),rset.getString("actionDescription"));
					subscriberActivity.add(subscriberActivity2);
				}
				MessageCS activity = new MessageCS(MessageType.ACTIVITY_LOG, subscriberActivity);
				client.sendToClient(activity);
				break;
			case REQUEST_EXTENSION_INIT:
				MessageCS requestInitMessage = new MessageCS(MessageType.REQUEST_EXTENSION_INIT, message.getUser());
				requestInitMessage.setUsersBorrows(selectBorrowsExt(requestInitMessage.getUser().getUserName()));
				client.sendToClient(requestInitMessage);
				break;
			case REQUEST_EXTENSION_CHECK:
				String bookId = message.getBorrowedbook().getBookId();
				String subscriptionNumber = message.getBorrowedbook().getSubscriptionNumber();
				MessageCS requestCheckMessage;
				String actionDescription="Request to extend the borrow period";
				updateActivityLog(actionDescription, subscriptionNumber);

				if (countBookOrders(bookId) > 0) { 
					requestCheckMessage = new MessageCS(MessageType.REQUEST_EXTENSION_CHECK, "orders exist");
					client.sendToClient(requestCheckMessage);
				} else {
					// extend borrow return date by a week
					LocalDate returnDate = message.getBorrowedbook().getReturnDate();
					updateBorrowReturnDate(returnDate, bookId, subscriptionNumber);

					requestCheckMessage = new MessageCS(MessageType.REQUEST_EXTENSION_CHECK, "no orders, updated");
					client.sendToClient(requestCheckMessage);
				}
				break;
			case SEARCH_SUBSCRIBER_FOR_OPTIONS:
				subscriber = null; 
				query = "SELECT * FROM subscriber r, user u WHERE u.userName = r.userName AND (r.userName = \""+ 
						message.getSubscriber().getSubscriberDetails() + "\" OR r.email = \"" + message.getSubscriber().getSubscriberDetails() + 
						"\" OR r.subscriberID = \"" + message.getSubscriber().getSubscriberDetails() + "\");";
				rset=stmt.executeQuery(query);
				message.getSubscriber().setSubscriberDetails(message.getSubscriber().getSubscriberDetails());
				//if the subscriber does not exist in the system
				if(rset.next() == false)
				{
					subscriber = new Subscriber("null");
				}
				//if the subscriber exist in the system
				else
				{
					subscriber = new Subscriber(rset.getString("subscriberID"),rset.getString("userName"),rset.getString("firstName"),rset.getString("lastName"),rset.getString("phoneNumber"),rset.getString("email"),rset.getString("subscriberStatus"),message.getSubscriber().getSubscriberDetails());
				} 
				resultSearchSubscriber = new MessageCS(MessageType.SEARCH_SUBSCRIBER_FOR_OPTIONS, subscriber);
				client.sendToClient(resultSearchSubscriber);
				break;
			case LIST_OF_ORDERS:
				query = "SELECT * FROM BookOrder o, Book b WHERE o.bookID = b.bookID AND o.subscriptionNumber = '" 
						+ message.getSubscriber().getSubscriberDetails() + "';";
				rset = stmt.executeQuery(query);
				bookList = new ArrayList<Book>();
				Statement stmtFindQueue = conn.createStatement();
				while(rset.next()) 
				{
					String queryFindQueue = "SELECT count(bookID) FROM bookorder WHERE orderDate <= '"
							+ rset.getDate("orderDate") + "'AND bookID = '" + rset.getString("bookID") + "' AND orderTime <= '"
							+ rset.getTime("orderTime") + "';";
					ResultSet rsetFindQueue = stmtFindQueue.executeQuery(queryFindQueue);
					if(rsetFindQueue.next())
					{
						Format formatter = new SimpleDateFormat("yyyy-MM-dd");
						String format = formatter.format(rset.getDate("orderDate"));
						//insert the converted LocalDate value
						book = new Book(rset.getString("bookID"),rset.getString("title"),LocalDate.parse(format),rsetFindQueue.getInt("count(bookID)"));
					}
					bookList.add(book);
				}
				books = new MessageCS(MessageType.LIST_OF_ORDERS,bookList);
				client.sendToClient(books);
				break;
				//Subscriber wants to order book
				//will go here only when book doesn't exist in the list
			case ORDER_BOOK:
				Statement stmtInsertOrder = conn.createStatement();		
				//query to look if subscriber meets the requirements to order a book
				query = "SELECT * FROM Subscriber s, Book b, BookOrder o WHERE b.bookID = o.bookID AND "
						+ "s.subscriberID = o.subscriptionNumber AND b.bookID = '" + message.getBookOrder().getBookID()+ 
						"' AND currentQuantity = '0' AND (SELECT COUNT(bookID) FROM BookOrder WHERE "
						+ "bookID = '" + message.getBookOrder().getBookID()+ "') <= originalQuantity";
				rset = stmt.executeQuery(query);

				stmtFindQueue = conn.createStatement();
				//if found a book that meet the conditions to order a book
				if(rset.next()) 
				{
					String title = rset.getString("title");
					String wanted = rset.getString("wanted");
					//query just in case to find if the bookOrder is in the list
					query = "SELECT * FROM BookOrder o, subscriber s WHERE o.bookID = \"" + message.getBookOrder().getBookID() + "\""
							+ "AND o.subscriptionNumber = \"" + message.getBookOrder().getSubscriptionNumber() +"\" AND o.subscriptionNumber = s.subscriberID;";
					rset = stmt.executeQuery(query);

					//if there no match (means book is in the list)
					if(!rset.next())
					{
						int queue = 0;
						//query to find the queue of given book
						String queryFindQueue = "SELECT * FROM bookorder WHERE bookID = '" + message.getBookOrder().getBookID()+ "';";
						ResultSet rsetFindQueue = stmtFindQueue.executeQuery(queryFindQueue);
						while(rsetFindQueue.next())
							queue++;
						query = "INSERT INTO ActivityLog VALUES (\""+ LocalDate.now()
						+"\", \"Ordered a book\",\""+ message.getBookOrder().getSubscriptionNumber() + "\")"; 
						stmt.executeUpdate(query);	
						//insert to the database
						String insertOrder = "INSERT INTO BookOrder VALUES (NULL, '" + LocalDate.now() + "', '"
								+ message.getBookOrder().getSubscriptionNumber() + "', '" 
								+ message.getBookOrder().getBookID() + "', '" + wanted + "', '" +LocalTime.now() + "');";
						//insert the order to the database 
						stmtInsertOrder.executeUpdate(insertOrder);
						//the book that we want to order
						book = new Book(message.getBookOrder().getBookID(),title,LocalDate.now(),++queue);
						message = new MessageCS(MessageType.ORDER_BOOK,book);
					}

				}
				else
				{
					book = null;
					message = new MessageCS(MessageType.ORDER_BOOK,book);
				}
				client.sendToClient(message);
				break;
			case SEARCH_BOOK_FOR_RETURN:
				book = null;
				query = "SELECT * FROM BorrowedBook bor, Subscriber s WHERE bor.bookId= \"" + message.getBook().getBookID()  + "\" "
						+ "AND bor.subscriptionNumber = s.subscriberID AND s.subscriberID = \"" 
						+ message.getBook().getSubscriptionNumber() + "\";";
				rset=stmt.executeQuery(query);
				//if the book does not exist in the system
				if(rset.next() == false)
				{
					book = null;
				}
				//if the book exist in the system
				else
				{
					book = new Book(rset.getDate("borrowDate"),rset.getDate("returnDate"));
				}
				resultBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_RETURN,book);
				client.sendToClient(resultBook);
				break;
			case RETURN_BOOK:
				//delete the book that returned to the library from borrowed book table
				query = "DELETE FROM BorrowedBook WHERE bookID = \"" + message.getBook().getBookDetails()
				+ "\" AND subscriptionNumber = \"" + message.getSubscriber().getSubscriberID() + "\";";
				stmt.executeUpdate(query);
				//update the book table and increase the amount by 1
				query = "UPDATE Book SET currentQuantity = currentQuantity + 1 WHERE bookID = \""
						+ message.getBook().getBookDetails()+ "\";";
				stmt.executeUpdate(query);
				//find if the subscriber has books left in the list
				query = "SELECT * FROM borrowedBook bor, Subscriber s WHERE subscriptionNumber = \"" 
						+message.getSubscriber().getSubscriberID() + "\" AND bor.subscriptionNumber = subscriberID;";
				rset = stmt.executeQuery(query);
				if(rset.next() == false)
				{
					query = "SELECT * FROM Subscriber WHERE subscriberID = \"" + 
							message.getSubscriber().getSubscriberID()+ "\"; ";
					rset = stmt.executeQuery(query);
					//move to the next row of the wanted subscriber
					rset.next();
					//subscriber doesn't have books left and his status is frozen
					if(message.getSubscriber().getSubscriberStatus().equals("Frozen"))
					{
						//convert from Date to LocalDate
						Format formatter = new SimpleDateFormat("yyyy-MM-dd");
						String format = formatter.format(rset.getDate("graduationDate"));
						//if date of today minus the graduated date is negative means he is graduated
						if(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(format)) < 0)
						{
							//if the status is frozen and subscriber is already graduated
							query = "UPDATE Subscriber SET subscriberStatus = \"Locked\" WHERE subscriberID = \""
									+ message.getSubscriber().getSubscriberID()+ "\";";
							stmt.executeUpdate(query);
						}
						else
						{
							//if the status is frozen and subscriber is not graduated yet return status back to active
							query = "UPDATE Subscriber SET subscriberStatus = \"Active\" WHERE subscriberID = \""
									+ message.getSubscriber().getSubscriberID()+ "\";";
							stmt.executeUpdate(query);
						}
					}
					query = "INSERT INTO ActivityLog VALUES (\""+ LocalDate.now()
					+"\", \"Returned a book\",\""+ message.getSubscriber().getSubscriberID() + "\")"; 
					stmt.executeUpdate(query);		
				}
				break;
			case CANCEL_ORDER:
				query = "DELETE FROM BookOrder WHERE bookID = \"" + message.getBookOrder().getBookID() + "\" " 
						+ "AND subscriptionNumber = \"" + message.getBookOrder().getSubscriptionNumber() + "\";";
				stmt.executeUpdate(query);
				query = "INSERT INTO ActivityLog VALUES (\""+ LocalDate.now()
				+"\", \"Canceled an order\",\""+ message.getBookOrder().getSubscriptionNumber() + "\")"; 
				stmt.executeUpdate(query);	
				break;
			case SEARCH_BOOK_FOR_ORDER:
				query = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 AND (b.bookID = '"
						+ message.getBook().getBookDetails() + "' OR title = '" + message.getBook().getBookDetails()+ "') ORDER BY returnDate";
				rset = stmt.executeQuery(query);
				if(rset.next())
				{
					book = new Book(rset.getString("bookId"),rset.getDate("returnDate")); 
				}
				else
					book = null;
				MessageCS soonestReturn = new MessageCS(MessageType.SEARCH_BOOK_FOR_ORDER,book);
				client.sendToClient(soonestReturn);
				break;
				/**
				 * Search book for Add new book
				 * @author Yarin	
				 */
			case SEARCH_BOOK_FOR_ADDNEWBOOK:
				Book newBook = null;
				query = "SELECT * FROM book WHERE bookID= '" + message.getBook().getBookID()+"';";
				rset=	stmt.executeQuery(query);
				if(rset.next() == true)
				{
					newBook = new Book(rset.getString("title"),rset.getString("author"),rset.getString("category"),rset.getString("description"));
				}
				MessageCS resultBookForAddNewBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_ADDNEWBOOK, newBook);
				client.sendToClient(resultBookForAddNewBook);
				break;

				/**
				 * Upload PDF file to OBL DB
				 * @author Yarin
				 */
			case UPLOAD_NEW_PDF:
				String path = "C:\\Server\\pdf\\" +message.getTableOfContent().getFileName() + ".pdf";
				System.out.println(path);
				File newPDFFile = new File (path);//write the file to location and added "1" to differ from main file
				FileOutputStream fos = new FileOutputStream(newPDFFile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);			  
				bos.write(message.getTableOfContent().getMybytearray(),0,message.getTableOfContent().getSize());
				bos.flush();
				bos.close();
				break;


				/**
				 * Add new book (after searching it on DB and verify it's doesn't exists)
				 * @author Yarin	
				 */
			case ADD_NEW_BOOK:

				int n;
				query="INSERT INTO book VALUES ('";
				query+=message.getBook().getBookID()+"','";
				query+=message.getBook().getBookTitle()+"','";
				query+=message.getBook().getAuthorName()+"','";
				query+=message.getBook().getEditionNumber()+"','";
				query+=message.getBook().getPublishedDate()+"','";
				query+=message.getBook().getBookGenre()+"','";
				query+=message.getBook().getBookDescription()+"','";
				query+=message.getBook().getOriginalQuantity()+"','";
				query+=LocalDate.now()+"','"; 
				query+=message.getBook().getShelfLocation()+"','";
				query+=message.getBook().getBookTitle()+".pdf"+"','";
				query+=message.getBook().getOriginalQuantity()+"','";
				query+=message.getBook().getBookDemand();
				query+="');";
				n=stmt.executeUpdate(query);

				break;

				/**
				 * Search book for Delete Book
				 * @author Yarin	
				 */
			case SEARCH_BOOK_FOR_DELETEBOOK:

				Book tempBook=null;
				query = "SELECT * FROM book WHERE bookID= '" + message.getBook().getBookDetails()+"';";
				rset=stmt.executeQuery(query);
				Date tempDate=new Date(0);

				if(rset.next()==true) {
					tempBook=new Book(rset.getString("bookID"),rset.getString("title"),rset.getString("author"),Integer.parseInt(rset.getString("edition")),
							tempDate.toString() ,rset.getString("category"),rset.getString("description"),
							Integer.parseInt(rset.getString("originalQuantity")),tempDate.toString(),rset.getString("location"),
							rset.getString("pdf"),Integer.parseInt(rset.getString("currentQuantity")),rset.getString("wanted"));
				}
				query = "SELECT * FROM BorrowedBook WHERE bookID = \"" + message.getBook().getBookDetails() + "\";";
				rset = stmt.executeQuery(query);
				if(rset.next())
				{
					tempBook=null;
					MessageCS resultBookForDeleteBook=new MessageCS(MessageType.SEARCH_BOOK_FOR_DELETEBOOK, tempBook);
					client.sendToClient(resultBookForDeleteBook);
					break;
				}
				query = "SELECT * FROM bookorder WHERE bookID = \"" + message.getBook().getBookDetails() + "\";";
				rset = stmt.executeQuery(query);
				if(rset.next())
				{
					tempBook=null;
					MessageCS resultBookForDeleteBook=new MessageCS(MessageType.SEARCH_BOOK_FOR_DELETEBOOK, tempBook);
					client.sendToClient(resultBookForDeleteBook);
					break;
				}
				MessageCS resultBookForDeleteBook=new MessageCS(MessageType.SEARCH_BOOK_FOR_DELETEBOOK, tempBook);
				client.sendToClient(resultBookForDeleteBook);
				break;

				/**
				 * Delete Book
				 * @author Yarin
				 */
			case DELETE_BOOK:
				
				int deleteReturnValue;
				query="DELETE FROM book WHERE bookID = '";
				query+=message.getBook().getBookID()+"';";
				//query+="');";
				deleteReturnValue=stmt.executeUpdate(query);
				break;

				/**
				 * @author Yarin
				 */
			case ACTIVITY_REPORT:
				ArrayList<GenerateReport> generateList=new ArrayList<>();
				int active=0;
				int frozen=0;
				int locked=0;
				int booksQuantity=0;
				int lates=0;
				int shalevislame;
				// Get subscriber statuses
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Active\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					active=rset.getInt("COUNT(subscriberStatus)");
				}
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Frozen\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					frozen=rset.getInt("COUNT(subscriberStatus)");
				}
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Locked\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					locked=rset.getInt("COUNT(subscriberStatus)");
				}

				// Get books originalQuantity
				query = "SELECT SUM(originalQuantity) FROM book ";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					booksQuantity=rset.getInt("SUM(originalQuantity)");
				}

				// Get laters
				query = "SELECT COUNT(lateReturn) FROM subscriber WHERE lateReturn > 0";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					lates=rset.getInt("COUNT(lateReturn)");
				}


				/* Check if report of today already exists in the system */
				query="SELECT * FROM activityreport WHERE date = \"";
				query+=LocalDate.now()+"\"";
				rset=stmt.executeQuery(query);
				if(rset.next()) {
					// Is exist, need to UPDATE
					query="UPDATE activityreport SET active = \"" + active + "\", frozen = \"" + frozen + "\", "
							+ "locked = \""+locked+"\", books = \"" + booksQuantity + "\", late = \"" +lates +"\" WHERE date = \"" + LocalDate.now()+"\";";
					shalevislame=stmt.executeUpdate(query);
				}
				else {
					// is not exist, need to INSERT
					query="INSERT INTO activityreport VALUES ('";
					query+=LocalDate.now()+"','";
					query+=active+"','";
					query+=frozen+"','";
					query+=locked+"','";
					query+=booksQuantity+"','";
					query+=lates;
					query+="');";
					shalevislame=stmt.executeUpdate(query);
				}
				GenerateReport generateReport = new GenerateReport(LocalDate.now(), active, frozen, locked, booksQuantity, lates);
				message = new MessageCS(MessageType.ACTIVITY_REPORT,generateReport);
				client.sendToClient(message);
				break;
			default:
				break;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** given a subscribers id, retrieves the subscriber from the database */
	private Subscriber selectSubscriber(String subscribersId) {
		// building query. example: SELECT * FROM subscriber WHERE '207'=subscriberId;
		String query = "";
		query += "SELECT";
		query += " * ";
		query += "FROM";
		query += " " + "subscriber" + " ";
		query += "WHERE";
		query += " " + "\"" + subscribersId + "\"" + "=" + "subscriber" + "Id" + ";";
		ResultSet rset = dbmanager.runQuery(query);
		try {
			// entity/s found
			if (rset.next() == true) {
				// initializes all object fields
				String id = rset.getString("subscriberId");
				String userName = rset.getString("userName");
				String firstName = rset.getString("firstName");
				String lastName = rset.getString("lastName");
				String phone = rset.getString("phoneNumber");
				String email = rset.getString("email");
				String status = rset.getString("subscriberStatus");
				String password = rset.getString("userName");
				Subscriber subscriber = new Subscriber(id, userName, firstName, lastName, phone, email, password);
				subscriber.setSubscriberStatus(status);
				return subscriber;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// entity was not found
		return null;
	}

	/** given a subscriber, updates the subscribers record in the database */
	public void updateSubscriber(Subscriber subscriber) {
		String query;
		// building query. example: UPDATE subscriber SET
		// subscriberId='208',firstName='moshe',lastName='peretz',phoneNumber='0502712993',email='moshe@gmail.com',subscriberStatus='Active'
		// WHERE subscriberId='208';
		query = "UPDATE subscriber SET ";
		query += "firstName" + "=" + "\"";
		query += subscriber.getFirstName();
		query += "\",";
		query += "lastName" + "=" + "\"";
		query += subscriber.getLastName();
		query += "\",";
		query += "phoneNumber" + "=" + "\"";
		query += subscriber.getPhone();
		query += "\",";
		query += "email" + "=" + "\"";
		query += subscriber.getEmail();
		query += "\",";
		query += "subscriberStatus" + "=" + "\"";
		query += subscriber.getSubscriberStatus();
		query += "\"";
		query += " WHERE ";
		query += "subscriberId" + "=" + "\"";
		query += subscriber.getId();
		query += "\";";
		dbmanager.runUpdateQuery(query);
	}

	/**
	 * given a subscriber, inserts the subscriber as a new record in the database
	 */
	public void insertSubscriber(Subscriber subscriber) {
		String query;
		// creates a new user such as: INSERT INTO user VALUES
		// ('208','Aa1234','Subscriber');
		query = "INSERT INTO user VALUES (\"";
		query += subscriber.getUserName(); // gets from parent
		query += "\",\"";
		query += subscriber.getPassword(); // gets from parent
		query += "\",\"";
		query += "Subscriber";
		query += "\");";
		dbmanager.runUpdateQuery(query);
		// building query. example: INSERT INTO subscriber VALUES
		// ('208','208','moshe','peretz','0502979234','moshe@gmail.com','Active')
		query = "INSERT INTO subscriber VALUES (\"";
		query += subscriber.getId();
		query += "\",\"";
		query += subscriber.getUserName(); // gets from parent
		query += "\",\"";
		query += subscriber.getFirstName();
		query += "\",\"";
		query += subscriber.getLastName();
		query += "\",\"";
		query += subscriber.getPhone();
		query += "\",\"";
		query += subscriber.getEmail();
		query += "\",\"";
		subscriber.setSubscriberStatus("Active"); // status = Active
		query += subscriber.getSubscriberStatus();
		query += "\",\"";
		query += LocalDate.now().plusYears(4);
		query += "\",\"";
		query += 0;
		query += "\");";
		dbmanager.runUpdateQuery(query);

	}

	/**
	 * given a subscribers userName, retrieves the subscribers borrows extended from
	 * the database
	 */
	private ArrayList<BorrowsExt> selectBorrowsExt(String userName) {
		ArrayList<BorrowsExt> usersBorrows = new ArrayList<BorrowsExt>();
		/*
		 * building query. example: SELECT bb.subscriptionNumber, s.firstName,
		 * bb.bookId, b.title, bb.borrowDate, bb.returnDate, u.userName FROM
		 * borrowedbook bb, book b, subscriber s, user u WHERE u.userName='201' AND
		 * (u.userName = s.userName) AND (bb.bookId = b.bookId) AND
		 * (bb.subscriptionNumber = s.subscriberId);
		 */
		String query = "SELECT bb.subscriptionNumber, s.firstName, bb.bookId, b.title, bb.borrowDate, bb.returnDate, u.userName, b.wanted FROM borrowedbook bb, book b, subscriber s, user u WHERE  u.userName= ";
		query += "\"" + userName + "\" ";
		query += "AND (u.userName = s.userName) AND (bb.bookId = b.bookId) AND (bb.subscriptionNumber = s.subscriberId);";
		ResultSet rset = dbmanager.runQuery(query);
		try {
			// entity/s found
			while (rset.next() == true) {
				// adding the borrow to the result
				String subscriptionNumber = rset.getString("subscriptionNumber");
				String firstName = rset.getString("firstName");
				String bookID = rset.getString("bookId");
				String bookTitle = rset.getString("title");
				String wanted = rset.getString("wanted");
				LocalDate borrowDate = null, returnDate = null;
				// converting to localdate
				try {
					Date tempdate = rset.getDate("borrowDate");
					borrowDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tempdate));
					tempdate = rset.getDate("returnDate");
					returnDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(tempdate));
				} catch (Exception e) {
					e.printStackTrace();
				}
				BorrowsExt borrowsExt = new BorrowsExt(subscriptionNumber, firstName, bookID, bookTitle, borrowDate,
						returnDate,wanted);
				usersBorrows.add(borrowsExt);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (usersBorrows.isEmpty()) {
			return null;
		} else {
			return usersBorrows;
		}
	}
	/** given a book id, returns the number of orders referencing that book */
	private int countBookOrders(String bookId) {
		// building query. example: SELECT * FROM subscriber WHERE '207'=subscriberId;
		String query = "SELECT count(bookId) FROM bookorder WHERE bookId=";
		query += "\"" + bookId + "\";";
		ResultSet rset = dbmanager.runQuery(query);

		try {
			if (rset.next() == true) {
				return rset.getInt("count(bookId)");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * given a borrow details, extends it to another week
	 */
	private void updateBorrowReturnDate(LocalDate returnDate, String bookId, String subscriptionNumber) {
		// building query. example: UPDATE borrowedbook SET returnDate = '2019-02-22'
		// WHERE bookId = '2' AND subscriptionNumber = '201';

		String query = "UPDATE borrowedbook SET returnDate = \"";
		query += returnDate.plusDays(7);
		query += "\" WHERE bookId = \"";
		query += bookId;
		query += "\" AND subscriptionNumber = \"";
		query += subscriptionNumber;
		query += "\";";
		dbmanager.runUpdateQuery(query);
	}

	/** given an action description and a subscription number, updates the activity log 
	 * with this information and the current date */
	private void updateActivityLog(String actionDescription, String subscriptionNumber) {
		// building query. example: INSERT INTO activitylog VALUES
		// ('2019-02-02','Request to extend the borrow period', '201');

		String query="INSERT INTO activitylog VALUES (\"";
		query+=LocalDate.now();
		query+="\",\"";
		query+=actionDescription;
		query+="\",\"";
		query+=subscriptionNumber;
		query+="\");";
		dbmanager.runUpdateQuery(query);
	}
}
