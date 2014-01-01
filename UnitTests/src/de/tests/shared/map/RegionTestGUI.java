package de.tests.shared.map;

import java.util.ArrayList;

import de.client.gui.Frame;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.generate.MapTypeHexagon;

public class RegionTestGUI {
	
	public static void main(String[] args) {
		Map map = new Map(MapTypeHexagon.NORMAL); //Map.getInstance(MapTypeHexagon.NORMAL);
		
		ArrayList<Player> emptyPlayers = new ArrayList<Player>();
		Frame frame = new Frame(emptyPlayers);
		frame.init();
		frame.initGame(map);
		
		
	}
	
}
