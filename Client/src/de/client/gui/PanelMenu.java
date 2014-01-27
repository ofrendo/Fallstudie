package de.client.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = -3149280268994476412L;

	private JButton buttonCompany;
	private JButton buttonMap;
	private JButton buttonFinances;
	private JButton buttonMarket;
	
	private JButton buttonReady;
	
	private JButton buttonDefault;
	
	private PanelMenuInformation panelMenuInformation;
	
	public PanelMenu() {
		setBackground(Look.COLOR_MENU_BACKGROUND);
		
		buttonCompany = new JButton(Strings.MENU_BUTTON_COMPANY);
		buttonCompany.addActionListener(new MenuButtonListener());
		
		buttonMap = new JButton(Strings.MENU_BUTTON_MAP);
		buttonMap.addActionListener(new MenuButtonListener());
		
		buttonFinances = new JButton(Strings.MENU_BUTTON_FINANCES);
		buttonFinances.addActionListener(new MenuButtonListener());
		
		buttonMarket = new JButton(Strings.MENU_BUTTON_MARKET);
		buttonMarket.addActionListener(new MenuButtonListener());
		
		buttonDefault = buttonMap;
		buttonDefault.setEnabled(false);
		
		setLayout(new BorderLayout());
		
		JPanel leftPanel = new JPanel(new GridLayout(1, 0));
		leftPanel.add(buttonCompany);
		leftPanel.add(buttonMap);
		leftPanel.add(buttonFinances);
		leftPanel.add(buttonMarket);
		
		
		this.add(getButtonReady(), BorderLayout.CENTER);
		
		this.add(leftPanel, BorderLayout.WEST);
		this.add(getPanelMenuInformation(), BorderLayout.EAST);
	}
	
	public void enableAllButtons() {
		buttonCompany.setEnabled(true);
		buttonFinances.setEnabled(true);
		buttonMap.setEnabled(true);
		buttonMarket.setEnabled(true);
	}
	
	public JButton getButtonMap() {
		return buttonMap;
	}
	
	public PanelMenuInformation getPanelMenuInformation() {
		if (panelMenuInformation == null) {
			panelMenuInformation = new PanelMenuInformation();
		}
		return panelMenuInformation;
	}
	
	private class MenuButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			enableAllButtons();
			source.setEnabled(false);
			
			if (source == buttonCompany)
				Controller.getInstance().getFrame().setPanelCompany();
			
			if (source == buttonMap)
				Controller.getInstance().getFrame().setPanelMain();
			
			if (source == buttonFinances)
				Controller.getInstance().getFrame().setPanelFinances();
			
			if (source == buttonMarket)
				Controller.getInstance().getFrame().setPanelMarket();
		}
	}
	
	public JButton getButtonReady() {
		if (buttonReady == null) {
			buttonReady = new JButton("Runde abschlieﬂen");
			buttonReady.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Controller.getInstance().checkAndSendReady();
				}
			});
		}
		return buttonReady;
	}
	
}
