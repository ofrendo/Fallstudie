package de.tests.client.gui;

import java.util.ArrayList;

import de.client.gui.Controller;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionStatus;
import de.shared.map.region.ResourceType;

public class GUITestMarket {
	
	public static void main(String[] args) {
		Map map = new Map(MapTypeHexagon.NORMAL); //Map.getInstance(MapTypeHexagon.NORMAL);
		Player ownPlayer = new Player("Olli");
		
		ResourceRegion r = null;
		for (Region region : map.getRegions()) {
			if (region instanceof ResourceRegion) {
				r = (ResourceRegion) region;
				if (r.resourceRegionStatus == ResourceRegionStatus.NEUTRAL && r.resourceType != ResourceType.EMPTY && !r.resourceType.isRenewable) {
					r.owner = ownPlayer;
					r.resourceRegionStatus = ResourceRegionStatus.OWNED;
					break;
				}
			}
		}
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(ownPlayer);
		
		Controller.getInstance().initGameLobby(players);
		Controller.getInstance().getClientGame().setMap(map);
		Controller.getInstance().initGame(map);
		
		Controller.getInstance().getFrame().setPanelMarket();
	}
	
}
