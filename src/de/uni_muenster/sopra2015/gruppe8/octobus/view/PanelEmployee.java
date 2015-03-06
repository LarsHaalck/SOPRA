package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerPanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PanelNavigation;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PaneTabs;

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

		panelNavigation = new PanelNavigation("Günni");
		add(panelNavigation);

		paneTabs = new PaneTabs();
		// ...
		// Pretty much same as above
		add(paneTabs);

		setVisible(true);
	}

	public void setTabs(boolean ticketPlaner, boolean scheduleManager, boolean hrManager, boolean networkPlaner, boolean busDriver)
	{
		paneTabs.setTabs(ticketPlaner, scheduleManager, hrManager, networkPlaner, busDriver);
	}

	/*
	* TODO: delete before Deployment, only for DEBUGGING-purposes
	 */
	public PaneTabs getPaneTabs()
	{
		return this.paneTabs;
	}
}
