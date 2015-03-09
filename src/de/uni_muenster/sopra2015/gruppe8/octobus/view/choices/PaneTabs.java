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

	public void setTabs(boolean ticketPlaner, boolean scheduleManager, boolean hrManager, boolean networkPlaner, boolean busDriver, int userId)
	{
		removeAll();

		addTab("Busnetz", new TabNetwork());

		if(scheduleManager)
		{
			addTab("Busse", new TabBus());
			addTab("Dienstplanung", new TabSchedule());
		}

		if(hrManager)
			addTab("Mitarbeiter", new TabEmployee());

		if(networkPlaner)
		{
			addTab("Haltestellen", new TabBusStop());
			addTab("Linien", new TabRoute());
		}

		if(ticketPlaner)
			addTab("Fahrkarten", new TabTicket());

		if(busDriver)
			addTab("Arbeitsplan", new TabWorkPlan(userId));

		revalidate();
		repaint();
	}


}
