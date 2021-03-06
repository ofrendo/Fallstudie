package de.tests.shared.map;

import org.junit.Assert;
import org.junit.Test;

import de.shared.map.EnergyExchange;

public class TestEnergyExchange {

	@Test
	public void testCurrentEnergyPrice() {
		EnergyExchange energyExchange = new EnergyExchange();
		Assert.assertEquals(200, energyExchange.getCurrentEnergyPrice(), 0.000000001);
		energyExchange.addTrade(+2000000);	// somebody offering (selling)
		energyExchange.addTrade(-2000000);	// somebody demanding (buying)
		energyExchange.calculatePrice();
		Assert.assertEquals(200, energyExchange.getCurrentEnergyPrice(), 0.005);
	}
	
	@Test
	public void testGetEnergy(){
		EnergyExchange energyExchange = new EnergyExchange();
		Assert.assertEquals(5*1000, energyExchange.getPrice(25), 0.005);
	}
	
	

}
