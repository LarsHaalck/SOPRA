package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerPanelEmployee implements ListenerButton
{
	private JPanel panel;

	public ControllerPanelEmployee(JPanel panel)
	{
		this.panel = panel;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case PANEL_EMPLOYEE_CHANGE_PASSWORD:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_CHANGE_PASSWORD);
				break;
			case PANEL_EMPLOYEE_LOGOUT:
				ControllerManager.informUserStateChanged(EmitterUserState.LOGGED_OUT);
				break;
		}
	}
}
