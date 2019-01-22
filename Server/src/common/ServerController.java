package common;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import common.ocsf.server.ConnectionToClient;
import entity.Book;
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
				  query = "SELECT * FROM user WHERE userName = '" +  message.user.getUserName() + "'" + ";";
				  rset = stmt.executeQuery(query);
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
			  case SEARCH_BOOK:
				  ArrayList<Book> bookList = new ArrayList<>();
				  query = "SELECT * FROM book WHERE title LIKE '%"+message.book.getBookTitle()+"%' " 
				  + "AND author LIKE '%"+ message.book.getAuthorName() + "%' "
				  + "AND category = '" + message.book.getBookGenre()+ "' "
				  + "AND description LIKE '%" + message.book.getBookDescription() + "%';";
				  rset = stmt.executeQuery(query);
				  while(rset.next())
				  {
					  Book book = new Book(rset.getString("title"), rset.getString("author"),
							  rset.getString("category"), rset.getString("description"));
					  bookList.add(book);
				  }
				  client.sendToClient(bookList);
				  		
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
