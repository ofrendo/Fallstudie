package de.client.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import de.client.company.ResourceRelation;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.relation.Contract;
import de.shared.map.relation.RegionRelation;

/**
 * Simple hexagonal button class.
 *
 * @author Kristian Johansen
 *
 */
public class HexagonButton extends JButton {

    private static final long serialVersionUID = -7142502695252118612L;
    private Polygon hexagonalShape;
    private Image image;
    private Color color;
    
    private Region region;
    private RegionRelation regionRelation;
    
    public PanelSubDetails panel;
    
    public HexagonButton(Region region) {
		this.setOpaque(false);
		this.region = region;
		hexagonalShape = getHexPolygon();
		
		String hexString;
		String imagePath;
		
		if (region instanceof ResourceRegion) {
			ResourceRegion resourceRegion = (ResourceRegion) region;
			switch (resourceRegion.resourceType) {
			case COAL: 
				hexString = "#a66600"; 
				imagePath = "kohle.png";
				break;
			case GAS:
				hexString = "#838383"; 
				imagePath = "gas.png";
				break;
			case SOLAR:
				hexString = "#b7e09c"; 
				imagePath = "solarpanel.png";
				break;
			case URANIUM:
				hexString = "#ffe900"; 
				imagePath = "kern.png";
				break;
			case WATER:
				hexString = "#27a9e3"; 
				imagePath = "wassersymbol.png";
				break;
			case WIND:
				hexString = "#DCDCDC";
				imagePath = "windrad.png";
				break;
			case EMPTY:
				//hexString = "#FFEEB2"; 
				hexString = "#FFF6D5";
				imagePath = "leer.png";
				break;
			default:
				hexString = "#ffffff"; 
				imagePath = "kohle.png";
				break;
			}
		}
		else {
			//CityRegion
			hexString = "#8f30bf";
			imagePath = "stadt.png";
		}
		
		//Set background color
		hexString = hexString.replaceAll("#", "");
		int intValue = Integer.parseInt(hexString, 16);
		this.color = new Color(intValue);
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream( imagePath );
		try {
			this.image = ImageIO.read(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				triggerControllerClick();
			}
		});
		
    }

    public Region getRegion() {
    	return region;
    }
    
    public RegionRelation getRegionRelation() {
    	if (regionRelation == null) {
    		regionRelation = Controller.getInstance().getCompany().getRegionRelation(region.coords);
    	}
    	return regionRelation; 
    }
    
    public void triggerControllerClick() {
    	Controller.getInstance().handleMapTileClick(this);
    }
    
    @Override
    public Dimension getPreferredSize() {
    	int imageWidth = 60; //doesnt do anything with layout manager
    	int imageHeight = imageWidth; //(int) (imageWidth / 0.866)
    	return new Dimension(imageWidth, imageHeight);
    }
    
    /**
     * Generates the buttons Hexagonal shape
     *
     * @return Polygon with the buttons hexagonal shape.
     */
    private Polygon getHexPolygon() {
		return buildHexagon(0);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#contains(java.awt.Point)
     */
    @Override
    public boolean contains(Point p) {
    	return hexagonalShape.contains(p);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#contains(int, int)
     */
    @Override
    public boolean contains(int x, int y) {
    	return hexagonalShape.contains(x, y);
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(java.awt.Dimension)
     */
    @Override
    public void setSize(Dimension d) {
		super.setSize(d);
		hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setSize(int, int)
     */
    @Override
    public void setSize(int w, int h) {
		super.setSize(w, h);
		hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setBounds(int, int, int, int)
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see java.awt.Component#setBounds(java.awt.Rectangle)
     */
    @Override
    public void setBounds(Rectangle r) {
		super.setBounds(r);
		hexagonalShape = getHexPolygon();
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#processMouseEvent(java.awt.event.MouseEvent)
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
	if (contains(e.getPoint()))
	    super.processMouseEvent(e);
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(Graphics g) {
    	//Draw the border
		//Check each border possiblity so that no empty border is there
		int radius = 0;
		
		if (region instanceof CityRegion) {
			//Check if have contract with city
			CityRegion cityRegion = (CityRegion) region;
			Contract contract = cityRegion.getPlayerContract(Controller.getInstance().getOwnPlayer());
			if (contract != null) {
				//Make a 2px big border
				drawCompletePolygon(g, Look.COLOR_MAP_BORDER_CITY_CONTRACT, hexagonalShape);
				radius = - Look.BORDER_WIDTH_CITYCONTRACT;
			}
		}
		else {
			ResourceRegion resourceRegion = (ResourceRegion) region;
			switch (resourceRegion.resourceRegionStatus) {
			case BUYABLE:
				drawCompletePolygon(g, Look.COLOR_MAP_BORDER_BUYABLE, hexagonalShape);
				radius -= Look.BORDER_WIDTH_CITYCONTRACT;
				break;
			case OWNED:
			case MINE:
			case POWERSTATION:
			case MINE_POWERSTATION:
				if (Controller.getInstance().getOwnPlayer().equals(resourceRegion.owner)) {
					//Test if building is on it
					ResourceRelation resourceRelation = (ResourceRelation) getRegionRelation();
					if (resourceRelation.powerStation != null) {
						drawCompletePolygon(g, Look.COLOR_MAP_BORDER_POWERSTATION, hexagonalShape);
						radius -= Look.BORDER_WIDTH_CITYCONTRACT;
					}
					if (resourceRelation.mine != null) {
						drawCompletePolygon(g, Look.COLOR_MAP_BORDER_MINE, buildHexagon(radius));
						radius -= Look.BORDER_WIDTH_CITYCONTRACT;
					}
					if (resourceRelation.powerStation == null && resourceRelation.mine == null) {
						drawCompletePolygon(g, Look.COLOR_MAP_BORDER_POWERSTATION, hexagonalShape);
						radius -= Look.BORDER_WIDTH_CITYCONTRACT;
					}
				}
				else {
					//Region belongs to someone else
					drawCompletePolygon(g, Look.COLOR_MAP_BORDER_OTHEROWNED, hexagonalShape);
					radius = - Look.BORDER_WIDTH_CITYCONTRACT;
				}
				break;
			default: break;
			}
		}
    	
    	//g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		//g.fillRect(0, 0, getWidth(), getHeight());
		
		//g.setColor(color);
		//g.drawPolygon(hexagonalShape);
		//g.fillPolygon(hexagonalShape);
		Color background;
		if (this == Controller.getInstance().lastHexButton) 
			background = Look.COLOR_MAP_BACKGROUND_CURRENTSELECTED;
		else 
			background = color;
		
		drawCompletePolygon(g, background, buildHexagon(radius));
		
		//Draw image
		int wantedImageWidth = (int) (this.getWidth() / 1.5);
		int wantedImageHeight = (int) (this.getHeight() / 1.5);
		
		int x = (this.getWidth() - wantedImageWidth) / 2;
		int y = (this.getHeight() - wantedImageHeight) / 2;
		
		g.drawImage(image, x, y, wantedImageWidth, wantedImageHeight, null);
    }
    
    private void drawCompletePolygon(Graphics g, Color color, Polygon polygon) {
    	g.setColor(color);
		g.drawPolygon(polygon);
		g.fillPolygon(polygon);
    }
    
    private Polygon buildHexagon(int radius) {
    	Polygon hex = new Polygon();
		int w = getWidth() - 1;
		int h = getHeight() - 1;
		int ratio = (int) (h * .25);
		int ratioRadius = (int) (radius * .25);
		
		hex.addPoint(w / 2, 0 - radius);
		hex.addPoint(w + radius, ratio - ratioRadius);
		hex.addPoint(w + radius, h - ratio + ratioRadius);
		hex.addPoint(w / 2, h + radius);
		hex.addPoint(0 - radius, h - ratio + ratioRadius);
		hex.addPoint(0 - radius, ratio - ratioRadius);
		return hex;
    }

    /*
     * (non-Javadoc)
     * @see javax.swing.AbstractButton#paintBorder(java.awt.Graphics)
     */
    @Override
    protected void paintBorder(Graphics g) {
	// Does not print border
    }
}
