package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.shared.game.Player;

public class PanelGlobalLeft extends JPanel {

	private static final long serialVersionUID = -9108387525912537550L;
	private JPanel panelPlayersReady;
	private JPanel panelChat;
	private JTextArea textAreaChat;
	private JTextField textFieldSendMessage;
	private JPanel panelRoundTitle;
	private JLabel labelRoundTitle;
	
	public PanelGlobalLeft() {
		setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(Look.COLOR_MAP_BACKGROUND);
		this.add(getPanelRoundTitle());
		
		this.add(getPanelPlayersReady());
		this.add(Box.createRigidArea(new Dimension(0, 50)));
		this.add(getPanelChat());
	}
	
	private JLabel getLabelRoundTitle() {
		if (labelRoundTitle == null) {
			labelRoundTitle = new JLabel("Runde 0");
			labelRoundTitle.setFont(Look.fontSectionTitle);
		}
		return labelRoundTitle;
	}
	
	private JPanel getPanelRoundTitle() {
		if (panelRoundTitle == null) {
			panelRoundTitle = new JPanel();
			panelRoundTitle.add(getLabelRoundTitle());
			panelRoundTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, 5, 0)));
			panelRoundTitle.setBackground(Look.COLOR_MAP_BACKGROUND);
			//panelRoundTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, getLabelRoundTitle().getPreferredSize().height + 2 * 5 ));
		}
		return panelRoundTitle;
	}
	
	public void updatePanelRoundTitle(int round) {
		getLabelRoundTitle().setText("Runde " + round);
	}
	
	private JPanel getPanelPlayersReady() {
		if (panelPlayersReady == null) {
			panelPlayersReady = new JPanel();
			panelPlayersReady.setBorder(new EmptyBorder(5, 5, 5, 5));
			panelPlayersReady.setAlignmentX(Component.RIGHT_ALIGNMENT);
			panelPlayersReady.setLayout(new BoxLayout(panelPlayersReady, BoxLayout.Y_AXIS));
			panelPlayersReady.setBackground(Look.COLOR_MAP_BACKGROUND);
			//panelPlayersReady.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
		}
		return panelPlayersReady;
	}
	
	public void updatePlayerList(ArrayList<Player> players) {
		panelPlayersReady.removeAll();
		
		JLabel labelPlayer = null;
		for (Player player : players) {
			String ready = Look.getReadySymbol(player.ready);
			labelPlayer = new JLabel(player.playerName + ": " + ready);
			labelPlayer.setFont(Look.fontSectionPart);
			
			//labelPlayer.setAlignmentX(Component.LEFT_ALIGNMENT);
			//labelPlayer.setAlignmentX(Component.RIGHT_ALIGNMENT);
			panelPlayersReady.add(labelPlayer);
		}
		panelPlayersReady.setPreferredSize(new Dimension(150, labelPlayer.getPreferredSize().height));
		panelPlayersReady.revalidate();
		panelPlayersReady.repaint();
	}
	
	public JPanel getPanelChat() {
		if (panelChat == null) {
			panelChat = new JPanel();
			panelChat.setBorder(new EmptyBorder(5, 5, 5, 5));
			panelChat.setLayout(new BorderLayout(0, 0));
			panelChat.add(getTextAreaChat(), BorderLayout.CENTER);
			panelChat.add(getTextFieldSendMessage(), BorderLayout.SOUTH);
			panelChat.setBackground(Look.COLOR_MAP_BACKGROUND);
			//panelChat.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));
		}
		return panelChat;
	}
	
	public void addChatMessage(String stringMessage) {
		getTextAreaChat().append(stringMessage + "\n");
	}
	
	
	private JTextArea getTextAreaChat() {
		if (textAreaChat == null) {
			textAreaChat = new JTextArea();
			textAreaChat.setLineWrap(true);
			textAreaChat.setEnabled(false);
			textAreaChat.setDisabledTextColor(Color.black);
		}
		return textAreaChat;
	}
	private JTextField getTextFieldSendMessage() {
		if (textFieldSendMessage == null) {
			textFieldSendMessage = new JTextField();
			textFieldSendMessage.setColumns(10);
			textFieldSendMessage.addActionListener(new ChatListener());
			//textFieldSendMessage.setFont(Look.fontSectionPart);
		}
		return textFieldSendMessage;
	}
	
	private class ChatListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String message = getTextFieldSendMessage().getText();
			Controller.getInstance().getClient().sendChatMessage(message);
			getTextFieldSendMessage().setText("");
		}
		
	}
}
