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
				query = "SELECT * FROM subscriber r, user u WHERE u.userName = r.userName AND (r.userName = '"+ 
						message.getSubscriber().getSubscriberDetails() + "' OR r.email = '" + message.getSubscriber().getSubscriberDetails() + 
						"' OR r.subscriberID = '" + message.getSubscriber().getSubscriberDetails() + "');";
				rset=	stmt.executeQuery(query);
				if(rset.next() == true)
				{
					subscriber = new Subscriber(rset.getString("subscriberID"),rset.getString("userName"),rset.getString("firstName"),rset.getString("lastName"),rset.getString("phoneNumber"),rset.getString("email"),rset.getString("subscriberStatus"));
				} 
				MessageCS resultSearchSubscriber = new MessageCS(MessageType.SEARCH_SUBSCRIBER, subscriber);
				client.sendToClient(resultSearchSubscriber);
				break;
			case SEARCH_BOOK_FOR_BORROW:
				Book book = null;
				query = "SELECT * FROM book WHERE title= '" + message.getBook().getbookDetails() + "' OR bookId = '" + message.getBook().getbookDetails() + "';";
				rset=	stmt.executeQuery(query);
				if(rset.next() == true)
				{
					book = new Book(rset.getString("bookId"),rset.getString("wanted"),rset.getInt("currentQuantity"));
				}
				MessageCS resultBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_BORROW, book);
				client.sendToClient(resultBook);
				break;

			case BORROW:
				//לטפל ב"ספר אבוד" מה זה אומר?
				//לטפל בכך שמכניסים שמות שלא קיימים במסד נתונים
				//לכתוב עוד שאילתה להוריד את כמות הספרים בסיפרייה של הספר שהושאל ב-1
				System.out.println(message.getBorrowedBook().getSubscriptionNumber());
				System.out.println(message.getBorrowedBook().getBookId());
				System.out.println(message.getBorrowedBook().getReturnDate());
				System.out.println(message.getBorrowedBook().getBorrowDate());
				System.out.println(message.getBorrowedBook().getLostBook());
				
				int x;
				query = "INSERT INTO borrowedbook VALUES(' " +
				message.getBorrowedBook().getSubscriptionNumber()+ " ';' "+
				message.getBorrowedBook().getBookId()+
						" ';'"+message.getBorrowedBook().getReturnDate()+"';'"+message.getBorrowedBook().getBorrowDate()+"';'"+22 + "')";
				
				query = "INSERT INTO `borrowedbook` (`subscriptionNumber`, `bookId`, `returnDate`, `borrowDate`, `lostBook`) VALUES ('207', '3', '2019-01-02', '2019-01-20', '0');";

				x=stmt.executeUpdate(query);
				System.out.println("hai");
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
