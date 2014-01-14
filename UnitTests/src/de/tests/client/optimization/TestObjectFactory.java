package de.tests.client.optimization;

import de.shared.map.region.*;
import de.client.company.PowerStation;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;

public class TestObjectFactory {
	
	private static TestObjectFactory instance;
	
	private TestObjectFactory() {}
	
	public static TestObjectFactory getInstance() {
		if (instance == null) 
			instance = new TestObjectFactory();
		
		return instance;
	}
	
	public PowerStation[] powerStations;
	public CityRelation[] cityRelations;
	public Contract[] contracts;
	
	public void init() {
		double city1Demand = 50;
		double city1Price = 0.39;
		
		double city2Demand = 60;
		double city2Price = 0.35;
		
		//double power1Prod = 30;
		//double power2Prod = 20;
		//double power3Prod = 40;
		
		//Make cityrelations
		Coords coords1 = new Coords(0, 0);
		CityRelation cityRelation1 = new CityRelation(coords1);
		Contract contract1 = new Contract(null, coords1, 0.0, 0.0, 0, city1Demand, 0.0, city1Price);
		//cityRelation1.setContract(contract1);
		
		Coords coords2 = new Coords(1, 1);
		CityRelation cityRelation2 = new CityRelation(coords2);
		Contract contract2 = new Contract(null, coords2, 0.0, 0.0, 0, city2Demand, 0.0, city2Price);
		//cityRelation2.setContract(contract2);
		
		CityRelation[] cityRelations = {cityRelation1, cityRelation2};
		this.cityRelations = cityRelations;
		
		Contract[] contracts = { contract1, contract2 };
		this.contracts = contracts;
		
		//Make powerstations
		PowerStation powerStation1 = new PowerStation(ResourceType.TEST_30);
		powerStation1.addPowerStationRelation(cityRelation1);
		
		PowerStation powerStation2 = new PowerStation(ResourceType.TEST_20);
		powerStation2.addPowerStationRelation(cityRelation1);
		powerStation2.addPowerStationRelation(cityRelation2);
		
		PowerStation powerStation3 = new PowerStation(ResourceType.TEST_40);
		powerStation3.addPowerStationRelation(cityRelation2);
		
		PowerStation[] powerStations = {powerStation1, powerStation2, powerStation3};
		this.powerStations = powerStations;
	}
	
	public void destroyData() {
		powerStations = null;
		cityRelations = null;
		contracts = null;
	}
	
}
