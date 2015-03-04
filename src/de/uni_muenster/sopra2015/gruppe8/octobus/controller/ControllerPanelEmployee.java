package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

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
				ControllerManager.getInstance().informWindowOpen(EmitterWindow.FORM_CHANGE_PASSWORD);
				break;
			case PANEL_EMPLOYEE_LOGOUT:
				ControllerManager.getInstance().informUserStateChanged(EmitterUserState.LOGGED_OUT);
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.getInstance().addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.getInstance().removeListener((ListenerButton) this);
	}
}
