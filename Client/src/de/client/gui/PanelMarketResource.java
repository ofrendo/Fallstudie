package de.client.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import de.shared.map.BuyableResource;
import de.shared.map.EnergyExchange;
import de.shared.map.ResourceMarket;

public class PanelMarketResource extends JPanel {

	private static final long serialVersionUID = 313952039768658438L;
	private JLabel labelResourceName;
	private JLabel labelEmpty1;
	private JLabel labelEmpty2;
	private JLabel labelResourcePrice;
	private JLabel labelBuyTitle;
	private JTextField textFieldBuyAmount;
	private JButton buttonBuy;
	private JLabel labelBuyPrice;
	private JLabel labelSellTitle;
	private JTextField textFieldSellAmount;
	private JButton buttonSell;
	private JLabel labelSellPrice;
	
	private BuyableResource buyableResource;
	
	public PanelMarketResource(BuyableResource buyableResource) {
		setBorder(new EmptyBorder(10, 20, 10, 20));
		this.buyableResource = buyableResource;
		
		setBackground(Look.COLOR_MAP_BACKGROUND);
		setLayout(new GridLayout(3, 4, 5, 5));
		add(getLabelResourceName());
		add(getLabelResourcePrice());
		add(getLabelEmpty1());
		add(getLabelEmpty2());
		add(getLabelBuyTitle());
		add(getTextFieldBuyAmount());
		add(getButtonBuy());
		add(getLabelBuyPrice());
		add(getLabelSellTitle());
		add(getTextFieldSellAmount());
		add(getButtonSell());
		add(getLabelSellPrice());

		setMaximumSize(new Dimension(Integer.MAX_VALUE, getButtonBuy().getPreferredSize().height*6));
	}
	
	public String getPriceString() {
		ResourceMarket market = Controller.getInstance()
				.getClientGame()
				.getMap()
				.getResourceMarket();
		
		String end = "€ / " + buyableResource.unit;
		
		switch (buyableResource) {
		case COAL:
		case GAS:
		case URANIUM:
			return Strings.fD(market.getResourcePrice(buyableResource.resourceType)) + end;
		case ENERGY:
			EnergyExchange exchange = Controller.getInstance().getClientGame().getMap().getEnergyExchange();
			return Strings.fD(exchange.getCurrentEnergyPrice()) + end;
		}
		return null;
	}
	
	private JLabel getLabelResourceName() {
		if (labelResourceName == null) {
			labelResourceName = new JLabel(buyableResource.name);
		}
		return labelResourceName;
	}
	private JLabel getLabelEmpty1() {
		if (labelEmpty1 == null) {
			labelEmpty1 = new JLabel("");
		}
		return labelEmpty1;
	}
	private JLabel getLabelEmpty2() {
		if (labelEmpty2 == null) {
			labelEmpty2 = new JLabel("");
		}
		return labelEmpty2;
	}
	private JLabel getLabelResourcePrice() {
		if (labelResourcePrice == null) {
			labelResourcePrice = new JLabel(getPriceString());
		}
		return labelResourcePrice;
	}
	private JLabel getLabelBuyTitle() {
		if (labelBuyTitle == null) {
			labelBuyTitle = new JLabel("Einkauf");
		}
		return labelBuyTitle;
	}
	private JTextField getTextFieldBuyAmount() {
		if (textFieldBuyAmount == null) {
			textFieldBuyAmount = new JTextField();
			textFieldBuyAmount.setColumns(10);
		}
		return textFieldBuyAmount;
	}
	private JButton getButtonBuy() {
		if (buttonBuy == null) {
			buttonBuy = new JButton("Kaufen");
		}
		return buttonBuy;
	}
	private JLabel getLabelBuyPrice() {
		if (labelBuyPrice == null) {
			labelBuyPrice = new JLabel("Brauchen?");
		}
		return labelBuyPrice;
	}
	private JLabel getLabelSellTitle() {
		if (labelSellTitle == null) {
			labelSellTitle = new JLabel("Verkaufen");
		}
		return labelSellTitle;
	}
	private JTextField getTextFieldSellAmount() {
		if (textFieldSellAmount == null) {
			textFieldSellAmount = new JTextField();
			textFieldSellAmount.setColumns(10);
		}
		return textFieldSellAmount;
	}
	private JButton getButtonSell() {
		if (buttonSell == null) {
			buttonSell = new JButton("Verkaufen");
		}
		return buttonSell;
	}
	private JLabel getLabelSellPrice() {
		if (labelSellPrice == null) {
			labelSellPrice = new JLabel("Brauchen?");
		}
		return labelSellPrice;
	}
}
