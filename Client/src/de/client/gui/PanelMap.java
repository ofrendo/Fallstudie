package de.client.gui;

import java.awt.Color;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.Region;

public class PanelMap extends JPanel {

	private static final long serialVersionUID = 2211410456715150941L;
	
	
	public PanelMap(MapType mapType) {
		setBackground(Color.green);
		
		int padding = 1;
		HexagonLayout layout = new HexagonLayout(((MapTypeRect) mapType).lengthRow, new Insets(padding, padding, padding, padding), false);
		layout.setMinimumSize(this.getMinimumSize());
		//layout.setPrefferedSize(this.getPreferredSize());
		
		setLayout(layout);
		
		ArrayList<Region> testRegions = RegionGenerator.generateRegions(mapType);
		
		for (Region region : testRegions) {
			HexagonButton button = new HexagonButton(region);
			this.add(button);
		}
		
		//HexagonButton testButton = new HexagonButton(testRegions.get(10));
		//testButton.setBounds(50, 50, 100, 80);
		
		//this.add(testButton);
		
		
	}
	
}
