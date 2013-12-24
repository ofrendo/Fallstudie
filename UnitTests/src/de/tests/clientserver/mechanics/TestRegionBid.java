package de.tests.clientserver.mechanics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionStatus;
import de.tests.TestUtils;
public class TestRegionBid {

	@Test
	public void testRegionBid() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "Olli");
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		
		TestUtils.blockShort();
		
		Client client2 = new Client(TestUtils.getIP(), "Jörn");
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
		
		Coords coords = server.getServerGame().getFirstNeutralResourceRegion().coords;
		
		//Set something up for bidding
		client1.getClientGame().startResourceRegionBidding(coords);
		client2.getClientGame().startResourceRegionBidding(coords);
		
		TestUtils.blockLong();
		
		//Then send ready again
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);

		TestUtils.blockLong();

		ResourceRegion r = (ResourceRegion) server.getServerGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.BUYABLE, r.resourceRegionStatus);
		r = (ResourceRegion) client1.getClientGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.BUYABLE, r.resourceRegionStatus);
		r = (ResourceRegion) client2.getClientGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.BUYABLE, r.resourceRegionStatus);
		
		assertEquals(1, client1.getClientGame().getRound());
		
		//both clients bid 
		client1.getClientGame().bidForResourceRegion(coords, 50000);
		client2.getClientGame().bidForResourceRegion(coords, 100000);
		
		TestUtils.blockShort();
		
		//Then send ready again
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		
		TestUtils.blockLong();
		
		//Now see if all rounds were incremented and if the resourceregionstatus has changed everywhere to owned
		assertEquals(2, client1.getClientGame().getRound());
		assertEquals(2, client2.getClientGame().getRound());
		assertEquals(2, server.getServerGame().getRound());
		
		r = (ResourceRegion) server.getServerGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.OWNED, r.resourceRegionStatus);
		assertEquals(client2.getClientGame().getPlayer(), r.owner);
		r = (ResourceRegion) client1.getClientGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.OWNED, r.resourceRegionStatus);
		assertEquals(client2.getClientGame().getPlayer(), r.owner);
		r = (ResourceRegion) client2.getClientGame().getMap().getRegion(coords);
		assertEquals(ResourceRegionStatus.OWNED, r.resourceRegionStatus);
		assertEquals(client2.getClientGame().getPlayer(), r.owner);
		
		
	
	}

}
