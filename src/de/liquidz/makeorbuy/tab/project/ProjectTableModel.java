package de.liquidz.makeorbuy.tab.project;

import javax.swing.table.DefaultTableModel;

public class ProjectTableModel extends DefaultTableModel {

	private static final long	serialVersionUID	= 3757238362089101799L;
	
	private String[]	columnNames;
	private int editibleColumn;

	public ProjectTableModel(String[] columnNames, int editibleColumn) {
		this.columnNames = columnNames;		
		this.editibleColumn = editibleColumn;
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
		return column == this.editibleColumn;
	}
	
}
