package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterDisplay;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import javax.swing.*;
import java.awt.*;

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
				panel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_CONNECTION);
				panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				break;
			case PANEL_PASSENGER_SHOW_TICKETS:
				panel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_TICKET);
				panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				break;
			case PANEL_PASSENGER_SHOW_NETWORK:
				panel.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_NETWORK);
				panel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}
}
