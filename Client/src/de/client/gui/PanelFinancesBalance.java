package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import de.client.company.finances.Balance;
import de.client.company.finances.ProfitAndLoss;

public class PanelFinancesBalance extends JPanel {
	
	private Balance balance;
	private ProfitAndLoss profitAndLoss;
	
	public PanelFinancesBalance(Balance balance) {
		int padding = Look.PANEL_LEFT_PADDING;
		setBorder(new EmptyBorder(padding, padding, padding, padding));
		this.balance = balance;
		this.profitAndLoss = balance.getProfitAndLoss();
		
		setBackground(Look.COLOR_MAP_BACKGROUND);
		setLayout(new GridLayout(2, 1, 0, 20));
		add(getPanelBalance());
		add(getPanelIncomeStatement());
	}
	
	private static final long serialVersionUID = -4114442334515386352L;
	private JPanel panelBalance;
	private JPanel panelBalanceTitle;
	private JLabel labelAktiva;
	private JLabel labelBalanceTitle;
	private JLabel labelPassive;
	private JPanel panelBalanceTable;
	private JPanel panelLeftMiddle;
	private JLabel labelAVTitle;
	private JLabel labelAV;
	private JLabel labelPlotTitle;
	private JLabel labelPlotValue;
	private JLabel labelBuildingsTitle;
	private JLabel labelBuildingsValue;
	private JLabel labelUVTitle;
	private JLabel labelUV;
	private JLabel labelMoneyTitle;
	private JLabel labelMoneyValue;
	private JLabel labelInventoryTitle;
	private JLabel labelInventoryValue;
	private JPanel panelRightMiddle;
	private JLabel labelEKTitle;
	private JLabel labelEK;
	private JLabel labelFKTitle;
	private JLabel labelFK;
	private JLabel labelSumLeft;
	private JLabel labelSumRight;
	private JPanel panelBalanceSums;
	private JPanel panelBalanceSumsLeft;
	private JPanel panelBalanceSumsRight;
	private JPanel panelIncomeStatement;
	private JPanel panelIncomeTitle;
	private JPanel panelIncomeTable;
	private JLabel labelIncomeProfitHeader;
	private JLabel labelIncomeTitle;
	private JLabel labelIncomeLossHeader;
	private JPanel panelIncomeTableLeft;
	private JLabel labelIncomeTitleAufw;
	private JLabel labelIncomeAufw;
	private JLabel labelIncomeDepCostsTitle;
	private JLabel labelIncomeDepCostsValue;
	private JLabel labelIncomeDepreciationTitle;
	private JLabel labelIncomeDepreciationValue;
	private JLabel labelIncomeInventoryChangeTitle;
	private JLabel labelIncomeInventoryChangeValue;
	private JLabel labelIncomeFinanceCostsTitle;
	private JLabel labelIncomeFinanceCostsValue;
	private JLabel labelIncomeResourceCostsTitle;
	private JLabel labelIncomeResourceCostsValue;
	private JLabel labelIncomeEnergyMarketCostsValue;
	private JLabel labelIncomeEnergyMarketCostsTitle;
	private JLabel labelIncomeBuildingRunningCostsTitle;
	private JLabel labelIncomeBuildingRunningCostsValue;
	private JLabel labelIncomeNetUsageCostsTitle;
	private JLabel labelIncomeNetUsageCostsValue;
	private JLabel labelIncomeOtherCostsTitle;
	private JLabel labelIncomeOtherCostsValue;
	private JLabel labelIncomeTaxesTitle;
	private JLabel labelIncomeTaxesValue;
	private JLabel labelIncomeProfitTitle;
	private JLabel labelIncomeProfit;
	private JPanel panelIncomeTableRight;
	private JLabel labelIncomeTurnoverTitle;
	private JLabel labelIncomeTurnover;
	private JLabel labelIncomeLossTitle;
	private JLabel labelIncomeLoss;
	private JPanel panelIncomeSums;
	private JPanel panelIncomeSumsLeft;
	private JLabel labelIncomeSumsLeft;
	private JPanel panelIncomeSumsRight;
	private JLabel labelIncomeSumsRight;

	
	private JPanel getPanelBalance() {
		if (panelBalance == null) {
			panelBalance = new JPanel();
			panelBalance.setLayout(new BorderLayout(0, 0));
			panelBalance.add(getPanelBalanceTitle(), BorderLayout.NORTH);
			panelBalance.add(getPanelBalanceTable());
			panelBalance.add(getPanelBalanceSums(), BorderLayout.SOUTH);
			panelBalance.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelBalance;
	}
	private JPanel getPanelBalanceTitle() {
		if (panelBalanceTitle == null) {
			panelBalanceTitle = new JPanel();
			panelBalanceTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelBalanceTitle.setLayout(new GridLayout(1, 3, 0, 0));
			panelBalanceTitle.add(getLabelAktiva());
			panelBalanceTitle.add(getLabelBalanceTitle());
			panelBalanceTitle.add(getLabelPassive());
			panelBalanceTitle.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelBalanceTitle;
	}
	private JLabel getLabelAktiva() {
		if (labelAktiva == null) {
			labelAktiva = new JLabel("A");
		}
		return labelAktiva;
	}
	private JLabel getLabelBalanceTitle() {
		if (labelBalanceTitle == null) {
			int year = 0; //NEED TO GET THE YEAR FROM SOMEWHERE
			labelBalanceTitle = new JLabel("Bilanz zum Jahr " + balance.getYear());
			labelBalanceTitle.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return labelBalanceTitle;
	}
	private JLabel getLabelPassive() {
		if (labelPassive == null) {
			labelPassive = new JLabel("P ");
			labelPassive.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelPassive;
	}
	private JPanel getPanelBalanceTable() {
		if (panelBalanceTable == null) {
			panelBalanceTable = new JPanel();
			panelBalanceTable.setLayout(new GridLayout(1, 2, 0, 0));
			panelBalanceTable.add(getPanelLeftMiddle());
			panelBalanceTable.add(getPanelRightMiddle());
			panelBalanceTable.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelBalanceTable;
	}
	private JPanel getPanelLeftMiddle() {
		if (panelLeftMiddle == null) {
			panelLeftMiddle = new JPanel();
			panelLeftMiddle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelLeftMiddle.setLayout(new GridLayout(6, 2, 0, 0));
			panelLeftMiddle.add(getLabelAVTitle());
			panelLeftMiddle.add(getLabelAV());
			panelLeftMiddle.add(getLabelPlotTitle());
			panelLeftMiddle.add(getLapelPlotValue());
			panelLeftMiddle.add(getLapelBuildingsTitle());
			panelLeftMiddle.add(getLapelBuildingsValue());
			panelLeftMiddle.add(getLabelUVTitle());
			panelLeftMiddle.add(getLabelUV());
			panelLeftMiddle.add(getLabelMoneyTitle());
			panelLeftMiddle.add(getLabelMoneyValue());
			panelLeftMiddle.add(getLabelInventoryTitle());
			panelLeftMiddle.add(getLabelInventoryValue());
			panelLeftMiddle.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelLeftMiddle;
	}
	private Component getLabelInventoryValue() {
		if(labelInventoryValue == null){
			labelInventoryValue = new JLabel(Strings.fD(balance.getInventoryValue()) + "\u20AC");
			labelInventoryValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelInventoryValue;
	}
	private Component getLabelInventoryTitle() {
		if(labelInventoryTitle == null){
			labelInventoryTitle = new JLabel("    Erzeugnisse");
		}
		return labelInventoryTitle;
	}
	private Component getLabelMoneyTitle() {
		if(labelMoneyTitle == null){
			labelMoneyTitle = new JLabel("    Kasse");
		}
		return labelMoneyTitle;
	}
	private Component getLabelMoneyValue() {
		if(labelMoneyValue == null){
			labelMoneyValue = new JLabel(Strings.fD(balance.getMoneyValue()) + "\u20AC");
			labelMoneyValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelMoneyValue;
	}
	private Component getLapelBuildingsValue() {
		if(labelBuildingsValue == null){
			labelBuildingsValue = new JLabel(Strings.fD(balance.getBuildingsValue()) + "\u20AC");
			labelBuildingsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelBuildingsValue;
	}
	private Component getLapelBuildingsTitle() {
		if(labelBuildingsTitle == null){
			labelBuildingsTitle = new JLabel("    Gebäude");
		}
		return labelBuildingsTitle;
	}
	private Component getLapelPlotValue() {
		if(labelPlotValue == null){
			labelPlotValue = new JLabel(Strings.fD(balance.getPlotValue()) + "\u20AC");
			labelPlotValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelPlotValue;
	}
	private Component getLabelPlotTitle() {
		if(labelPlotTitle == null){
			labelPlotTitle = new JLabel("    Grundstücke");
		}
		return labelPlotTitle;
	}
	private JLabel getLabelAVTitle() {
		if (labelAVTitle == null) {
			labelAVTitle = new JLabel("AV");
		}
		return labelAVTitle;
	}
	private JLabel getLabelAV() {
		if (labelAV == null) {
			double AV = balance.getBuildingsValue() + balance.getPlotValue();
			labelAV = new JLabel(Strings.fD(AV) + "\u20AC");
			labelAV.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelAV;
	}
	private JLabel getLabelUVTitle() {
		if (labelUVTitle == null) {
			labelUVTitle = new JLabel("UV");
		}
		return labelUVTitle;
	}
	private JLabel getLabelUV() {
		if (labelUV == null) {
			double UV = balance.getInventoryValue() + balance.getMoneyValue();
			labelUV = new JLabel(Strings.fD(UV) + "\u20AC");
			labelUV.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelUV;
	}
	private JPanel getPanelRightMiddle() {
		if (panelRightMiddle == null) {
			panelRightMiddle = new JPanel();
			panelRightMiddle.setBorder(new EmptyBorder(2, 3, 2, 2));
			panelRightMiddle.setLayout(new GridLayout(2, 2, 0, 0));
			panelRightMiddle.add(getLabelEKTitle());
			panelRightMiddle.add(getLabelEK());
			panelRightMiddle.add(getLabelFKTitle());
			panelRightMiddle.add(getLabelFK());
			panelRightMiddle.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelRightMiddle;
	}
	private JLabel getLabelEKTitle() {
		if (labelEKTitle == null) {
			labelEKTitle = new JLabel("EK");
		}
		return labelEKTitle;
	}
	private JLabel getLabelEK() {
		if (labelEK == null) {
			labelEK = new JLabel(Strings.fD(balance.getEquity()) + "\u20AC");
			labelEK.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelEK;
	}
	private JLabel getLabelFKTitle() {
		if (labelFKTitle == null) {
			labelFKTitle = new JLabel("FK");
		}
		return labelFKTitle;
	}
	private JLabel getLabelFK() {
		if (labelFK == null) {
			labelFK = new JLabel(Strings.fD(balance.getDebtCapital()) + "\u20AC");
			labelFK.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelFK;
	}
	private JLabel getLabelSumLeft() {
		if (labelSumLeft == null) {
			double sum = balance.getAssetsSum();
			labelSumLeft = new JLabel(Strings.fD(sum) + "\u20AC");
			labelSumLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelSumLeft;
	}
	private JLabel getLabelSumRight() {
		if (labelSumRight == null) {
			double sum = balance.getLiabilitiesSum();
			labelSumRight = new JLabel(Strings.fD(sum) + "\u20AC");
			labelSumRight.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelSumRight;
	}
	private JPanel getPanelBalanceSums() {
		if (panelBalanceSums == null) {
			panelBalanceSums = new JPanel();
			panelBalanceSums.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
			panelBalanceSums.setLayout(new GridLayout(0, 2, 0, 0));
			panelBalanceSums.add(getPanelBalanceSumsLeft());
			panelBalanceSums.setBackground(Look.COLOR_MAP_BACKGROUND);
			panelBalanceSums.add(getPanelBalanceSumsRight());
		}
		return panelBalanceSums;
	}
	private JPanel getPanelBalanceSumsLeft() {
		if (panelBalanceSumsLeft == null) {
			panelBalanceSumsLeft = new JPanel();
			panelBalanceSumsLeft.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelBalanceSumsLeft.setLayout(new GridLayout(0, 1, 0, 0));
			panelBalanceSumsLeft.add(getLabelSumLeft());
			panelBalanceSumsLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelBalanceSumsLeft;
	}
	private JPanel getPanelBalanceSumsRight() {
		if (panelBalanceSumsRight == null) {
			panelBalanceSumsRight = new JPanel();
			panelBalanceSumsRight.setBorder(new EmptyBorder(2, 3, 2, 2));
			panelBalanceSumsRight.setLayout(new GridLayout(0, 1, 0, 0));
			panelBalanceSumsRight.add(getLabelSumRight());
			panelBalanceSumsRight.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelBalanceSumsRight;
	}
	private JPanel getPanelIncomeStatement() {
		if (panelIncomeStatement == null) {
			panelIncomeStatement = new JPanel();
			panelIncomeStatement.setLayout(new BorderLayout(0, 0));
			panelIncomeStatement.add(getPanelIncomeTitle(), BorderLayout.NORTH);
			panelIncomeStatement.add(getPanelIncomeTable(), BorderLayout.CENTER);
			panelIncomeStatement.add(getPanelIncomeSums(), BorderLayout.SOUTH);
		}
		return panelIncomeStatement;
	}
	private JPanel getPanelIncomeTitle() {
		if (panelIncomeTitle == null) {
			panelIncomeTitle = new JPanel();
			panelIncomeTitle.setBorder(new CompoundBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelIncomeTitle.setLayout(new GridLayout(1, 3, 0, 0));
			panelIncomeTitle.add(getLabelIncomeProfitHeader());
			panelIncomeTitle.add(getLabelIncomeTitle());
			panelIncomeTitle.add(getLabelIncomeLossHeader());
			panelIncomeTitle.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeTitle;
	}
	private JPanel getPanelIncomeTable() {
		if (panelIncomeTable == null) {
			panelIncomeTable = new JPanel();
			panelIncomeTable.setLayout(new GridLayout(1, 2, 0, 0));
			panelIncomeTable.add(getPanelIncomeTableLeft());
			panelIncomeTable.add(getPanelIncomeTableRight());
		}
		return panelIncomeTable;
	}
	private JLabel getLabelIncomeProfitHeader() {
		if (labelIncomeProfitHeader == null) {
			labelIncomeProfitHeader = new JLabel("S");
		}
		return labelIncomeProfitHeader;
	}
	private JLabel getLabelIncomeTitle() {
		if (labelIncomeTitle == null) {
			labelIncomeTitle = new JLabel("GuV");
			labelIncomeTitle.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return labelIncomeTitle;
	}
	private JLabel getLabelIncomeLossHeader() {
		if (labelIncomeLossHeader == null) {
			labelIncomeLossHeader = new JLabel("H ");
			labelIncomeLossHeader.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeLossHeader;
	}
	private JPanel getPanelIncomeTableLeft() {
		if (panelIncomeTableLeft == null) {
			panelIncomeTableLeft = new JPanel();
			panelIncomeTableLeft.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelIncomeTableLeft.setLayout(new GridLayout(10, 2, 0, 0));
			//panelIncomeTableLeft.add(getLabelIncomeTitleAufw());
			//panelIncomeTableLeft.add(getLabelIncomeAufw());
			panelIncomeTableLeft.add(getLabelIncomeDepCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeDepCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeDepreciationTitle());
			panelIncomeTableLeft.add(getLabelIncomeDepreciationValue());
			panelIncomeTableLeft.add(getLabelIncomeBuildingRunningCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeBuildingRunningCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeFinanceCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeFinanceCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeResourceCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeResourceCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeNetUsageCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeNetUsageCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeEnergyMarketCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeEnergyMarketCostsValue());
			panelIncomeTableLeft.add(getLabelIncomeOtherCostsTitle());
			panelIncomeTableLeft.add(getLabelIncomeOtherCostsValue());
			if(profitAndLoss.getProfitNet() > 0){
				panelIncomeTableLeft.add(getLabelIncomeTaxesTitle());
				panelIncomeTableLeft.add(getLabelIncomeTaxesValue());
				panelIncomeTableLeft.add(getLabelIncomeProfitTitle());
				panelIncomeTableLeft.add(getLabelIncomeProfit());
			}
			panelIncomeTableLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeTableLeft;
	}
	private Component getLabelIncomeTaxesTitle() {
		if (labelIncomeTaxesTitle == null) {
			labelIncomeTaxesTitle = new JLabel("Steuern");
		}
		return labelIncomeTaxesTitle;
	}
	private Component getLabelIncomeTaxesValue() {
		if (labelIncomeTaxesValue == null) {
			labelIncomeTaxesValue = new JLabel(Strings.fD(profitAndLoss.getTaxes()) + "\u20AC");
			labelIncomeTaxesValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeTaxesValue;
	}
	private Component getLabelIncomeEnergyMarketCostsValue() {
		if (labelIncomeEnergyMarketCostsValue == null) {
			labelIncomeEnergyMarketCostsValue = new JLabel(Strings.fD(profitAndLoss.getEnergyMarketCosts()) + "\u20AC");
			labelIncomeEnergyMarketCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeEnergyMarketCostsValue;
	}
	private Component getLabelIncomeEnergyMarketCostsTitle() {
		if (labelIncomeEnergyMarketCostsTitle == null) {
			labelIncomeEnergyMarketCostsTitle = new JLabel("Energiekäufe");
		}
		return labelIncomeEnergyMarketCostsTitle;
	}
	private Component getLabelIncomeOtherCostsValue() {
		if (labelIncomeOtherCostsValue == null) {
			labelIncomeOtherCostsValue = new JLabel(Strings.fD(profitAndLoss.getOtherCosts()) + "\u20AC");
			labelIncomeOtherCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeOtherCostsValue;
	}
	private Component getLabelIncomeOtherCostsTitle() {
		if (labelIncomeOtherCostsTitle == null) {
			labelIncomeOtherCostsTitle = new JLabel("Sonstige Kosten");
		}
		return labelIncomeOtherCostsTitle;
	}
	private Component getLabelIncomeNetUsageCostsValue() {
		if (labelIncomeNetUsageCostsValue == null) {
			labelIncomeNetUsageCostsValue = new JLabel(Strings.fD(profitAndLoss.getNetUsageCosts()) + "\u20AC");
			labelIncomeNetUsageCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeNetUsageCostsValue;
	}
	private Component getLabelIncomeNetUsageCostsTitle() {
		if (labelIncomeNetUsageCostsTitle == null) {
			labelIncomeNetUsageCostsTitle = new JLabel("Netznutzungskosten");
		}
		return labelIncomeNetUsageCostsTitle;
	}
	private Component getLabelIncomeBuildingRunningCostsValue() {
		if (labelIncomeBuildingRunningCostsValue == null) {
			labelIncomeBuildingRunningCostsValue = new JLabel(Strings.fD(profitAndLoss.getBuildingRunningCosts()) + "\u20AC");
			labelIncomeBuildingRunningCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeBuildingRunningCostsValue;
	}
	private Component getLabelIncomeBuildingRunningCostsTitle() {
		if (labelIncomeBuildingRunningCostsTitle == null) {
			labelIncomeBuildingRunningCostsTitle = new JLabel("Gebäude lfd. Kosten");
		}
		return labelIncomeBuildingRunningCostsTitle;
	}
	private Component getLabelIncomeResourceCostsValue() {
		if (labelIncomeResourceCostsValue == null) {
			labelIncomeResourceCostsValue = new JLabel(Strings.fD(profitAndLoss.getResourceCosts()) + "\u20AC");
			labelIncomeResourceCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeResourceCostsValue;
	}
	private Component getLabelIncomeResourceCostsTitle() {
		if (labelIncomeResourceCostsTitle == null) {
			labelIncomeResourceCostsTitle = new JLabel("Rohstoffkäufe");
		}
		return labelIncomeResourceCostsTitle;
	}
	private Component getLabelIncomeFinanceCostsValue() {
		if (labelIncomeFinanceCostsValue == null) {
			labelIncomeFinanceCostsValue = new JLabel(Strings.fD(profitAndLoss.getFinanceCosts()) + "\u20AC");
			labelIncomeFinanceCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeFinanceCostsValue;
	}
	private Component getLabelIncomeFinanceCostsTitle() {
		if (labelIncomeFinanceCostsTitle == null) {
			labelIncomeFinanceCostsTitle = new JLabel("Zinsen");
		}
		return labelIncomeFinanceCostsTitle;
	}
	private Component getLabelIncomeDepreciationValue() {
		if (labelIncomeDepreciationValue == null) {
			labelIncomeDepreciationValue = new JLabel(Strings.fD(profitAndLoss.getDepreciation()) + "\u20AC");
			labelIncomeDepreciationValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeDepreciationValue;
	}
	private Component getLabelIncomeDepreciationTitle() {
		if (labelIncomeDepreciationTitle == null) {
			labelIncomeDepreciationTitle = new JLabel("Abschreibungen");
		}
		return labelIncomeDepreciationTitle;
	}
	private Component getLabelIncomeDepCostsValue() {
		if (labelIncomeDepCostsValue == null) {
			labelIncomeDepCostsValue = new JLabel(Strings.fD(profitAndLoss.getDepartmentCosts()) + "\u20AC");
			labelIncomeDepCostsValue.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeDepCostsValue;
	}
	private Component getLabelIncomeDepCostsTitle() {
		if (labelIncomeDepCostsTitle == null) {
			labelIncomeDepCostsTitle = new JLabel("Lagerkosten");
		}
		return labelIncomeDepCostsTitle;
	}
	private JLabel getLabelIncomeTitleAufw() {
		if (labelIncomeTitleAufw == null) {
			labelIncomeTitleAufw = new JLabel("Aufwendungen");
		}
		return labelIncomeTitleAufw;
	}
	private JLabel getLabelIncomeAufw() {
		if (labelIncomeAufw == null) {
			double aufwendungen = profitAndLoss.getFinanceCosts() + profitAndLoss.getDepartmentCosts() + profitAndLoss.getDepreciation();
			//ARE THESE THE RIGHT NUMBERS?
			labelIncomeAufw = new JLabel(0 + "\u20AC");
			labelIncomeAufw.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeAufw;
	}
	private JLabel getLabelIncomeProfitTitle() {
		if (labelIncomeProfitTitle == null) {
			labelIncomeProfitTitle = new JLabel("Gewinn");
		}
		return labelIncomeProfitTitle;
	}
	private JLabel getLabelIncomeProfit() {
		if (labelIncomeProfit == null) {
			labelIncomeProfit = new JLabel(Strings.fD(profitAndLoss.getProfitNet()) + "\u20AC");
			labelIncomeProfit.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeProfit;
	}
	private JPanel getPanelIncomeTableRight() {
		if (panelIncomeTableRight == null) {
			panelIncomeTableRight = new JPanel();
			panelIncomeTableRight.setBorder(new EmptyBorder(2, 3, 2, 2));
			panelIncomeTableRight.setLayout(new GridLayout(2, 2, 0, 0));
			if(profitAndLoss.getProfitNet() > 0) panelIncomeTableRight.setLayout(new GridLayout(1, 2, 0, 0)); 
			panelIncomeTableRight.add(getLabelIncomeTurnoverTitle());
			panelIncomeTableRight.add(getLabelIncomeTurnover());
			if(profitAndLoss.getProfitNet() <= 0){
				panelIncomeTableRight.add(getLabelIncomeLossTitle());
				panelIncomeTableRight.add(getLabelIncomeLoss());
			}
			panelIncomeTableRight.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeTableRight;
	}
	private JLabel getLabelIncomeTurnoverTitle() {
		if (labelIncomeTurnoverTitle == null) {
			labelIncomeTurnoverTitle = new JLabel("Ertr\u00E4ge");
		}
		return labelIncomeTurnoverTitle;
	}
	private JLabel getLabelIncomeTurnover() {
		if (labelIncomeTurnover == null) {
			labelIncomeTurnover = new JLabel(Strings.fD(profitAndLoss.getRevenue()) + "\u20AC");
			labelIncomeTurnover.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeTurnover;
	}
	private JLabel getLabelIncomeLossTitle() {
		if (labelIncomeLossTitle == null) {
			labelIncomeLossTitle = new JLabel("Verlust");
		}
		return labelIncomeLossTitle;
	}
	private JLabel getLabelIncomeLoss() {
		if (labelIncomeLoss == null) {
			labelIncomeLoss = new JLabel(Strings.fD(-profitAndLoss.getProfitNet()) + "\u20AC");
			labelIncomeLoss.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeLoss;
	}
	private JPanel getPanelIncomeSums() {
		if (panelIncomeSums == null) {
			panelIncomeSums = new JPanel();
			panelIncomeSums.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
			panelIncomeSums.setLayout(new GridLayout(1, 2, 0, 0));
			panelIncomeSums.add(getPanelIncomeSumsLeft());
			panelIncomeSums.add(getPanelIncomeSumsRight());
		}
		return panelIncomeSums;
	}
	private JPanel getPanelIncomeSumsLeft() {
		if (panelIncomeSumsLeft == null) {
			panelIncomeSumsLeft = new JPanel();
			panelIncomeSumsLeft.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)), new EmptyBorder(2, 2, 2, 2)));
			panelIncomeSumsLeft.setLayout(new GridLayout(0, 1, 0, 0));
			panelIncomeSumsLeft.add(getLabelIncomeSumsLeft());
			panelIncomeSumsLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeSumsLeft;
	}
	private JLabel getLabelIncomeSumsLeft() {
		if (labelIncomeSumsLeft == null) {
			labelIncomeSumsLeft = new JLabel("");
			labelIncomeSumsLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeSumsLeft;
	}
	private JPanel getPanelIncomeSumsRight() {
		if (panelIncomeSumsRight == null) {
			panelIncomeSumsRight = new JPanel();
			panelIncomeSumsRight.setBorder(new EmptyBorder(2, 3, 2, 2));
			panelIncomeSumsRight.setLayout(new GridLayout(0, 1, 0, 0));
			panelIncomeSumsRight.add(getLabelIncomeSumsRight());
			panelIncomeSumsRight.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeSumsRight;
	}
	private JLabel getLabelIncomeSumsRight() {
		if (labelIncomeSumsRight == null) {
			labelIncomeSumsRight = new JLabel("");
			labelIncomeSumsRight.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeSumsRight;
	}
}
