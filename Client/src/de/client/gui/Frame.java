package de.client.gui;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

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
	
	private PanelLobby panelLobby;
	
	private PanelMenu panelMenu;

	private PanelMain panelMain; 
	private PanelCompany panelCompany;
	private PanelFinances panelFinances;
	private PanelMarket panelMarket;
	
	private Map map;
	
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
		this.map = map;
		setPanelMain(); 
		setSize(gameWidth, gameHeight);
		setLocationRelativeTo(null);
	}
	
	public void setContentEmpty() {
		getContentPane().removeAll();
		getContentPane().add(getPanelMenu(), BorderLayout.NORTH);
	}
	
	public void refresh() {
		getContentPane().revalidate();
		getContentPane().repaint();
	}
	
	public PanelMenu getPanelMenu() {
		if (panelMenu == null) {
			panelMenu = new PanelMenu();
		}
		return panelMenu;
	}

	public PanelMain getPanelMain(Map map) {
		if (panelMain == null) {
			panelMain = new PanelMain(map);
		}
		return panelMain;
	}
	
	public PanelCompany getPanelCompany() {
		if (panelCompany == null) {
			panelCompany = new PanelCompany();
		}
		return panelCompany;
	}
	
	public PanelFinances getPanelFinances() {
		if (panelFinances == null) {
			panelFinances = new PanelFinances();
		}
		return panelFinances;
	}
	
	public PanelMarket getPanelMarket() {
		if (panelMarket == null) {
			panelMarket = new PanelMarket();
		}
		return panelMarket;
	}
	
	public void setPanelMain() {
		setContentEmpty();
		getContentPane().add(getPanelMain(map));
		refresh();
	}
	
	public void setPanelCompany() {
		setContentEmpty();
		getContentPane().add(getPanelCompany());
		refresh();
	}
	
	public void setPanelFinances() {
		setContentEmpty();
		getContentPane().add(getPanelFinances());
		refresh();
	}
	
	public void setPanelMarket() {
		setContentEmpty();
		getContentPane().add(getPanelMarket());
		refresh();
	}

}
