package de.shared.map;

import java.io.Serializable;
import java.util.ArrayList;

import de.shared.game.Constants;
import de.shared.game.Player;
import de.shared.map.generate.MapType;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.relation.Contract;

/**
 * A Map is made up of regions that are generated by the static method RegionGenerator.generateRegions(MapType mapType).
 */
public class Map implements Serializable {
	
	private static final long serialVersionUID = 5481144647633971711L;

	private ArrayList<Region> regions;
	private EnergyExchange energyExchange;
	private ResourceMarket resourceMarket;

	private double energyFactor = Constants.ENERGY_FACTOR;

	public MapType mapType;
	
	public Map(MapType mapType) {
		this.mapType = mapType;
		regions = RegionGenerator.generateRegions(mapType);
		energyExchange = new EnergyExchange();
		resourceMarket = new ResourceMarket();
	}
	
	public Region getRegion(Coords coords) {
		for (Region region : regions) {
			if (region.coords.equals(coords)) {
				return region;
			}
		}
		return null;
	}
	
	public ArrayList<Region> getRegions() {
		return regions;
	}
	
	public ArrayList<ResourceRegion> getOwnedResourceRegions(Player player){
		ArrayList<ResourceRegion> resourceRegions = new ArrayList<ResourceRegion>();
		for(ResourceRegion region: getResourceRegions()) {
			if (region.getOwner() != null && region.getOwner().equals(player)) {
				resourceRegions.add(region);
			}
		}
		return resourceRegions;
	}

	public ArrayList<CityRegion> getCityRegions() {
		ArrayList<CityRegion> cityRegions = new ArrayList<CityRegion>();
		for (Region region : regions) {
			if (region instanceof CityRegion) 
				cityRegions.add( (CityRegion) region);
		}
		return cityRegions;
	}

	public EnergyExchange getEnergyExchange() {
		return energyExchange;
	}
	
	public ResourceMarket getResourceMarket() {
		return resourceMarket;
	}

	public synchronized double getEnergyFactor() {
		return this.energyFactor;
	}
	
	public synchronized void setEnergyFactor(double energyFactor) {
		this.energyFactor = energyFactor;
	}
	
	public synchronized void finishRound() {
		energyExchange.nextGlobalValues();
		calculateContracts();
	}
	
	public void calculateContracts() {
		for (CityRegion cityRegion : getCityRegions()) {
				calculateCityRegionContracts(cityRegion);
		}
	}

	private void calculateCityRegionContracts(CityRegion cityRegion) {
		//Sort contracts
		cityRegion.sortContracts();
		
		ArrayList<Contract> contractsToRemove = new ArrayList<Contract>();
		
		final int population = cityRegion.getPopulation();
		cityRegion.setFreeCustomers(population);
		int remainingPopulation = cityRegion.getPopulation();
		
		final double averageEnergyPrice = cityRegion.getAverageEnergyPrice(); //old average price
		double newSumEnergyPrice = 0;
		
		for (Contract contract : cityRegion.getContracts()) {
			
			int averagePriceCustomers = (int) (population * contract.awareness * contract.popularity);
			int customersForClient = customerFunction(population, averagePriceCustomers, averageEnergyPrice, contract.amountMoneyPerCustomer);
			
			if (remainingPopulation == 0) { //No customers left!
				break;
			}
			
			double energyNeeded = customersForClient * getEnergyFactor();
			if (energyNeeded > contract.maxAmountEnergyNeeded && contract.maxAmountEnergyNeeded > 0) { 
				//Check if user has specified the maximum amount of energy he wants to transfer
				customersForClient = (int) (contract.maxAmountEnergyNeeded / getEnergyFactor());
				energyNeeded = customersForClient * getEnergyFactor();
			}
			
			if (customersForClient > 0) {
				contract.amountCustomer = customersForClient;
				contract.amountEnergyNeeded = energyNeeded;
				
				newSumEnergyPrice += customersForClient * contract.amountMoneyPerCustomer;
				cityRegion.setFreeCustomers( cityRegion.getFreeCustomers() - customersForClient);
				
				if (customersForClient > remainingPopulation) { //Not enough customers left
					customersForClient = remainingPopulation;
					remainingPopulation = 0;
				}
				else if (customersForClient <= remainingPopulation) { //Normal case
					remainingPopulation -= customersForClient;
				}
			}
			else {
				contractsToRemove.add(contract);
			}
		}
		for (Contract c : contractsToRemove) {
			cityRegion.removeContract(c);
		}
		
		if (remainingPopulation > 0) {
			newSumEnergyPrice += remainingPopulation * Constants.NORMAL_ENERGY_PRICE * 1.25;
		}
		
		double newAverageEnergyPrice = newSumEnergyPrice / population;
		cityRegion.setAverageEnergyPrice(newAverageEnergyPrice);
	}
	
	private int customerFunction(int population, int averagePriceCustomers, double averagePrice, double price) {
		/**
		 * Zwei Parabeln: eine von 0 bis zum preis danach von 0 bis customer = 0;
		 * Parabel in der Scheitelpunktform f(x) = a(x-d)^2 + b
		 */
		int b = averagePriceCustomers;
		double a = (b+population) / averagePrice;
		double d = averagePrice;
		double x = price;
		
		if (x > d) a = -a;
		
		int result = (int) (a * (x-d) * (x-d) + b);
		if (result < 0) result = 0;
		
		return result;
	}

	public ArrayList<ResourceRegion> getResourceRegions() {
		ArrayList<ResourceRegion> result = new ArrayList<ResourceRegion>();
		for (Region region : getRegions()) {
			if (region instanceof ResourceRegion) 
				result.add((ResourceRegion) region);
		}
		return result;
	}
	
}
