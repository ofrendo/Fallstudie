package de.client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.client.EventMessage;
import de.client.company.Company;
import de.shared.map.region.ResourceType;

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
			int padding = Look.PANEL_LEFT_PADDING;
			panelLeft = new JPanel(new GridLayout(2, 2, padding, padding));
			panelLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
			
			//ACCOUNT INFORMATION
			JLabel labelAccountInformation = new JLabel(Controller.getInstance().getHtmlAccountInformation());
			labelAccountInformation.setFont(Look.fontSectionPart);
			
			//POWERSTATION INFORMATION
			JLabel labelPowerStationInformation = new JLabel(getHtmlPowerStations());
			labelPowerStationInformation.setFont(Look.fontSectionPart);
			
			//RESOURCE AND PRODUCTION INFORMATION
			JPanel panelLeftBottom = new JPanel(new GridLayout(2, 1));
			panelLeftBottom.setBackground(Look.COLOR_MAP_BACKGROUND);
			
			JLabel labelResourceInformation = new JLabel(Controller.getInstance().getHtmlResourceInformation());
			labelResourceInformation.setFont(Look.fontSectionPart);
			
			JLabel labelEnergyInformation = new JLabel(Controller.getInstance().getHtmlEnergyInformation());
			labelEnergyInformation.setFont(Look.fontSectionPart);
			
			panelLeftBottom.add(labelResourceInformation);
			panelLeftBottom.add(labelEnergyInformation);
			
			//MINE INFORMATION
			JLabel labelMineInformation = new JLabel(getHtmlMines());
			labelMineInformation.setFont(Look.fontSectionPart);
			
			panelLeft.add(labelAccountInformation);
			panelLeft.add(labelPowerStationInformation);
			panelLeft.add(panelLeftBottom);
			panelLeft.add(labelMineInformation);
			
			panelLeft.setBorder(new EmptyBorder(padding, padding, padding, padding));
		}
		return panelLeft;
	}
	
	private String getHtmlPowerStations() {
		String result = "<html><table>"
				+ "<tr>"
				+ "<td>Kraftwerke</td><td></td>"
				+ "</tr>"
				+ getHtmlPowerStationRow(ResourceType.COAL)
				+ getHtmlPowerStationRow(ResourceType.GAS)
				+ getHtmlPowerStationRow(ResourceType.URANIUM)
				+ getHtmlPowerStationRow(ResourceType.WIND)
				+ getHtmlPowerStationRow(ResourceType.SOLAR)
				+ getHtmlPowerStationRow(ResourceType.WATER)
		 		+ "</table></html>";

		return result;
	}
	
	private String getHtmlPowerStationRow(ResourceType resourceType) {
		if (company.getNumberPowerStations(resourceType) > 0) {
			return "<tr>"
					+ "<td>" + Strings.getResourceString(resourceType) + "</td>"
					+ "<td>" + company.getNumberPowerStations(resourceType) + "x</td>"
					+ "</tr>";
		}
		else {
			return "";
		}
	}

	private String getHtmlMines() {
		String result = "<html><table>"
				+ "<tr>"
				+ "<td>Minen</td><td></td>"
				+ "</tr>"
				+ getHtmlMineRow(ResourceType.COAL)
				+ getHtmlMineRow(ResourceType.GAS)
				+ getHtmlMineRow(ResourceType.URANIUM)
		 		+ "</table></html>";

		return result;
	}
	
	private String getHtmlMineRow(ResourceType resourceType) {
		if (company.getNumberMines(resourceType) > 0) {
			return "<tr>"
					+ "<td>" + Strings.getResourceString(resourceType) + "</td>"
					+ "<td>" + company.getNumberMines(resourceType) + "x</td>"
					+ "</tr>";
		}
		else {
			return "";
		}
	}
	
	public JPanel buildPanelEventMessage(final EventMessage eventMessage) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panelInnerLeft = new JPanel();
		panelInnerLeft.setLayout(new BoxLayout(panelInnerLeft, BoxLayout.Y_AXIS));
		
		JButton buttonLook = new JButton("\uD83D\uDD0E");
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
				
				//Controller.getInstance().getClientGame().getEventMessages().remove(eventMessage);
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
		panel.add(Box.createRigidArea(new Dimension(5,0)));
		
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
	
	private void refreshPanelNews() {
		Controller.getInstance().getFrame().setPanelCompany();
	}
	
}
