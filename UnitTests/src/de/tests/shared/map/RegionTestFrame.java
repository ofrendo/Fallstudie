package de.tests.shared.map;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.shared.map.generate.MapTypeRect;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;

public class RegionTestFrame {
	
	public static void main(String[] args) {
		//int numberCities = 0;
		//int numberTiles = 0;
		
		final MapTypeRect mapType = MapTypeRect.NORMAL;
		
		final ArrayList<Region> regions = RegionGenerator.generateRegions(mapType);
		
		for (Region region : regions) {
			String content;
			if (region instanceof ResourceRegion) {
    			content = ((ResourceRegion) region).resourceType.name();
			}
			else {
				content = "[city]";
			}

			System.out.println(region.coords.x + " " + region.coords.y + " " + content);
			
			if (region instanceof CityRegion || true) {
				//numberCities++;
			}
			//numberTiles++;
		}
		
		//assertEquals(mapType.getNumberCities(), numberCities);
		//assertEquals(mapType.getNumberTiles(), numberTiles);

		//Test draw the map
		JFrame frame = new JFrame("Test regions");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
        final int hexLength = 30;
        final int padding = 5;
		
		class TestPanel extends JPanel {
			private static final long serialVersionUID = 1L;

			@Override
	        public void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            

	            int maxRowIndex = mapType.amountRows-1;
	            
	            for (Region region : regions) {
	            	int xShift = - region.coords.y / 2;
	            	
	            	int pixelX = padding + (region.coords.x - xShift) * 2 + region.coords.y % 2;
	    			int pixelY = padding + maxRowIndex - region.coords.y;
	            	
	    			String content;
	    			if (region instanceof ResourceRegion) {
		    			content = ((ResourceRegion) region).resourceType.name();
	    			}
	    			else {
	    				content = "[city]";
	    			}
	    			
	    			g.drawString(content, pixelX*hexLength, pixelY*hexLength);
	    		}
	        }
		}
		frame.setSize(3 * mapType.lengthRow * (hexLength + padding), 3 * mapType.amountRows * (hexLength + padding));
		frame.setLocationRelativeTo(null);
		frame.add(new TestPanel());
		frame.setVisible(true);
	}
	
	

}
