package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



import common.ocsf.server.ConnectionToClient;
import entity.User.Role;

public class ServerController {
	
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
				  query = "SELECT * FROM user WHERE `User Name` = '" +  message.user.getUserName() + "'" + ";";
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
							  System.out.println("Subscriber");
							  break;
						  case "Librarian":
							  message.user.setRole(Role.LIBRARIAN);
							  System.out.println("Librarian");
							  break;
						  case "Manager":
							  message.user.setRole(Role.MANAGER);
							  System.out.println("Manager");
							  break;
							  
						  }
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
