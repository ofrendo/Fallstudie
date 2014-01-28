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
import de.client.company.finances.ProfitAndLoss;
import de.server.Server;
import de.shared.game.Constants;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegion;
import de.tests.TestUtils;
import de.tests.clientserver.AbstractClientServerTest;

public class TestBalance extends AbstractClientServerTest {
	
	private Client client1;
	
/*	@Test
	public void testBalance(){
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
		client1.sendReadyMessage(true);	// start round 1
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 2
		TestUtils.blockLong();
		client1.sendReadyMessage(true);	// start round 3
		TestUtils.blockLong();
		
		Company company = client1.getClientGame().getCompany();
		Coords coords = server.getServerGame().getFirstNeutralResourceRegion().coords;
		
		// buy powerstation
		ResourceRegion region = (ResourceRegion) server.getServerGame().getMap().getRegion(coords);
		ResourceRelation relation = (ResourceRelation) client1.getClientGame().getCompany().getRegionRelation(coords);
		client1.getClientGame().getCompany().buyPowerStation(relation,region.resourceType);
		
		Assert.assertEquals(
				Constants.START_MONEY 
				- region.resourceType.pPurchaseValue, 
				company.getMoney(), 0.001);
		
		// buy mine
		client1.getClientGame().getCompany().buyMine(relation,region.resourceType);
		
		Assert.assertEquals(
				Constants.START_MONEY 
				- region.resourceType.pPurchaseValue 
				- region.resourceType.mPurchaseValue, 
				company.getMoney(), 0.001);
		
		// set building time to 0
		ArrayList<Building> buildings = company.getBuildings();
		for(Building building: buildings){
			while(building.getBuildingTimeLeft() >= 0){
				building.decrementBuildingTimeLeft();
			}
			if(building.isBuilt()) System.out.println("gebaut");
		}
		
		System.out.println("Ressource: " + region.resourceType.name());
		System.out.println("Produktion Mine:     " + region.resourceType.mMaxProduction);
		System.out.println("Verbrauch Kraftwerk: " + region.resourceType.pConsumption);
		System.out.println("Bestand Jahr 0:      " + company.getWarehouse().getWare(region.resourceType).getAmount());
		
		client1.sendReadyMessage(true);	// start round 4
		TestUtils.blockLong();
		System.out.println("Bestand Jahr 1:      " + company.getWarehouse().getWare(region.resourceType).getAmount());
		System.out.println("Wert:                " + company.getWarehouse().getStoredValue());
		

		Balance b = company.getFinances().getBalance();
		ProfitAndLoss p = b.getProfitAndLoss();
		System.out.println("GuV");
		System.out.println("Aufwendungen");
		System.out.println("    Abschreibungen:   " + p.getDepreciation());
		System.out.println("    Abteilungskosten: " + p.getDepartmentCosts());
		System.out.println("    Zinsen:           " + p.getFinanceCosts());
		System.out.println("    Energiekäufe:     " + p.getEnergyMarketCosts());
		System.out.println("    Rohstoffkäufe:    " + p.getResourceCosts());
		System.out.println("    laufende Kosten:  " + p.getBuildingRunningCosts());
		System.out.println("    Netzkosten:       " + p.getNetUsageCosts());
		System.out.println("Erträge");
		System.out.println("    Umsatzerlöse:     " + p.getRevenue());
		System.out.println("    Bestandsver.      " + p.getInventoryChange());
		System.out.println("Gewinn vor Steuern:   " + p.getProfitBeforeTaxes());
		System.out.println("- Steuern:            " + p.getTaxes());
		System.out.println("Gewinn:  " + p.getProfitNet());
		System.out.println();
		System.out.println("Aktiva:");
		System.out.println("AV");
		System.out.println("    Gebäude:     " + b.getBuildingsValue());
		System.out.println("    Grundstücke: " + b.getPlotValue());
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
		System.out.println("    Energiekäufe:     " + p.getEnergyMarketCosts());
		System.out.println("    Rohstoffkäufe:    " + p.getResourceCosts());
		System.out.println("    laufende Kosten:  " + p.getBuildingRunningCosts());
		System.out.println("    Netzkosten:       " + p.getNetUsageCosts());
		System.out.println("Erträge");
		System.out.println("    Umsatzerlöse:     " + p.getRevenue());
		System.out.println("    Bestandsver.      " + p.getInventoryChange());
		System.out.println("Gewinn vor Steuern:   " + p.getProfitBeforeTaxes());
		System.out.println("- Steuern:            " + p.getTaxes());
		System.out.println("Gewinn:  " + p.getProfitNet());
		System.out.println();
		System.out.println("Aktiva:");
		System.out.println("AV");
		System.out.println("    Gebäude:     " + b.getBuildingsValue());
		System.out.println("    Grundstücke: " + b.getPlotValue());
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
	}

}
