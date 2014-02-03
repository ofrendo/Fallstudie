package de.client.gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelGameEnd extends JPanel {

	private static final long serialVersionUID = 4811195817023574968L;
	
	public PanelGameEnd(String message) {
		setLayout(new BorderLayout());
		JLabel messageLabel = new JLabel(message);
		messageLabel.setFont(Look.fontSectionTitle);
		messageLabel.setHorizontalAlignment(JLabel.CENTER);
		setBackground(Look.COLOR_MAP_BACKGROUND);
	
		add(messageLabel, BorderLayout.CENTER);
	}
	
}
