package de.client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private JLabel labelUVTitle;
	private JLabel labelUV;
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
			labelBalanceTitle = new JLabel("Bilanz zum Jahr " + 0);
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
			panelLeftMiddle.setLayout(new GridLayout(2, 2, 0, 0));
			panelLeftMiddle.add(getLabelAVTitle());
			panelLeftMiddle.add(getLabelAV());
			panelLeftMiddle.add(getLabelUVTitle());
			panelLeftMiddle.add(getLabelUV());
			panelLeftMiddle.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelLeftMiddle;
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
			double sum = balance.getBuildingsValue() + balance.getInventoryValue()
					   + balance.getMoneyValue() + balance.getPlotValue();
			labelSumLeft = new JLabel(Strings.fD(sum) + "\u20AC");
			labelSumLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelSumLeft;
	}
	private JLabel getLabelSumRight() {
		if (labelSumRight == null) {
			double sum = balance.getDebtCapital() + balance.getEquity();
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
			panelIncomeTableLeft.setLayout(new GridLayout(2, 2, 0, 0));
			panelIncomeTableLeft.add(getLabelIncomeTitleAufw());
			panelIncomeTableLeft.add(getLabelIncomeAufw());
			panelIncomeTableLeft.add(getLabelIncomeProfitTitle());
			panelIncomeTableLeft.add(getLabelIncomeProfit());
			panelIncomeTableLeft.setBackground(Look.COLOR_MAP_BACKGROUND);
		}
		return panelIncomeTableLeft;
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
			labelIncomeProfitTitle = new JLabel("");
		}
		return labelIncomeProfitTitle;
	}
	private JLabel getLabelIncomeProfit() {
		if (labelIncomeProfit == null) {
			labelIncomeProfit = new JLabel("");
			labelIncomeProfit.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeProfit;
	}
	private JPanel getPanelIncomeTableRight() {
		if (panelIncomeTableRight == null) {
			panelIncomeTableRight = new JPanel();
			panelIncomeTableRight.setBorder(new EmptyBorder(2, 3, 2, 2));
			panelIncomeTableRight.setLayout(new GridLayout(2, 2, 0, 0));
			panelIncomeTableRight.add(getLabelIncomeTurnoverTitle());
			panelIncomeTableRight.add(getLabelIncomeTurnover());
			panelIncomeTableRight.add(getLabelIncomeLossTitle());
			panelIncomeTableRight.add(getLabelIncomeLoss());
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
			labelIncomeTurnover = new JLabel("0\u20AC");
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
			labelIncomeLoss = new JLabel("0\u20AC");
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
			labelIncomeSumsLeft = new JLabel("0\u20AC");
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
			labelIncomeSumsRight = new JLabel("0\u20AC");
			labelIncomeSumsRight.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelIncomeSumsRight;
	}
}
