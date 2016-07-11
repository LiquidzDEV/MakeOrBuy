package de.liquidz.makeorbuy.tab.pool;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import de.liquidz.makeorbuy.MakeOrBuy;

public class PoolTab extends JPanel {

	private static final long		serialVersionUID	= -3063267133378249816L;

	public static final String		TEXTAREA			= "textarea";
	public static final String		QUALITYMENU			= "quality";
	public static final String		SPEEDMENU			= "speed";
	public static final String		UNITMENU			= "unit";

	public static final String[]	QUALITYS			= { "Sehr hoch", "Hoch", "Normal", "Niedrig" };
	public static final String[]	SPEEDS				= { "Sehr schnell", "Schnell", "Normal", "Langsam" };
	public static final String[]	UNITS				= { "Stk.", "kg", "L", "m", "m²", "m³" };

	public MakeOrBuy				main;

	protected String[]				columnTypes;

	private PoolActionListener		poolActionListener;

	protected JTable				table;

	public PoolTab(MakeOrBuy main, String[][] columns) {
		this.main = main;

		this.columnTypes = columns[1];

		this.setLayout(new BorderLayout());

		this.table = new JTable(new PoolTableModel(columns[0]));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		this.add(scrollPane);

		this.poolActionListener = new PoolActionListener(this);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
		this.add(buttonPane, BorderLayout.SOUTH);
		{
			JButton addButton = new JButton("Hinzufügen");
			addButton.setActionCommand("add");
			addButton.addActionListener(poolActionListener);
			buttonPane.add(addButton);

			JButton editButton = new JButton("Bearbeiten");
			editButton.setActionCommand("edit");
			editButton.addActionListener(poolActionListener);
			buttonPane.add(editButton);

			JButton deleteButton = new JButton("Löschen");
			deleteButton.setActionCommand("delete");
			deleteButton.addActionListener(poolActionListener);
			buttonPane.add(deleteButton);
		}
	}

	public void addEntry(String[] data) {
		((PoolTableModel) this.table.getModel()).addRow(data);
	}

	public Object[][] getTableData() {
		int rowCount = table.getModel().getRowCount(), columnCount = table.getModel().getColumnCount();
		Object[][] tableData = new Object[rowCount][columnCount];
		for (int row = 0; row < rowCount; row++)
			for (int column = 0; column < columnCount; column++)
				tableData[row][column] = table.getModel().getValueAt(row, column);
		return tableData;
	}

	public void addEntry() {
		new PoolAddEntryDialog(this);
	}

	public void editEntry(int selectedColumn, int selectedRow, String message) {
		String outPut = null;
		switch (this.columnTypes[selectedColumn]) {
			case PoolTab.QUALITYMENU:
				outPut = (String) JOptionPane.showInputDialog(null, message, "Bearbeiten", JOptionPane.QUESTION_MESSAGE, null, PoolTab.QUALITYS, PoolTab.QUALITYS[0]);
				break;
			case PoolTab.SPEEDMENU:
				outPut = (String) JOptionPane.showInputDialog(null, message, "Bearbeiten", JOptionPane.QUESTION_MESSAGE, null, PoolTab.SPEEDS, PoolTab.SPEEDS[0]);
				break;
			case PoolTab.UNITMENU:
				outPut = (String) JOptionPane.showInputDialog(null, message, "Bearbeiten", JOptionPane.QUESTION_MESSAGE, null, PoolTab.UNITS, PoolTab.UNITS[0]);
				break;
			default:
				outPut = JOptionPane.showInputDialog(message);
		}

		if (outPut == null) return;

		if (outPut.trim().length() > 0) {
			this.table.setValueAt(outPut, selectedRow, selectedColumn);
		} else {
			JOptionPane.showMessageDialog(null, "Sie müssen einen Wert eintragen.");
		}

	}
}
