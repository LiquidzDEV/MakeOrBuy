package de.liquidz.makeorbuy.tab.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import de.liquidz.makeorbuy.MakeOrBuy;
import de.liquidz.makeorbuy.NumberTextField;

public class ProjectDialog extends JDialog {

	private static final long	serialVersionUID	= -1779603471277502980L;
	private MakeOrBuy			main;
	protected Project			project;

	protected NumberTextField	txtQuantity;

	private JLabel				lblPrice;
	private JLabel				lblTime;
	private JLabel				lblDeliverer;

	protected JTable			tblStaff;
	protected JTable			tblMachine;
	protected JTable			tblMaterial;
	protected JComboBox<String>	cBoxDeliverer;

	protected JTextField		txtBuyPrice;
	protected JTextField		txtBuyShipping;
	protected JTextField		txtBuyTime;
	private JLabel				lblBuyPrice;
	private JLabel				lblBuyTime;

	public ProjectDialog(Project project) {
		this.project = project;
		this.setTitle(MakeOrBuy.TITLE + " - " + project.title + " von " + project.creator);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new ProjectDialogWindowListener(this));
		this.setModal(true);
		this.setResizable(false);
		this.setSize(500, 600);
		this.setLocationRelativeTo(main);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Gewünschte Menge
		JPanel subPanel = new JPanel();
		subPanel.add(new JLabel("Gewünschte Menge:"));
		this.txtQuantity = new NumberTextField(project.quantity, 15);
		this.txtQuantity.addFocusListener(new ComponentFocusListener());
		subPanel.add(this.txtQuantity);
		subPanel.add(new JLabel("Stück"));
		subPanel.setMaximumSize(new Dimension(500, 45));
		panel.add(subPanel);

		// Trennstrich
		panel.add(new JSeparator());

		// 5 Pixel abstand
		panel.add(Box.createVerticalStrut(5));

		// Personal verwalten
		subPanel = new JPanel(new BorderLayout());
		subPanel.add(new JLabel("Personal verwalten"), BorderLayout.NORTH);

		this.tblStaff = new JTable(new ProjectTableModel(new String[] { "Arbeitsgruppe", "Anzahl" }, 1));
		for (int i = 0; i < project.personal.length; i++) {
			((ProjectTableModel) this.tblStaff.getModel()).addRow(new Object[] { project.personal[i][0], project.personalQuantity[i] });
		}
		this.tblStaff.addFocusListener(new ComponentFocusListener());
		JScrollPane scrollPane = new JScrollPane(this.tblStaff);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		subPanel.add(scrollPane, BorderLayout.CENTER);
		panel.add(subPanel);

		// 5 Pixel abstand
		panel.add(Box.createVerticalStrut(5));

		// Maschinen verwalten
		subPanel = new JPanel(new BorderLayout());
		subPanel.add(new JLabel("Maschinen verwalten"), BorderLayout.NORTH);

		this.tblMachine = new JTable(new ProjectTableModel(new String[] { "Hersteller", "Bezeichnung", "Anzahl" }, 2));
		for (int i = 0; i < project.machine.length; i++) {
			((ProjectTableModel) this.tblMachine.getModel()).addRow(new Object[] { project.machine[i][0], project.machine[i][1], project.machineQuantity[i] });
		}
		this.tblMachine.addFocusListener(new ComponentFocusListener());
		scrollPane = new JScrollPane(this.tblMachine);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		subPanel.add(scrollPane, BorderLayout.CENTER);
		panel.add(subPanel);

		// 5 Pixel abstand
		panel.add(Box.createVerticalStrut(5));

		// Material verwalten
		subPanel = new JPanel(new BorderLayout());
		subPanel.add(new JLabel("Material pro Stück auswählen:"), BorderLayout.NORTH);

		this.tblMaterial = new JTable(new ProjectTableModel(new String[] { "Name", "Bezeichnung", "Menge", "Einheit" }, 2));
		for (int i = 0; i < project.material.length; i++) {
			((ProjectTableModel) this.tblMaterial.getModel()).addRow(new Object[] { project.material[i][0], project.material[i][1], project.materialQuantity[i], project.material[i][4] });
		}
		this.tblMaterial.addFocusListener(new ComponentFocusListener());
		scrollPane = new JScrollPane(this.tblMaterial);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		subPanel.add(scrollPane, BorderLayout.CENTER);
		panel.add(subPanel);

		// Lieferant auswählen
		subPanel = new JPanel();
		subPanel.add(new JLabel("Lieferant:"));
		String[] data = new String[project.lieferanten.length];
		for (int i = 0; i < project.lieferanten.length; i++) {
			data[i] = (String) project.lieferanten[i][0];
		}
		this.cBoxDeliverer = new JComboBox<String>(data);
		this.cBoxDeliverer.setSelectedIndex(this.project.choosenDeliverer);
		cBoxDeliverer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row = ((JComboBox<String>) e.getSource()).getSelectedIndex();
				lblDeliverer.setText("Qualität: " + project.lieferanten[row][2] + " Lieferzeit: " + project.lieferanten[row][1] + " Tage");
				calculateResult(true);
			}
		});
		subPanel.add(cBoxDeliverer);
		this.lblDeliverer = new JLabel("Qualität: " + project.lieferanten[this.project.choosenDeliverer][2] + " Lieferzeit: " + project.lieferanten[this.project.choosenDeliverer][1] + " Tage");
		subPanel.add(lblDeliverer);
		panel.add(subPanel);

		// Trennstrich
		panel.add(new JSeparator());

		// Einkaufen
		subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
		subPanel.setBorder(BorderFactory.createTitledBorder("Buy-Kriterien"));

		JPanel anotherPanel = new JPanel();
		anotherPanel.add(new JLabel("Kaufpreis das Stück:"));
		this.txtBuyPrice = new JTextField(String.valueOf(project.buyPrice), 15);
		this.txtBuyPrice.addFocusListener(new ComponentFocusListener());
		anotherPanel.add(this.txtBuyPrice);
		anotherPanel.add(new JLabel("Euro"));
		subPanel.add(anotherPanel);

		anotherPanel = new JPanel();
		anotherPanel.add(new JLabel("Versandkosten:"));
		this.txtBuyShipping = new JTextField(String.valueOf(project.buyShipping), 15);
		this.txtBuyShipping.addFocusListener(new ComponentFocusListener());
		anotherPanel.add(this.txtBuyShipping);
		anotherPanel.add(new JLabel("Euro"));
		subPanel.add(anotherPanel);

		anotherPanel = new JPanel();
		anotherPanel.add(new JLabel("Dauer:"));
		this.txtBuyTime = new JTextField(String.valueOf(project.buyTime), 15);
		this.txtBuyTime.addFocusListener(new ComponentFocusListener());
		anotherPanel.add(this.txtBuyTime);
		anotherPanel.add(new JLabel("Tage"));
		subPanel.add(anotherPanel);

		// subPanel.setMaximumSize(new Dimension(500, 30));
		panel.add(subPanel);

		// Trennstrich
		panel.add(new JSeparator());

		// 5 Pixel abstand
		panel.add(Box.createVerticalStrut(5));
		
		// Ergebniss
		subPanel = new JPanel();
		subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));

		anotherPanel = new JPanel(new BorderLayout());
		this.lblPrice = new JLabel("Fertigungspreis: ");
		anotherPanel.add(lblPrice, BorderLayout.WEST);
		this.lblBuyPrice = new JLabel("Kaufpreis: ");
		anotherPanel.add(lblBuyPrice, BorderLayout.EAST);
		subPanel.add(anotherPanel);

		anotherPanel = new JPanel(new BorderLayout());
		this.lblTime = new JLabel("Fertigungszeit: ");
		anotherPanel.add(this.lblTime, BorderLayout.WEST);
		this.lblBuyTime = new JLabel("Dauer: ");
		anotherPanel.add(this.lblBuyTime, BorderLayout.EAST);
		subPanel.add(anotherPanel);

		panel.add(subPanel);

		this.add(panel);

		this.calculateResult(false);

		this.setVisible(true);
	}

	protected void calculateResult(boolean saveFirst) {
		if (saveFirst) this.saveToProject();
		boolean compare[] = new boolean[4];

		float personalKosten = 0.0f;
		float arbeitsZeit = 0.0f;
		for (int i = 0; i < this.project.personal.length; i++) {
			System.out.println(this.project.personalQuantity[i]);
			float tempArbeitsZeit = (Float.valueOf(String.valueOf(this.project.personal[i][2])) / 5.0f) * Float.valueOf(String.valueOf(this.project.personal[i][3]));
			if (this.project.personalQuantity[i] > 0) arbeitsZeit += tempArbeitsZeit / this.project.personalQuantity[i];
			personalKosten += Float.valueOf(String.valueOf(this.project.personal[i][1])) * tempArbeitsZeit * this.project.personalQuantity[i];
		}

		int maschinenBedienerAnzahl = 0;
		for (int i = 0; i < this.project.machineQuantity.length; i++) {
			maschinenBedienerAnzahl += this.project.machineQuantity[i] * Float.valueOf(String.valueOf(this.project.machine[i][3]));
		}

		float zeitAnMaschine = arbeitsZeit / maschinenBedienerAnzahl;

		float maschinenKosten = 0.0f;
		for (int i = 0; i < this.project.machine.length; i++) {
			maschinenKosten += zeitAnMaschine * Float.valueOf(String.valueOf(this.project.machine[i][2]));
		}

		float materialKosten = 0.0f;
		for (int i = 0; i < this.project.material.length; i++) {

			materialKosten += Float.valueOf(String.valueOf(this.project.material[i][2])) * this.project.materialQuantity[i] * this.project.quantity
					* this.getQuality(String.valueOf(project.lieferanten[project.choosenDeliverer][2]));
		}

		int lieferZeit = Integer.valueOf(String.valueOf(this.project.lieferanten[project.choosenDeliverer][1]));

		int versandKosten = Integer.valueOf(String.valueOf(this.project.lieferanten[project.choosenDeliverer][3]));

		float kaufPreis = this.project.buyPrice * this.project.quantity + (float) this.project.buyShipping;
		if (this.project.buyPrice > 0 && this.project.buyShipping > 0 && this.project.quantity > 0) {
			this.lblBuyPrice.setText("Kaufpreis: " + kaufPreis + " Euro");
			compare[1] = true;
		} else {
			this.lblBuyPrice.setText("Kaufpreis: N/A");
		}

		if (this.project.buyTime > 0) {
			this.lblBuyTime.setText("Dauer: " + this.project.buyTime + " Tage");
			compare[3] = true;
		} else {
			this.lblBuyTime.setText("Dauer: N/A");
		}

		if (lieferZeit > 0 && arbeitsZeit > 0) {
			this.lblTime.setText("Fertigungsdauer: " + (lieferZeit + arbeitsZeit) + " Tage");
			compare[2] = true;
		} else {
			this.lblTime.setText("Fertigungsdauer: N/A");
		}

		System.out.println("Personal: " + personalKosten + " Maschinen: " + maschinenKosten + " Material: " + materialKosten + " Versand: " + versandKosten);

		float gesamtKosten = personalKosten + maschinenKosten + materialKosten + versandKosten;
		if (personalKosten > 0 && maschinenKosten > 0 && materialKosten > 0 && versandKosten > 0) {
			this.lblPrice.setText("Fertigungskosten: " + String.format("%.2f",gesamtKosten) + " Euro (" + String.format("%.2f",(gesamtKosten / this.project.quantity)) + " Euro pro Stück)");
			compare[0] = true;
		} else {
			this.lblPrice.setText("Fertigungskosten: N/A");
		}

		if (compare[0] && compare[1]) {
			this.lblBuyPrice.setForeground(kaufPreis <= gesamtKosten ? Color.GREEN : Color.RED);
			this.lblPrice.setForeground(kaufPreis > gesamtKosten ? Color.GREEN : Color.RED);
		}

		if (compare[2] && compare[3]) {
			this.lblBuyTime.setForeground(this.project.buyTime <= (lieferZeit + arbeitsZeit) ? Color.GREEN : Color.RED);
			this.lblTime.setForeground(this.project.buyTime > (lieferZeit + arbeitsZeit) ? Color.GREEN : Color.RED);
		}
	}

	public void saveToProject() {
		this.project.quantity = Integer.parseInt(this.txtQuantity.getText());

		for (int i = 0; i < this.project.personalQuantity.length; i++) {
			this.project.personalQuantity[i] = Integer.parseInt(String.valueOf(this.tblStaff.getValueAt(i, 1)));
		}

		for (int i = 0; i < this.project.machineQuantity.length; i++) {
			this.project.machineQuantity[i] = Integer.parseInt(String.valueOf(this.tblMachine.getValueAt(i, 2)));
		}

		for (int i = 0; i < this.project.materialQuantity.length; i++) {
			this.project.materialQuantity[i] = Float.parseFloat(String.valueOf(this.tblMaterial.getValueAt(i, 2)));
		}

		this.project.choosenDeliverer = this.cBoxDeliverer.getSelectedIndex();

		this.project.buyPrice = Float.parseFloat(this.txtBuyPrice.getText());
		this.project.buyShipping = Float.parseFloat(this.txtBuyShipping.getText());
		this.project.buyTime = Float.parseFloat(this.txtBuyTime.getText());
	}

	public float getQuality(String quality) {
		switch (quality) {
			case "Sehr hoch":
				return 1.4f;
			case "Hoch":
				return 1.2f;
			case "Normal":
				return 1.0f;
			case "Niedrig":
				return 0.8f;
			default:
				return 1.0f;
		}
	}

	private class ComponentFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {

		}

		@Override
		public void focusLost(FocusEvent e) {
			calculateResult(true);
		}

	}
}
