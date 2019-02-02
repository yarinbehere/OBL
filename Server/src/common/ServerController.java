package common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import common.MessageCS.MessageType;
import common.ocsf.server.ConnectionToClient;
import controller.LoginController;
import controller.SubscriberMenuController;
import entity.Book;
import entity.BookOrder;
import entity.FileTransfer;
import entity.Subscriber;
import entity.User.Role;

public class ServerController {

	public void messageReceivedFromClient(Object msg, ConnectionToClient client,Connection conn)
	{
		MessageCS message = (MessageCS)msg;
		String query;
		Statement stmt;
		ResultSet rset;
		Book book = null;
		try {
			stmt = conn.createStatement(); 
			switch(message.getMessageType())
			{
			case LOGIN:
				//Query to find user in DB
				query = "SELECT * FROM user WHERE userName = '" +  message.getUser().getUserName() + "'" + ";";
				rset = stmt.executeQuery(query);
				// If user is exists in DB 
				if(rset.next() == true)
				{
					// If password is match
					if(rset.getString("Password").equals(message.getUser().getPassword()))
					{ 
						// Go to relevant main menu 
						switch(rset.getString("Role"))
						{
						case "Subscriber":
							message.getUser().setRole(Role.SUBSCRIBER);
							break;
						case "Librarian": 
							message.getUser().setRole(Role.LIBRARIAN);
							break;
						case "Manager":
							message.getUser().setRole(Role.MANAGER);
							break;

						}
						//sending message back with the relevant user
						client.sendToClient(message);

					}	
					else {
						//password is wrong
						client.sendToClient("Password inserted is incorrect!!");
					}

				}
				else {
					//Error message: user not found
					client.sendToClient("User can't be found");
				}
				break;
			case SEARCH_SUBSCRIBER:
				Subscriber subscriber = null;
				query = "SELECT * FROM User u, Subscriber s WHERE u.userName = s.userName AND s.userName = '"+
						message.getSubscriber().getSubscriberDetails() + "';";
				rset = stmt.executeQuery(query);
				if(rset.next())
				{
					subscriber = new Subscriber(rset.getString("SubscriberID"));
				}
				MessageCS subscriberLoggedIn = new MessageCS(MessageType.SEARCH_SUBSCRIBER,subscriber);
				client.sendToClient(subscriberLoggedIn);
				break;
				//לשנות ל- SEARCH_SUBSCRIBER_FOR_OPTIONS אצל חי
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
				MessageCS resultSearchSubscriber = new MessageCS(MessageType.SEARCH_SUBSCRIBER_FOR_OPTIONS, subscriber);
				client.sendToClient(resultSearchSubscriber);
				break;
			case SEARCH_BOOK:
				ArrayList<Book> bookList = new ArrayList<>();
				book = null;
				//query to find title (substring), author(substring), category and description(substring) in the database
				query = "SELECT * FROM book WHERE title LIKE '%"+message.getBook().getBookTitle()+"%' " 
						+ "AND author LIKE '%"+ message.getBook().getAuthorName() + "%' "
						+ "AND category = '" + message.getBook().getBookGenre()+ "' "
						+ "AND description LIKE '%" + message.getBook().getBookDescription() + "%';";
				Statement stmtFindSoonestReturn = conn.createStatement();
				rset = stmt.executeQuery(query);
				//save the values of the book in ArrayList of type Book
				while(rset.next())
				{
					//if there are no book available
					if(rset.getInt("currentQuantity") == 0)
					{
						//find the unavailable books and arrange them in ascended return dates so we know the first list will be the soonest return date
						String soonestReturnQuery;
						soonestReturnQuery = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 and b.bookID = '"
								+ rset.getString("bookID") + "' ORDER BY returnDate";
						//must create new ResultSet for inner checking
						ResultSet rsetFindSoonestReturn = stmtFindSoonestReturn.executeQuery(soonestReturnQuery);
						//creating an entity with the first book in the list with soonest return date with quantity 0
						if(rsetFindSoonestReturn.next())
						{
							book = new Book(rsetFindSoonestReturn.getString("bookID"),rsetFindSoonestReturn.getString("title"),rsetFindSoonestReturn.getString("location"), "No",
									rsetFindSoonestReturn.getDate("returnDate")); 
						}
						rsetFindSoonestReturn.close();
					}
					//create an entity with the existing books in the library
					else
					{
						book = new Book(rset.getString("bookID"),rset.getString("title"), rset.getString("location"),
								"Yes", null);
					} 
					bookList.add(book); 
				}
				//create a new message of messageCS type with the result of the search of books (going to the wanted constructor)
				MessageCS books = new MessageCS(MessageType.SEARCH_BOOK,bookList);
				client.sendToClient(books);//send message back to the client
				break;
			case TABLE_OF_CONTENT:
				//choose book by it's serial number (since there might be 2 books with same names but different serial number)
				query = "SELECT * FROM book WHERE bookID = " + message.getBook().getBookID() + " ;";
				rset = stmt.executeQuery(query);
				if(rset.next())
				{

					FileTransfer tableOfContent = new FileTransfer(rset.getString("title"));//initialize the entity FileTransfer with the title book
					String path =  "/common/" + rset.getString("pdf");//temporary (need to change it)
					File newFile = new File (message.getBook().getBookTitle() + ".pdf");//get the file and it's location
					byte [] mybytearray  = new byte [(int)newFile.length()];
					FileInputStream fis = new FileInputStream(newFile);
					BufferedInputStream bis = new BufferedInputStream(fis);			 
					tableOfContent.initArray(mybytearray.length);
					tableOfContent.setSize(mybytearray.length);
					bis.read(tableOfContent.getMybytearray(),0,mybytearray.length);
					MessageCS file = new MessageCS(MessageType.TABLE_OF_CONTENT,tableOfContent);
					client.sendToClient(file);
					bis.close();
				}
				//will never get to that else because we are sending a known book that exists in the list
				else
					message.setBook(null);
				break;
			case SEARCH_BOOK_FOR_BORROW:
				book = null;
				query = "SELECT * FROM book WHERE title= '" + message.getBook().getBookDetails() + "' OR bookId = '" + message.getBook().getBookDetails() + "';";
				rset =	stmt.executeQuery(query);
				if(rset.next() == true)
				{
					book = new Book(rset.getString("bookId"),rset.getString("wanted"),rset.getInt("currentQuantity"));
				}
				MessageCS resultBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_BORROW, book);
				client.sendToClient(resultBook);
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
						//query to find the queue of given book
						String queryFindQueue = "SELECT count(bookID) FROM bookorder WHERE orderDate <= '"
								+ LocalDate.now() + "'AND bookID = '" + message.getBookOrder().getBookID()+ "' AND orderTime <= '"
								+ LocalTime.now() + "';";
						ResultSet rsetFindQueue = stmtFindQueue.executeQuery(queryFindQueue);
						if(rsetFindQueue.next())
						{
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
							book = new Book(message.getBookOrder().getBookID(),title,LocalDate.now(),rsetFindQueue.getInt("count(bookID)"));
							message = new MessageCS(MessageType.ORDER_BOOK,book);
							
							
						}
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
			case CANCEL_ORDER:
				query = "DELETE FROM BookOrder WHERE bookID = \"" + message.getBookOrder().getBookID() + "\" " 
						+ "AND subscriptionNumber = \"" + message.getBookOrder().getSubscriptionNumber() + "\";";
				stmt.executeUpdate(query);
				query = "INSERT INTO ActivityLog VALUES (\""+ LocalDate.now()
				+"\", \"Canceled an order\",\""+ message.getSubscriber().getSubscriberID() + "\")"; 
				stmt.executeUpdate(query);	
				break;
			default:
				break;
			}

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace(); 
		}
	}

}
