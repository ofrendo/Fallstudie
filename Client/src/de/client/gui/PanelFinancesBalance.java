package de.client.gui;

import javax.swing.JPanel;

import de.client.company.Balance;

import java.awt.GridLayout;

import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;

public class PanelFinancesBalance extends JPanel {

	public PanelFinancesBalance(Balance balance) {
		setBackground(Look.COLOR_MAP_BACKGROUND);
		setLayout(new GridLayout(2, 1, 0, 0));
		add(getPanelBalance());
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
			labelBalanceTitle = new JLabel("Bilanz zum");
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
			labelAV = new JLabel("0\u20AC");
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
			labelUV = new JLabel("0\u20AC");
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
			labelEK = new JLabel("0\u20AC");
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
			labelFK = new JLabel("0\u20AC");
			labelFK.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelFK;
	}
	private JLabel getLabelSumLeft() {
		if (labelSumLeft == null) {
			labelSumLeft = new JLabel("0\u20AC");
			labelSumLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		}
		return labelSumLeft;
	}
	private JLabel getLabelSumRight() {
		if (labelSumRight == null) {
			labelSumRight = new JLabel("0\u20AC");
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
}
