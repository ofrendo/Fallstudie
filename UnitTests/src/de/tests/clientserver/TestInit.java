package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.tests.TestUtils;

public class TestInit extends AbstractClientServerTest {

	@Test
	public void testInitialize() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.TEST_setUnitTestMode();
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		
		TestUtils.blockShort();
	
		Client client2 = new Client(TestUtils.getIP(), "Jörn");
		client2.TEST_setUnitTestMode();
		client2.setCompanyName("JörnAG");
		client2.connectToServer();
		client2.sendInitMessage();
		
		TestUtils.blockLong();
		
		client1.start();
		client2.start();
		
		TestUtils.blockLong();

		assertEquals(2, server.getServerGame().getPlayers().size());
	}

}
