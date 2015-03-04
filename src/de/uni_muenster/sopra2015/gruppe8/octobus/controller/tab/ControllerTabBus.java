package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabBus;

import java.util.Date;

/**
 *
 */
public class ControllerTabBus implements ListenerButton
{
	private TabBus tabBus;

	public ControllerTabBus(TabBus tabBus)
	{
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
				Bus bus = new Bus("Test", 12, 12, "gtest", "test", new Date(), false);
				ControllerManager.getInstance().informWindowOpen(EmitterWindow.FORM_BUS_EDIT, 1);
				break;

			case TAB_BUS_NEW:

				break;
		}
	}
}
