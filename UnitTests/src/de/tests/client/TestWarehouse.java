package de.tests.client;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import de.client.company.Company;
import de.client.company.Warehouse;
import de.shared.game.Constants;
import de.shared.map.region.ResourceType;


public class TestWarehouse {
	
	@Test
	public void testAddWare() {
		Warehouse wh = new Warehouse(null);
		wh.addWare(ResourceType.GAS, 20);
		wh.addWare(ResourceType.COAL, 50);
		wh.addWare(ResourceType.GAS, 50);
		Assert.assertEquals(50, wh.getWare(ResourceType.COAL).getAmount(), 0.000000001);
		Assert.assertEquals(70, wh.getWare(ResourceType.GAS).getAmount(), 0.000000001);
	}
	
	@Test
	public void testReduceWare() {
		Warehouse wh = new Warehouse(null);
		wh.addWare(ResourceType.GAS, 100);
		wh.addWare(ResourceType.COAL, 100);
		// reduce ware
		try {
			wh.reduceWare(ResourceType.GAS, 40);
		} catch (Exception e) {
			fail(e.getMessage());
		}
		// try to get more than exists
		try {
			wh.reduceWare(ResourceType.COAL, 110);
			// if the method doesn't throw an exception:
			fail("Problems in Error handling with reducing the ware more than existent");
		} catch (Exception e) {
			// method should throw an exception
		}
		Assert.assertEquals(60, wh.getWare(ResourceType.GAS).getAmount(), 0.001);
		Assert.assertEquals(100, wh.getWare(ResourceType.COAL).getAmount(), 0.001);
	}
	
	@Test
	public void testCalculateCosts() {
		Warehouse wh = new Warehouse(null);
		wh.addWare(ResourceType.GAS, 150);
		wh.addWare(ResourceType.COAL, 100);
		wh.nextRound();
		Assert.assertEquals(25000, wh.getCosts(), 0.000000001);
	}
	
	@Test
	public void testBuyWare() throws Exception{
		Company company = new Company(null, null);
		Warehouse wh = company.getWarehouse();
		wh.buyWare(ResourceType.COAL, 10);
		Assert.assertEquals(Constants.START_MONEY - Constants.COST_COAL * 10, company.getMoney(), 0.001);
		Assert.assertEquals(10, company.getWarehouse().getWare(ResourceType.COAL).getAmount(), 0.001);
		company.getFinances().getBalance().nextYear();
		Assert.assertEquals(Constants.COST_COAL * 10, company.getFinances().getBalance().getProfitAndLoss().getResourceCosts(), 0.001);
	}
	
	@Test
	public void testSellWare() throws Exception{
		Company company = new Company(null, null);
		Warehouse wh = company.getWarehouse();
		wh.addWare(ResourceType.COAL, 100);
		wh.addWare(ResourceType.GAS, 10);
		wh.sellWare(ResourceType.COAL, 50);
		Assert.assertEquals(50, wh.getWare(ResourceType.COAL).getAmount(), 0.001);
		Assert.assertEquals(10, wh.getWare(ResourceType.GAS).getAmount(), 0.001);
		Assert.assertEquals(Constants.START_MONEY + Constants.COST_COAL * 50, company.getMoney(), 0.001);
		company.getFinances().getBalance().nextYear();
		Assert.assertEquals(Constants.COST_COAL * 50, company.getFinances().getBalance().getProfitAndLoss().getRevenue(), 0.001);
		// try to sell more than existent
		try {
			wh.sellWare(ResourceType.COAL, 60);
			// if it doesn't throw an exception
			fail("Problems with error handling selling more than stored in Warehouse!");
		} catch (Exception e) {
			// should get here
		}
		// try to sell a ware that cannot be stored in Warehouse
		try {
			wh.sellWare(ResourceType.WATER, 0);
			// if it doesn't throw an exception
			fail("Problems with error handling selling not storable resources");
		} catch (Exception e) {
			// should get here
		}
	}

}
