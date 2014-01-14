package de.tests.client.optimization;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.client.company.PowerStation;
import de.client.optimization.MatrixWrapper;
import de.client.optimization.Optimizer;
import de.shared.map.relation.Contract;

public class OptimizeBuildMatrix {

	@Before
	public void setup() {
		TestObjectFactory.getInstance().init();
	}
	
	@Test
	public void test() {
		PowerStation[] ps = TestObjectFactory.getInstance().powerStations;
		Contract[] contracts = TestObjectFactory.getInstance().contracts;
		
		MatrixWrapper testWrapper = Optimizer.buildMatrixWrapper(ps, contracts);
		
		double[][] expectedMatrix = {
				{1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
				{0, 1, 1, 0, 0, 1, 0, 0, 0, 1},
				{0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
				{30, 20, 0, 0, 0, 0, 0, 1, 0, 50},
				{0, 0, 20, 40, 0, 0, 0, 0, 1, 60},
				{-0.39*30, -0.39*20, -0.35*20, -0.35*40, 0, 0, 0, 0, 0, 0}
		};
		
		/*for (double[] row : testWrapper.values) {
			for (double number : row) {
				System.out.print(number + " ");
			}
			System.out.println();
		}*/
		
		for (int i = 0; i < expectedMatrix.length; i++) {
			Assert.assertArrayEquals(expectedMatrix[i], testWrapper.values[i], 0.0000000000001);
		}
		
	}
	
	@After
	public void tearDown() {
		TestObjectFactory.getInstance().destroyData();
	}

}
