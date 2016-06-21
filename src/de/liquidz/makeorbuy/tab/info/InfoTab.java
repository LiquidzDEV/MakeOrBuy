package de.liquidz.makeorbuy.tab.info;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.liquidz.makeorbuy.MakeOrBuy;

/**
 * Enthält den Informationstab vom Hauptmenü.
 * 
 * @author Pascal
 */
public class InfoTab extends JPanel {

	private static final long	serialVersionUID	= -6662885666862424675L;

	public InfoTab() {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JLabel label = new JLabel("<html>Dies ist ein Projekt einiger Studenten<br>vom Berufskolleg am Haspel zum Thema \"Make or Buy\"<br><br>Programmierung:<br><t>Pascal Hobza<br><br>"
				+ "Konzept/Systementwurf:<br>Pascal Hobza<br>René Dahl<br>Andreas Sorgenicht<br>Mehdi Can<br><br><br>Version: " + MakeOrBuy.VERSION
				+ "<br>Geistiges Eigentum der am Projekt beteiligten Personen.</html>");
		this.add(label, BorderLayout.NORTH);
	}
}
