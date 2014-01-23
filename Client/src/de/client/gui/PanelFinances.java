package de.client.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.client.company.Balance;
import de.client.company.Company;


public class PanelFinances extends PanelAbstractContent {

	private static final long serialVersionUID = 1281615806739435379L;

	private Company company;
	
	public PanelFinances() {
		company = Controller.getInstance().getCompany();
		getPanelBalance();
		getPanelAccountInformation();
		
		super.initLayout();
	}
	
	public JPanel getPanelBalance() {
		if (panelLeft == null) {
			panelLeft = new JPanel();
			
			Balance balance = company.getFinances().getBalance();
			PanelFinancesBalance panelBalance = new PanelFinancesBalance(balance);
			
			
			panelLeft.add(panelBalance, BorderLayout.CENTER);
		}
		return panelLeft;
	}
	
	public JPanel getPanelAccountInformation() {
		if (panelRight == null) {
			panelRight = new JPanel();

			double currentIncome = company.getCurrentIncome();
			double currentExpenditures = company.getCurrentExpenditures();
			String differenceString = (currentIncome > currentExpenditures) ? "Gewinn: " : "Verlust: ";
			
			String htmlAccountInformation = "<html><table>"
					+ "<tr>"
					+ "<td>Konto: </td>"
					+ "<td>" + Strings.fD(company.getMoney()) + "€</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Einnahmen: </td>"
					+ "<td>" + Strings.fD(currentIncome) + "€</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Ausgaben: </td>"
					+ "<td>" + Strings.fD(currentExpenditures) + "€</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>" + differenceString + "</td>"
					+ "<td>" + Strings.fD(Math.abs(currentIncome-currentExpenditures)) + "€</td>"
					+ "</tr>"
					+ "</table></html>";
			
			JLabel labelAccountInformation = new JLabel(htmlAccountInformation);
			panelRight.add(labelAccountInformation);
			
		}
		return panelRight;
	}
	
	
	
}
