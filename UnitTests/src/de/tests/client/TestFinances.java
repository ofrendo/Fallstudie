package de.tests.client;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.client.Client;
import de.client.company.Company;
import de.client.company.finances.Credit;
import de.client.company.finances.CreditType;
import de.server.Server;
import de.shared.game.Constants;
import de.tests.TestUtils;

public class TestFinances {
	
	private Client client1;
	private Client client2;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("[TEST] Starting server...");
		Server server = Server.getInstance();
		server.start();
		
		TestUtils.blockLong();
		
		System.out.println("[TEST] Starting clients...");
		client1 = new Client(TestUtils.getIP(), "Olli");
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		
		TestUtils.blockShort();
		
		client2 = new Client(TestUtils.getIP(), "Jörn");
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
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCredits() {
		Company company = client1.getClientGame().getCompany();
		// add 2 credits
		company.getFinances().addCredit(CreditType.TEST_1);
		company.getFinances().addCredit(CreditType.TEST_2);
		Assert.assertEquals(1500000, company.getFinances().getDebtCapital(), 0.01);
		Assert.assertEquals(Constants.START_MONEY + 1500000, company.getMoney(), 0.01);
		// test whether interests are calculated right
		ArrayList<Credit> credits = company.getFinances().getCredits();
		Assert.assertEquals( 6250, credits.get(0).getInterestsPerQuarter(), 0.01);
		Assert.assertEquals( 25000, credits.get(1).getInterestsPerQuarter(), 0.01);
		
		// YEAR 1
		// new round
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		// check DebtCapital, money and FinanceCosts
		Assert.assertEquals(1354166.66666, company.getFinances().getDebtCapital(), 0.01);
		Assert.assertEquals(Constants.START_MONEY + 1322916.6666, company.getMoney(), 0.01);
		// not the end of the year; so still no new balance!
		Assert.assertEquals(0, company.getFinances().getBalance().getProfitAndLoss().getFinanceCosts(), 0.01);
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		Assert.assertEquals(4, client1.getClientGame().getRound());
		// check DebtCapital, money and FinanceCosts
		Assert.assertEquals(916666.66666, company.getFinances().getDebtCapital(), 0.01);
		Assert.assertEquals(125000, company.getFinances().getBalance().getProfitAndLoss().getFinanceCosts(), 0.01);
		Assert.assertEquals(Constants.START_MONEY + 791666.6666, company.getMoney(), 0.01);
		
		// YEAR 2
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		// check DebtCapital, money and FinanceCosts
		Assert.assertEquals(333333.3333, company.getFinances().getDebtCapital(), 0.01);
		Assert.assertEquals(79166.6666, company.getFinances().getBalance().getProfitAndLoss().getFinanceCosts(), 0.01);
		Assert.assertEquals(Constants.START_MONEY + 129166.6666, company.getMoney(), 0.01);
		
		// YEAR 3
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		client1.sendReadyMessage(true);
		client2.sendReadyMessage(true);
		TestUtils.blockLong();
		// check DebtCapital, money and FinanceCosts
		Assert.assertEquals(0, company.getFinances().getDebtCapital(), 0.01);
		Assert.assertEquals(33333.3333, company.getFinances().getBalance().getProfitAndLoss().getFinanceCosts(), 0.01);
		Assert.assertEquals(Constants.START_MONEY - 237500, company.getMoney(), 0.01);
	}

}
