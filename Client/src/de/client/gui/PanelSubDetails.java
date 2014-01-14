package de.client.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.client.company.PowerStation;
import de.client.company.ResourceRelation;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.relation.Contract;
import de.shared.map.relation.RegionRelation;

public class PanelSubDetails extends JPanel {

	private static final long serialVersionUID = -2089650389647041653L;

	private HexagonButton hexButton;
	public Region region;
	public RegionRelation relation;
	
	public JButton buttonStartRegionBidding;
	
	public JTextField textFieldRegionBid;
	public JButton buttonRegionBid;
	
	public JButton buttonBuildMine;
	public JSlider sliderMineUtilization;
	
	public JButton buttonBuildPowerStation;
	public JSlider sliderPowerStationMaintenanceRate;
	public JSlider sliderPowerStationUtilization;
	
	public JTextField textFieldMaxCustomers;
	public JTextField textFieldPrice;
	public JButton buttonRequestContract;
	
	public JButton buttonCancelContract;
	
	public PanelSubDetails(HexagonButton hexButton) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Look.COLOR_MAP_BACKGROUND);
		this.hexButton = hexButton;
	}
	
	protected JButton getButtonStartRegionBidding() {
		if (buttonStartRegionBidding == null) {
			buttonStartRegionBidding = new JButton("Auktion starten");
			buttonStartRegionBidding.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Controller.getInstance().sendStartRegionBidding(region);
					buttonStartRegionBidding.setEnabled(false);
				}
			});
		}
		return buttonStartRegionBidding;
	}
	
	protected JTextField getTextFieldRegionBid() {
		if (textFieldRegionBid == null) {
			textFieldRegionBid = new JTextField();
			textFieldRegionBid.setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, textFieldRegionBid.getPreferredSize().height) );
			textFieldRegionBid.setAlignmentX( Component.LEFT_ALIGNMENT );
		}
		return textFieldRegionBid;
	}
	
	protected JButton getButtonRegionBid() {
		if (buttonRegionBid == null) {
			buttonRegionBid = new JButton("Bieten");
			buttonRegionBid.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						double bid = Double.parseDouble(textFieldRegionBid.getText()); 
						if (bid >= 100000) {
							Controller.getInstance().sendRegionBid(region, bid);
							buttonRegionBid.setEnabled(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Mindestgebot ist 100000€.");
						}
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Bitte Eingaben überprüfen!");
					}
				}
			});
		}
		return buttonRegionBid;
	}
	
	protected JButton getButtonBuildMine() {
		if (buttonBuildMine == null) {
			buttonBuildMine = new JButton("Bauen");
			buttonBuildMine.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().buildMine((ResourceRelation) relation, ((ResourceRegion) region).resourceType);
					buttonBuildMine.setEnabled(false);
				}
			});
		}
		return buttonBuildMine;
	}
	
	protected JButton getButtonBuildPowerStation() {
		if (buttonBuildPowerStation == null) {
			buttonBuildPowerStation = new JButton("Bauen");
			buttonBuildPowerStation.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Controller.getInstance().buildPowerStation((ResourceRelation) relation, ((ResourceRegion) region).resourceType);
					buttonBuildPowerStation.setEnabled(false);
				}
			});
		}
		return buttonBuildPowerStation;
	}

	public JSlider getSliderMineUtilization() {
		if (sliderMineUtilization == null) {
			int initValue = (int) (((ResourceRelation) relation).mine.utilizationRate * 100);
			sliderMineUtilization = new JSlider(0, 100, initValue);
			//sliderMineUtilization.setSnapToTicks(true);
			sliderMineUtilization.setAlignmentX( Component.LEFT_ALIGNMENT );
			sliderMineUtilization.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					JSlider source = (JSlider) arg0.getSource();
					if (!source.getValueIsAdjusting()) {
						double newValue = source.getValue() / 100.0;
						((ResourceRelation) relation).mine.setUtilizationRate(newValue);
						Controller.getInstance().updatePanelDetails(hexButton);
					}
				}
			});
		}
		return sliderMineUtilization;
	}
	
	public JSlider getSliderPowerStationMaintenanceRate() {
		if (sliderPowerStationMaintenanceRate == null) {
			int initValue = (int) (((ResourceRelation) relation).powerStation.maintenanceRate * 100);
			sliderPowerStationMaintenanceRate = new JSlider(0, 100, initValue);
			//sliderPowerStationMaintenanceRate.setSnapToTicks(true);
			sliderPowerStationMaintenanceRate.setAlignmentX( Component.LEFT_ALIGNMENT );
			sliderPowerStationMaintenanceRate.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					JSlider source = (JSlider) arg0.getSource();
					if (!source.getValueIsAdjusting()) {
						double newValue = source.getValue() / 100.0;
						((ResourceRelation) relation).powerStation.setMaintenanceRate(newValue);
						Controller.getInstance().getCompany().optimizePowerStations();
						Controller.getInstance().updatePanelDetails(hexButton);
					}
				}
			});
		}
		return sliderPowerStationMaintenanceRate;
	}
	
	public JSlider getSliderPowerStationUtilization() {
		if (sliderPowerStationUtilization == null) {
			PowerStation p = ((ResourceRelation) relation).powerStation;
			final int steps = (int) (1.0 / p.getAdjustability());
			int initValue = (int) (p.utilizationRate * steps);
			
			sliderPowerStationUtilization = new JSlider(0, steps, initValue);
			//sliderPowerStationUtilization.setSnapToTicks(true);
			sliderPowerStationUtilization.setAlignmentX( Component.LEFT_ALIGNMENT );
			sliderPowerStationUtilization.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					JSlider source = (JSlider) arg0.getSource();
					if (!source.getValueIsAdjusting()) {
						double newValue = (double) source.getValue() / (double) steps;
						((ResourceRelation) relation).powerStation.setUtilizationRate(newValue);
						Controller.getInstance().getCompany().optimizePowerStations();
						Controller.getInstance().updatePanelDetails(hexButton);
					}
				}
			});
		}
		return sliderPowerStationUtilization;
	}

	public JTextField getTextFieldMaxCustomers() {
		if (textFieldMaxCustomers == null) {
			textFieldMaxCustomers = new JTextField();
			textFieldMaxCustomers.setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, textFieldMaxCustomers.getPreferredSize().height) );
			textFieldMaxCustomers.setAlignmentX( Component.LEFT_ALIGNMENT );
		}
		return textFieldMaxCustomers;
	}
	
	public JTextField getTextFieldPrice() {
		if (textFieldPrice == null) {
			textFieldPrice = new JTextField();
			textFieldPrice.setMaximumSize( 
				     new Dimension(Integer.MAX_VALUE, textFieldPrice.getPreferredSize().height) );
			textFieldPrice.setAlignmentX( Component.LEFT_ALIGNMENT );
		}
		return textFieldPrice;
	}
	
	public JButton getButtonRequestContract() {
		if (buttonRequestContract == null) {
			buttonRequestContract = new JButton("Vertrag anfragen");
			buttonRequestContract.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						int maxCustomers = Integer.parseInt(textFieldMaxCustomers.getText());
						double price = Double.parseDouble(textFieldPrice.getText()); 
						
						if (price > 0 && maxCustomers > 0) {
							Controller.getInstance().getClientGame().requestContract((CityRegion) region, maxCustomers, price);
							buttonRequestContract.setEnabled(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Bitte Zahlen überprüfen.");
						}
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Bitte Eingaben überprüfen!");
					}
				}
			});
		}
		return buttonRequestContract;
	}

	public JButton getButtonCancelContract() {
		if (buttonCancelContract == null) {
			buttonCancelContract = new JButton("Vertrag kündigen");
			buttonCancelContract.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CityRegion cityRegion = (CityRegion) region;
					Contract c = cityRegion.getPlayerContract(Controller.getInstance().getOwnPlayer());
					Controller.getInstance().sendCancelContract(c, hexButton);
					buttonCancelContract.setEnabled(false);
				}
			});
		}
		return buttonCancelContract;
	}
	
}
