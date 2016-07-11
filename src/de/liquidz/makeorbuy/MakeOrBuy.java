package de.liquidz.makeorbuy;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import de.liquidz.makeorbuy.tab.info.InfoTab;
import de.liquidz.makeorbuy.tab.pool.PoolTab;
import de.liquidz.makeorbuy.tab.project.Project;
import de.liquidz.makeorbuy.tab.project.ProjectPanel;

//TODO Erstelldatum, Änderungsdatum von Projekten

public class MakeOrBuy extends JFrame {

	private static final long	serialVersionUID	= -1991469493506350640L;

	public static final String	TITLE				= "Make or Buy";
	public static final String	VERSION				= "InDev 0.8.035";

	public Map<String, Project>	projects			= new HashMap<>();

	private ProjectPanel		panelProject;

	public PoolTab				panelStaff;
	public PoolTab				panelMaterials;
	public PoolTab				panelMachines;
	public PoolTab				panelDeliverer;

	public MakeOrBuy() {
		this.setTitle(MakeOrBuy.TITLE);
		this.setResizable(false);
		this.setLocation(300, 300);
		this.setSize(800, 450);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		new File("data/projects").mkdirs();

		this.addWindowListener(new MakeOrBuyWindowListener());
		try {
			this.setIconImage(ImageIO.read(new File("data/logo/mobIcon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.projects = Serializer.loadProjects();

		this.create();

		this.setVisible(true);
	}

	private void create() {
		this.panelProject = new ProjectPanel(this);

		String[][] staffData = { { "Arbeitsgruppe", "Stundenlohn", "Arbeitszeit/Woche", "Arbeitstage/Woche" }, { PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA } };
		PoolTab managerPanel = Serializer.loadManager(Serializer.STAFF_DIR);
		this.panelStaff = managerPanel == null ? new PoolTab(this, staffData) : managerPanel;

		String[][] materialsData = {
				{ "Name", "Bezeichnung", "Preis/VE", "Verpackungseinheit", "Einheit" },
				{ PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.UNITMENU } };
		managerPanel = Serializer.loadManager(Serializer.MATERIAL_DIR);
		this.panelMaterials = managerPanel == null ? new PoolTab(this, materialsData) : managerPanel;

		String[][] machineData = { { "Hersteller", "Bezeichnung", "Kosten/h", "Bedieneranzahl" }, { PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.TEXTAREA } };
		managerPanel = Serializer.loadManager(Serializer.MACHINE_DIR);
		this.panelMachines = managerPanel == null ? new PoolTab(this, machineData) : managerPanel;

		String[][] delivererData = { { "Name", "Lieferzeit in Tagen", "Qualität", "Versandkosten" }, { PoolTab.TEXTAREA, PoolTab.TEXTAREA, PoolTab.QUALITYMENU, PoolTab.TEXTAREA } };
		managerPanel = Serializer.loadManager(Serializer.DELIVERER_DIR);
		this.panelDeliverer = managerPanel == null ? new PoolTab(this, delivererData) : managerPanel;

		panelStaff.addEntry(new String[] { "Elektroniker", "16", "38", "5" });
		panelStaff.addEntry(new String[] { "Industriemechaniker", "18", "38", "5" });
		panelStaff.addEntry(new String[] { "Automatisierer", "12", "38", "5" });
		panelStaff.addEntry(new String[] { "Arbeitsvorbereiter", "15", "40", "3" });

		panelMaterials.addEntry(new String[] { "Schraube", "M12x10", "3", "100", "Stk." });
		panelMaterials.addEntry(new String[] { "Mutter", "M12", "2", "100", "Stk." });
		panelMaterials.addEntry(new String[] { "Stahl", "S235JR+C", "50", "1", "kg" });
		panelMaterials.addEntry(new String[] { "Kabel", "NYM-J 3x1.5mm²", "40", "50", "m" });
		panelMaterials.addEntry(new String[] { "Rundstahl", "11SMNPB30", "25", "1", "m" });

		panelMachines.addEntry(new String[] { "Super", "Bohrmaschine", "10", "1" });
		panelMachines.addEntry(new String[] { "Express", "Fräsmaschine", "30", "1" });

		panelDeliverer.addEntry(new String[] { "SuperFix", "5", "Niedrig", "30" });
		panelDeliverer.addEntry(new String[] { "TipTop", "10", "Sehr hoch", "50" });
		panelDeliverer.addEntry(new String[] { "Normalo", "7", "Normal", "40" });

		this.createLayout();
	}

	private void createLayout() {
		this.getContentPane().setLayout(new BorderLayout());
		// NORTH
		{
			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout(FlowLayout.CENTER));

			JLabel label = new JLabel("");
			ImageIcon icon = new ImageIcon("data/logo/mobAvatar.png");
			label.setIcon(icon);
			panel.add(label);

			panel.add(Box.createHorizontalStrut(30));

			label = new JLabel("");
			icon = new ImageIcon("data/logo/mobLogo.png");
			label.setIcon(icon);
			panel.add(label);

			this.getContentPane().add(panel, BorderLayout.NORTH);
		}
		// CENTER
		{
			JTabbedPane tab = new JTabbedPane();
			tab.addTab("Projekte", this.panelProject);
			tab.addTab("Personal", this.panelStaff);
			tab.addTab("Material", this.panelMaterials);
			tab.addTab("Maschinen", this.panelMachines);
			tab.addTab("Lieferanten", this.panelDeliverer);
			tab.addTab("Impressum", new InfoTab());
			tab.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			this.getContentPane().add(tab, BorderLayout.CENTER);
		}
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MakeOrBuy();
	}

	class MakeOrBuyWindowListener implements WindowListener {

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			Serializer.saveManager(Serializer.STAFF_DIR, panelStaff);
			Serializer.saveManager(Serializer.MATERIAL_DIR, panelMaterials);
			Serializer.saveManager(Serializer.MACHINE_DIR, panelMachines);
			Serializer.saveManager(Serializer.DELIVERER_DIR, panelDeliverer);
			dispose();
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}
	}
}
