package de.tests.clientserver.mechanics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.shared.map.region.CityRegion;
import de.shared.map.relation.Contract;
import de.tests.TestUtils;
import de.tests.clientserver.AbstractClientServerTest;

public class TestContract extends AbstractClientServerTest {

	@Test
	public void testContractRequestAndAnswer() {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		Client client1 = new Client(TestUtils.getIP(), "1");
		client1.TEST_setUnitTestMode();
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		
		TestUtils.blockShort();
		
		Client client2 = new Client(TestUtils.getIP(), "2");
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
		
		CityRegion cityRegion = server.getServerGame().getFirstCityRegion();
		
		double price1 = 0.2;
		double price2 = 0.25;

		System.out.println("[TEST] Requesting contracts...");
		
		client1.getClientGame().requestContract(cityRegion, 0, price1);
		client2.getClientGame().requestContract(cityRegion, 0, price2);
		
		TestUtils.blockLong();
		
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		
		TestUtils.blockLong();
		
		Contract c1 = client1.getClientGame().getCompany().getContracts().get(0);
		Contract c2 = client2.getClientGame().getCompany().getContracts().get(0);
		
		int newCustomers = c1.amountCustomer + c2.amountCustomer;
		
		assertEquals(price1, c1.amountMoneyPerCustomer, 0.001);
		assertEquals(price2, c2.amountMoneyPerCustomer, 0.001);
		assertEquals(newCustomers, cityRegion.getPopulation() - cityRegion.getFreeCustomers());
		
		
		//Now cancel contracts
		client1.getClientGame().cancelContract(c1);
		client2.getClientGame().cancelContract(c2);
		
		TestUtils.blockLong();
		
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		
		TestUtils.blockLong();
		
		assertEquals(cityRegion.getPopulation(), cityRegion.getFreeCustomers());
		assertEquals(0, client1.getClientGame().getCompany().getContracts().size());
		assertEquals(0, client2.getClientGame().getCompany().getContracts().size());
	}
	
}
