package de.client.gui;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import de.shared.map.region.ResourceType;


public class Strings {
	public static final String FRAME_TITLE = "Unternehmensplanspiel";
	
	public static final String MENU_BUTTON_COMPANY = "Unternehmen";
	public static final String MENU_BUTTON_MAP = "Karte";
	public static final String MENU_BUTTON_FINANCES = "Finanzen";
	public static final String MENU_BUTTON_MARKET = "Markt";

	public static final String ENERGY_UNIT = "kWh";
	
	public static String getResourceString(ResourceType type) {
		switch (type) {
		case COAL:
			return "Kohle";
		case GAS:
			return "Gas";
		case SOLAR: 
			return "Solar";
		case URANIUM:
			return "Uran";
		case WATER:
			return "Wasser";
		case WIND:
			return "Wind";
		default:
			return "";
		}
	}
	
	public static String getResourceUnit(ResourceType type) {
		switch (type) {
		case URANIUM:
		case COAL:
			return "t";
		case GAS:
			return "m³";
		default: 
			return "";
		}
	}
	
	public static String getRemainingRoundString(int number) {
		if (number == 1)
			return "1 Runde";
		else 
			return number + " Runden";
	}
	
	private static DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
	
	//private static DecimalFormat df = new DecimalFormat("0.##");
	
	public static String fD(double number) {
		symbols.setGroupingSeparator('.');
		DecimalFormat formatter = new DecimalFormat("###,###.##", symbols);
		
		return formatter.format(number);
	}
	
}
