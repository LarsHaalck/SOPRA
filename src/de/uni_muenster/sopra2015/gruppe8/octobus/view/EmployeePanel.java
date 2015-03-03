package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerEmployeeArea;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.NavigationPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.TabsPane;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class EmployeePanel extends JPanel
{
	private ControllerEmployeeArea controllerEmployeeArea;
	private NavigationPanel navigationPanel;

	public EmployeePanel()
	{
		super();

		controllerEmployeeArea = new ControllerEmployeeArea(this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		navigationPanel = new NavigationPanel("Günni");
		navigationPanel.setListener(controllerEmployeeArea);
		add(navigationPanel);

		TabsPane tabsPane = new TabsPane();
		// ...
		// Pretty much same as above
		add(tabsPane);

		setVisible(true);
	}

	public void setListener(ControllerMainFrame controllerMainFrame)
	{
		controllerEmployeeArea.setListener(controllerMainFrame);
	}
}
