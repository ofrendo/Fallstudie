package de.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class PanelMain extends JPanel {

	private static final long serialVersionUID = -5348125842643459414L;
	
	private PanelMenu panelMenu;
	private PanelMap panelMap;
	private PanelDetails panelDetails;
	
	public PanelMain() {
		this.setLayout(new GridBagLayout());
		
		panelMenu = new PanelMenu();
		panelMap = new PanelMap();
		panelDetails = new PanelDetails();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.BOTH;
		this.add(panelMenu, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		panelMap.setPreferredSize(new Dimension(500, 400));
		this.add(panelMap, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		this.add(panelDetails, c);
	
		
	}
	
}
