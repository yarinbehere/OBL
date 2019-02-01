package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.MessageCS.MessageType;
import common.ocsf.server.ConnectionToClient;
import controller.BorrowBookController;
import entity.ActivityLog;
import entity.Book;
import entity.Subscriber;
import entity.User.Role;

public class ServerController {

	public void messageReceivedFromClient(Object msg, ConnectionToClient client,Connection conn)
	{
		MessageCS message = (MessageCS)msg;
		String query;
		Statement stmt;
		ResultSet rset;
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
			case SEARCH_BOOK:
				ArrayList<Book> bookList = new ArrayList<>();
				query = "SELECT * FROM book WHERE title LIKE '%"+message.getBook().getBookTitle()+"%' " 
						+ "AND author LIKE '%"+ message.getBook().getAuthorName() + "%' "
						+ "AND category = '" + message.getBook().getBookGenre()+ "' "
						+ "AND description LIKE '%" + message.getBook().getBookDescription() + "%';";
				rset = stmt.executeQuery(query);
				while(rset.next())
				{
					Book book = new Book(rset.getString("title"), rset.getString("author"),
							rset.getString("category"), rset.getString("description"));
					bookList.add(book);
				}
				client.sendToClient(bookList);
				break;
			case SEARCH_SUBSCRIBER:
				Subscriber subscriber = null; 
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
				Book book = null;
				System.out.println();
				query = "SELECT * FROM book WHERE bookId= \"" + message.getBook().getbookDetails()  + "\";";
				rset=stmt.executeQuery(query);
				//if the book does not exist in the system
				if(rset.next() == false)
				{
					book = new Book("null");
				}
				//if the book exist in the system
				else
				{
					book = new Book(rset.getString("bookId"),rset.getString("wanted"),rset.getInt("currentQuantity"),rset.getInt("originalQuantity"),message.getBook().getbookDetails());
				}
				MessageCS resultBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_BORROW,book);
				client.sendToClient(resultBook);
				break;
			case BORROW:
				int x;
				//////////////////////////////////////////////////////////////////////////
				String borrowingDescription="Borrowed a book";
				//////////////////////////////////////////////////////////////////////////

				boolean flag=true;
				query = "SELECT * FROM borrowedbook WHERE bookId= \"" + message.getBorrowedBook().getBookId()  + "\";";
				rset=stmt.executeQuery(query);
				Book book2 = null;
				while(flag)
				{
					if(rset.next() == true)
					{
						if(rset.getString("subscriptionNumber").equals(message.getBorrowedBook().getSubscriptionNumber()))
						{
							flag=false;
							book2=new Book("subscriber already borrowed this book");
							MessageCS cancel_borrow = new MessageCS(MessageType.BORROW1,book2);
							client.sendToClient(cancel_borrow);
							return;
						}
					}
					else
						flag=false;
				}
				query = "INSERT INTO borrowedbook VALUES ('";
				query += message.getBorrowedBook().getSubscriptionNumber();
				query += "','";
				query += message.getBorrowedBook().getBookId(); 
				query += "','";
				query += message.getBorrowedBook().getReturnDate();
				query += "','";
				query += message.getBorrowedBook().getBorrowDate();
				query += "','";
				query += message.getBorrowedBook().getLostBook();
				query += "');";
				x=stmt.executeUpdate(query);
				query = "SELECT * FROM book WHERE bookId= \"" + message.getBorrowedBook().getBookId()  + "\";";
				rset=stmt.executeQuery(query);
				if(rset.next() == true)
				{
				query = "UPDATE book SET ";
				query += "currentQuantity" + "=" + "'";
				query += rset.getInt("currentQuantity")-1;
				query += "'";
				query += " WHERE ";
				query += "bookId" + "=" + "'";
				query += rset.getString("bookId");
				query += "';";
				stmt.executeUpdate(query);
				///// the new table\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				query = "INSERT INTO activitylog VALUES ('";
				query += message.getBorrowedBook().getBorrowDate();
				query += "','";
				query += borrowingDescription; 
				query += "','";
				query += message.getBorrowedBook().getSubscriptionNumber();
				query += "');";
				stmt.executeUpdate(query);
				/////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
				}
				break;
			case ACTIVITY_LOG:
				ArrayList<ActivityLog> subscriberActivity = new ArrayList<>();
				ActivityLog subscriberActivity2;
				String subscriberNumberA="null";
				Boolean flag2=false;
				query = "SELECT * FROM subscriber r, user u WHERE u.userName = r.userName AND (r.userName = \""+ 
						message.getSubscriber().getSubscriberDetails() + "\" OR r.email = \"" + message.getSubscriber().getSubscriberDetails() + 
						"\" OR r.subscriberID = \"" + message.getSubscriber().getSubscriberDetails() + "\");";
				rset=stmt.executeQuery(query);
				///to take care if user doesn't exist
				while(rset.next() == true)
				{
					subscriberNumberA=rset.getString("subscriberId");
				}
				query="SELECT * FROM activitylog WHERE subscriberNumber= \"" + subscriberNumberA + "\";";
				rset=stmt.executeQuery(query);
				while(rset.next() == true)
				{
					subscriberActivity2=new ActivityLog(rset.getString("actionDescription"),rset.getString("actionDate"));
					subscriberActivity.add(subscriberActivity2);
					flag2=true;
				}
				///if the user doesn't exist
				if(flag2==false)
				{
				}
		    	MessageCS activity = new MessageCS(MessageType.ACTIVITY_LOG, subscriberActivity);
				client.sendToClient(activity);
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