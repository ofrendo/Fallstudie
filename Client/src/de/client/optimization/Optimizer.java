package de.client.optimization;

import de.client.company.PowerStation;
import de.client.company.PowerStationRelation;
import de.shared.map.relation.CityRelation;

/**
 * Optimizes Power Station relations.
 * @author D059373
 *
 */
public class Optimizer {
	/**
	 * Steps:
	 * Zielfunktion aufstellen:
	 * for each stadt for each kraftwerk in range
	 * z = SIGMA StadtPreis * (SIGMA Kraftwerkoutput * Kraftwerkrelation)
	 * 
	 * Nebenbedinungen aufstellen:
	 * for each stadt for each kraftwerk in range
	 * SIGMA kraftwerkoutput * kraftwerkrelation  <= stadtbedarf
	 * 
	 * for each kraftwerk
	 * SIGMA kraftwerkrelations <= 1
	 * 
	 * 
	 */
	
	public static void optimizePowerStations(PowerStation[] powerStations, CityRelation[] cityRelationsWithContract) {
		if (powerStations.length == 0) 
			return;
		
		
		MatrixWrapper matrixWrapper = buildMatrixWrapper(powerStations, cityRelationsWithContract);
		iterate(matrixWrapper);
		
		//each variable in variableUsed that is NOT a string is a powerstationrelation: value is last value in that row
		//now all that remains is to actually set the values
		//Set powerstationrelation
		double[][] matrix = matrixWrapper.values;
		for (int i = 0; i < matrixWrapper.variablesUsed.length; i++) {
			Object o = matrixWrapper.variablesUsed[i];
			if (o instanceof PowerStationRelation) {
				((PowerStationRelation) o).partPowerStationProduction = matrix[i][ matrix[i].length-1 ];
			}
		}
		
		//Update contract details (how much energy is delivered)
		for (CityRelation cityRelation : cityRelationsWithContract) {
			
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					if (cityRelation.compareRegionRelation(powerStationRelation)) { 
						
						cityRelation.getContract().amountEnergySupplied += 
								powerStationRelation.partPowerStationProduction * powerStation.getProduction();
						
					}
				}
			}
			
		}
		
	}
	
	public static MatrixWrapper buildMatrixWrapper(PowerStation[] powerStations, CityRelation[] cityRelations) {
		//Aim: Create a matrix that iterate() can solve
		int anzahlNeben = powerStations.length + cityRelations.length + 1;
		
		int anzahlPowerStationRelations = 0;
		for (PowerStation powerStation : powerStations) {
			anzahlPowerStationRelations += powerStation.getPowerStationRelations().size();
		}
		int anzahlVariablen = anzahlNeben + anzahlPowerStationRelations;
		
		//anfangslösung sind alle x = 0; also stehen links in der spalte die schlupfvariablen
		Object[] allVariables = new Object[anzahlVariablen-1];
		Object[] variablesUsed = new Object[anzahlNeben-1];
		for (int i = 0; i < variablesUsed.length; i++) { 
			variablesUsed[i] = "s" + (i+1);
			allVariables[i + anzahlPowerStationRelations] = variablesUsed[i];
		}
		
		
		double[][] matrix = new double[ anzahlNeben ][];
		int matrixRow = 0;
		int matrixVarColumn = 0; //x11, x21, x22, usw
		int matrixAddVarColumn = anzahlPowerStationRelations; //Schlupfvariable, additional variable column
		//Erste nebenbedinungen: Kraftwerkoutput <= 1
		for (PowerStation powerStation : powerStations) {
			matrix[matrixRow] = new double[anzahlVariablen];
			
			for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
				allVariables[matrixVarColumn] = powerStationRelation;
				matrix[matrixRow][matrixVarColumn] = 1;
				matrixVarColumn++;
			}
			
			matrix[matrixRow][anzahlVariablen-1] = 1;
			matrix[matrixRow][matrixAddVarColumn] = 1;
			matrixAddVarColumn++;
					
			matrixRow++;
		}
		
		//Zweite nebenbedinungen: 
		//SIGMA kraftwerkoutput * kraftwerkrelation  <= stadtbedarf
		matrixVarColumn = 0;
		for (CityRelation cityRelation : cityRelations) {
			matrix[matrixRow] = new double[anzahlVariablen];
			
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					if (cityRelation.compareRegionRelation(powerStationRelation)) {
						matrix[matrixRow][matrixVarColumn] = powerStation.getProduction();
						matrixVarColumn++;
					}
				}
			}
			
			matrix[matrixRow][anzahlVariablen-1] = cityRelation.getContract().amountEnergyNeeded;
			matrix[matrixRow][matrixAddVarColumn] = 1;
			matrixAddVarColumn++;
			matrixRow++;
		}
		
		//Zielfunktion
		matrixVarColumn = 0;
		matrix[matrixRow] = new double[anzahlVariablen];
		for (CityRelation cityRelation : cityRelations) {
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					if (cityRelation.compareRegionRelation(powerStationRelation)) {
						
						matrix[matrixRow][matrixVarColumn] = 
								- cityRelation.getContract().amountMoneyPerCustomer * powerStation.getProduction();
						
						matrixVarColumn++;
					}
				}
			}
		}
		
		return new MatrixWrapper(matrix, variablesUsed, allVariables);
	}
	
	public static MatrixWrapper iterate(MatrixWrapper matrixWrapper) {
		//Find lowest negative number of last row
		double[][] matrix = matrixWrapper.values;
		
		double[] lastRow = matrix[ matrix.length-1 ];
		double lowestNumber = 0;
		int columnIndex = 0;
		for (int i = 0; i < lastRow.length-1; i++) {
			if (lastRow[i] < lowestNumber) {
				columnIndex = i; 
				lowestNumber = lastRow[i];
			}
		}
		//If no negative number: done
		if (lowestNumber == 0) {
			return matrixWrapper;
		}
		
		//else: continue iterating
		//Find lowest positive Q > 0
		int rowIndex = 0;
		double lowestQ = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length-1; i++) {
			double[] row = matrix[i];
			//Calculate Q = b/row[rowIndex]
			double q = row[ row.length-1 ] / row[ columnIndex ];
			if (q < lowestQ && q > 0) {
				rowIndex = i;
				lowestQ = q;
			}
			
		}
		
		//Actual iterating step
		//Find pivot element
		double pivotElement = matrix[rowIndex][columnIndex];
		
		//Change that row first
		for (int i = 0; i < matrix[rowIndex].length; i++) {
			//Change variables
			matrixWrapper.variablesUsed[rowIndex] = matrixWrapper.allVariables[columnIndex];
			
			double number = matrix[rowIndex][i];
			matrix[rowIndex][i] = number / pivotElement;
		}
		
		//Now change all other rows
		for (int i = 0; i < matrix.length; i++) {
			if (i != rowIndex) {
				
				double rowMultiplier = - matrix[i][columnIndex];
				
				for (int j = 0; j < matrix[i].length; j++) {
					double number = matrix[i][j];
					matrix[i][j] = number + rowMultiplier * matrix[rowIndex][j];
				}
				
			}
		}
		
		//Do this until only positive last row
		return iterate(matrixWrapper);
	}
	
	
	
	
	
	
	
	
}
