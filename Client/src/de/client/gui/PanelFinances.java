package de.client.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.client.company.Company;
import de.client.company.finances.Balance;


public class PanelFinances extends PanelAbstractContent {

	private static final long serialVersionUID = 1281615806739435379L;

	private Company company;
	private PanelCredits panelCredits;
	
	public PanelFinances() {
		company = Controller.getInstance().getCompany();
		getPanelBalance();
		getPanelAccountInformation();
		
		super.initLayout();
	}
	
	public JPanel getPanelBalance() {
		if (panelLeft == null) {
			panelLeft = new JPanel(new BorderLayout());
			
			Balance balance = company.getFinances().getBalance();
			PanelFinancesBalance panelBalance = new PanelFinancesBalance(balance);
			
			
			panelLeft.add(panelBalance, BorderLayout.CENTER);
		}
		return panelLeft;
	}
	
	public JPanel getPanelAccountInformation() {
		if (panelRight == null) {
			panelRight = new JPanel();
			panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
			JPanel panelAccountInformation = new JPanel();
			panelAccountInformation.setBackground(Look.COLOR_MAP_BACKGROUND);
			JLabel labelAccountInformation = new JLabel(Controller.getInstance().getHtmlAccountInformation());
			panelAccountInformation.add(labelAccountInformation);
			//labelAccountInformation.setFont(Look.fontSectionPart);
			panelRight.add(panelAccountInformation);
			panelRight.add(getPanelCredits());
		
		}
		return panelRight;
	}

	private PanelCredits getPanelCredits() {
		if(panelCredits == null){
			panelCredits = new PanelCredits(company);
		}
		return panelCredits;
	}
	
	
	
}
