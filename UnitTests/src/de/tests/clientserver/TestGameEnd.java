package de.tests.clientserver;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.Constants;
import de.shared.game.GamePhase;
import de.tests.TestUtils;

public class TestGameEnd extends AbstractClientServerTest {
	
	@Test
	public void testGameEnd() {
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
		
		client1.start();
		TestUtils.blockLong();

		//Start game by setting client as ready
		client1.sendReadyMessage(true);
		TestUtils.blockLong();
		
		assertEquals(GamePhase.GAME_STARTED, client1.getClientGame().gamePhase);
		
		for (int i = 0; i < Constants.GAME_END_MIN_ROUNDS; i++) {
			client1.sendReadyMessage(true);
			TestUtils.blockLong();
		}
		assertEquals(GamePhase.GAME_FINISHED, client1.getClientGame().gamePhase);
		assertEquals(GamePhase.GAME_FINISHED, server.getServerGame().gamePhase);
	}
	
}
