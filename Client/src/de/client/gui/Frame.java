package de.client.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.shared.game.Player;
import de.shared.map.Map;

public class Frame extends JFrame {

	private static final long serialVersionUID = -4775983963721353054L;

	private int lobbyWidth = 500;
	private int lobbyHeight = 400;
	
	private int gameWidth = 1280;
	private int gameHeight = 667;
	
	//private int minWidth = 1024;
	//private int minHeight = 667;
	
	private PanelMain panelMain;
	private PanelLobby panelLobby;
	
	public Frame(ArrayList<Player> players) {
		//ADD LOBBY PANEL FIRST
		this.panelLobby = new PanelLobby(players);
		this.add(panelLobby, BorderLayout.CENTER);

		pack();
		
		setTitle(Strings.FRAME_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public PanelLobby getPanelLobby() {
		return panelLobby;
	}
	
	public void init() {
		setSize(lobbyWidth, lobbyHeight);
		//setMinimumSize(new Dimension(minWidth, minHeight));
		setLocationRelativeTo(null);
		setVisible(true);
		//pack();
	}
	
	public void initGame(Map map) {
		getContentPane().removeAll();
		this.add(getPanelMain(map), BorderLayout.CENTER);
		refresh();
		setSize(gameWidth, gameHeight);
		setLocationRelativeTo(null);
	}
	
	public void refresh() {
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
	private JPanel getPanelMain(Map map) {
		if (panelMain == null) {
			panelMain = new PanelMain(map);
		}
		return panelMain;
	}
	
}
