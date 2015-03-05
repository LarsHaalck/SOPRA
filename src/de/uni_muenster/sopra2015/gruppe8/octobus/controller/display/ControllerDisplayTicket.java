package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayTicket;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerDisplayTicket extends Controller
{
	DisplayTicket panel;

	public ControllerDisplayTicket(DisplayTicket panel)
	{
		this.panel = panel;
	}

	@Override
	protected void addListeners()
	{

	}

	@Override
	protected void removeListeners()
	{

	}
}
