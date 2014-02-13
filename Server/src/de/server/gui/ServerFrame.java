package de.server.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import de.server.Server;

public class ServerFrame extends JFrame {
	
	private static final long serialVersionUID = 143155800075308995L;

	public ServerFrame() {
		setLayout(new BorderLayout());
		
		JLabel info = new JLabel("Server is am laufen...");
		info.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(info, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300, 80);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void init() {
		Server.getInstance().start();
	}
	
	public static void main(String[] args) {
		ServerFrame serverFrame = new ServerFrame();
		serverFrame.init();
	}
	
}
