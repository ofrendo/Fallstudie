package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.tests.TestUtils;

public class TestInit {

	@Test
	public void testInitialize() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.start();
		Client client2 = new Client(TestUtils.getIP(), "NoobJörn");
		client2.start();
		
		TestUtils.blockLong();

		assertEquals(2, server.getServerGame().getPlayers().size());
	}

}
