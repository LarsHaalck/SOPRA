package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabTicket;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabTicket extends Controller implements ListenerButton
{
	private TabTicket tabTicket;

	public ControllerTabTicket(TabTicket tabTicket)
	{
		this.tabTicket = tabTicket;
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
		switch(btn)
		{
			case TAB_TICKET_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TICKET_NEW);
				break;

			case TAB_TICKET_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TICKET_EDIT, tabTicket.getSelectedID());
				break;

			case TAB_TICKET_DELETE:
				break;
		}
	}
}
