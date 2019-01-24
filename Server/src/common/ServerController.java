package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.ocsf.server.ConnectionToClient;
import entity.User.Role;

public class ServerController {

	public void messageReceivedFromClient(Object msg, ConnectionToClient client, Connection conn) {
		MessageCS message = (MessageCS) msg;
		String query;
		Statement stmt;
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
			case CREATE_SUBSCRIBER: // TODO: handle inheritance..
				// executes a query to create the new user (received as subscriber)
				// creates for example: INSERT INTO user VALUES ('208','Aa1234','Subscriber');
				query = "INSERT INTO user VALUES ('";
				query += message.subscriber.getUserName(); // gets from parent
				query += "','";
				query += message.subscriber.getPassword(); // gets from parent
				query += "','";
				query += "Subscriber";
				query += "');";
				// executes the query
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// executes a query to create the new subscriber received
				// creates for example: INSERT INTO subscriber VALUES ('208','208','moshe','peretz','0502979234','moshe@gmail.com','Active')
				query = "INSERT INTO subscriber VALUES ('";
				query += message.subscriber.getId();
				query += "','";
				query += message.subscriber.getUserName(); // gets from parent
				query += "','";
				query += message.subscriber.getFirstName();
				query += "','";
				query += message.subscriber.getLastName();
				query += "','";
				query += message.subscriber.getPhone();
				query += "','";
				query += message.subscriber.getEmail();
				query += "','";
				message.subscriber.setSubscriberStatus("Active"); // status = Active
				query += message.subscriber.getSubscriberStatus();
				query += "');";
				// executes the query
				try {
					stmt = conn.createStatement();
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				// sending the new subscriber to the client
				client.sendToClient(message);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
