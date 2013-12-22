package de.tests.shared.map;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import de.shared.map.generate.MapTypeRect;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;

public class GenerateRegions {

	@Test
	public void testGenerateRegions() {
		int numberCities = 0;
		int numberTiles = 0;
		
		final MapTypeRect mapType = MapTypeRect.NORMAL;
		
		final ArrayList<Region> regions = RegionGenerator.generateRegions(mapType);
		
		for (Region region : regions) {
			if (region instanceof CityRegion) {
				numberCities++;
				//System.out.println(region.regionIDX + " " + region.regionIDY);
			}
			numberTiles++;
		}
		
		assertEquals(mapType.getNumberCities(), numberCities);
		assertEquals(mapType.getNumberTiles(), numberTiles);
	}
}
