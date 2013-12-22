package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.tests.TestUtils;

public class TestReady {

	@Test
	public synchronized void testReady() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.start();
		Client client2 = new Client(TestUtils.getIP(), "NoobJörn");
		client2.start();
		
		TestUtils.blockShort();
		
		client1.sendReadyMessage();
		client2.sendReadyMessage();
		
		TestUtils.blockLong();
		
		assertEquals(
			GamePhase.GAME_STARTED,
			server.getGamePhase()
		);
	
	}

}
