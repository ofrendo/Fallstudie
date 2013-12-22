package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.tests.TestUtils;

public class TestGameStart {

	@Test
	public void testGameStart() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.start();
		Client client2 = new Client(TestUtils.getIP(), "NoobJ�rn");
		client2.start();
		
		TestUtils.blockShort();
		
		client1.sendReadyMessage();
		client2.sendReadyMessage();
		
		TestUtils.blockLong();
		
		//Compare the client maps with the servermap
		int serverTiles = server.getServerGame().getMap().getRegions().size();
		int client1Tiles = client1.getClientGame().getMap().getRegions().size();
		int client2Tiles = client2.getClientGame().getMap().getRegions().size();
		
		assertEquals(serverTiles, client1Tiles);
		assertEquals(serverTiles, client2Tiles);
		assertEquals(0, client1.getClientGame().getRound());
	}

}
