package de.client.optimization;

/**
 * Utility class to wrap a matrix ready to be optimized.
 */
public class MatrixWrapper {
	
	public double[][] values;
	public Object[] variablesUsed; //Linke Spalte
	public Object[] allVariables; //Oberste Zeile
	
	public MatrixWrapper(double[][] values, Object variablesUsed[], Object[] allVariables) {
		this.values = values;
		this.variablesUsed = variablesUsed;
		this.allVariables = allVariables;
	}
	
}
