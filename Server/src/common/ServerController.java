package common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.MessageCS.MessageType;
import common.ocsf.server.ConnectionToClient;
import controller.SubscriberMenuController;
import entity.Book;
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
			switch(message.messageType)
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
				query = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 and b.bookID = '"
						+ message.getBook().getBookDetails() + "' ORDER BY returnDate";
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
							+ rset.getDate("orderDate") + "'AND bookID = '" + rset.getString("bookID") + "' AND hour <= '"
							+ rset.getTime("hour") + "';";
					ResultSet rsetFindQueue = stmtFindQueue.executeQuery(queryFindQueue);
					if(rsetFindQueue.next())
					{
						book = new Book(rset.getString("title"),rset.getDate("orderDate"),rsetFindQueue.getInt("count(bookID)"));
					}
					bookList.add(book);
				}
				for(int i=0; i<bookList.size();i++)
					System.out.println(bookList.get(i).getBookTitle() + " " + bookList.get(i).getDateOrder() + " " + bookList.get(i).getQueue());
				books = new MessageCS(MessageType.LIST_OF_ORDERS,bookList);
				client.sendToClient(books);
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
