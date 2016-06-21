package de.liquidz.makeorbuy.tab.pool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class PoolActionListener implements ActionListener{

	private PoolTab	managerPanel;

	public PoolActionListener(PoolTab managerPanel) {
		this.managerPanel = managerPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		PoolTableModel tableModel = ((PoolTableModel)this.managerPanel.table.getModel());
		
		switch(e.getActionCommand()){
			case "add":
				this.managerPanel.addEntry();
				break;
			case "edit":
				if(this.managerPanel.table.getSelectedRow() < 0 || this.managerPanel.table.getSelectedColumn() < 0){
					JOptionPane.showMessageDialog(null, "Bitte w�hle die Zelle aus, die du bearbeiten m�chtest.");
					return;
				}
				this.managerPanel.editEntry(this.managerPanel.table.getSelectedColumn(), this.managerPanel.table.getSelectedRow(), "Trage den neuen Wert ein:");		
				break;
			case "delete":
				if(this.managerPanel.table.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Bitte w�hle die zu entfernende Reihe aus.");
					return;
				}
				if(JOptionPane.showConfirmDialog(null, "M�chtest du diesen Eintrag wirklich l�schen?", "Best�tigen", JOptionPane.YES_NO_OPTION) == 0){
					tableModel.removeRow(this.managerPanel.table.getSelectedRow());
				}
				break;
			default:
				System.out.println(e.getActionCommand());
		}
	}
}
