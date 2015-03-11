package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.*;

import javax.swing.*;

/**
 * Contains the tabs available to the user.
 * Only displayed to logged in users.
 */
public class PaneTabs extends JTabbedPane
{

	public PaneTabs()
	{
		super();
	}

	/**
	 * Adds tabs to PaneTabs.
	 * @param ticketPlanner	true if user is ticket planner.
	 * @param scheduleManager true if user is schedule manager.
	 * @param hrManager true if user is hr manager.
	 * @param networkPlanner true if user is network planner.
	 * @param busDriver true if user is bus driver.
	 * @param userId user id.
	 */
	public void setTabs(boolean ticketPlanner, boolean scheduleManager, boolean hrManager, boolean networkPlanner, boolean busDriver, int userId)
	{
		removeAll();

		addTab("Busnetz", new TabNetwork());

		if(scheduleManager)
		{
			addTab("Busse", new TabBus());
			addTab("Dienstplanung", new TabSchedule());
		}

		if(hrManager)
			addTab("Mitarbeiter", new TabEmployee(userId));

		if(networkPlanner)
		{
			addTab("Haltestellen", new TabBusStop());
			addTab("Linien", new TabRoute());
		}

		if(ticketPlanner)
			addTab("Fahrkarten", new TabTicket());

		if(busDriver)
			addTab("Arbeitsplan", new TabWorkPlan(userId));

		revalidate();
		repaint();
	}


}
