package de.shared.map.generate;

import java.util.Arrays;
import java.util.Collections;

public class CityNameGenerator {
	
	private static int indexState = -1;
	private static int indexCity = 0;
	
	private static String[][] names = {
		{
			"Berlin",
			"Hamburg",
			"M�nchen",
			"K�ln",
			"Frankfurt am Main",
			"Stuttgart",
			"D�sseldorf",
			"Dortmund",
			"Essen",
			"Bremen",
			"Dresden",
			"Leipzig",
			"Hannover",
			"N�rnberg",
			"Duisburg",
			"Bochum",
			"Wuppertal",
			"Bielefeld",
			"Bonn",
			"M�nster",
			"Karlsruhe",
			"Mannheim",
			"Wiesbaden",
			"Augsburg",
			"Gelsenkirchen",
			"M�nchengladbach",
			"Braunschweig",
			"Chemnitz",
			"Aachen",
			"Kiel",
			"Magdeburg",
			"Krefeld",
			"Oberhausen",
			"L�beck",
			"Erfurt",
			"Rostock",
			"Mainz"
		}
	};
	
	public static String getNextCityName() {
		if (indexState == -1) {
			indexState = (int) (Math.random() * (names.length));
			Collections.shuffle(Arrays.asList(names[indexState]));
		}
		
		return names[indexState][indexCity++];
	}
	
}

