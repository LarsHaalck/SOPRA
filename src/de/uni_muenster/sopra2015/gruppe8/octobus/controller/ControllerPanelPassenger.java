package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

import javax.swing.*;

/**
 * Created by Joshua on 02.03.2015.
 */
public class ControllerPanelPassenger implements ListenerButton
{
	private JPanel panel;

	public ControllerPanelPassenger(JPanel panel)
	{
		ControllerManager.addListener(this);
		this.panel = panel;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case PANEL_PASSENGER_LOGIN:
				ControllerManager.informWindowOpen(EmitterWindow.OPEN_LOGIN_FORM);
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
}
