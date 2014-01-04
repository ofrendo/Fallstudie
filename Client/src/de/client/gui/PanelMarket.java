package de.client.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.shared.map.BuyableResource;

public class PanelMarket extends PanelAbstractContent {

	private static final long serialVersionUID = -6017892025944871000L;
	
	private PanelMarketResource panelMarketEnergy;
	private PanelMarketResource panelMarketCoal;
	private PanelMarketResource panelMarketGas;
	private PanelMarketResource panelMarketUranium;
	
	public PanelMarket() {
		this.setLayout(new GridLayout(1, 2));
		this.add(getPanelEnergy());
		this.add(getPanelResources());
	}
	
	public JPanel getPanelEnergy() {
		if (panelLeft == null) {
			panelLeft = new JPanel();
			panelLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
			panelLeft.setBorder(new EmptyBorder(p, p, p, p));
			panelLeft.add(getPanelMarketEnergy());
		}
		return panelLeft;
	}
	
	public JPanel getPanelResources() {
		if (panelRight == null) {
			panelRight = new JPanel();
			panelRight.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
			panelRight.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(p, p, p, p)));
			panelRight.add(getPanelMarketCoal());
			panelRight.add(getPanelMarketGas());
			panelRight.add(getPanelMarketUranium());
		}
		return panelRight;
	}
	
	public PanelMarketResource getPanelMarketEnergy() {
		if (panelMarketEnergy == null) {
			panelMarketEnergy = new PanelMarketResource(BuyableResource.ENERGY);
		}
		return panelMarketEnergy;
	}
	
	public PanelMarketResource getPanelMarketCoal() {
		if (panelMarketCoal == null) {
			panelMarketCoal = new PanelMarketResource(BuyableResource.COAL);
		}
		return panelMarketCoal;
	}
	
	public PanelMarketResource getPanelMarketGas() {
		if (panelMarketGas == null) {
			panelMarketGas = new PanelMarketResource(BuyableResource.GAS);
		}
		return panelMarketGas;
	}
	
	public PanelMarketResource getPanelMarketUranium() {
		if (panelMarketUranium == null) {
			panelMarketUranium = new PanelMarketResource(BuyableResource.URANIUM);
		}
		return panelMarketUranium;
	}
	
}
