package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.tests.TestUtils;

public class TestGameRound extends AbstractClientServerTest {

	@Test
	public void testGameRound() {
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
		
		//Start game by setting all clients as ready
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		
		TestUtils.blockLong();
		
		assertEquals(GamePhase.GAME_STARTED, client1.getClientGame().gamePhase);
		
		TestUtils.blockShort();
		
		//Then send ready again
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);

		TestUtils.blockLong();
		
		assertEquals(1, client1.getClientGame().getRound());
		assertEquals(1, client2.getClientGame().getRound());
		assertEquals(1, server.getServerGame().getRound());
	}

}
