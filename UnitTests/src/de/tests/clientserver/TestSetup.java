package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import java.net.UnknownHostException;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.tests.TestUtils;

public class TestSetup extends AbstractClientServerTest {

	@Test
	public void testConnect() throws UnknownHostException {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.connectToServer();
		Client client2 = new Client(TestUtils.getIP(), "Jörn");
		client2.connectToServer();
		
		TestUtils.blockLong();

		assertEquals(2, server.getNumberConnections());
	}
	
}
