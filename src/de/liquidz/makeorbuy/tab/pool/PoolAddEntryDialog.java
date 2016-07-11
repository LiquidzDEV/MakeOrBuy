package de.liquidz.makeorbuy.tab.pool;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PoolAddEntryDialog extends JDialog {

	private static final long	serialVersionUID	= -15342039217848619L;
	private PoolTab				poolPanel;

	private JComponent[]		components;

	public PoolAddEntryDialog(PoolTab poolPanel) {
		this.poolPanel = poolPanel;

		String[] columnTypes = poolPanel.columnTypes;
		this.components = new JComponent[columnTypes.length];

		this.setTitle("Eintrag hinzufügen");
		this.setModal(true);
		this.setResizable(false);
		this.setLocationRelativeTo(poolPanel.main);
		this.setSize(200, 100 + this.poolPanel.columnTypes.length * 50);

		this.setLayout(new BorderLayout());
		{
			JPanel northPanel = new JPanel();
			northPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				northPanel.add(new JLabel("Trage deine Werte in die Felder ein:"), BorderLayout.NORTH);
			}
			this.add(northPanel, BorderLayout.NORTH);

			JPanel centerPanel = new JPanel();
			centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
			centerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			{
				for (int i = 0; i < columnTypes.length; i++) {
					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
					panel.add(Box.createVerticalStrut(5));

					JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
					labelPanel.add(new JLabel(this.poolPanel.table.getModel().getColumnName(i)));
					panel.add(labelPanel);

					if (poolPanel.columnTypes[i].equals(PoolTab.TEXTAREA)) {
						this.components[i] = new JTextField("", 15);
					} else if (poolPanel.columnTypes[i].equals(PoolTab.QUALITYMENU)) {
						this.components[i] = new JComboBox<String>(PoolTab.QUALITYS);
					} else if (poolPanel.columnTypes[i].equals(PoolTab.SPEEDMENU)) {
						this.components[i] = new JComboBox<String>(PoolTab.SPEEDS);
					} else if (poolPanel.columnTypes[i].equals(PoolTab.UNITMENU)) {
						this.components[i] = new JComboBox<String>(PoolTab.UNITS);
					}

					panel.add(this.components[i]);
					centerPanel.add(panel);
				}
			}
			this.add(centerPanel, BorderLayout.CENTER);

			JPanel southPanel = new JPanel(new FlowLayout());
			{
				JButton button = new JButton("Hinzufügen");
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						Object[] data = new Object[components.length];
						for (int i = 0; i < components.length; i++) {
							if (poolPanel.columnTypes[i].equals(PoolTab.TEXTAREA)) {
								String text = ((JTextField) components[i]).getText();
								if (text.trim().length() < 1) {
									JOptionPane.showMessageDialog(null, "Sie haben bei " + poolPanel.table.getModel().getColumnName(i) + " noch keinen Wert eingetragen.");
									return;
								}
								data[i] = text;
							} else {
								data[i] = ((JComboBox<String>) components[i]).getSelectedItem();
							}
						}
						((PoolTableModel) poolPanel.table.getModel()).addRow(data);
						dispose();
					}
				});
				southPanel.add(button);

				button = new JButton("Abbrechen");
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				southPanel.add(button);
			}
			this.add(southPanel, BorderLayout.SOUTH);
		}
		this.setVisible(true);
	}
}
