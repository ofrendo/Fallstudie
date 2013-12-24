package de.tests.shared.map;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;

public class GenerateRegions {

	@Test
	public void testGenerateRegions() {
		int numberCities = 0;
		int numberTiles = 0;
		
		MapType mapType = MapTypeHexagon.SMALL;
		
		final ArrayList<Region> regions = RegionGenerator.generateRegions(mapType);
		
		for (Region region : regions) {
			if (region instanceof CityRegion) {
				numberCities++;
				//System.out.println(region.regionIDX + " " + region.regionIDY);
			}
			System.out.println(region.coords);
			numberTiles++;
		}
		
		assertEquals(mapType.getNumberCities(), numberCities);
		assertEquals(mapType.getNumberTiles(), numberTiles);
	}
}
