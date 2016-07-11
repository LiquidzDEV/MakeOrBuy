package de.liquidz.makeorbuy.tab.project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import de.liquidz.makeorbuy.MakeOrBuy;
import de.liquidz.makeorbuy.tab.pool.PoolTableModel;

public class ProjectPanel extends JPanel {

	private static final long		serialVersionUID	= 802420736700699108L;

	public MakeOrBuy				main;

	private ProjectPanelActionListener	panelActionListener;

	protected JTable				table;
	protected JTextField			fldUser;

	public ProjectPanel(MakeOrBuy main) {
		this.main = main;

		this.setLayout(new BorderLayout());

		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		this.table = new JTable(new PoolTableModel(new String[] { "Projekt", "Ersteller" }));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);

		for (Map.Entry<String, Project> entry : this.main.projects.entrySet()) {
			((PoolTableModel)table.getModel()).addRow(new String[]{entry.getKey(), entry.getValue().creator});
		}

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(300, 400));
		table.setFillsViewportHeight(true);

		this.panelActionListener = new ProjectPanelActionListener(this);

		JPanel westPanel = new JPanel(new BorderLayout());
		{
			JLabel label = new JLabel("<html>Hier kannst du deine vorherigen Projekte einsehen<br>oder ein neues erstellen.</html>");
			westPanel.add(label, BorderLayout.NORTH);

			JPanel creatorPanel = new JPanel(new FlowLayout());
			{
				label = new JLabel("Benutzername:");
				creatorPanel.add(label);
				this.fldUser = new JTextField("Pascal Hobza", 15);
				creatorPanel.add(this.fldUser);
			}
			westPanel.add(creatorPanel, BorderLayout.WEST);

			JPanel buttonPane = new JPanel();
			{
				JButton addButton = new JButton("Hinzufügen");
				addButton.setActionCommand("add");
				addButton.addActionListener(panelActionListener);
				buttonPane.add(addButton);

				JButton editButton = new JButton("Bearbeiten");
				editButton.setActionCommand("edit");
				editButton.addActionListener(panelActionListener);
				buttonPane.add(editButton);

				JButton deleteButton = new JButton("Löschen");
				deleteButton.setActionCommand("delete");
				deleteButton.addActionListener(panelActionListener);
				buttonPane.add(deleteButton);
			}
			westPanel.add(buttonPane, BorderLayout.SOUTH);
		}
		this.add(westPanel, BorderLayout.WEST);

		this.add(scrollPane, BorderLayout.EAST);
	}

	public void addProject(String message) {
		if (this.fldUser.getText().trim().length() < 3) {
			JOptionPane.showMessageDialog(null, "Trage vorher einen Benutzernamen ein.");
			return;
		}

		if (this.main.panelStaff.getTableData().length < 1) {
			JOptionPane.showMessageDialog(null, "Sie haben noch kein Personal im Pool definiert.");
			return;
		}

		if (this.main.panelMachines.getTableData().length < 1) {
			JOptionPane.showMessageDialog(null, "Sie haben noch keine Maschinen im Pool definiert.");
			return;
		}

		if (this.main.panelMaterials.getTableData().length < 1) {
			JOptionPane.showMessageDialog(null, "Sie haben noch keine Materialien im Pool definiert.");
			return;
		}

		if (this.main.panelDeliverer.getTableData().length < 1) {
			JOptionPane.showMessageDialog(null, "Sie haben noch keine Lieferanten im Pool definiert.");
			return;
		}

		String outPut = JOptionPane.showInputDialog(message);

		if (outPut == null) return;

		if (outPut.trim().length() > 2) {
			((DefaultTableModel) this.table.getModel()).addRow(new String[] { outPut, this.fldUser.getText() });
			this.main.projects.put(outPut, new Project(this.main, outPut, this.fldUser.getText()));

			new ProjectDialog(this.main.projects.get(outPut));
		} else {
			this.addProject("Wie soll das Projekt heißen?\nDer Name muss min. 3 Zeichen enthalten.");
		}

	}
}
