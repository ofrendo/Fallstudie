package de.client.gui;

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
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
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
			textFieldRegionBid = new JTextField(20);
			textFieldRegionBid.setMaximumSize( textFieldRegionBid.getPreferredSize() );
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
			sliderPowerStationMaintenanceRate.setSnapToTicks(true);
			sliderPowerStationMaintenanceRate.setPreferredSize(new Dimension(200, 0));
			sliderPowerStationMaintenanceRate.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					JSlider source = (JSlider) arg0.getSource();
					if (!source.getValueIsAdjusting()) {
						double newValue = source.getValue() / 100.0;
						((ResourceRelation) relation).powerStation.setMaintenanceRate(newValue);
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
			sliderPowerStationUtilization.setPreferredSize(new Dimension(100, 0));
			sliderPowerStationUtilization.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent arg0) {
					JSlider source = (JSlider) arg0.getSource();
					if (!source.getValueIsAdjusting()) {
						double newValue = (double) source.getValue() / (double) steps;
						((ResourceRelation) relation).powerStation.setUtilizationRate(newValue);
						Controller.getInstance().updatePanelDetails(hexButton);
					}
				}
			});
		}
		return sliderPowerStationUtilization;
	}
	
}
