package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBus;

import java.util.Date;

/**
 * Controller for TabBus-class
 */
public class ControllerTabBus extends Controller implements ListenerButton
{
	private TabBus tabBus;

	public ControllerTabBus(TabBus tabBus)
	{
		super();
		this.tabBus = tabBus;
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_BUS_DELETE:
				break;

			case TAB_BUS_EDIT:
				//GetBusByID
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_EDIT, 1);
				break;

			case TAB_BUS_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_BUS_NEW);
				break;
		}
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
}
