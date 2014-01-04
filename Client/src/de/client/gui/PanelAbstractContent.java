package de.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public abstract class PanelAbstractContent extends JPanel {

	private static final long serialVersionUID = 5731119251181448790L;
	
	protected JPanel panelLeft;
	protected JPanel panelRight;
	
	public PanelAbstractContent() {
		
	}
	
	protected final int p = 5; //Padding
	
	public void initLayout() {
		this.setLayout(new GridBagLayout());
		
		panelLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
		panelLeft.setBorder(new EmptyBorder(p, p, p, p));
		
		panelRight.setBackground(Look.COLOR_MAP_BACKGROUND);
		panelRight.setBorder(new CompoundBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(p, p, p, p)));
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.fill = GridBagConstraints.BOTH;
		//c.weighty = 1.0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		this.add(panelLeft, c);
		
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.ipadx = 20;
		c.ipady = 20;
		c.fill = GridBagConstraints.BOTH;
		this.add(panelRight, c);
	}
	
}
