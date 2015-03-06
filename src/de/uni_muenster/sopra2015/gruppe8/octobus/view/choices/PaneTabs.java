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
	}

	public void setTabs(boolean ticketPlaner, boolean scheduleManager, boolean hrManager, boolean networkPlaner, boolean busDriver)
	{
		removeAll();

		addTab("Busnetz", new TabNetwork());

		if(ticketPlaner)
			addTab("Fahrkarten", new TabTicket());

		if(scheduleManager)
		{
			addTab("Dienstplanung", new TabSchedule());
			addTab("Busse", new TabBus());
		}

		if(hrManager)
			addTab("Mitarbeiter", new TabEmployee());

		if(networkPlaner)
		{
			addTab("Haltestellen", new TabBusStop());
			addTab("Linien", new TabRoute());
		}

		if(busDriver)
			addTab("Arbeitsplan", new TabWorkPlan());

		revalidate();
		repaint();
	}
}
