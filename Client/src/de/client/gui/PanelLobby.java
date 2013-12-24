package de.client.gui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.shared.game.Player;

public class PanelLobby extends JPanel {

	private static final long serialVersionUID = 2477877345696841001L;
	
	private ArrayList<Player> players;
	private JButton buttonReady;
	
	public PanelLobby(ArrayList<Player> players) {
		this.setLayout(new GridLayout(2, 0));
		
		this.players = players;
		updateReadyStatus(players);
	}
	
	public void updateReadyStatus(ArrayList<Player> newPlayers) {
		this.players = newPlayers;

		this.removeAll();
		this.setLayout(new GridLayout(players.size() + 1, 0));
		
		for (Player p : players) {
			this.add(new JLabel(p.playerName + ": " + p.ready));
		}
		
		this.add(getButtonReady());
	}	
	
	private JButton getButtonReady() {
		if (buttonReady == null) {
			buttonReady = new JButton("Bereit");
			buttonReady.setFont(new Font("Tahoma", Font.PLAIN, 20));
			buttonReady.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().sendReady();
				}
				
			});
			
		}
		return buttonReady;
	}
}
