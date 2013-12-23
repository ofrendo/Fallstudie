package de.client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.shared.map.generate.MapTypeRect;

public class PanelMain extends JPanel {

	private static final long serialVersionUID = -5348125842643459414L;
	
	private PanelMenu panelMenu;
	private PanelMap panelMap;
	private PanelDetails panelDetails;
	
	public PanelMain() {
		this.setLayout(new GridBagLayout());
		
		panelMenu = new PanelMenu();
		panelMap = new PanelMap(MapTypeRect.SMALL);
		panelDetails = new PanelDetails();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(panelMenu, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		Dimension d = new Dimension(1000, 400);
		panelMap.setMinimumSize(d);
		panelMap.setMaximumSize(d);
		panelMap.setSize(d);
		this.add(panelMap, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.ipadx = 20;
		c.ipady = 20;
		c.fill = GridBagConstraints.BOTH;
		this.add(panelDetails, c);
	
		
	}
	
}
