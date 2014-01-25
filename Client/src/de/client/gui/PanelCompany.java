package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.client.EventMessage;
import de.client.company.Company;

public class PanelCompany extends PanelAbstractContent {

	private static final long serialVersionUID = -1699546782655151017L;

	private Company company;
	private JPanel panelNewsTitle;
	
	private int PADDING = 5;
	
	public PanelCompany() {
		company = Controller.getInstance().getCompany();
		
		getPanelInformation();
		getPanelNews();
		
		super.initLayout();
	}
	
	public JPanel getPanelInformation() {
		if (panelLeft == null) {
			panelLeft = new JPanel(new GridLayout(2, 2));
			panelLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
			
			panelLeft.add(new JLabel("A"));
			panelLeft.add(new JLabel("A"));
			panelLeft.add(new JLabel("A"));
			panelLeft.add(new JLabel("A"));
		}
		return panelLeft;
	}
	
	public JPanel buildPanelEventMessage(final EventMessage eventMessage) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panelInnerLeft = new JPanel();
		panelInnerLeft.setLayout(new BoxLayout(panelInnerLeft, BoxLayout.Y_AXIS));
		
		JButton buttonLook = new JButton("LUPE");
		buttonLook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getInstance().getFrame().getPanelMenu().enableAllButtons();
				Controller.getInstance().getFrame().getPanelMenu().getButtonMap().setEnabled(false);
				Controller.getInstance().getFrame().setPanelMain();
				
				ArrayList<HexagonButton> buttons = Controller.getInstance().getFrame().getPanelMain(null).getPanelMap().getHexButtons();
				
				for (HexagonButton button : buttons) {
					if (button.getRegion().coords.equals(eventMessage.coords)) {
						Controller.getInstance().handleMapTileClick(button);
						break;
					}
				}
				
				Controller.getInstance().getClientGame().getEventMessages().remove(eventMessage);
			}
		});
		buttonLook.setMaximumSize(new Dimension(Integer.MAX_VALUE, buttonLook.getPreferredSize().height));
		panelInnerLeft.add(buttonLook);
		
		JButton buttonDismiss = new JButton("KREUZ");
		buttonDismiss.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller.getInstance().getClientGame().getEventMessages().remove(eventMessage);
				refreshPanelNews();
			}
		});
		buttonDismiss.setPreferredSize(buttonDismiss.getPreferredSize());
		panelInnerLeft.add(buttonDismiss);
		//panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelMessage.getPreferredSize().height + 2 * PADDING));
		
		
		panel.add(panelInnerLeft);
		
		JLabel labelMessage = new JLabel("<html>" + eventMessage.message + "</html>");
		labelMessage.setFont(Look.fontSectionPart);
		panel.add(labelMessage);
		
		panel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(PADDING, PADDING, PADDING, PADDING)));
		panel.setBackground(Look.COLOR_MAP_BACKGROUND);
		return panel;
	}
	
	private JPanel getPanelDetailsTitle() {
		if (panelNewsTitle == null) {
			panelNewsTitle = new JPanel();
			
			JLabel labelTitle = new JLabel("Rundeninformationen");
			labelTitle.setFont(Look.fontSectionTitle);
			
			panelNewsTitle.add(labelTitle);
			panelNewsTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, PADDING, 0)));
			panelNewsTitle.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelNewsTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelTitle.getPreferredSize().height + 2 * PADDING));
		}
		return panelNewsTitle;
	}
	
	public JPanel getPanelNews() {
		if (panelRight == null) {
			panelRight = new JPanel();
			panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
			panelRight.add(getPanelDetailsTitle());
			
			for (EventMessage eventMessage : Controller.getInstance().getClientGame().getEventMessages()) {
				panelRight.add(buildPanelEventMessage(eventMessage));
			}
			panelRight.setPreferredSize(new Dimension(350, Integer.MAX_VALUE));
		}
		return panelRight;
	}
	
	private void refresh() {
		this.revalidate();
		this.repaint();
	}
	
	private void refreshPanelNews() {
		Controller.getInstance().getFrame().setPanelCompany();
	}
	
}
