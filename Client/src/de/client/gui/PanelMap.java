package de.client.gui;

import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.generate.RegionGenerator;
import de.shared.map.region.Region;

public class PanelMap extends JPanel {

	private static final long serialVersionUID = 2211410456715150941L;
	
	private MapType mapType;
	
	public PanelMap(MapType mapType) {
		setBackground(Look.COLOR_MAP_BACKGROUND);
		this.mapType = mapType;
	}

	public void init() {
		int padding = 1;
		
		int cols;
		if (mapType instanceof MapTypeRect) {
			cols = ((MapTypeRect) mapType).lengthRow;
		}
		else {
			cols = ((MapTypeHexagon) mapType).getMaxAmountTilesForRow();
		}
		
		HexagonLayout layout = new HexagonLayout(cols, new Insets(padding, padding, padding, padding), false, mapType);

		//layout.setMinimumSize(this.getMinimumSize());
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
