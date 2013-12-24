package de.shared.map;

import java.io.Serializable;
import java.util.ArrayList;

import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.Coords;
import de.shared.map.region.Region;

public class Map implements Serializable {
	
	private static final long serialVersionUID = 5481144647633971711L;

	private ArrayList<Region> regions;
	private EnergyExchange energyExchange;
	private ResourceMarket resourceMarket;

	//NOTE ENERGY DEMAND PER PERSON PER CITY OR FOR THE WHOLE MAP?
	private double energyFactor = 3.14159;

	public MapType mapType;
	
	private static Map instance;
	private Map(MapType mapType) {
		this.mapType = mapType;
		regions = RegionGenerator.generateRegions(mapType);
		energyExchange = new EnergyExchange();
		resourceMarket = new ResourceMarket();
	}
	
	public static Map getInstance() {
		if (instance == null)
			instance = new Map(MapTypeRect.NORMAL);
		
		return instance;
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


	public EnergyExchange getEnergyExchange() {
		return energyExchange;
	}
	
	public ResourceMarket getResourceMarket() {
		return resourceMarket;
	}

	//Not finished
	@Override
	public boolean equals(Object o) {
		if (o instanceof Map) {
			Map otherMap = (Map) o;
			
			for (int i = 0; i < getRegions().size(); i++) {
				Region thisRegion = getRegions().get(i);
				Region otherRegion = otherMap.getRegions().get(i);
				
				if (thisRegion != otherRegion)
					return false;
				
				/*if (thisRegion.getClass() == CityRegion.class && otherRegion.getClass() == CityRegion.class) {
					System.out.println("HERE");
				}
				if (thisRegion.getClass() == ResourceRegion.class && otherRegion.getClass() == ResourceRegion.class) {
					ResourceRegion thisRRegion = (ResourceRegion) thisRegion;
					ResourceRegion otherRREgion = (ResourceRegion) otherRegion;
					
					if (thisRRegion.resourceRegionStatus != otherRREgion.resourceRegionStatus) {
						//System.out.println(thisRRegion.resourceRegionStatus);
						//System.out.println(otherRREgion.resourceRegionStatus);
						return false;
					}					
					
					
				}
				else {
					System.out.println(thisRegion == otherRegion);
					System.out.println(otherRegion);
					System.out.println(thisRegion.getClass() == CityRegion.class);
					System.out.println(otherRegion.getClass() == CityRegion.class);
					return false;
				}*/
			}
			return true;
		}
		return false;
	}
	
	public synchronized double getEnergyFactor() {
		return this.energyFactor;
	}
	
	public synchronized void setEnergyFactor(double energyFactor) {
		this.energyFactor = energyFactor;
	}
	
}
