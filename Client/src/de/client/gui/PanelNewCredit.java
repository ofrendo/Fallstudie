package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.client.company.Company;
import de.client.company.finances.CreditType;


public class PanelNewCredit extends JPanel {

	private static final long serialVersionUID = -8268787475063054536L;
	private Company company;
	private CreditType crType;
	
	private JPanel panelCreditInfo;
	
	private JLabel labelAmountTitle;
	private JLabel labelAmountValue;
	private JLabel labelRateTitle;
	private JLabel labelRateValue;
	private JLabel labelRuntimeTitle;
	private JLabel labelRuntimeValue;
	
	private JButton buttonAddCredit;
	
	public PanelNewCredit(Company company, CreditType crType){
		this.company = company;
		this.crType = crType;
		this.setLayout(new BorderLayout());
		this.add(getPanelCreditInfo(), BorderLayout.CENTER);
		this.add(getButtonAddCredit(), BorderLayout.SOUTH);
		this.setBackground(Look.COLOR_MAP_BACKGROUND);
	}

	private JButton getButtonAddCredit() {
		if(buttonAddCredit == null){
			buttonAddCredit = new JButton("Kredit aufnehmen");
			buttonAddCredit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					if(company.getFinances().isCreditWorthyFor(crType)){
						company.getFinances().addCredit(crType);
						Controller.getInstance().getFrame().setPanelFinances();
						Controller.getInstance().resetPanelMenuInformation();
					} else {
						 JOptionPane.showMessageDialog(null,
                                 "Dieser Kredit würde Ihren maximalen Verschuldungsgrad (2:1) übersteigen!",
                                 "Kreditaufnahme verweigert",					      
                                 JOptionPane.WARNING_MESSAGE);
					}
				}
			});
		}
		return buttonAddCredit;
	}

	private JPanel getPanelCreditInfo() {
		if(panelCreditInfo == null){
			panelCreditInfo = new JPanel();
			panelCreditInfo.setLayout(new GridLayout(3, 2, 5, 5));
			panelCreditInfo.add(getLabelAmountTitle());
			panelCreditInfo.add(getLabelAmountValue());
			panelCreditInfo.add(getLabelRateTitle());
			panelCreditInfo.add(getLabelRateValue());
			panelCreditInfo.add(getLabelRuntimeTitle());
			panelCreditInfo.add(getLabelRuntimeValue());
			panelCreditInfo.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelCreditInfo;
	}

	private Component getLabelRuntimeValue() {
		if(labelRuntimeValue == null){
			labelRuntimeValue = new JLabel(Strings.fD(crType.runtime / 4) + " Jahre");
			labelRuntimeValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelRuntimeValue;
	}

	private Component getLabelRuntimeTitle() {
		if(labelRuntimeTitle == null){
			labelRuntimeTitle = new JLabel("Laufzeit");
		}
		return labelRuntimeTitle;
	}

	private Component getLabelRateValue() {
		if(labelRateValue == null){
			labelRateValue = new JLabel(Strings.fD(crType.rate) + "%");
			labelRateValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelRateValue;
	}

	private Component getLabelRateTitle() {
		if(labelRateTitle == null){
			labelRateTitle = new JLabel("Zinssatz");
		}
		return labelRateTitle;
	}

	private Component getLabelAmountValue() {
		if(labelAmountValue == null){
			labelAmountValue = new JLabel(Strings.fD(crType.amount) + "\u20AC");
			labelAmountValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelAmountValue;
	}

	private Component getLabelAmountTitle() {
		if(labelAmountTitle == null){
			labelAmountTitle = new JLabel("Kredithöhe");
		}
		return labelAmountTitle;
	}
}
