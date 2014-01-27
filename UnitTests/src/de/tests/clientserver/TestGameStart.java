package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.tests.TestUtils;

public class TestGameStart extends AbstractClientServerTest {

	@Test
	public void testGameStart() {
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
		
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		
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
