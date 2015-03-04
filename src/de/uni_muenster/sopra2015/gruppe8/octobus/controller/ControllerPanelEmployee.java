package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import javax.swing.*;

/**
 * Controller for the PanelEmployee class.
 */
public class ControllerPanelEmployee extends Controller implements ListenerButton
{
	private JPanel panel;

	public ControllerPanelEmployee(JPanel panel)
	{
		super();
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
