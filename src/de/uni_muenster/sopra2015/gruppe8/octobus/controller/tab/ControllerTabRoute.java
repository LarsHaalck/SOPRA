package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabLine;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabRoute extends Controller implements ListenerButton
{
	private TabLine tabLine;

	public ControllerTabRoute(TabLine tabLine)
	{
		this.tabLine = tabLine;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case TAB_LINE_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_NEW);
				break;

			case TAB_LINE_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_ROUTE_EDIT, tabLine.getSelectedID());
				break;

			case TAB_LINE_DELETE:
				break;
		}
	}
}
