package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FrameConnect extends JFrame {

	private static final long serialVersionUID = -4835729043783596096L;

	private Dimension size = new Dimension(500, 400);
	private JPanel panelIPAddress;
	private JPanel panelPlayerName;
	private JPanel panelCompanyName;
	private JLabel labelIPAddress;
	private JTextField textFieldIPAddress;
	private JLabel labelPlayerName;
	private JTextField textFieldPlayerName;
	private JLabel labelCompanyName;
	private JTextField textFieldCompanyName;
	private JButton buttonConnect;
	
	
	
	public FrameConnect() {
		setTitle("Verbinden");
		getContentPane().setLayout(new GridLayout(4, 0, 10, 10));
		getContentPane().add(getPanelIPAddress());
		getContentPane().add(getPanelPlayerName());
		getContentPane().add(getPanelCompanyName());
		getContentPane().add(getButtonConnect());
	}
	
	
	
	public String getIPAddress() {
		return getTextFieldIPAddress().getText();
	}
	
	public String getPlayerName() {
		return getTextFieldPlayerName().getText();
	}
	
	public String getCompanyName() {
		return getTextFieldCompanyName().getText();
	}
	
	public boolean isInputsCompleted() {
		if (getIPAddress().isEmpty())
			return false;
		
		try {
			@SuppressWarnings("unused")
			InetAddress test = InetAddress.getByName(getIPAddress());
		}
		catch (Exception e) {
			return false;
		}
		
		if (getPlayerName().isEmpty()) 
			return false;
		
		if (getCompanyName().isEmpty()) 
			return false;
		
		return true;
	}
	
	public void init() {
		this.setSize(size);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel getPanelIPAddress() {
		if (panelIPAddress == null) {
			panelIPAddress = new JPanel();
			panelIPAddress.setLayout(new BorderLayout(0, 0));
			panelIPAddress.add(getLabelIPAddress(), BorderLayout.NORTH);
			panelIPAddress.add(getTextFieldIPAddress());
		}
		return panelIPAddress;
	}
	private JPanel getPanelPlayerName() {
		if (panelPlayerName == null) {
			panelPlayerName = new JPanel();
			panelPlayerName.setLayout(new BorderLayout(0, 0));
			panelPlayerName.add(getLabelPlayerName(), BorderLayout.NORTH);
			panelPlayerName.add(getTextFieldPlayerName(), BorderLayout.CENTER);
		}
		return panelPlayerName;
	}
	private JPanel getPanelCompanyName() {
		if (panelCompanyName == null) {
			panelCompanyName = new JPanel();
			panelCompanyName.setLayout(new BorderLayout(0, 0));
			panelCompanyName.add(getLabelCompanyName(), BorderLayout.NORTH);
			panelCompanyName.add(getTextFieldCompanyName(), BorderLayout.CENTER);
		}
		return panelCompanyName;
	}
	private JLabel getLabelIPAddress() {
		if (labelIPAddress == null) {
			labelIPAddress = new JLabel("IP Addresse:");
			labelIPAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return labelIPAddress;
	}
	private JTextField getTextFieldIPAddress() {
		if (textFieldIPAddress == null) {
			textFieldIPAddress = new JTextField();
			textFieldIPAddress.setText("127.0.0.1");
			textFieldIPAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textFieldIPAddress.setColumns(10);
		}
		return textFieldIPAddress;
	}
	private JLabel getLabelPlayerName() {
		if (labelPlayerName == null) {
			labelPlayerName = new JLabel("Spielername:");
			labelPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		}
		return labelPlayerName;
	}
	private JTextField getTextFieldPlayerName() {
		if (textFieldPlayerName == null) {
			textFieldPlayerName = new JTextField();
			textFieldPlayerName.setText("Olli");
			textFieldPlayerName.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textFieldPlayerName.setColumns(10);
		}
		return textFieldPlayerName;
	}
	private JLabel getLabelCompanyName() {
		if (labelCompanyName == null) {
			labelCompanyName = new JLabel("Firmaname:");
			labelCompanyName.setFont(new Font("Tahoma", Font.PLAIN, 20));
			labelCompanyName.setForeground(new Color(0, 0, 0));
		}
		return labelCompanyName;
	}
	private JTextField getTextFieldCompanyName() {
		if (textFieldCompanyName == null) {
			textFieldCompanyName = new JTextField();
			textFieldCompanyName.setText("0815 Company AG");
			textFieldCompanyName.setFont(new Font("Tahoma", Font.PLAIN, 20));
			textFieldCompanyName.setColumns(10);
		}
		return textFieldCompanyName;
	}
	private JButton getButtonConnect() {
		if (buttonConnect == null) {
			buttonConnect = new JButton("Verbinden");
			buttonConnect.setFont(new Font("Tahoma", Font.PLAIN, 29));
			
			buttonConnect.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (isInputsCompleted()) {
						Controller.getInstance().connect();
					}
					else {
						JOptionPane.showMessageDialog(null, "Bitte Eingaben überprüfen!");
					}
				}
				
			});
			
		}
		return buttonConnect;
	}

	public void showInitRejected() {
		JOptionPane.showMessageDialog(null, "Spielername oder Firmaname existiert bereits.");
	}
}
