package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.ocsf.server.ConnectionToClient;
import entity.Subscriber;
import entity.User.Role;

public class ServerController {
	DataBaseManager dbmanager;

	public void messageReceivedFromClient(Object msg, ConnectionToClient client, Connection conn) {
		MessageCS message = (MessageCS) msg;
		String query;
		Statement stmt;
		dbmanager = EchoServer.getDbManager();
		try {
			stmt = conn.createStatement();
			switch (message.messageType) {
			case LOGIN:
				// Query to find user in DB
				query = "SELECT * FROM user WHERE userName = '" + message.user.getUserName() + "'" + ";";
				ResultSet rset = stmt.executeQuery(query);
				// If user is exists in DB
				if (rset.next() == true) {
					// If password is match
					if (rset.getString("Password").equals(message.user.getPassword())) {
						// Go to relevant main menu
						switch (rset.getString("Role")) {
						case "Subscriber":
							message.user.setRole(Role.SUBSCRIBER);
							break;
						case "Librarian":
							message.user.setRole(Role.LIBRARIAN);
							break;
						case "Manager":
							message.user.setRole(Role.MANAGER);
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
	private void updateSubscriber(Subscriber subscriber) {
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
	private void insertSubscriber(Subscriber subscriber) {
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
