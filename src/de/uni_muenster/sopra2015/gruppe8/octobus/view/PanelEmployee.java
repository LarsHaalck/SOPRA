package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerEmployeeArea;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.NavigationPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.TabsPane;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class PanelEmployee extends JPanel
{
	private ControllerEmployeeArea controllerEmployeeArea;
	private NavigationPanel navigationPanel;

	public PanelEmployee()
	{
		super();

		controllerEmployeeArea = new ControllerEmployeeArea(this);
		ControllerManager.addListener(controllerEmployeeArea);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		navigationPanel = new NavigationPanel("Günni");
		add(navigationPanel);

		TabsPane tabsPane = new TabsPane();
		// ...
		// Pretty much same as above
		add(tabsPane);

		setVisible(true);
	}
}
