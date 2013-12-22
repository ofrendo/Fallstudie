package de.tests.client.optimization;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.client.company.PowerStation;
import de.client.optimization.Optimizer;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;

public class OptimizePowerStations {

	@Before
	public void setup() {
		TestObjectFactory.getInstance().init();
	}
	
	@Test
	public void test() {
		PowerStation[] ps = TestObjectFactory.getInstance().powerStations;
		CityRelation[] cs = TestObjectFactory.getInstance().cityRelations;
		
		Optimizer.optimizePowerStations(ps, cs);
		
		//Check the following things:
		//Contract energy supplied
		Contract contract1 = cs[0].getContract();
		Assert.assertEquals(50, contract1.amountEnergySupplied, 0);
		
		Contract contract2 = cs[1].getContract();
		Assert.assertEquals(40, contract2.amountEnergySupplied, 0);
		
	}
	
	@After
	public void tearDown() {
		TestObjectFactory.getInstance().destroyData();
	}

}
