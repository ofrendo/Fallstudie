package de.tests.clientserver.mechanics;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.client.Client;
import de.client.company.Building;
import de.client.company.Company;
import de.client.company.ResourceRelation;
import de.client.company.finances.Balance;
import de.client.company.finances.Credit;
import de.client.company.finances.CreditType;
import de.client.company.finances.ProfitAndLoss;
import de.server.Server;
import de.shared.game.Constants;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceType;
import de.tests.TestUtils;
import de.tests.clientserver.AbstractClientServerTest;

public class TestBalance extends AbstractClientServerTest {
	
	private Client client1;
	
	/*@Test
	public void test() {
		Server server = Server.getInstance();
		server.start();
		TestUtils.blockLong();
		client1 = new Client(TestUtils.getIP(), "Olli");
		client1.TEST_setUnitTestMode();
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		TestUtils.blockShort();
		client1.start();
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 0
		TestUtils.blockLong();
		
		// buy plot
		Company company = client1.getClientGame().getCompany();
		Coords coords = server.getServerGame().getFirstNeutralResourceRegion().coords;
		client1.getClientGame().startResourceRegionBidding(coords);	// start bidding
		TestUtils.blockShort();
		client1.sendReadyMessage(true);	// start round 1
		TestUtils.blockLong();
		client1.getClientGame().bidForResourceRegion(coords, 250000);
		TestUtils.blockShort();
		client1.sendReadyMessage(true);	// start round 2
		TestUtils.blockLong();
		
		Assert.assertEquals(Constants.START_MONEY - 250000, company.getMoney(), 0.001);
		
		// buy powerstation
		ResourceRegion region = (ResourceRegion) server.getServerGame().getMap().getRegion(coords);
		ResourceRelation relation = (ResourceRelation) client1.getClientGame().getCompany().getRegionRelation(coords);
		client1.getClientGame().getCompany().buyPowerStation(relation,region.resourceType);
		
		Assert.assertEquals(
				Constants.START_MONEY 
				- 250000 
				- region.resourceType.pPurchaseValue, 
				company.getMoney(), 0.001);
		
		// buy mine
		client1.getClientGame().getCompany().buyMine(relation,region.resourceType);
		ArrayList<Building> buildings = company.getBuildings();
		
		Assert.assertEquals(
				Constants.START_MONEY 
				- 250000 
				- region.resourceType.pPurchaseValue 
				- region.resourceType.mPurchaseValue, 
				company.getMoney(), 0.001);
		
		// set building time to 0
		for(Building building: buildings){
			while(building.getBuildingTimeLeft() >= 0){
				building.decrementBuildingTimeLeft();
			}
			if(building.isBuilt()) System.out.println("gebaut");
		}
		
		// get a credit
		company.getFinances().addCredit(CreditType.TEST_1);
		Credit credit = company.getFinances().getCredits().get(0);
		Assert.assertEquals(Constants.START_MONEY - 250000 - region.resourceType.pPurchaseValue - region.resourceType.mPurchaseValue + CreditType.TEST_1.amount, company.getMoney(), 0.001);
		
		// buy resources
		try {
			company.getWarehouse().buyWare(ResourceType.COAL, 10);
			company.getWarehouse().buyWare(ResourceType.URANIUM, 5);
			company.getWarehouse().buyWare(ResourceType.GAS, 2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("An error occurred when buying resources!");
		}
		try {
			company.getWarehouse().sellWare(ResourceType.COAL, 5);
		} catch (Exception e) {
			e.printStackTrace();
			fail("An error occurred when selling resources!");
		}
		
		client1.sendReadyMessage(true);	// start round 3
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 4
		TestUtils.blockLong();
		
		Assert.assertEquals(
				region.resourceType.mPurchaseValue / region.resourceType.mDepreciationYears
				+ region.resourceType.pPurchaseValue / region.resourceType.pDepreciationYears, 
				company.getFinances().getBalance().getProfitAndLoss().getDepreciation(), 0.001);
		
		// testen, ob Summe aller Aktiva = Summe aller Passiva
		Balance b = company.getFinances().getBalance();
		ProfitAndLoss p = b.getProfitAndLoss();
		System.out.println("GuV");
		System.out.println("Aufwendungen");
		System.out.println("    Abschreibungen:   " + p.getDepreciation());
		System.out.println("    Abteilungskosten: " + p.getDepartmentCosts());
		System.out.println("    Zinsen:           " + p.getFinanceCosts());
		System.out.println("    Energiek�ufe:     " + p.getEnergyMarketCosts());
		System.out.println("    Rohstoffk�ufe:    " + p.getResourceCosts());
		System.out.println("    laufende Kosten:  " + p.getBuildingRunningCosts());
		System.out.println("    Netzkosten:       " + p.getNetUsageCosts());
		System.out.println("Ertr�ge");
		System.out.println("    Umsatzerl�se:     " + p.getRevenue());
		System.out.println("    Bestandsver.      " + p.getInventoryChange());
		System.out.println("Gewinn vor Steuern:   " + p.getProfitBeforeTaxes());
		System.out.println("- Steuern:            " + p.getTaxes());
		System.out.println("Gewinn:  " + p.getProfitNet());
		System.out.println();
		System.out.println("Aktiva:");
		System.out.println("AV");
		System.out.println("    Geb�ude:     " + b.getBuildingsValue());
		System.out.println("    Grundst�cke: " + b.getPlotValue());
		System.out.println("UV");
		System.out.println("    Bestand:     " + b.getInventoryValue());
		System.out.println("    Kasse:       " + b.getMoneyValue());
		System.out.println("-----------------------------");
		System.out.println(b.getBuildingsValue() + b.getPlotValue() + b.getInventoryValue() + b.getMoneyValue());
		System.out.println();
		System.out.println("Passiva:");
		System.out.println("Eigenkapital:    " + b.getEquity());
		System.out.println("Fremdkapital:    " + b.getDebtCapital());
		System.out.println("-----------------------------");
		System.out.println(b.getEquity() + b.getDebtCapital());
		
		Assert.assertEquals(
				b.getBuildingsValue()
				+ b.getPlotValue()
				+ b.getInventoryValue()
				+ b.getMoneyValue(),
				b.getEquity()
				+ b.getDebtCapital(), 0.001);
	}*/
	
	@Test
	public void test() {
		// Start server & client
		Server server = Server.getInstance();
		server.start();
		TestUtils.blockLong();
		client1 = new Client(TestUtils.getIP(), "Olli");
		client1.TEST_setUnitTestMode();
		client1.setCompanyName("OlliAG");
		client1.connectToServer();
		client1.sendInitMessage();
		TestUtils.blockShort();
		client1.start();
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 0
		TestUtils.blockLong();
		
		// buy plot
		Company company = client1.getClientGame().getCompany();
		Coords coords = server.getServerGame().getFirstNeutralResourceRegion().coords;
		client1.getClientGame().startResourceRegionBidding(coords);	// start bidding
		TestUtils.blockShort();
		
		client1.sendReadyMessage(true);	// start round 1
		TestUtils.blockLong();
		
		// bid for region to buy it for 250'000 �
		client1.getClientGame().bidForResourceRegion(coords, 250000);
		TestUtils.blockShort();
		
		client1.sendReadyMessage(true);	// start round 2
		TestUtils.blockLong();
		
		Assert.assertEquals(Constants.START_MONEY - 250000, company.getMoney(), 0.001);
		
		// buy powerstation
		ResourceRegion region = (ResourceRegion) server.getServerGame().getMap().getRegion(coords);
		ResourceRelation relation = (ResourceRelation) client1.getClientGame().getCompany().getRegionRelation(coords);
		client1.getClientGame().getCompany().buyPowerStation(relation,region.resourceType);
		
		// compare remaining money
		Assert.assertEquals(
				Constants.START_MONEY 
				- 250000 
				- region.resourceType.pPurchaseValue, 
				company.getMoney(), 0.001);
		
		// buy mine
		client1.getClientGame().getCompany().buyMine(relation,region.resourceType);
		ArrayList<Building> buildings = company.getBuildings();
		
		// compare remaining money
		Assert.assertEquals(
				Constants.START_MONEY 
				- 250000 
				- region.resourceType.pPurchaseValue 
				- region.resourceType.mPurchaseValue, 
				company.getMoney(), 0.001);
		
		// set building time to 0
		for(Building building: buildings){
			while(building.getBuildingTimeLeft() >= 0){
				building.decrementBuildingTimeLeft();
			}
			if(building.isBuilt()) System.out.println("gebaut");
		}
		
		// get a credit
		company.getFinances().addCredit(CreditType.TEST_1);
		Credit credit = company.getFinances().getCredits().get(0);
		Assert.assertEquals(Constants.START_MONEY - 250000 - region.resourceType.pPurchaseValue - region.resourceType.mPurchaseValue + CreditType.TEST_1.amount, company.getMoney(), 0.001);
		
		// get resource price
		System.out.println("Energiepreis: " + company.getClient().getClientGame().getMap().getEnergyExchange().getCurrentEnergyPrice());
		
		// buy resources
		try {
			company.getWarehouse().buyWare(ResourceType.COAL, 10);
			company.getWarehouse().buyWare(ResourceType.URANIUM, 5);
			company.getWarehouse().buyWare(ResourceType.GAS, 2);
		} catch (Exception e) {
			e.printStackTrace();
			fail("An error occurred when buying resources!");
		}
		try {
			company.getWarehouse().sellWare(ResourceType.COAL, 5);
		} catch (Exception e) {
			e.printStackTrace();
			fail("An error occurred when selling resources!");
		}
		
		// get a contract
		CityRegion cityRegion = server.getServerGame().getFirstCityRegion();
		client1.getClientGame().requestContract(cityRegion, 0, 0.2);
		
		TestUtils.blockLong();

		
		client1.sendReadyMessage(true);	// start round 3
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 4
		TestUtils.blockLong();
		
		Assert.assertEquals(
				region.resourceType.mPurchaseValue / region.resourceType.mDepreciationYears
				+ region.resourceType.pPurchaseValue / region.resourceType.pDepreciationYears, 
				company.getFinances().getBalance().getProfitAndLoss().getDepreciation(), 0.001);
		
		// testen, ob Summe aller Aktiva = Summe aller Passiva
		Balance b = company.getFinances().getBalance();
		ProfitAndLoss p = b.getProfitAndLoss();
		System.out.println("GuV");
		System.out.println("Aufwendungen");
		System.out.println("    Abschreibungen:   " + p.getDepreciation());
		System.out.println("    Abteilungskosten: " + p.getDepartmentCosts());
		System.out.println("    Zinsen:           " + p.getFinanceCosts());
		System.out.println("    Energiek�ufe:     " + p.getEnergyMarketCosts());
		System.out.println("    Rohstoffk�ufe:    " + p.getResourceCosts());
		System.out.println("    laufende Kosten:  " + p.getBuildingRunningCosts());
		System.out.println("    Netzkosten:       " + p.getNetUsageCosts());
		System.out.println("Ertr�ge");
		System.out.println("    Umsatzerl�se:     " + p.getRevenue());
		System.out.println("    Bestandsver.      " + p.getInventoryChange());
		System.out.println("Gewinn vor Steuern:   " + p.getProfitBeforeTaxes());
		System.out.println("- Steuern:            " + p.getTaxes());
		System.out.println("Gewinn:  " + p.getProfitNet());
		System.out.println();
		System.out.println("Aktiva:");
		System.out.println("AV");
		System.out.println("    Geb�ude:     " + b.getBuildingsValue());
		System.out.println("    Grundst�cke: " + b.getPlotValue());
		System.out.println("UV");
		System.out.println("    Bestand:     " + b.getInventoryValue());
		System.out.println("    Kasse:       " + b.getMoneyValue());
		System.out.println("-----------------------------");
		System.out.println(b.getBuildingsValue() + b.getPlotValue() + b.getInventoryValue() + b.getMoneyValue());
		System.out.println();
		System.out.println("Passiva:");
		System.out.println("Eigenkapital:    " + b.getEquity());
		System.out.println("Fremdkapital:    " + b.getDebtCapital());
		System.out.println("-----------------------------");
		System.out.println(b.getEquity() + b.getDebtCapital());
		
		Assert.assertEquals(
				b.getBuildingsValue()
				+ b.getPlotValue()
				+ b.getInventoryValue()
				+ b.getMoneyValue(),
				b.getEquity()
				+ b.getDebtCapital(), 0.001);
		
		// the same using the functions provided by the balance
		Assert.assertEquals(b.getAssetsSum(), b.getLiabilitiesSum(), 0.001);
	}

}
