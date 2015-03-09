package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterDisplay;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import javax.swing.*;

/**
 * Controller for the PanelPassenger class.
 */
public class ControllerPanelPassenger extends Controller implements ListenerButton
{
	private JPanel panel;

	public ControllerPanelPassenger(JPanel panel)
	{
		super();
		this.panel = panel;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case PANEL_PASSENGER_LOGIN:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_LOGIN);
				break;
			case PANEL_PASSENGER_SEARCH_CONNECTION:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_JOURNEY_SEARCH);
				break;
			case PANEL_PASSENGER_SHOW_TICKETS:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_TICKET);
				break;
			case PANEL_PASSENGER_SHOW_NETWORK:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_NETWORK);
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
	}
}
