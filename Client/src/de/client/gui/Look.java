package de.client.gui;

import java.awt.Color;
import java.awt.Font;

public class Look {

	public static Font fontSectionTitle = new Font("Tahoma", Font.PLAIN, 30);
	public static Font fontSectionPart = new Font("Tahoma", Font.PLAIN, 20);
	
	public static Color COLOR_MAP_BACKGROUND = getColor("#FFEEB2");
	public static Color COLOR_MENU_BACKGROUND = getColor("#C38537");
	
	public static Color COLOR_MAP_BORDER_MINE = Color.black;
	public static Color COLOR_MAP_BORDER_POWERSTATION = Color.blue;
	public static Color COLOR_MAP_BORDER_OTHEROWNED = Color.red;
	
	public static Color COLOR_MAP_BORDER_CURRENTSELECTED = Color.green;
	public static final int BORDER_WIDTH_CURRENT_SELECTED = 4;
	
	public static Color COLOR_MAP_BORDER_CITY_CONTRACT = Color.black;
	public static final int BORDER_WIDTH_CITYCONTRACT = 4;
	
	public static final Color COLOR_MAP_BORDER_BUYABLE = Color.white;
	
	public static final int PANEL_LEFT_PADDING = 20;
	
	public static Color getColor(String hex) {
		hex = hex.replace("#", "");
		int intValue = Integer.parseInt(hex, 16);
		return new Color(intValue);
	}
	

}
