package de.client.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import de.shared.map.generate.MapTypeHexagon;

public class PanelMain extends JPanel {

	private static final long serialVersionUID = -5348125842643459414L;
	
	private PanelMenu panelMenu;
	private PanelMap panelMap;
	private PanelDetails panelDetails;
	
	public PanelMain() {
		this.setLayout(new GridBagLayout());
		
		panelMenu = new PanelMenu();
		panelMap = new PanelMap(MapTypeHexagon.SMALL);
		panelDetails = new PanelDetails();
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.WEST;
		//c.fill = GridBagConstraints.VERTICAL;
		c.weighty = 0;
		//Dimension menuD = new Dimension(100)
		this.add(panelMenu, c);
		
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		//c.weighty = 1.0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		panelMap.init();
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
