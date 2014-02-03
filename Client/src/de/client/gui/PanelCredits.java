package de.client.gui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import de.client.company.Company;
import de.client.company.finances.CreditType;

public class PanelCredits extends JPanel {

	private static final long serialVersionUID = -8014087846148538928L;

	private Company company;
	
	private JPanel panelNewCredits;
	
	public PanelCredits(Company company){
		this.company = company;
		setBackground(Look.COLOR_MAP_BACKGROUND);
		this.add(getNewCreditsPanel());
	}

	private JPanel getNewCreditsPanel() {
		if(panelNewCredits == null){
			panelNewCredits = new JPanel();
			// add valid creditTypes
			ArrayList<CreditType> crTypes = new ArrayList<CreditType>();
			crTypes.add(CreditType.CREDIT_1);
			crTypes.add(CreditType.CREDIT_2);
			crTypes.add(CreditType.CREDIT_3);
			panelNewCredits.setLayout(new GridLayout(3, 1, 20, 20));
			for(CreditType crType: crTypes){
				PanelNewCredit panelNewCredit  = new PanelNewCredit(company, crType);
				panelNewCredits.add(panelNewCredit);
			}
			panelNewCredits.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelNewCredits;
	}
}
