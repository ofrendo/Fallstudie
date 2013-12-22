package de.tests.client.optimization;

import static org.junit.Assert.*;

import org.junit.Test;

import de.client.optimization.*;

public class OptimizeMatrix {

	@Test
	public void optimizeBasicMatrix() {
		//Test 1
		double[][] matrix = { 
				{1, 0,    1,    0, 0,   5},
				{0, 1, -0.5,  0.5, 0, 2.5},
				{0, 0,  0.5, -0.5, 1, 1.5},
				{0, 0, -0.5,  1.5, 0, 12.5}
		};
		
		String[] variablesUsed = {
				"x1", "x2", "s3"
		};
		String[] allVariables = {
				"x1", "x2", "s1", "s2", "s3"
		};
		
		MatrixWrapper matrixWrapper = new MatrixWrapper(matrix, variablesUsed, allVariables);
		
		Optimizer.iterate(matrixWrapper);
		
		assertEquals(matrix[0][5], 2, 0);
		assertEquals(matrix[1][5], 4, 0);
		assertEquals(matrix[2][5], 3, 0);
		assertEquals(matrix[3][5], 14, 0);
		
		//for (Object v : matrixWrapper.variablesUsed) {
			//System.out.println(v);
		//}
		
		double[][] matrix2 = {
				{1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
				{0, 1, 1, 0, 0, 1, 0, 0, 0, 1},
				{0, 0, 0, 1, 0, 0, 1, 0, 0, 1},
				{30, 20, 0, 0, 0, 0, 0, 1, 0, 50},
				{0, 0, 20, 40, 0, 0, 0, 0, 10, 60},
				{-0.39*30, -0.39*20, -0.35*20, -0.35*40, 0, 0, 0, 0, 0, 0}
		};
		
		String[] variablesUsed2 = {
				"s1", "s2", "s3", "s4", "s5"
		};
		String[] allVariables2 = {
				"x11", "x21", "x22", "x32", "s1", "s2", "s3", "s4", "s5"
		};
		
		MatrixWrapper matrixWrapper2 = new MatrixWrapper(matrix2, variablesUsed2, allVariables2);
		
		Optimizer.iterate(matrixWrapper2);
		
		/*int row = 0;
		for (Object v : matrixWrapper2.variablesUsed) {
			System.out.println(v + ": " + matrixWrapper2.values[row][9]);
			row ++;
		}*/
	}

}
