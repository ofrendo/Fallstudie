package de.client.gui;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.shared.map.region.Region;

public class PanelDetails extends JPanel {

	private static final long serialVersionUID = -3177233247088338481L;

	public PanelDetails() {
		setBackground(Color.RED);
		add(new JLabel("Details longer longer longer"));
	}
	
	public void setContentEmpty() {
		
	}
	
	public void setRegionContent(Region region) {
		
	}
	
}
