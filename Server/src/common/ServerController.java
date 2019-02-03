package common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;
import java.util.ArrayList;

import org.omg.PortableInterceptor.LOCATION_FORWARD;

import common.MessageCS.MessageType;
import common.ocsf.server.ConnectionToClient;
import entity.Book;
import entity.FileTransfer;
import entity.GenerateReport;
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
				
			/**
			 * Search book for Add new book
			 * @author Yarin	
			 */
			case SEARCH_BOOK_FOR_ADDNEWBOOK:
				Book newBook = null;
				query = "SELECT * FROM book WHERE bookID= '" + message.getBook().getBookID()+"';";
				rset=	stmt.executeQuery(query);
				if(rset.next() == true)
				{
					newBook = new Book(rset.getString("title"),rset.getString("author"),rset.getString("category"),rset.getString("description"));
				}
				MessageCS resultBookForAddNewBook = new MessageCS(MessageType.SEARCH_BOOK_FOR_ADDNEWBOOK, newBook);
				client.sendToClient(resultBookForAddNewBook);
				break;
				
			/**
			 * Upload PDF file to OBL DB
			 * @author Yarin
			 */
			case UPLOAD_NEW_PDF:
				File newPDFFile = new File ("C:\\Users\\rami\\Desktop\\eclipse-jee-photon-R-win32-x86_64\\eclipse\\harry2" + "1.pdf");//write the file to location and added "1" to differ from main file
			    FileOutputStream fos = new FileOutputStream(newPDFFile);
			    BufferedOutputStream bos = new BufferedOutputStream(fos);			  
			    bos.write(message.getTableOfContent().getMybytearray(),0,message.getTableOfContent().getSize());
			    bos.flush();
		    	bos.close();
		    	break;
				
				
			/**
			 * Add new book (after searching it on DB and verify it's doesn't exists)
			 * @author Yarin	
			 */
			case ADD_NEW_BOOK:
			
				int n;
				query="INSERT INTO book VALUES ('";
				query+=message.getBook().getBookID()+"','";
				query+=message.getBook().getBookTitle()+"','";
				query+=message.getBook().getAuthorName()+"','";
				query+=message.getBook().getEditionNumber()+"','";
				query+=message.getBook().getPublishedDate()+"','";
				query+=message.getBook().getBookGenre()+"','";
				query+=message.getBook().getBookDescription()+"','";
				query+=message.getBook().getOriginalQuantity()+"','";
				query+="2019-30-1"+"','"; 
				query+=message.getBook().getShelfLocation()+"','";
				query+=message.getBook().getPdfPath()+"','";
				query+=message.getBook().getOriginalQuantity()+"','";
				query+=message.getBook().getBookDemand();
				query+="');";
				n=stmt.executeUpdate(query);
				
				break;
				
				/**
				 * Search book for Delete Book
				 * @author Yarin	
				 */
			case SEARCH_BOOK_FOR_DELETEBOOK:
				
				Book tempBook=null;
				query = "SELECT * FROM book WHERE bookID= '" + message.getBook().getBookID()+"';";
				rset=stmt.executeQuery(query);
				Date tempDate=new Date(0);
				
				if(rset.next()==true) {
					tempBook=new Book(rset.getString("bookID"),rset.getString("title"),rset.getString("author"),Integer.parseInt(rset.getString("edition")),
							tempDate.toString() ,rset.getString("category"),rset.getString("description"),
							Integer.parseInt(rset.getString("originalQuantity")),tempDate.toString(),rset.getString("location"),
							rset.getString("pdf"),Integer.parseInt(rset.getString("currentQuantity")),rset.getString("wanted"));
				}
				
				MessageCS resultBookForDeleteBook=new MessageCS(MessageType.SEARCH_BOOK_FOR_DELETEBOOK, tempBook);
				client.sendToClient(resultBookForDeleteBook);
				break;
				
			/**
			 * Delete Book
			 * @author Yarin
			 */
			case DELETE_BOOK:
				
				int deleteReturnValue;
				query="DELETE FROM book WHERE bookID = '";
				query+=message.getBook().getBookID()+"';";
				//query+="');";
				deleteReturnValue=stmt.executeUpdate(query);
				break;
				
				/**
				 * @author Yarin
				 */
			case ACTIVITY_REPORT:
				ArrayList<GenerateReport> generateList=new ArrayList<>();
				int active=0;
				int frozen=0;
				int locked=0;
				int booksQuantity=0;
				int lates=0;
				int shalevislame;
				// Get subscriber statuses
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Active\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					active=rset.getInt("COUNT(subscriberStatus)");
				}
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Frozen\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					frozen=rset.getInt("COUNT(subscriberStatus)");
				}
				query = "SELECT COUNT(subscriberStatus) FROM subscriber "
						+ "WHERE subscriberStatus = \"Locked\"";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					locked=rset.getInt("COUNT(subscriberStatus)");
				}
				
				// Get books originalQuantity
				query = "SELECT SUM(originalQuantity) FROM book ";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					booksQuantity=rset.getInt("SUM(originalQuantity)");
				}
				
				// Get laters
				query = "SELECT COUNT(lateReturn) FROM subscriber WHERE lateReturn > 0";
				rset = stmt.executeQuery(query);
				if(rset.next()) {
					lates=rset.getInt("COUNT(lateReturn)");
				}
				
				
				/* Check if report of today already exists in the system */
				query="SELECT * FROM activityreport WHERE date = \"";
				query+=LocalDate.now()+"\"";
				rset=stmt.executeQuery(query);
				if(rset.next()) {
					// Is exist, need to UPDATE
					query="UPDATE activityreport SET active = \"" + active + "\", frozen = \"" + frozen + "\", "
							+ "locked = \""+locked+"\", books = \"" + booksQuantity + "\", late = \"" +lates +"\" WHERE date = \"" + LocalDate.now()+"\";";
					shalevislame=stmt.executeUpdate(query);
				}
				else {
					// is not exist, need to INSERT
					query="INSERT INTO activityreport VALUES ('";
					query+=LocalDate.now()+"','";
					query+=active+"','";
					query+=frozen+"','";
					query+=locked+"','";
					query+=booksQuantity+"','";
					query+=lates;
					query+="');";
					shalevislame=stmt.executeUpdate(query);
				}
				GenerateReport generateReport = new GenerateReport(LocalDate.now(), active, frozen, locked, booksQuantity, lates);
				message = new MessageCS(MessageType.ACTIVITY_REPORT,generateReport);
				client.sendToClient(message);
				break;
				
				
			case SEARCH_BOOK: 
				ArrayList<Book> bookList = new ArrayList<>();
				Book book = null;
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
						String tempQuery;
						tempQuery = "SELECT * FROM BorrowedBook bor, Book b where bor.bookID = b.bookID and b.currentQuantity = 0 and b.bookID = '"
								+ rset.getString("bookID") + "' ORDER BY returnDate";
						//must create new ResultSet for inner checking
						ResultSet rsetFindSoonestReturn = stmtFindSoonestReturn.executeQuery(tempQuery);
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
					System.out.println("no such a book");
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
