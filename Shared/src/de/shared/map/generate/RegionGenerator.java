package de.shared.map.generate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceType;

public class RegionGenerator {
	
	public static ArrayList<Region> generateRegions(MapType mapType) {
		double portionCoal = 1/7;
		double portionGas = 1/7;
		double portionUranium = 1/14;
		double portionWind = 1/7;
		double portionSolar = 1/7;
		double portionWater = 1/14;
		//double portionEmpty = 1/7;

		int minHexLengthCityDistance = 1;
		int minPopulation = 1000000;
		int maxPopulation = 2000000;
		
		int coalTiles = (int) (portionCoal * mapType.getNumberTiles());
		int gasTiles = (int) (portionGas * mapType.getNumberTiles());
		int uraniumTiles = (int) (portionUranium * mapType.getNumberTiles());
		int windTiles = (int) (portionWind * mapType.getNumberTiles());
		int solarTiles = (int) (portionSolar * mapType.getNumberTiles());
		int waterTiles = (int) (portionWater * mapType.getNumberTiles());
		int emptyTiles = mapType.getNumberTiles() - coalTiles - gasTiles - uraniumTiles - windTiles - solarTiles - waterTiles;
		
		HashMap<ResourceType, Integer> resourceMap = new HashMap<ResourceType, Integer>();
		
		resourceMap.put(ResourceType.COAL, coalTiles);
		resourceMap.put(ResourceType.GAS, gasTiles);
		resourceMap.put(ResourceType.URANIUM, uraniumTiles);
		resourceMap.put(ResourceType.WIND, gasTiles);
		resourceMap.put(ResourceType.SOLAR, solarTiles);
		resourceMap.put(ResourceType.WATER, waterTiles);
		resourceMap.put(ResourceType.EMPTY, emptyTiles);
		
		//Population should be between 1 and 2 mill
		//HEXAGON POINT SHOULD BE TOP
		ArrayList<Region> regions = new ArrayList<Region>();
		
		int numberCitiesLeft = mapType.getNumberCities();
		//int numberTilesLeft = mapType.getNumberTiles();
		
		if (mapType instanceof MapTypeRect) {
			boolean citiesDone = false;

			while (!citiesDone) {
				//Cities not allowed at edge of map
				//city circles amount = amountcities
				int rowIndex = getRandomInt(1, ((MapTypeRect) mapType).amountRows-2); //Which row this tile is in
				int tileIndex = getRandomInt(1, ((MapTypeRect) mapType).lengthRow-2); //Index of tile in row
				
				int xShift = - rowIndex / 2;
				
				int regionIDX = tileIndex + xShift;
				int regionIDY = rowIndex;
				
				//Check if this region has been added already or is in range of another
				boolean isOKToPlace = true;
				for (Region region : regions) {
					if (getRegionDistance(regionIDX, regionIDY, region.coords.x, region.coords.y) <= minHexLengthCityDistance) {
						isOKToPlace = false;
					}
				}
				
				if (isOKToPlace == true) {
					int population = getRandomInt(minPopulation, maxPopulation);
					CityRegion cityRegion = new CityRegion(regionIDX, regionIDY, population);
					regions.add(cityRegion);
					numberCitiesLeft--;
					//numberTilesLeft--;
					if (numberCitiesLeft == 0) {
						citiesDone = true;
					}
				}
			}
			
			for (int rowIndex = 0; rowIndex < ((MapTypeRect) mapType).amountRows; rowIndex++) {
				for (int tileIndex = 0; tileIndex < ((MapTypeRect) mapType).lengthRow; tileIndex++) {
					
					int xShift = - rowIndex / 2;
					
					int regionIDX = tileIndex + xShift;
					int regionIDY = rowIndex;
				
					//Check if this is not already a city
					boolean isOKToPlace = true;
					for (int i = 0; i < mapType.getNumberCities(); i++) {
						Region cityRegion = regions.get(i);
						
						if (getRegionDistance(regionIDX, regionIDY, cityRegion.coords.x, cityRegion.coords.y) == 0) {
							isOKToPlace = false;
						}
					}
					
					if (isOKToPlace == true) {
						ResourceType resourceType = getRandomResourceType(resourceMap);
						ResourceRegion resourceRegion = new ResourceRegion(regionIDX, regionIDY, resourceType);
						
						regions.add(resourceRegion);
					}
					
				}
			}
			
		}
		else if (mapType instanceof MapTypeHexagon) {
			
		}
		
		return regions;
	}
	
	private static Random random = new Random();
	/**
	 * Returns the index of a resource that still has tiles left. Also counts down the number of remaining Tiles.
	 * @return
	 */
	public static ResourceType getRandomResourceType(HashMap<ResourceType, Integer> resourceMap) {
		List<ResourceType> keys = new ArrayList<ResourceType>(resourceMap.keySet());
		ResourceType randomKey = keys.get( random.nextInt(keys.size()) );
		
		resourceMap.put(randomKey, resourceMap.get(randomKey) - 1);
		
		if (resourceMap.get(randomKey) == 0) {
			resourceMap.remove(randomKey);
		}
		
		return randomKey;
	}
	
	
	
	/**
	 * Returns a random integer in a range from min to max.
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomInt(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	public static int getRegionDistance(int regionIDX1, int regionIDY1, int regionIDX2, int regionIDY2) {
		//If vorzeichen is same: add beträge
		//if vorzeichen is different: take bigger one
		int dx = regionIDX1 - regionIDX2;
		int dy = regionIDY1 - regionIDY2;
		
		if ((dx < 0 && dy > 0) || (dx > 0 && dy < 0)) {
			//Different vorzeichen
			return Math.max(dx, dy);
		}
		else {
			//Same: add beträge
			return Math.abs(dx) + Math.abs(dy);
		}
	}
	
}
