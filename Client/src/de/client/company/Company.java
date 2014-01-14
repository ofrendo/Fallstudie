package de.client.company;

import java.util.ArrayList;

import de.client.Client;
import de.shared.game.Constants;
import de.shared.game.Game;
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
		for (RegionRelation relation : regionRelations) {
			if (relation instanceof CityRelation) {
				CityRelation cityRelation = (CityRelation) relation;
				if (cityRelation.getContract() != null) 
					contracts.add(cityRelation.getContract());
			}
			
		}
		return contracts;
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
	
	public PowerStation[] getPowerStations() {
		ArrayList<PowerStation> powerStations = new ArrayList<PowerStation>();
		for (Building building : buildings) {
			if (building instanceof PowerStation) {
				powerStations.add((PowerStation) building);
			}
		}
		return powerStations.toArray(new PowerStation[0]);
	}
	
	public CityRelation[] getCityRelationsWithContract() {
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
	}
	
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
		this.addPowerStation(relation.coords, resourceType);
	}
	
	public void addPowerStation(Coords coords, ResourceType resourceType) {
		PowerStation powerStation = new PowerStation(resourceType);
		
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
	}
	
	public void finishRound() {
		//check if a building has finished
		for (Building building : buildings) {
			building.nextRound();
			money -= building.getRunningCosts();
			
			if (building.getBuildingTimeLeft()==0) {
				sendFinishBuildingMessage(building);
			}
		}
		
		//handle production	
		double production = getResourceProduction(ResourceType.COAL, true);
		warehouse.addWare(ResourceType.COAL, production);
		production = getResourceProduction(ResourceType.URANIUM, true);
		warehouse.addWare(ResourceType.URANIUM, production);
		production = getResourceProduction(ResourceType.GAS, true);
		warehouse.addWare(ResourceType.GAS, production);
		//System.out.println(production);
		
		//handle energy consumption
		
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
	}
	
	private double getResourceProduction(ResourceType resourceType, boolean finishRound) {
		
		double productionSum = 0;
		for (Building building : buildings) {
			
			if(building instanceof Mine)
			{
				
				Mine mine = (Mine) building;
				if (building.isBuilt() && mine.getResourceType() == resourceType) {
					productionSum += mine.getProduction();
					if (finishRound) {
						
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

}
