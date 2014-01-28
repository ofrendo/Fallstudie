package de.client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.client.company.ResourceRelation;
import de.shared.game.Constants;
import de.shared.map.region.CityRegion;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;

public class PanelDetails extends JPanel {

	private static final long serialVersionUID = -3177233247088338481L;
	private JPanel panelDetailsTitle;
	private JLabel labelDetailsTitle;

	private JPanel panelDetails;
	
	private HexagonButton currentHexButton;
	
	public PanelDetails() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(getPanelDetailsTitle());
		add(getPanelDetails());
	}
	
	public void setContentEmpty() {
		currentHexButton = null;
		
		getPanelDetails().removeAll();
	}
	
	public void refresh() {
		getPanelDetails().revalidate();
		getPanelDetails().repaint();
	}
	
	public void setRegionContent(HexagonButton hexButton) {
		setContentEmpty();
		
		currentHexButton = hexButton;
		
		if (hexButton.panel == null) {
			hexButton.panel = new PanelSubDetails(hexButton);
			
			currentHexButton.panel.region = hexButton.getRegion();
			currentHexButton.panel.relation = Controller.getInstance().getCompany().getRegionRelation(hexButton.getRegion().coords);
			if (hexButton.getRegion() instanceof ResourceRegion)
				setResourceRegionContent((ResourceRegion) hexButton.getRegion(), hexButton.panel); 
			
			if (hexButton.getRegion() instanceof CityRegion)
				setCityRegionContent((CityRegion) hexButton.getRegion(), hexButton.panel);
		}
		
		getPanelDetails().add(hexButton.panel);
		
		refresh();
	}
	
	public void setResourceRegionContent(ResourceRegion region, PanelSubDetails panel) {
		ResourceRelation relation = (ResourceRelation) currentHexButton.panel.relation;
		
		if (region.resourceType == ResourceType.EMPTY) 
			return;
		
		switch (region.resourceRegionStatus) {
		case NEUTRAL:
			String resourceRow = (region.resourceAmount > 0) ? 
					"<tr>"
					+ "<td>Rohstoffmenge:</td>"
					+ "<td>" + Strings.fD(region.resourceAmount) + "</td>"
					+ "</tr>" : "";
			
			String htmlText = "<html>"
					+ "<table>"
					+ "<tr>"
					+ "<td>Typ:</td>"
					+ "<td>" + Strings.getResourceString(region.resourceType) + "</td>"
					+ "</tr>"
					+ resourceRow
					+ "<tr>"
					+ "<td>Inhaber:</td>"
					+ "<td>-</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Mindestgebot:</td>"
					+ "<td>" + Strings.fD(Constants.MINIMUM_REGION_BID) + " €</td>"
					+ "</tr>"
					+ "</table>"
					+ "</html>";
			
			JLabel labelDetailsHTML = new JLabel(htmlText);
			labelDetailsHTML.setFont(Look.fontSectionPart);
			panel.add(labelDetailsHTML);
			panel.add(panel.getButtonStartRegionBidding());
			break;
		case BUYABLE:
			resourceRow = (region.resourceAmount > 0) ? 
					"<tr>"
					+ "<td>Rohstoffmenge:</td>"
					+ "<td>" + Strings.fD(region.resourceAmount) + "</td>"
					+ "</tr>" : "";
			
			htmlText = "<html>"
					+ "<table>"
					+ "<tr>"
					+ "<td>Typ:</td>"
					+ "<td>" + Strings.getResourceString(region.resourceType) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ resourceRow
					+ "<td>Inhaber:</td>"
					+ "<td>-</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Mindestgebot:</td>"
					+ "<td>" + Strings.fD(Constants.MINIMUM_REGION_BID) + "€</td>"
					+ "</tr>"
					+ "</table>"
					+ "</html>";
			
			labelDetailsHTML = new JLabel(htmlText);
			labelDetailsHTML.setFont(Look.fontSectionPart);
			panel.add(labelDetailsHTML);
			panel.add(panel.getTextFieldRegionBid());
			panel.add(panel.getButtonRegionBid());
			break;
		case MINE:
		case POWERSTATION:
		case MINE_POWERSTATION:
		case OWNED:
			if (region.owner.equals(Controller.getInstance().getOwnPlayer())) {
				resourceRow = (region.resourceAmount > 0) ? 
						"<tr>"
						+ "<td>Rohstoffmenge:</td>"
						+ "<td>" + Strings.fD(relation.resourceAmount) + "</td>"
						+ "</tr>" : "";
				
				String htmlText1 = "<html>"
						+ "<table>"
						+ "<tr>"
						+ "<td>Typ:</td>"
						+ "<td>" + Strings.getResourceString(region.resourceType) + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Inhaber:</td>"
						+ "<td>" + region.owner.playerName + " (ME)</td>"
						+ "</tr>"
						+ resourceRow;
				
				if (relation.mine == null && !region.resourceType.isRenewable) {
					htmlText1 += "<tr>"
							+ "<td>Mine:</td>"
							+ "<td>" + Strings.fD(region.resourceType.mPurchaseValue) + "€</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td>Bauzeit:</td>"
							+ "<td>" + Strings.getRemainingRoundString(region.resourceType.mBuildTime) + "</td>"
							+ "</tr>"
							+ "</table></html>";
					
					JLabel labelDetailsHTML1 = new JLabel(htmlText1);
					labelDetailsHTML1.setFont(Look.fontSectionPart);
					panel.add(labelDetailsHTML1);
					panel.add(panel.getButtonBuildMine());
				}
				else if (!region.resourceType.isRenewable) {
					if (!relation.mine.isBuilt()) {
						htmlText1 += "<tr>"
								+ "<td>Mine:</td>"
								+ "<td>Am Bauen...</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>Bauzeit:</td>"
								+ "<td>" + relation.mine.getBuildingTimeLeft() + " Runden noch</td>"
								+ "</tr>"
								+ "</table></html>";
						JLabel labelDetailsHTML1 = new JLabel(htmlText1);
						labelDetailsHTML1.setFont(Look.fontSectionPart);
						panel.add(labelDetailsHTML1);
					}
					else {
						htmlText1 += "<tr>"
								+ "<td>Mine:</td>"
								+ "<td></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>lfd. Kosten:</td>"
								+ "<td>" + Strings.fD(relation.mine.getRunningCosts()) + "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>Produktion:</td>"
								+ "<td>" + Strings.fD(relation.mine.getProduction()) + "</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>Auslastung:</td>"
								+ "<td>"
								+ "</tr>"
								+ "</table></html>";
						JLabel labelDetailsHTML1 = new JLabel(htmlText1);
						labelDetailsHTML1.setFont(Look.fontSectionPart);
						panel.add(labelDetailsHTML1);
						
						panel.add(panel.getSliderMineUtilization());
					}
				}
				else {
					htmlText1 += "</table></html>";
					JLabel labelDetailsHTML1 = new JLabel(htmlText1);
					labelDetailsHTML1.setFont(Look.fontSectionPart);
					panel.add(labelDetailsHTML1);
				}
				
				String htmlText2 = "<html><table>"
						+ "<tr>"
						+ "<td>Kraftwerk:</td>";
				
				if (relation.powerStation == null) {
					int buildTime = region.resourceType.pBuildTime;
					htmlText2 += "<td>" + Strings.fD(region.resourceType.pPurchaseValue) + "€</td>"
							+ "</tr>"
							+ "<tr>"
							+ "<td>Bauzeit:</td>"
							+ "<td>" + Strings.getRemainingRoundString(buildTime) + "</td>"
							+ "</tr>"
							+ "</table></html>";
					
					JLabel labelDetailsHTML2 = new JLabel(htmlText2);
					labelDetailsHTML2.setFont(Look.fontSectionPart);
					panel.add(labelDetailsHTML2);
					panel.add(panel.getButtonBuildPowerStation());
				}
				else {
					if (!relation.powerStation.isBuilt()) {
						htmlText2 += "<td>Am Bauen...</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>Bauzeit:</td>"
								+ "<td>" + Strings.getRemainingRoundString(relation.powerStation.getBuildingTimeLeft()) + " noch</td>"
								+ "</tr>"
								+ "</table></html>";
						
						JLabel labelDetailsHTML2 = new JLabel(htmlText2);
						labelDetailsHTML2.setFont(Look.fontSectionPart);
						panel.add(labelDetailsHTML2);
					}
					else {
						htmlText2 += "<td></td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>lfd. Kosten:</td>"
								+ "<td>" + Strings.fD(relation.powerStation.getRunningCosts()) + "€</td>"
								+ "</tr>"
								+ "<tr>"
								+ "<td>Wartungskosten:</td>"
								+ "<td></td>"
								+ "</table></html>";
						
						JLabel labelDetailsHTML2 = new JLabel(htmlText2);
						labelDetailsHTML2.setFont(Look.fontSectionPart);
						panel.add(labelDetailsHTML2);
						panel.add(panel.getSliderPowerStationMaintenanceRate());
						
						String htmlText3 = "<html><table>"
								+ "<tr>"
								+ "<td>Produktion:</td>"
								+ "<td>" + Strings.fD(relation.powerStation.getProduction()) + Strings.ENERGY_UNIT + "</td>"
								+ "</tr>";
						
						if (relation.powerStation.getConsumption() > 0) {
							htmlText3 += "<tr>"
									+ "<td>Verbrauch:</td>"
									+ "<td>" + Strings.fD(relation.powerStation.getConsumption()) 
											 + Strings.getResourceUnit(relation.powerStation.getResourceType()) + "</td>"
									+ "</tr>";
						}
						
						htmlText3 += "<tr>"
								+ "<td>Auslastung:</td>"
								+ "<td></td>"
								+ "</table>"
								+ "</html>";
						
						JLabel labelDetailsHTML3 = new JLabel(htmlText3);
						labelDetailsHTML3.setFont(Look.fontSectionPart);
						panel.add(labelDetailsHTML3);
						panel.add(panel.getSliderPowerStationUtilization());
					}
				}
				
			}
			else {
				htmlText = "<html>"
						+ "<table>"
						+ "<tr>"
						+ "<td>Typ:</td>"
						+ "<td>" + Strings.getResourceString(region.resourceType) + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>Inhaber:</td>"
						+ "<td>" + region.owner.playerName + "(NICHT ICH)</td>"
						+ "</tr>"
						+ "</table>"
						+ "</html>";
				labelDetailsHTML = new JLabel(htmlText);
				labelDetailsHTML.setFont(Look.fontSectionPart);
				panel.add(labelDetailsHTML);
			}
			break;
		default:
			break;
		}
	}
	
	public void setCityRegionContent(CityRegion region, PanelSubDetails panel) {
		CityRelation relation = (CityRelation) Controller.getInstance().getCompany().getRegionRelation(region.coords);
		
		JLabel labelDetailsHTML;
		String htmlText;
		Contract contract = region.getPlayerContract(Controller.getInstance().getOwnPlayer());
				
		if (contract == null) { //no contract with city
			htmlText = "<html>"
					+ "<table>"
					+ "<tr>"
					+ "<td>Name:</td>"
					+ "<td>" + region.getCityName() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Bevölkerung:</td>"
					+ "<td>" + Strings.fD(region.getPopulation()) + "</td>"
					+ "</tr>";
			
			if (!Controller.getInstance().getCompany().isPowerStationInRange(relation)) {
				htmlText += "</table></html>";
				
				labelDetailsHTML = new JLabel(htmlText);
				labelDetailsHTML.setFont(Look.fontSectionPart);
				panel.add(labelDetailsHTML);
			}
			else { //powerstation in range
				htmlText += "<tr>"
						+ "<td>Bekanntheit:</td>"
						+ "<td>" + Strings.fD(relation.awareness) + "</td>"
						+ "<tr>"
						+ "<td>Beliebtheit:</td>"
						+ "<td>" + Strings.fD(relation.popularity) + "</td>"
						+ "</tr>"
						+ "</table>"
						+ "</html>";
				
				labelDetailsHTML = new JLabel(htmlText);
				labelDetailsHTML.setFont(Look.fontSectionPart);
				panel.add(labelDetailsHTML);
				panel.add(panel.getTextFieldMaxCustomers());
				panel.add(panel.getTextFieldPrice());
				panel.add(panel.getButtonRequestContract());
			}
		}
		else {  //contract with city
			htmlText = "<html><table>"
					+ "<tr>"
					+ "<td>Name:</td>"
					+ "<td>" + region.getCityName() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Bevölkerung:</td>"
					+ "<td>" + Strings.fD(region.getPopulation()) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Kunden:</td>"
					+ "<td>" + Strings.fD(contract.amountCustomer) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Preis:</td>"
					+ "<td>" + Strings.fD(contract.amountMoneyPerCustomer) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Kosten:</td>"
					+ "<td>" + Strings.fD(Constants.NET_USAGE_COSTS) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Bekanntheit:</td>"
					+ "<td>" + Strings.fD(relation.awareness) + "</td>"
					+ "<tr>"
					+ "<td>Beliebtheit:</td>"
					+ "<td>" + Strings.fD(relation.popularity) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>Energiebedarf:</td>"
					+ "<td>" + Strings.fD(contract.amountEnergyNeeded) + "</td>"
					+ "</tr>"
					+ "</table></html>";
			
			labelDetailsHTML = new JLabel(htmlText);
			labelDetailsHTML.setFont(Look.fontSectionPart);
			panel.add(labelDetailsHTML);
			panel.add(panel.getButtonCancelContract());
		}
	}
	
	private JPanel getPanelDetailsTitle() {
		if (panelDetailsTitle == null) {
			panelDetailsTitle = new JPanel();
			panelDetailsTitle.add(getLabelDetailsTitle());
			panelDetailsTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(0, 0, 5, 0)));
			panelDetailsTitle.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelDetailsTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, getLabelDetailsTitle().getPreferredSize().height + 2 * 5 ));
			panelDetailsTitle.setAlignmentX( Component.LEFT_ALIGNMENT );
		}
		return panelDetailsTitle;
	}
	
	private JLabel getLabelDetailsTitle() {
		if (labelDetailsTitle == null) {
			labelDetailsTitle = new JLabel("Feldinformationen");
			labelDetailsTitle.setFont(Look.fontSectionTitle);
		}
		return labelDetailsTitle;
	}
	private JPanel getPanelDetails() {
		if (panelDetails == null) {
			panelDetails = new JPanel();
			panelDetails.setLayout(new BoxLayout(panelDetails, BoxLayout.Y_AXIS));
			panelDetails.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelDetails.setAlignmentX( Component.LEFT_ALIGNMENT );
			panelDetails.setBorder(new EmptyBorder(5, 5, 5, 5));
		}
		return panelDetails;
	}
	
	
	
	
	
}
