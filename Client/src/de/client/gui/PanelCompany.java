package de.client.gui;

import javax.swing.JPanel;

public class PanelCompany extends PanelAbstractContent {

	private static final long serialVersionUID = -1699546782655151017L;

	public PanelCompany() {
		getPanelInformation();
		getPanelNews();
		
		super.initLayout();
	}
	
	public JPanel getPanelInformation() {
		if (panelLeft == null) {
			panelLeft = new JPanel();
		}
		return panelLeft;
	}
	
	public JPanel getPanelNews() {
		if (panelRight == null) {
			panelRight = new JPanel();
		}
		return panelRight;
	}
	
}
