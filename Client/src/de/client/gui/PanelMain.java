package de.client.gui;

import java.awt.Dimension;

import de.shared.map.Map;

public class PanelMain extends PanelAbstractContent {

	private static final long serialVersionUID = -5348125842643459414L;
	
	public PanelMain(Map map) {
		//panelMap = new PanelMap(map);
		//panelDetails = new PanelDetails();
		panelLeft = new PanelMap(map);
		((PanelMap) panelLeft).init();
		
		panelRight = new PanelDetails();
		panelRight.setMinimumSize(new Dimension(300, panelRight.getPreferredSize().height));
		panelRight.setPreferredSize(new Dimension(300, panelRight.getPreferredSize().height));
		super.initLayout();
	}
	
	public PanelDetails getPanelDetails() {
		return (PanelDetails) panelRight;
	}
	
	public PanelMap getPanelMap() {
		return (PanelMap) panelLeft;
	}
	
}
