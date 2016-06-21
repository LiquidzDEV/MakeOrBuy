package de.liquidz.makeorbuy.tab.project;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import de.liquidz.makeorbuy.Serializer;


public class ProjectDialogWindowListener implements WindowListener {

	private ProjectDialog	projectDialog;

	public ProjectDialogWindowListener(ProjectDialog dialog) {
		this.projectDialog = dialog;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.projectDialog.saveToProject();
		Serializer.saveProject(this.projectDialog.project); 
		this.projectDialog.dispose();
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
