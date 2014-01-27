package de.client.company;

import java.util.ArrayList;

import de.client.Client;
import de.client.EventMessage;
import de.client.company.finances.Finances;
import de.client.optimization.Optimizer;
import de.shared.game.Constants;
import de.shared.game.Game;
import de.shared.game.Player;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.FinishedBuilding;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionStatus;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;
import de.shared.map.relation.RegionRelation;

public class Company {
	public final String companyName;
	
	private ArrayList<RegionRelation> regionRelations = new ArrayList<RegionRelation>();
	public ArrayList<Building> buildings = new ArrayList<Building>();
	private double money;
	private Client client;
	private Warehouse warehouse;
	private Finances finances;
	
	private double temporaryEnergyBought; //Used for adding energy after a round if not enough has been added
	
	public Company(String companyName, Client client) {
		this.companyName = companyName;
		this.money = Constants.START_MONEY;
		this.client = client;
		this.warehouse = new Warehouse(this);
		this.finances = new Finances(this);
	}
	
	public Warehouse getWarehouse(){
		return this.warehouse;
	}
	
	public Finances getFinances(){
		return this.finances;
	}
	
	public void initRelations(ArrayList<Region> regions) {
		for (Region region : regions) {
			RegionRelation newRegionRelation;
			if (region instanceof CityRegion) {
				newRegionRelation = new CityRelation(region.coords);
			}
			else {
				ResourceRegion resourceRegion = (ResourceRegion) region;
				newRegionRelation = new ResourceRelation(region.coords, resourceRegion.resourceAmount);
			}
			regionRelations.add(newRegionRelation);
		}
	}
	
	public RegionRelation getRegionRelation(Coords coords) {
		for (RegionRelation relation : regionRelations) {
			if (relation.coords.equals(coords))
				return relation;
		}
		return null;
	}
	
	public ArrayList<Contract> getContracts() {
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		for (Region region : client.getClientGame().getMap().getRegions()) {
			if (region instanceof CityRegion) {
				CityRegion cityRegion = (CityRegion) region;
				Contract contract = cityRegion.getPlayerContract(client.getClientGame().getPlayer());
				if (contract != null) 
					contracts.add(contract);
			}
		}
		return contracts;
	}
	
	public Contract[] getContractsArray() {
		return getContracts().toArray(new Contract[0]);
	}
	
	public ArrayList<Contract> getContractsWithNotEnoughEnergy() {
		ArrayList<Contract> contracts = new ArrayList<Contract>();
		Player ownPlayer = client.getClientGame().getPlayer();
		for (Contract contract : getContracts()) {
			if (contract.isOwnPlayer(ownPlayer) && 
				contract.amountEnergySupplied < contract.amountEnergyNeeded) {
				
				contracts.add(contract);
			}
		}
		return contracts;
	}
	
	public ArrayList<CityRegion> getCityRegionsWithNotEnoughEnergy() {
		ArrayList<Contract> contracts = getContractsWithNotEnoughEnergy();
		ArrayList<CityRegion> cityRegions = new ArrayList<CityRegion>();
		
		for (Contract contract : contracts) {
			cityRegions.add( (CityRegion) client.getClientGame().getMap().getRegion(contract.coords) );
		}
		
		return cityRegions;
	}
	
	public ArrayList<RegionRelation> getRegionRelations() {
		return regionRelations;
	}
	
	public ArrayList<Building> getBuildings() {
		return buildings;
	}
	
	public double getMoney() {
		return money;
	}
	
	public void setMoney(double money){
		this.money = money;
	}
	
	public void buyTemporaryEnergy(double amount) {
		money -= client.getClientGame().getMap().getEnergyExchange().getPrice(amount);
		addTemporaryEnergyBought(amount);
	}
	
	public void addTemporaryEnergyBought(double amount) {
		temporaryEnergyBought = amount;
	}
	
	public PowerStation[] getPowerStations() {
		ArrayList<PowerStation> powerStations = new ArrayList<PowerStation>();
		for (Building building : buildings) {
			if (building instanceof PowerStation) {
				powerStations.add((PowerStation) building);
			}
		}
		return powerStations.toArray(new PowerStation[0]);
	}
	
	/*public CityRelation[] getCityRelationsWithContract() {
		ArrayList<CityRelation> cityRelations = new ArrayList<CityRelation>();
		for (RegionRelation relation : regionRelations) {
			if (relation instanceof CityRelation) {
				CityRelation r = (CityRelation) relation;
				
				if (r.getContract() != null) {
					cityRelations.add(r);
				}
				
			}
		}
		
		return cityRelations.toArray(new CityRelation[0]);
	}*/
	
	public boolean isPowerStationInRange(CityRelation cityRelation) {
		for (RegionRelation regionRelation : getRegionRelations()) {
			if (regionRelation instanceof ResourceRelation) {
				ResourceRelation relation = (ResourceRelation) regionRelation;
				if (relation.powerStation != null && relation.powerStation.isBuilt()) {
					if (Game.regionDistance(relation.coords, cityRelation.coords) <= Constants.MAX_POWERSTATION_DISTANCE) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public void addBuilding(Building building) {
		buildings.add(building);

	}
	
	public boolean isEnoughMoney(double amount) {
		return money >= amount;
	}
	
	public void buyMine(ResourceRelation relation, ResourceType resourceType) {
		this.money -= resourceType.mPurchaseValue;
		this.addMine(relation, resourceType);
	}
	
	public void addMine(ResourceRelation relation, ResourceType resourceType) {
		relation.mine = new Mine(resourceType, relation);
		addBuilding(relation.mine);
	}
	
	public void buyPowerStation(ResourceRelation relation, ResourceType resourceType) {
		this.money -= resourceType.pPurchaseValue;
		this.addPowerStation(relation, resourceType);
	}
	
	public void addPowerStation(ResourceRelation relation, ResourceType resourceType) {
		Coords coords = relation.coords;
		PowerStation powerStation = new PowerStation(coords, resourceType);
		
		//Add ps to resourcerelation
		ResourceRelation resourceRelation = (ResourceRelation) this.getRegionRelation(coords);
		resourceRelation.setPowerStation(powerStation);

		//Add powerstationrelations to city
		for (RegionRelation regionRelation : regionRelations) {
			if (regionRelation instanceof CityRelation) {
				CityRelation cityRelation = (CityRelation) regionRelation;
				
				if (Game.regionDistance(coords, cityRelation.coords) <= Constants.MAX_POWERSTATION_DISTANCE) {
					powerStation.addPowerStationRelation(cityRelation);
				}
				
			}
		}
		
		addBuilding(powerStation);
		optimizePowerStations();
	}
	
	public void finishRound() {
		//handle building / check if a building has finished
		for (Building building : buildings) {
			building.nextRound();
			money -= building.getRunningCosts();
			
			if (building.getBuildingTimeLeft()==0) {
				sendFinishBuildingMessage(building);
			}
		}
		
		//handle resource production	
		double production = getResourceProduction(ResourceType.COAL, true);
		warehouse.addWare(ResourceType.COAL, production);
		production = getResourceProduction(ResourceType.URANIUM, true);
		warehouse.addWare(ResourceType.URANIUM, production);
		production = getResourceProduction(ResourceType.GAS, true);
		warehouse.addWare(ResourceType.GAS, production);
		
		//handle resource consumption
		try {
			double consumption = getResourceConsumption(ResourceType.COAL);
			warehouse.reduceWare(ResourceType.COAL, consumption);
			consumption = getResourceConsumption(ResourceType.COAL);
			warehouse.reduceWare(ResourceType.COAL, consumption);
			consumption = getResourceConsumption(ResourceType.COAL);
			warehouse.reduceWare(ResourceType.COAL, consumption);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		//handle energy production / distribution
		//There's already been a check to see whether there is enough energy. So:
		//Sell superflous energy automatically
		if (getSuperflousEnergy() > 0 && temporaryEnergyBought == 0) {
			double amountMoney = client.getClientGame().getMap().getEnergyExchange()
									   .getPrice( getSuperflousEnergy() );
			//ADD WHERE? TO FINANCES? TO MONEY?
			money += amountMoney;
		}
		
		//Add costs of Netznutzungsgebühren
		//Add profits from customers
		double sumNetUsageCosts = 0;
		double sumMoneyCustomers = 0;
		for (Contract contract : getContracts()) {
			sumNetUsageCosts += contract.amountCustomer * Constants.NET_USAGE_COSTS;
			sumMoneyCustomers += contract.amountCustomer * contract.amountMoneyPerCustomer;
			
			money -= sumNetUsageCosts;
			money += sumMoneyCustomers;
		}
		//ADD AND REDUCE WHERE?

		
		// handle warehouse
		warehouse.nextRound();
		// handle finances
		finances.nextRound();
		
		// after 4 quarters perform nextYear methods
		if(client.getClientGame().getRound() % 4 == 3){
			for (Building building : buildings) {
				building.nextYear();
			}
			
			warehouse.nextYear();
			finances.nextYear();
		}
		
		//Reset temporaryEnergyBought
		temporaryEnergyBought = 0;
	}
	
	private double getResourceConsumption(ResourceType resourceType) {
		double consumptionSum = 0;
		for (Building building : buildings) {
			
			if(building instanceof PowerStation)
			{
				
				PowerStation ps = (PowerStation) building;
				if (building.isBuilt() && ps.getResourceType() == resourceType) {
					consumptionSum += ps.getConsumption();
					
				}
			}
		
		}
		return consumptionSum;
	}

	public double getResourceProduction(ResourceType resourceType, boolean finishRound) {
		
		double productionSum = 0;
		for (Building building : buildings) {
			
			if(building instanceof Mine)
			{
				
				Mine mine = (Mine) building;
				if (building.isBuilt() && mine.getResourceType() == resourceType) {
					productionSum += mine.getProduction();
					if (finishRound) {
						
						if (mine.getProduction()>0.0 && mine.getProduction() == mine.getResourceRelation().resourceAmount) {
							client.getClientGame().getEventMessages().add(new EventMessage("Ein Rohstoffvorkommen wurde aufgebraucht", mine.getResourceRelation().coords));
						}
						//reduce the ResourceAmount in the resourceRaltion
						mine.getResourceRelation().decreaseResourceAmount(productionSum);
					
					}
					
				}
			}
		}
		
		return productionSum;
	}

	public void sendFinishBuildingMessage(Building building) {
		ResourceRegionStatus status;
		Coords coords = null;
		// cast to Powerstation to determine if it is a Powerstation or a Mine
		if (building instanceof PowerStation) {
			status = ResourceRegionStatus.POWERSTATION;
		}
		else {
			status = ResourceRegionStatus.MINE;
		}
		
		for (RegionRelation relation : getRegionRelations()) {
			if (relation instanceof ResourceRelation) {
				ResourceRelation resourceRelation = (ResourceRelation) relation;
				
				if (status == ResourceRegionStatus.MINE) {
					if (resourceRelation.getMine() == building) {
						coords = resourceRelation.coords;
						break;
					}
				}
				else if (status == ResourceRegionStatus.POWERSTATION)
				{
					if (resourceRelation.getPowerStation() == building) {
						coords = resourceRelation.coords;
						break;
					}
				}
			}
		}
		client.getClientGame().sendFinishBuilding(new FinishedBuilding(coords, status));
		
	}

	public boolean isEnoughResourcesAvailable() {
		// if not enough resources are available
		if (( getResourceProduction(ResourceType.COAL, false) + warehouse.getWare(ResourceType.COAL).getAmount() ) 
				< getResourceConsumption(ResourceType.COAL)   || 
			( getResourceProduction(ResourceType.URANIUM, false) + warehouse.getWare(ResourceType.URANIUM).getAmount() ) 
				< getResourceConsumption(ResourceType.URANIUM)   || 
			( getResourceProduction(ResourceType.GAS, false) + warehouse.getWare(ResourceType.GAS).getAmount() ) 
				< getResourceConsumption(ResourceType.GAS)) {
			return false;
		}
		return true;
	}
	
	public void optimizePowerStations() {
		Optimizer.optimizePowerStations(getPowerStations(), getContractsArray());
	}
	
	public double getEnergyProductionSum() {
		double sum = 0;
		
		for (PowerStation powerStation : getPowerStations()) {
			if (powerStation.isBuilt()) 
				sum += powerStation.getProduction();
		}
		
		sum += temporaryEnergyBought;
		return sum;
	}
	
	public double getEnergyNeededSum() {
		double sum = 0;
		
		for (Contract contract : getContracts()) {
			sum += contract.amountEnergyNeeded;
		}
		return sum;
	}

	public double getSuperflousEnergy() {
		return getEnergyProductionSum() - getEnergyNeededSum();
	}

	public double getCurrentIncome() {
		double sum = 0;
		for (Contract contract : getContracts()) {
			sum += contract.amountCustomer * contract.amountMoneyPerCustomer;
		}
		if (getSuperflousEnergy() > 0) {
			sum += client.getClientGame().getMap().getEnergyExchange().getPrice( getSuperflousEnergy() );
		}
		return sum;
	}
	
	public double getCurrentExpenditures() {
		double sum = 0;
		
		//Building costs
		for (Building building : buildings) {
			if (building.isBuilt()) 
				sum += building.getRunningCosts();
		}
		//Net usage costs
		for (Contract contract : getContracts()) {
			sum += contract.amountCustomer * Constants.NET_USAGE_COSTS;
		}
		//Costs to buy energy
		if (getSuperflousEnergy() < 0) {
			sum += client.getClientGame().getMap().getEnergyExchange().getPrice( getSuperflousEnergy() );
		}
		return sum;
	}
	
	public double getNetIncome() {
		return getCurrentIncome() - getCurrentExpenditures();
	}

	public int getNumberPowerStations(ResourceType resourceType) {
		int result = 0;
		for (PowerStation powerStation : getPowerStations()) {
			if (powerStation.isBuilt() && powerStation.getResourceType() == resourceType) {
				result++;
			}
		}
		return result;
	}

	public int getNumberMines(ResourceType resourceType) {
		int result = 0;
		for (Building building : buildings)  {
			if (building instanceof Mine) {
				Mine mine = (Mine) building;
				if (mine.isBuilt() && mine.getResourceType() == resourceType) {
					result++;
				}
			}
		}
		return result;
	}
	
}
