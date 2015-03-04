package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabEmployee;

/**
 * Controller for TabEmployee
 */
public class ControllerTabEmployee extends Controller implements ListenerButton
{
	TabEmployee tabEmployee;

	public ControllerTabEmployee(TabEmployee tabEmployee)
	{
		this.tabEmployee = tabEmployee;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_EMPLOYEE_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_EMPLOYEE_NEW);
				break;

			case TAB_EMPLOYEE_EDIT:
				System.out.println(tabEmployee.getSelectedID()+"");
				ControllerManager.informWindowOpen(EmitterWindow.FORM_EMPLOYEE_EDIT, tabEmployee.getSelectedID());
				break;

			case TAB_EMPLOYEE_DELETE:
				//TODO: Delete it
				break;
		}
	}
}
