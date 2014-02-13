package de.client.optimization;

import de.client.company.PowerStation;
import de.client.company.PowerStationRelation;
import de.shared.map.relation.Contract;

/**
 * Optimizes Power Station relations.
 */
public class Optimizer {
	
	/**
	 * Steps:<br>
	 * Zielfunktion aufstellen:
	 * for each stadt for each kraftwerk in range
	 * z = SIGMA StadtPreis * (SIGMA Kraftwerkoutput * Kraftwerkrelation)
	 * <br><br>
	 * Nebenbedinungen aufstellen:
	 * for each stadt for each kraftwerk in range
	 * SIGMA kraftwerkoutput * kraftwerkrelation  <= stadtbedarf
	 * <br><br>
	 * for each kraftwerk
	 * SIGMA kraftwerkrelations <= 1
	 * 
	 * 
	 */
	public static void optimizePowerStations(PowerStation[] powerStations, Contract[] contracts) {
		//Reset from previous in case a contract was cancelled
		for (PowerStation powerStation : powerStations) {
			for (PowerStationRelation p : powerStation.getPowerStationRelations()) {
				p.partPowerStationProduction = 0;
			}
		}
		
		if (powerStations.length == 0) 
			return;
		
		
		MatrixWrapper matrixWrapper = buildMatrixWrapper(powerStations, contracts);
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
		for (Contract contract : contracts) {
			contract.amountEnergySupplied = 0;
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					
					if (contract.coords.equals(powerStationRelation.coords)) {
						contract.amountEnergySupplied += 
								powerStationRelation.partPowerStationProduction * powerStation.getProduction();
					}
					
				}
			}
			
		}
		
	}
	
	public static MatrixWrapper buildMatrixWrapper(PowerStation[] powerStations, Contract[] contracts) {
		//Aim: Create a matrix that iterate() can solve
		int anzahlNeben = powerStations.length + contracts.length + 1;
		
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
		//gleichzeitig Zielfunktion.
		matrix[anzahlNeben-1] = new double[anzahlVariablen];
		
		matrixVarColumn = 0;
		for (Contract contract : contracts) {
			matrix[matrixRow] = new double[anzahlVariablen];
			
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					if (contract.coords.equals(powerStationRelation.coords)) {
						matrix[matrixRow][matrixVarColumn] = powerStation.getProduction();
						
						//Zielfunktion einfügen
						matrix[anzahlNeben-1][matrixVarColumn] = 
								- contract.amountMoneyPerCustomer * powerStation.getProduction();
					}
					matrixVarColumn++;
				}
			}
			
			matrix[matrixRow][anzahlVariablen-1] = contract.amountEnergyNeeded;
			matrix[matrixRow][matrixAddVarColumn] = 1;
			matrixAddVarColumn++;
			matrixRow++;
			matrixVarColumn = 0;
		}
		
		/*for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}*/
		
		/*
		//Zielfunktion
		matrixVarColumn = 0;
		matrix[matrixRow] = new double[anzahlVariablen];
		for (Contract contract : contracts) {
			for (PowerStation powerStation : powerStations) {
				for (PowerStationRelation powerStationRelation : powerStation.getPowerStationRelations()) {
					if (contract.coords.equals(powerStationRelation.coords)) {
						
						matrix[matrixRow][matrixVarColumn] = 
								- contract.amountMoneyPerCustomer * powerStation.getProduction();
						System.out.println(contract.amountMoneyPerCustomer + " " + powerStation.getProduction());
						matrixVarColumn++;
					}
				}
			}
		}
		*/
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
		
		//Change variables
		matrixWrapper.variablesUsed[rowIndex] = matrixWrapper.allVariables[columnIndex];
		
		//Change that row first
		for (int i = 0; i < matrix[rowIndex].length; i++) {
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
