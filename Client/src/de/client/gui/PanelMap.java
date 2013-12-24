package de.client.gui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import de.shared.map.Map;
import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.region.Region;

public class PanelMap extends JPanel {

	private static final long serialVersionUID = 2211410456715150941L;
	
	private Map map;
	
	public PanelMap(Map map) {
		setBackground(Look.COLOR_MAP_BACKGROUND);
		this.map = map;
	}

	public void init() {
		int padding = 1;
		
		MapType mapType = map.mapType;
		
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

		for (Region region : map.getRegions()) {
			HexagonButton button = new HexagonButton(region);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					Controller.getInstance().handleMapTileClick((HexagonButton) e.getSource());
				}
				
			});
			this.add(button);
		}
		
		//HexagonButton testButton = new HexagonButton(testRegions.get(10));
		//testButton.setBounds(50, 50, 100, 80);
		
		//this.add(testButton);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Controller.getInstance().handleMapPanelClick();
			}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
			
		});
		
	}
	
	
	
}
