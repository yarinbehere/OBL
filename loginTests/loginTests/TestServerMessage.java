/**
 * 
 */
package loginTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import common.DataBaseManager;
import common.EchoServer;
import common.MessageCS;
import common.MessageCS.MessageType;
import entity.User;

/**
 * @author G6
 *
 */
public class TestServerMessage {
	EchoServer echoServer;
	MessageCS msg;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		echoServer = new EchoServer(0);
		DataBaseManager dbManager = new DataBaseManager();
		echoServer.setDbManager(dbManager);
		echoServer.setConn(dbManager.dbConnection());
	}

	/**
	 * Test method for
	 * {@link common.ServerController#messageReceivedFromClient(java.lang.Object, common.ocsf.server.ConnectionToClient, java.sql.Connection)}.
	 * validates librarian
	 */
	@Test
	public void testLibrarianMessage() {
		// testing validity of username and password
		User user = new User("100", "1111");
		msg = new MessageCS(MessageType.LOGIN, user);
		echoServer.handleMessageFromClient(msg, null);
		msg = echoServer.getServer().getMessage();
		// validation succeded
		assertEquals(User.Role.LIBRARIAN, msg.getUser().getRole());
	}

	/**
	 * Test method for
	 * {@link common.ServerController#messageReceivedFromClient(java.lang.Object, common.ocsf.server.ConnectionToClient, java.sql.Connection)}.
	 * validates subscriber
	 */
	@Test
	public void testSubscriberMessage() {
		User user = new User("200", "3333");
		msg = new MessageCS(MessageType.LOGIN, user);
		echoServer.handleMessageFromClient(msg, null);
		msg = echoServer.getServer().getMessage();
		// validation succeded
		assertEquals(User.Role.SUBSCRIBER, msg.getUser().getRole());
	}

	/**
	 * Test method for
	 * {@link common.ServerController#messageReceivedFromClient(java.lang.Object, common.ocsf.server.ConnectionToClient, java.sql.Connection)}.
	 * validates manager
	 */
	@Test
	public void testManagerMessage() {
		User user = new User("101", "2222");
		msg = new MessageCS(MessageType.LOGIN, user);
		echoServer.handleMessageFromClient(msg, null);
		msg = echoServer.getServer().getMessage();
		// validation succeded
		assertEquals(User.Role.MANAGER, msg.getUser().getRole());
	}

	/**
	 * Test method for
	 * {@link common.ServerController#messageReceivedFromClient(java.lang.Object, common.ocsf.server.ConnectionToClient, java.sql.Connection)}.
	 * 
	 */
	@Test
	public void testNotExistMessage() {
		// check if user exist in the system
		User user = new User("12", "123");
		msg = new MessageCS(MessageType.LOGIN, user);
		echoServer.handleMessageFromClient(msg, null);
		msg = echoServer.getServer().getMessage();
		assertEquals("User can't be found", msg.getTextMessage());
	}

	/**
	 * Test method for
	 * {@link common.ServerController#messageReceivedFromClient(java.lang.Object, common.ocsf.server.ConnectionToClient, java.sql.Connection)}.
	 * validates manager
	 */
	@Test
	public void testPassword() {
		// username is correct but password is incorrect
		User user = new User("100", "123");
		msg = new MessageCS(MessageType.LOGIN, user);
		echoServer.handleMessageFromClient(msg, null);
		msg = echoServer.getServer().getMessage();
		assertEquals("Password inserted is incorrect!!", msg.getTextMessage());
	}
}
