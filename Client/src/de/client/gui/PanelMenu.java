package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = -3149280268994476412L;

	private JButton buttonCompany;
	private JButton buttonMap;
	private JButton buttonFinances;
	private JButton buttonMarket;
	
	public PanelMenu() {
		setBackground(Color.BLUE);
		
		buttonCompany = new JButton(Strings.MENU_BUTTON_COMPANY);
		buttonMap = new JButton(Strings.MENU_BUTTON_MAP);
		buttonFinances = new JButton(Strings.MENU_BUTTON_FINANCES);
		buttonMarket = new JButton(Strings.MENU_BUTTON_MARKET);
		
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel(new GridLayout(1, 4));
		leftPanel.add(buttonCompany);
		leftPanel.add(buttonMap);
		leftPanel.add(buttonFinances);
		leftPanel.add(buttonMarket);
		
		this.add(leftPanel, BorderLayout.WEST);
	}
	
}
