package de.shared.map.region;

import java.util.ArrayList;

import de.shared.game.Player;
import de.shared.map.relation.Contract;

public class CityRegion extends Region {

	private static final long serialVersionUID = 5740457263191994777L;
	
	private int population;
	private int freeCustomers;
	private double environmentalAwareness;
	private String cityName = "Mannheim";
	
	private ArrayList<Contract> contracts;
	
	public CityRegion(int regionIDX, int regionIDY, int population) {
		super(regionIDX, regionIDY);
		this.population = population;
		this.freeCustomers = population;
		this.environmentalAwareness = Math.random();
		this.contracts = new ArrayList<Contract>();
	}
	
	public String getCityName() {
		return cityName;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public int getFreeCustomers() {
		return freeCustomers;
	}
	
	public synchronized void setFreeCustomers(int n) {
		this.freeCustomers = n;
	}
	
	public double getEnvironmentalAwareness() {
		return environmentalAwareness;
	}
	
	public synchronized void addContract(Contract contract) {
		freeCustomers -= contract.amountCustomer;
		this.contracts.add(contract);
	}
	
	public synchronized void removeContract(Contract cancelledContract) {
		Contract contractToRemove = null;
		for (Contract contract : contracts) {
			if (contract.getPlayer().equals(cancelledContract.getPlayer())) {
				freeCustomers += cancelledContract.amountCustomer;
				contractToRemove = contract;
			}
		}
		contracts.remove(contractToRemove);
	}
	
	public Contract getPlayerContract(Player player) {
		for (Contract contract : contracts) {
			if (contract.isOwnPlayer(player)) {
				return contract;
			}
		}
		return null;
	}
	
	public double getAverageEnergyPrice(double currentEnergyExchangePrice) {
		//Free customers will return the price of the energy exchange
		double average = freeCustomers * currentEnergyExchangePrice;
		
		for (Contract contract : contracts) {
			average += contract.amountCustomer * contract.amountMoneyPerCustomer;
		}
		
		average = average / population;
		
		return average;
	}
}
