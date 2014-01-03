package de.client.gui;

import de.shared.map.Map;

public class PanelMain extends PanelAbstractContent {

	private static final long serialVersionUID = -5348125842643459414L;
	
	public PanelMain(Map map) {
		//panelMap = new PanelMap(map);
		//panelDetails = new PanelDetails();
		panelLeft = new PanelMap(map);
		((PanelMap) panelLeft).init();
		
		panelRight = new PanelDetails();
		
		super.initLayout();
	}
	
	public PanelDetails getPanelDetails() {
		//return panelDetails;
		return (PanelDetails) panelRight;
	}
	
	public PanelMap getPanelMap() {
		//return panelMap;
		return (PanelMap) panelLeft;
	}
	
}
