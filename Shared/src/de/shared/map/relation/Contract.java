package de.shared.map.relation;

import java.io.Serializable;

public class Contract implements Serializable {
	private static final long serialVersionUID = -8190051619971410891L;

	public int amountCustomer;
	public double amountEnergyNeeded;
	public double amountEnergySupplied;
	public double amountMoneyPerQuarter; 
	public double amountMoneyPerCustomer; //for example 0.39
	
	public Contract(int amountCustomer, double amountEnergyNeeded, double amountMoneyPerCustomer) {
		this.amountCustomer = amountCustomer;
		this.amountEnergyNeeded = amountEnergyNeeded;
		this.amountMoneyPerCustomer = amountMoneyPerCustomer;
		this.amountMoneyPerQuarter = amountMoneyPerCustomer * amountCustomer;
	}
	
}
