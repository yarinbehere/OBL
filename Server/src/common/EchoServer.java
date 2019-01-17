package common;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import common.ocsf.server.*;
import entity.User.Role;


/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
	private static DataBaseManager dbManager;
	private static Connection conn;
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
  }

  
  //Instance methods ************************************************
  
  /**
   * @author Yarin, Roman
   * This method handles any messages received from the client.
   * 
   * ArrayList<String,Object>
   * String is the option desired,
   * Object is the entity related to the method.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient(Object msg, ConnectionToClient client)
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
					  // Error msg: password not match
					  client.sendToClient("password");
				  }

			  }
			  else {
				  // Error msg: user not found
				  System.out.println("User not found.");
				  client.sendToClient("User");
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

    
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
  }
  
  
  protected void clientConnected(ConnectionToClient client) 
  {
	System.out.println("Client Connected From " + client);  
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on
    dbManager = new DataBaseManager();
    conn = dbManager.dbConnection();
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    
  }
}
//End of EchoServer class
