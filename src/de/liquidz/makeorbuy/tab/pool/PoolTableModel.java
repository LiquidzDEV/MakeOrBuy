package de.liquidz.makeorbuy.tab.pool;

import javax.swing.table.DefaultTableModel;

public class PoolTableModel extends DefaultTableModel {

	private static final long	serialVersionUID	= 3757238362089101799L;
	
	private String[]	columnNames;

	public PoolTableModel(String[] columnNames) {
		this.columnNames = columnNames;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	
	
}
