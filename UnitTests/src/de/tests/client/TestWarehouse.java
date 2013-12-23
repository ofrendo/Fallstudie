package de.tests.client;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import de.client.company.Warehouse;
import de.shared.map.region.ResourceType;

public class TestWarehouse {

	@Test
	public void testAddWare() {
		Warehouse wh = new Warehouse();
		wh.addWare(ResourceType.GAS, 20);
		wh.addWare(ResourceType.COAL, 50);
		wh.addWare(ResourceType.GAS, 50);
		Assert.assertEquals(50, wh.getWare(ResourceType.COAL).getAmount(), 0.000000001);
		Assert.assertEquals(70, wh.getWare(ResourceType.GAS).getAmount(), 0.000000001);
	}
	
	@Test
	public void testReduceWare() {
		Warehouse wh = new Warehouse();
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
	}
	
	@Test
	public void testCalculateCosts() {
		Warehouse wh = new Warehouse();
		wh.addWare(ResourceType.GAS, 150);
		wh.addWare(ResourceType.COAL, 100);
		wh.calculateCosts();
		Assert.assertEquals(25000, wh.getCosts(), 0.000000001);
	}

}
