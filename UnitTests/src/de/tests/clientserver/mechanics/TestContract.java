package de.tests.clientserver.mechanics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.client.Client;
import de.server.Server;
import de.shared.game.GamePhase;
import de.shared.map.region.CityRegion;
import de.tests.TestUtils;

public class TestContract {

	@Test
	public void testContractRequestAndAnswer() {
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
		
		//Start game by setting all clients as ready
		client1.sendReadyMessage();
		client2.sendReadyMessage();
		
		TestUtils.blockLong();
		
		assertEquals(GamePhase.GAME_STARTED, client1.getClientGame().gamePhase);
		
		CityRegion cityRegion = server.getServerGame().getFirstCityRegion();
		
		double price1 = 0.35;
		double price2 = 0.40;
		
		//PRO STADT ZEIGEN WIE VIEL BEDARF IN DER STADT GRAD IST ODER PRO PERSON?
		//Stadt vertrag bilden: 
		//"Vertrag vorschlagen" -> RequestContract zu den bedingungen
		//Antwort vom Server erhalten -> im GUI anzeigen
		//"Vertrag akzeptieren -> zum Server schicken
		client1.getClientGame().requestContract(cityRegion, 100000, price1);
		client2.getClientGame().requestContract(cityRegion, 50000, price2);
		
		TestUtils.blockLong();
		
		client1.getClientGame().acceptContract( client1.getLatestContractAnswer() );
		client2.getClientGame().acceptContract( client2.getLatestContractAnswer() );
		
		System.out.println(client1.getClientGame().getCompany().getContracts().get(0).amountCustomer);
		System.out.println(client2.getClientGame().getCompany().getContracts().get(0).amountCustomer);
		
		assertEquals(price1, client1.getClientGame().getCompany().getContracts().get(0).amountMoneyPerCustomer, 0.001);
		assertEquals(price2, client2.getClientGame().getCompany().getContracts().get(0).amountMoneyPerCustomer, 0.001);
		
	}
	
}
