package de.client.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.client.company.Company;
import de.shared.map.region.ResourceType;

public class PanelMenuInformation extends JPanel {

	private static final long serialVersionUID = -6881156700121584687L;
	
	private Company company;
	
	private JLabel labelInfo;
	
	public PanelMenuInformation() {
		company = Controller.getInstance().getCompany();
		reset();
		setBackground(Look.COLOR_MENU_BACKGROUND);
	}
	
	public void reset() {
		removeAll();
		labelInfo = null;
		add(getLabelInfo(), BorderLayout.CENTER);
		refresh();
	}
	
	private JLabel getLabelInfo() {
		if (labelInfo == null) {
			double money = company.getMoney();
			double netIncome = company.getNetIncome();
			String moneySymbol = (netIncome >= 0) ? "+" : "";
			
			double energyNetIncome = company.getSuperflousEnergy();
			String energySymbol = (energyNetIncome >= 0) ? "+" : "";
			
			String temporaryEnergyCells = (company.getTemporaryEnergyBought() <= 0) ? 
					"<td></td><td></td><td></td>" :
					"<td>Gekaufte Energie:</td><td></td>"
					+ "<td>" + Strings.fD(company.getTemporaryEnergyBought()) + Strings.ENERGY_UNIT + "</td>";
			
			
			String htmlInfo = "<html><table cellpadding=2 cellspacing=0>"
					+ "<tr>"
					+ "<td>Konto:</td>"
					+ "<td align='right'>" + Strings.fD(money) + "€"
					+ "<td align='right'> " + moneySymbol + " " + Strings.fD(netIncome) + "€"
					+ " " + Controller.getInstance().getHtmlResourceRow(ResourceType.COAL)
					+ "</tr>"
					+ "<tr>"
					+ "<td>Energie:</td>"
					+ "<td></td>"
					+ "<td align='right'> " + energySymbol + " " + Strings.fD(energyNetIncome) + " " + Strings.ENERGY_UNIT
					+ " " + Controller.getInstance().getHtmlResourceRow(ResourceType.GAS)
					+ "</tr>"
					+ "<tr>"
					+ temporaryEnergyCells
					+ " " + Controller.getInstance().getHtmlResourceRow(ResourceType.URANIUM)
					+ "</tr>"
					+ "</table></html>";
			
			labelInfo = new JLabel(htmlInfo);
		}
		return labelInfo;
	}
	
	private void refresh() {
		this.revalidate();
		this.repaint();
	}
	
	
}
