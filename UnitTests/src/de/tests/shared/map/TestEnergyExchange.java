package de.tests.shared.map;

import org.junit.Assert;
import org.junit.Test;

import de.shared.map.EnergyExchange;

public class TestEnergyExchange {

	@Test
	public void testCurrentEnergyPrice() {
		EnergyExchange energyExchange = new EnergyExchange();
		Assert.assertEquals(0.20, energyExchange.getCurrentEnergyPrice(), 0.000000001);
		energyExchange.addTrade(+20);	// somebody offering (selling) 20
		energyExchange.addTrade(-10);	// somebody demanding (buying) 10
		energyExchange.calculatePrice();
		Assert.assertEquals(0.18, energyExchange.getCurrentEnergyPrice(), 0.005);
	}
	
	@Test
	public void testGetEnergy(){
		EnergyExchange energyExchange = new EnergyExchange();
		Assert.assertEquals(5, energyExchange.getPrice(25), 0.005);
	}
	
	

}
