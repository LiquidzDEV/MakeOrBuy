package de.liquidz.makeorbuy.tab.project;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ProjectTable extends JScrollPane {

	private static final long	serialVersionUID	= 1720816122456636006L;
	
	public ProjectTable(Object[][] data, String[] columnNames){
		this.setMaximumSize(new Dimension(500, 200));
		this.setSize(500, 200);
		
		JTable table = new JTable(data, columnNames);
		this.add(table);
	}

}
