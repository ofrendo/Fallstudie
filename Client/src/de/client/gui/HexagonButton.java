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

import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;

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
				hexString = "#656660"; 
				imagePath = "kohle.png";
				break;
			case GAS:
				hexString = "#656660"; 
				imagePath = "gas.png";
				break;
			case SOLAR:
				hexString = "#656660"; 
				imagePath = "solar.png";
				break;
			case URANIUM:
				hexString = "#656660"; 
				imagePath = "kern.png";
				break;
			case WATER:
				hexString = "#656660"; 
				imagePath = "wassser.png";
				break;
			case WIND:
				hexString = "#656660";
				imagePath = "windrad.png";
				break;
			case EMPTY:
				hexString = "#000000"; 
				imagePath = "kohle.png";
				break;
			default:
				hexString = "#ffffff"; 
				imagePath = "kohle.png";
				break;
			}
		}
		else {
			//CityRegion
			hexString = "#FFEFB3";
			imagePath = "stadt.png";
		}
		
		
		//Set background color
		hexString = hexString.replaceAll("#", "");
		int intValue = Integer.parseInt(hexString, 16);
		this.color = new Color(intValue);
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream( imagePath );
		try {
			//ImageIcon icon = new ImageIcon(ImageIO.read(input));
			this.image = ImageIO.read(input);
			//ImageIcon icon = new ImageIcon(image);
			//setIcon(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Clicked");
			}
			
		});
		
    }

    public Region getRegion() {
    	return region;
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
	Polygon hex = new Polygon();
	int w = getWidth() - 1;
	int h = getHeight() - 1;
	int ratio = (int) (h * .25);

	hex.addPoint(w / 2, 0);
	hex.addPoint(w, ratio);
	hex.addPoint(w, h - ratio);
	hex.addPoint(w / 2, h);
	hex.addPoint(0, h - ratio);
	hex.addPoint(0, ratio);

	return hex;
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
    	g.setColor(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		//g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(color);
		g.drawPolygon(hexagonalShape);
		g.fillPolygon(hexagonalShape);
		
		int wantedImageWidth = (int) (this.getWidth() / 1.5);
		int wantedImageHeight = (int) (this.getHeight() / 1.5);
		
		int x = (this.getWidth() - wantedImageWidth) / 2;
		int y = (this.getHeight() - wantedImageHeight) / 2;
		
		g.drawImage(image, x, y, wantedImageWidth, wantedImageHeight, null);
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
