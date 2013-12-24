package de.client.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = -4775983963721353054L;

	private int width = 1280;
	private int height = 667;
	
	//private int minWidth = 1024;
	//private int minHeight = 667;
	
	private PanelMain panelMain;
	
	public Frame() {
		this.panelMain = new PanelMain();
		
		add(panelMain, BorderLayout.CENTER);
		pack();
		
		setTitle(Strings.FRAME_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	public void init() {
		
		setSize(width, height);
		//setMinimumSize(new Dimension(minWidth, minHeight));
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		//For testing
		Frame frame = new Frame();
		frame.init();
	}
	
	
}
