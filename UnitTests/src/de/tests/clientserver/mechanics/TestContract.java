package de.tests.clientserver.mechanics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.shared.map.region.CityRegion;
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
		
		CityRegion cityRegion = server.getServerGame().getFirstCityRegion();
		
		//PRO STADT ZEIGEN WIE VIEL BEDARF IN DER STADT GRAD IST ODER PRO PERSON?
		//Stadt vertrag bilden: 
		//"Vertrag vorschlagen" -> RequestContract zu den bedingungen
		//Antwort vom Server erhalten -> im GUI anzeigen
		//"Vertrag akzeptieren -> zum Server schicken
		double price1 = 0.45;
		double price2 = 0.30;

		client1.getClientGame().requestContract(cityRegion, 100000, price1);
		client2.getClientGame().requestContract(cityRegion, 50000, price2);
		
		TestUtils.blockLong();
		
		client1.getClientGame().acceptContract( client1.getLatestContractAnswer() );
		client2.getClientGame().acceptContract( client2.getLatestContractAnswer() );
		
		int newCustomers = 
				client1.getClientGame().getCompany().getContracts().get(0).amountCustomer + 
				client2.getClientGame().getCompany().getContracts().get(0).amountCustomer;
		
		TestUtils.blockShort();
		
		assertEquals(price1, client1.getClientGame().getCompany().getContracts().get(0).amountMoneyPerCustomer, 0.001);
		assertEquals(price2, client2.getClientGame().getCompany().getContracts().get(0).amountMoneyPerCustomer, 0.001);
		assertEquals(newCustomers, cityRegion.getPopulation() - cityRegion.getFreeCustomers());
	}
	
}
