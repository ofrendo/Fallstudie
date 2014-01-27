package de.tests.client.gui;

import java.util.ArrayList;

import de.client.EventMessage;
import de.client.company.ResourceRelation;
import de.client.gui.Controller;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionStatus;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.Contract;

public class GUITestCompany {
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
		Controller.getInstance().getOwnPlayer().companyName = "Olli AG";
		
		ResourceRelation relation = (ResourceRelation) Controller.getInstance().getCompany().getRegionRelation(r.coords);
		Controller.getInstance().getCompany().buyMine(relation, r.resourceType);
		//relation.mine = new Mine(r.resourceType,relation);
		while (!relation.mine.isBuilt())
			relation.mine.nextRound();
			
		Controller.getInstance().getCompany().buyPowerStation(relation, r.resourceType);
		//relation.powerStation = new PowerStation(r.resourceType);
		while (!relation.powerStation.isBuilt())
			relation.powerStation.nextRound();
		
		CityRegion firstCityRegion = Controller.getInstance().getClientGame().getFirstCityRegion();
		firstCityRegion.addContract(new Contract(ownPlayer, firstCityRegion.coords, 0.5, 0.1, 100000, 40000000, 0, 0.2));
		
		Controller.getInstance().getCompany().optimizePowerStations();
		
		Controller.getInstance().getClientGame().getEventMessages().add(new EventMessage("Test Nachricht mit einem langen langen langen langen langen langen langen Inhalt", new Coords(0,0)));
		Controller.getInstance().getClientGame().getEventMessages().add(new EventMessage("Test Nachricht mit einem kurzen Inhalt", new Coords(1,1)));
		
		Controller.getInstance().getFrame().setPanelCompany();
	}
}
