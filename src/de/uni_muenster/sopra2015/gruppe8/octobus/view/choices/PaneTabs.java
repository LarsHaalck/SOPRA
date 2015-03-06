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

		//addTab("Example-Tab", new TabExample());
		addTab("Busnetz", new TabNetwork());
		addTab("Busse", new TabBus());
		addTab("Mitarbeiter", new TabEmployee());
		addTab("Haltestellen", new TabBusStop());
		addTab("Linien", new TabRoute());
		addTab("Fahrkarten", new TabTicket());
		addTab("Arbeitsplan", new TabWorkPlan());
		addTab("Dienstplanung", new TabSchedule());
	}
}
