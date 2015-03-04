package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.*;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class PaneTabs extends JTabbedPane
{
	public PaneTabs()
	{
		super();

		addTab("Example-Tab", new TabExample());
		addTab("Busnetz", new TabNetwork());
		addTab("Busse", new TabBuses());
		addTab("Mitarbeiter", new TabEmployee());
		addTab("Haltestellen", new TabBusStop());
		addTab("Linien", new TabLine());
		addTab("Fahrkarten", new TabTicket());
		addTab("Arbeitsplan", new TabWorkPlan());
		addTab("Dienstplanung", new TabSchedule());
	}
}
