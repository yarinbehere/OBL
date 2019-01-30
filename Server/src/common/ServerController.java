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
import entity.Book;
import entity.FileTransfer;
import entity.Subscriber;
import entity.User.Role;

public class ServerController {
	DataBaseManager dbmanager = EchoServer.getDbManager();

	public void messageReceivedFromClient(Object msg, ConnectionToClient client, Connection conn) {
		MessageCS message = (MessageCS) msg;
		String query;
		Statement stmt;
		ResultSet rset;

		try {
			stmt = conn.createStatement();
			switch (message.messageType) {
			case LOGIN:
				// Query to find user in DB
				query = "SELECT * FROM user WHERE userName = '" + message.getUser().getUserName() + "'" + ";";
				rset = stmt.executeQuery(query);
				// If user is exists in DB
				if (rset.next() == true) {
					// If password is match
					if (rset.getString("Password").equals(message.getUser().getPassword())) {
						// Go to relevant main menu
						switch (rset.getString("Role")) {
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
						// sending message back with the relevant user
						client.sendToClient(message);

					} else {
						// password is wrong
						client.sendToClient("Password inserted is incorrect!!");
					}

				} else {
					// Error message: user not found
					client.sendToClient("User can't be found");
				}
				break;
			case SEARCH_BOOK:
				ArrayList<Book> bookList = new ArrayList<>();
				Book book = null;
				// query to find title (substring), author(substring), category and
				// description(substring) in the database
				query = "SELECT * FROM book WHERE title LIKE '%" + message.getBook().getBookTitle() + "%' "
						+ "AND author LIKE '%" + message.getBook().getAuthorName() + "%' " + "AND category = '"
						+ message.getBook().getBookGenre() + "' " + "AND description LIKE '%"
						+ message.getBook().getBookDescription() + "%';";
				Statement stmtFindSoonestReturn = conn.createStatement();
				rset = stmt.executeQuery(query);
				// save the values of the book in ArrayList of type Book
				while (rset.next()) {
					// if there are no book available
					if (rset.getInt("currentQuantity") == 0) {
						// find the unavailable books and arrange them in ascended return dates so we
						// know the first list will be the soonest return date
						String tempQuery;
						tempQuery = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 and b.bookID = '"
								+ rset.getString("bookID") + "' ORDER BY returnDate";
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
					String path = "/common/" + rset.getString("pdf");// temporary (need to change it)
					File newFile = new File(message.getBook().getBookTitle() + ".pdf");// get the file and it's location
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
				insertSubscriber(message.subscriber);
				// sending the new subscriber to the client
				client.sendToClient(message);
				break;
			case REVIEW_SUBSCRIBER_SEARCH:
				String subscribersId = message.textMessage;
				message.subscriber = selectSubscriber(subscribersId);
				client.sendToClient(message);
				break;
			case REVIEW_SUBSCRIBER_UPDATE:
				updateSubscriber(message.subscriber);
				// sending confirmation message to the client
				client.sendToClient(message);
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
		query += " " + "'" + subscribersId + "'" + "=" + "subscriber" + "Id" + ";";
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
		query += "firstName" + "=" + "'";
		query += subscriber.getFirstName();
		query += "',";
		query += "lastName" + "=" + "'";
		query += subscriber.getLastName();
		query += "',";
		query += "phoneNumber" + "=" + "'";
		query += subscriber.getPhone();
		query += "',";
		query += "email" + "=" + "'";
		query += subscriber.getEmail();
		query += "',";
		query += "subscriberStatus" + "=" + "'";
		query += subscriber.getSubscriberStatus();
		query += "'";
		query += " WHERE ";
		query += "subscriberId" + "=" + "'";
		query += subscriber.getId();
		query += "';";
		dbmanager.runUpdateQuery(query);
	}

	/**
	 * given a subscriber, inserts the subscriber as a new record in the database
	 */
	public void insertSubscriber(Subscriber subscriber) {
		String query;
		// creates a new user such as: INSERT INTO user VALUES
		// ('208','Aa1234','Subscriber');
		query = "INSERT INTO user VALUES ('";
		query += subscriber.getUserName(); // gets from parent
		query += "','";
		query += subscriber.getPassword(); // gets from parent
		query += "','";
		query += "Subscriber";
		query += "');";
		dbmanager.runUpdateQuery(query);
		// building query. example: INSERT INTO subscriber VALUES
		// ('208','208','moshe','peretz','0502979234','moshe@gmail.com','Active')
		query = "INSERT INTO subscriber VALUES ('";
		query += subscriber.getId();
		query += "','";
		query += subscriber.getUserName(); // gets from parent
		query += "','";
		query += subscriber.getFirstName();
		query += "','";
		query += subscriber.getLastName();
		query += "','";
		query += subscriber.getPhone();
		query += "','";
		query += subscriber.getEmail();
		query += "','";
		subscriber.setSubscriberStatus("Active"); // status = Active
		query += subscriber.getSubscriberStatus();
		query += "');";
		dbmanager.runUpdateQuery(query);
	}
}
