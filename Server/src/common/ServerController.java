package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.ocsf.server.ConnectionToClient;
import entity.Subscriber;
import entity.User.Role;

public class ServerController {
	
	@SuppressWarnings("null")
	public void messageReceivedFromClient(Object msg, ConnectionToClient client,Connection conn)
	{
		 MessageCS message = (MessageCS)msg;
		 String query;
		 Statement stmt;
		 try {
			 stmt = conn.createStatement();
			 switch(message.messageType)
			 {
			  case LOGIN:
				  //Query to find user in DB
				  query = "SELECT * FROM user WHERE userName = '" +  message.user.getUserName() + "'" + ";";
				  ResultSet rset = stmt.executeQuery(query);
				  // If user is exists in DB 
				  if(rset.next() == true)
				  {
					  // If password is match
					  if(rset.getString("Password").equals(message.user.getPassword()))
					  { 
						  // Go to relevant main menu 
						  switch(rset.getString("Role"))
						  {
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
				  //Query to find user in DB
				  ArrayList<Subscriber> arrsubscriber = new   ArrayList<Subscriber>();
				  message.setArrSubscriber(arrsubscriber);
				  query = "SELECT * FROM obldb.readingcard WHERE "
							+ "subscriptionNumber ='"+ message.getSubscriber().getId()
							+"'or userName ='" + message.getSubscriber().getUsername()
							+"'or firstName ='" + message.getSubscriber().getFirstname()
							+"'or lastName ='" + message.getSubscriber().getLastname()
							+"'or phoneNumber ='" +message.getSubscriber().getPhone()
							+"'or email ='" + message.getSubscriber().getEmail()
							+ "'";
				  ResultSet rset1 = stmt.executeQuery(query);
					 while(rset1.next()) {
						 String id = rset1.getString("subscriptionNumber");
						 String userName = rset1.getString("userName");
						 String firstName = rset1.getString("firstName");
						 String lastName = rset1.getString("lastName");
						 String phoneNumber = rset1.getString("phoneNumber");
						 String email = rset1.getString("email");
						 String status = rset1.getString("subscriberStatus");
						 
						 Subscriber subscriber=new Subscriber(userName, id, firstName,
								 email, phoneNumber, lastName,status);
						 message.getArrSubscriber().add(subscriber);
					 }
					 
					 
					//sending message back with the relevant Subscribers
					 client.sendToClient(message);
					  
				  break;
			case SEARCH_A_BOOK:
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
