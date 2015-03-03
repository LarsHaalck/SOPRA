package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

import javax.swing.*;

/**
 * Created by Joshua on 02.03.2015.
 */
public class ControllerPanelPassanger implements ListenerButton
{
	private JPanel panel;

	public ControllerPanelPassanger(JPanel panel)
	{
		ControllerManager.addListener(this);
		this.panel = panel;
	}

	@Override
	public void buttonPressed(String emitter)
	{
		switch (emitter)
		{
			case "loginRequest":
				ControllerManager.informButtonPressed("login_request");
				break;
			case "searchConnectionRequest":
				System.out.println("Searching Connection");
				break;
			case "showTicketsRequest":
				System.out.println("Show tickets");
				break;
			case "showNetworkRequest":
				System.out.println("Show network");
				break;
		}
	}
}
