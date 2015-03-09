package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerPanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PanelNavigation;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PaneTabs;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayTicket;

import javax.swing.*;

/**
 * Displays the content available to logged in users, consists of PanelNavigation and PaneTabs.
 */
public class PanelEmployee extends JPanel
{
	private ControllerPanelEmployee controllerPanelEmployee;
	private PanelNavigation panelNavigation;
	private PaneTabs paneTabs;

	public PanelEmployee()
	{
		super();

		controllerPanelEmployee = new ControllerPanelEmployee(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panelNavigation = new PanelNavigation("");
		add(panelNavigation);

		paneTabs = new PaneTabs();
		add(paneTabs);

		setVisible(true);
	}

	public void setTabs(boolean ticketPlaner, boolean scheduleManager, boolean hrManager, boolean networkPlaner, boolean busDriver, int userId)
	{
		paneTabs.setTabs(ticketPlaner, scheduleManager, hrManager, networkPlaner, busDriver, userId);
	}

	public void setUsername(String username)
	{
		panelNavigation.setWelcomeText("Willkommen, "+username+"!");
	}

	/*
	 * TODO: delete before Deployment, only for DEBUGGING-purposes
	 */
	public PaneTabs getPaneTabs()
	{
		return this.paneTabs;
	}
}
