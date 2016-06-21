package de.liquidz.makeorbuy.tab.project;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import de.liquidz.makeorbuy.Serializer;
import de.liquidz.makeorbuy.tab.pool.PoolTableModel;

public class ProjectPanelActionListener implements java.awt.event.ActionListener {

	private JTable	managerTable;
	private ProjectPanel	panel;

	public ProjectPanelActionListener(ProjectPanel panel) {
		this.panel = panel;
		this.managerTable = panel.table;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		PoolTableModel tableModel = ((PoolTableModel)this.managerTable.getModel());
		
		switch(e.getActionCommand()){
			case "add":
				this.panel.addProject("Wie soll das Projekt heiﬂen?");
				break;
			case "edit":
				if(this.managerTable.getSelectedRow() < 0 || this.managerTable.getSelectedColumn() < 0){
					JOptionPane.showMessageDialog(null, "Bitte w‰hle das Projekt aus, das du bearbeiten mˆchtest.");
					return;
				}
				new ProjectDialog(this.panel.main.projects.get((String)this.managerTable.getValueAt(this.managerTable.getSelectedRow(), 0)));
				break;
			case "delete":
				if(this.managerTable.getSelectedRow() < 0){
					JOptionPane.showMessageDialog(null, "Bitte w‰hle die zu entfernende Reihe aus.");
					return;
				}
				if(JOptionPane.showConfirmDialog(null, "Mˆchtest du dieses Projekt wirklich lˆschen?", "Projekt lˆschen...", JOptionPane.YES_NO_OPTION) == 0){
					String projectName = (String)this.managerTable.getValueAt(this.managerTable.getSelectedRow(), 0);
					tableModel.removeRow(this.managerTable.getSelectedRow());
					this.panel.main.projects.remove(projectName);
					Serializer.deleteProject(projectName);
				}
				break;
			default:
				System.out.println(e.getActionCommand());
		}
	}
}
