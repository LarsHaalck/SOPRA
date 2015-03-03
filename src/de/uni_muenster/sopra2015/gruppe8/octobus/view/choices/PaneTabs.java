package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class PaneTabs extends JTabbedPane
{
	public PaneTabs()
	{
		super();

		addTab("Busnetz", new JPanel());
		addTab("Busse", new JPanel());
		addTab("Benutzer", new JPanel());
		addTab("Haltestellen", new JPanel());
		addTab("Linien", new JPanel());
		addTab("Fahrkarten", new JPanel());
		addTab("Arbeitsplan", new JPanel());
		addTab("Dienstplanung", new JPanel());
	}
}
