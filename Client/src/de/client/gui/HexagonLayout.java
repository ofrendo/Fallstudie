package de.client.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashMap;

import de.client.company.PowerStation;
import de.client.company.PowerStationRelation;
import de.client.company.ResourceRelation;
import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegion;

/**
 * [Concept taken and adapted from http://forums.codeguru.com/showthread.php?478270-Hexagon-Buttons]
 * 
 * A layoutmanager for interleaved hexagons. This layout is based on GridLayout. It
 * divides the parent component into equal parts and resizes the subcomponents to fit
 * these. Since every other row will hold one element less than than the one before
 * (or after), this LM only register how many columns the user wants, and then calculates
 * the number of rows needed to fit this need according to the number of gui elements
 * that needs to be allocated. The number of columns given by the user is the number of
 * elements in a big row:
 *
 * Example:
 *
 * *** *** *** *** *** ***    big row
 *   *** *** *** *** ***      small row
 * *** *** *** *** *** ***    big row
 *   *** *** *** *** ***      etc.
 * *** *** *** *** *** ***
 *
 * The user can also specify wether or not to begin the layout with a small row, and can
 * specify the insets between gui elements via a Insets object.
 *
 * @author Kristian Johansen
 *
 */
public class HexagonLayout implements LayoutManager {

    private Insets insets;
    private int rows;
    private int cols;
    private boolean beginWithSmallRow;

    private Dimension minSize;
    private Dimension prefSize;

    private MapType mapType;

    /**
     * Generates a HexagonLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a long row.
     * @param cols Number of items in a big row
     */
    public HexagonLayout(int cols) {
		checkColInput(cols);
		minSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		prefSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		insets = new Insets(0, 0, 0, 0);
		this.rows = 0;
		this.cols = cols;
		beginWithSmallRow = false;
    }

    /**
     * Generates a HexagonLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor has a flag for wether or not to begin with a small row
     *
     * @param cols Number of items in a big row.
     * @param beginWithSmallRow Wether or not to begin with a small row.
     */
    public HexagonLayout(int cols, boolean beginWithSmallRow) {
		checkColInput(cols);
		minSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		prefSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		insets = new Insets(0, 0, 0, 0);
		this.rows = 0;
		this.cols = cols;
		this.beginWithSmallRow = beginWithSmallRow;
	    }

    /**
     * Generates a HexagonLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor also takes an Insets object that specify insets between
     * elements in the gui.
     *
     * @param cols Number of coulumns in a big row.
     * @param i Insets object that specifies the spacing between gui elements.
     */
    public HexagonLayout(int cols, Insets i) {
	checkColInput(cols);
	insets = i;
	minSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
	prefSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
	this.rows = 0;
	this.cols = cols;
	beginWithSmallRow = false;
    }

    /**
     * Generates a HexagonLayout with the number of columns given. The layout
     * divides the componenets into equal portions of the parent container. The
     * rows are arranged in big rows and small rows (every other). The layout
     * calculates how many rows it need to hold the number of items the parent has.
     * The number of columns represent the number of items in a big row.
     *
     * This constructor has a flag for wether or not to start with a small row.
     *
     * This constructor also takes an Insets object that specify insets between
     * elements in the gui.
     *
     * @param cols Number of columns in a big row.
     * @param i Insets object that specify the spacing between gui elements
     * @param beginWithSmallRow Flag for wether or not to begin with a small row.
     */
    public HexagonLayout(int cols, Insets i, boolean beginWithSmallRow, MapType mapType) {
		checkColInput(cols);
		insets = i;
		minSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		prefSize = new Dimension(800, 600); //Standard size. Can be changed with setter.
		this.rows = 0;
		this.cols = cols;
		this.beginWithSmallRow = beginWithSmallRow;
		this.mapType = mapType;
    }

    /**
     * Checks that the column input is valid: Columns must be set to n > 0;
     * @param cols
     */
    private void checkColInput(int cols) {
	if (cols <= 0) {
	    throw new IllegalArgumentException("Columns can't be set to n < 0");
	}
    }

    /**
     * Calculates the numbers of rows needed for the components given the
     * number of columns given.
     * @param componentCount
     * @return
     */
    private int calculateRows(int componentCount) {
		/*boolean smallRow = beginWithSmallRow;
	
		int numberOfRows = 0;
		int bgRow = cols;
		int smRow = bgRow - 1;
	
		int placedItems = 0;
		if (smallRow) {
		    while (true) {
			if (placedItems >= componentCount) {
			    break;
			}
			placedItems += smRow;
			numberOfRows += 1;
			if (placedItems >= componentCount) {
			    break;
			}
			placedItems += bgRow;
			numberOfRows += 1;
		    }
		} else {
		    while (true) {
			if (placedItems >= componentCount) {
			    break;
			}
			placedItems += bgRow;
			numberOfRows += 1;
			if (placedItems >= componentCount) {
			    break;
			}
			placedItems += smRow;
			numberOfRows += 1;
		    }
	
		}
		//System.out.println(numberOfRows);
		return numberOfRows;*/
    	return mapType.getAmountRows();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    @Override
    public void layoutContainer(Container parent) {
		// Get componentCount and check that it is not 0
		int componentCount = parent.getComponentCount();
		if (componentCount == 0) {
		    return;
		}
	
		// This indicates wither or not to begin with a small row
		boolean smallRow = beginWithSmallRow;
	
		// Calculating the number of rows needed
		rows = calculateRows(componentCount);
	
		// insets
		int leftOffset = insets.left;
		int rightOffset = insets.right;
		int topOffset = insets.top;
		int bottomOffset = insets.bottom;
	
		//int minBoxWidth = 50;
		//int minBoxHeight = 50;
		
		int containerWidth = parent.getWidth();
		int containerHeight = parent.getHeight();
		
		double heightRatio = 0.75;
		
		int maxRowLength;
		double addCol;
		if (mapType instanceof MapTypeRect) {
			maxRowLength = ((MapTypeRect) mapType).lengthRow;
			addCol = 0.5;
		}
		else { //if (mapType instanceof MapTypeHexagon) {
			maxRowLength = ((MapTypeHexagon) mapType).getMaxAmountTilesForRow();
			addCol = 0.5; //Dont know why this should be 0.5, but it is for smooth switching between ratios
		}
			
		double rectRatio = (double) (maxRowLength + 0.5) / (mapType.getAmountRows());
		double isRatio = (double) containerWidth / containerHeight;
		double hexagonWidthHeightRatio = 1;
		
		int boxWidth;
		int boxHeight;
		
		int containerXPadding;
		int containerYPadding;
		
		if (rectRatio > isRatio)  { //Container is too high
			boxWidth = (int) (containerWidth / (cols + addCol));
			boxHeight = (int) (boxWidth * (1.0/hexagonWidthHeightRatio));
			
			containerXPadding = 0;
			containerYPadding = (int) ((containerHeight - boxHeight * rows)/2.0) ;
		}
		else { //container is too wide
			boxHeight =  (int) (containerHeight / rows);
			boxWidth = (int) (boxHeight * hexagonWidthHeightRatio);
			containerXPadding = (int) ((containerWidth - boxWidth * (cols + 0.5)) /2.0);
			containerYPadding = 0;
		}
		containerYPadding += (rows * boxHeight - rows * boxHeight * heightRatio - boxHeight * (1-heightRatio) )/2.0;
		
		// spacing dimensions
		
		//int boxHeight = (int) Math.round((parent.getHeight() / (1 + (0.75 * (rows - 1)))));
	
		// component dimensions
		int cWidth = (boxWidth - (leftOffset + rightOffset));
		int cHeight = (boxHeight - (topOffset + bottomOffset));
	
	
		int x;
		int y;
		if (smallRow){
		    x = (int)Math.round((boxWidth / 2.0));
		} 
		else {
		    x = 0;
		}
		y = 0;
		
	
		//Draw power station relations
		//Nothing selected: Show ALL
		//City selected: Show relations to city
		//PowerStation selected: Show relations from PowerStation
		//anything else: Show none
		
		//In loop: compare c.coords to all hashmap coords
		//if in it: draw from there to all coords in arraylist
		HashMap<Coords, ArrayList<Coords>> connections = new HashMap<Coords, ArrayList<Coords>>();
		
		HexagonButton lastHexButton = Controller.getInstance().lastHexButton;
		if (lastHexButton == null) { //Nothing selected, show all connections
			
			for (PowerStation powerStation : Controller.getInstance().getCompany().getPowerStations()) {
				ArrayList<Coords> thisPowerStationRelations = new ArrayList<Coords>();
				for (PowerStationRelation psRelation : powerStation.getPowerStationRelations()) {
					if (psRelation.partPowerStationProduction > 0) {
						thisPowerStationRelations.add(psRelation.coords);
					}
				}
				if (thisPowerStationRelations.size() > 0) 
					connections.put(powerStation.coords, thisPowerStationRelations);
			}
			
		}
		else if (lastHexButton.getRegion() instanceof CityRegion) {
			//City selected, show all connections TO this city
			CityRegion cityRegion = (CityRegion) lastHexButton.getRegion();
			for (PowerStation powerStation : Controller.getInstance().getCompany().getPowerStations()) {
				
				ArrayList<Coords> thisPowerStationRelations = new ArrayList<Coords>();
				for (PowerStationRelation psRelation : powerStation.getPowerStationRelations()) {
					if (psRelation.partPowerStationProduction > 0 && psRelation.coords.equals(cityRegion.coords)) {
						thisPowerStationRelations.add(psRelation.coords);
					}
				}
				if (thisPowerStationRelations.size() > 0) 
					connections.put(powerStation.coords, thisPowerStationRelations);
			}
			
		}
		else if (lastHexButton.getRegion() instanceof ResourceRegion) {
			ResourceRegion resourceRegion = (ResourceRegion) lastHexButton.getRegion();
			ResourceRelation resourceRelation = (ResourceRelation) Controller.getInstance()
						.getCompany().getRegionRelation(resourceRegion.coords);
			
			
			if (resourceRelation.powerStation != null) {
				ArrayList<Coords> thisPowerStationRelations = new ArrayList<Coords>();
				for (PowerStationRelation psRelation : resourceRelation.powerStation.getPowerStationRelations()) {
					if (psRelation.partPowerStationProduction > 0) {
						thisPowerStationRelations.add(psRelation.coords);
					}
				}
				connections.put(resourceRelation.powerStation.coords, thisPowerStationRelations);
			}
		}
		
		
		int buttonsPlaced = 0;
		int buttonsPlacedCurrentRow = 0;
		int row = 0;
		int rowLength = 0;
		for (Component c : parent.getComponents()) {
			//int rowLength;
			if (mapType instanceof MapTypeRect) {
				rowLength = ((MapTypeRect) mapType).lengthRow;
				
				if (buttonsPlaced % rowLength == 0 && buttonsPlaced != 0)  {
					smallRow = !smallRow;
					row++;
				}
				
				if (!smallRow) {
					x = containerXPadding + (buttonsPlaced % rowLength) * boxWidth;
					y = (int) (containerYPadding + (row * boxHeight * heightRatio)); 
					
				}
				else {
					x = (int) (containerXPadding + (buttonsPlaced % rowLength) * boxWidth + (boxWidth / 2.0));
					y = (int) (containerYPadding + (row * boxHeight * heightRatio)); 
				}
			}
			else {
				rowLength = ((MapTypeHexagon) mapType).getAmountTilesForRow(row);

				if (buttonsPlacedCurrentRow % rowLength == 0 && buttonsPlaced != 0) {
					row ++;
					buttonsPlacedCurrentRow = 0;
				}
				//smallRow = (row % 2 == 0); 
				
				int longestRowIndex = ((MapTypeHexagon) mapType).getLongestRowIndex();
				
				//Number of x half box shifts: If at the first or last row, number = longestRowIndex, else row % longestRowIndex
				int xShifts;
				if (row < longestRowIndex) {
					xShifts = longestRowIndex - (row % longestRowIndex);
				}
				else if (row == longestRowIndex) {
					xShifts = 0;
				}
				else {
					xShifts = (row % longestRowIndex == 0) ? longestRowIndex : row % longestRowIndex;
				}
				int rowXPadding = (int) (xShifts * (boxWidth / 2.0) + boxWidth/4.0);
				
				x = containerXPadding + rowXPadding + buttonsPlacedCurrentRow * boxWidth;
				y = (int) (containerYPadding + (row * boxHeight * heightRatio));
				
				buttonsPlacedCurrentRow++;
			}
			
			
			//System.out.println(x + " " + y);
			c.setBounds(x, y, cWidth, cHeight);
			
			buttonsPlaced++;
		}
		
		ArrayList<HexagonButtonLine> lines = new ArrayList<HexagonButtonLine>();
		for (Component c : parent.getComponents()) {
			
			HexagonButton hexButton = (HexagonButton) c;
			Coords currentCoords = hexButton.getRegion().coords;
			for (java.util.Map.Entry<Coords, ArrayList<Coords>> entry : connections.entrySet()) {
				
				if (entry.getKey().equals(currentCoords)) {
					
					for (Component otherComponent : parent.getComponents()) {
						HexagonButton otherHexButton = (HexagonButton) otherComponent;
						Coords otherCoords = otherHexButton.getRegion().coords;
						
						for (Coords potentialOtherCoords : entry.getValue()) {
							if (otherCoords.equals(potentialOtherCoords)) {
								//NOW DRAW A LINE
								int x1 = hexButton.getX() + boxWidth/2;
								int y1 = hexButton.getY() + boxHeight/2;
								int x2 = otherHexButton.getX() + boxWidth/2;
								int y2 = otherHexButton.getY() + boxHeight/2;
								
								/*if (x1 < x2) {
									x1 += boxWidth/2;
									x2 -= boxWidth/2;
								}
								else {
									x1 -= boxWidth/2;
									x2 += boxWidth/2;
								}
								if (y1 < x2) {
									y1 += boxHeight/2;
									y2 -= boxHeight/2;
								}
								else {
									y1 -= boxHeight/2;
									y2 += boxHeight/2;
								}*/
								
								lines.add(new HexagonButtonLine(x1, y1, x2, y2));
							}
						}
					}
					
				}
			}
		}
		
		Controller.getInstance().getFrame().getPanelMain(null).getPanelMap().setConnections(lines);
		
		// Laying out each of the components in the container
		/*for (Component c : parent.getComponents()) {
			
		    if (x > parent.getWidth() - boxWidth) {
				smallRow = !smallRow;
				if (smallRow) {
				    x = (int)Math.round(boxWidth / 2.0);
				    y += Math.round(boxHeight * heightRatio);
				} else {
				    x = 0;
				    y += Math.round(boxHeight * heightRatio);
				}
	
		    }
		    c.setBounds(x + leftOffset, y + topOffset, cWidth, cHeight);
		    x += boxWidth;
		}*/
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
    	return minSize;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
    	return prefSize;
    }

    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    @Override
    public void removeLayoutComponent(Component comp) {
	// NOT IMPLEMENTED

    }
    /*
     * (non-Javadoc)
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
	// NOT IMPLEMENTED

    }

    public Insets getInsets() {
        return insets;
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
    }

    public int getColumns() {
        return cols;
    }

    public void setColumns(int cols) {
        this.cols = cols;
    }

    public boolean isBeginWithSmallRow() {
        return beginWithSmallRow;
    }

    public void setBeginWithSmallRow(boolean beginWithSmallRow) {
        this.beginWithSmallRow = beginWithSmallRow;
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public void setMinimumSize(Dimension minSize) {
        this.minSize = minSize;
    }

    public Dimension getPreferredSize() {
        return prefSize;
    }

    public void setPrefferedSize(Dimension prefSize) {
        this.prefSize = prefSize;
    }

    public int getRows() {
        return rows;
    }

}
