package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

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
				System.out.println("Searching Connection");
				break;
			case PANEL_PASSENGER_SHOW_TICKETS:
				System.out.println("Show tickets");
				break;
			case PANEL_PASSENGER_SHOW_NETWORK:
				System.out.println("Show network");
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
