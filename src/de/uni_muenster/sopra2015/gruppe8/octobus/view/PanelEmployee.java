package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerPanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PanelNavigation;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.PaneTabs;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class PanelEmployee extends JPanel
{
	private ControllerPanelEmployee controllerPanelEmployee;
	private PanelNavigation panelNavigation;

	public PanelEmployee()
	{
		super();

		controllerPanelEmployee = new ControllerPanelEmployee(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		panelNavigation = new PanelNavigation("Günni");
		add(panelNavigation);

		PaneTabs paneTabs = new PaneTabs();
		// ...
		// Pretty much same as above
		add(paneTabs);

		setVisible(true);
	}
}
