package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelPassanger;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

import java.awt.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerMainFrame implements ListenerButton
{
	private FrameMain frame;

	public ControllerMainFrame(FrameMain frame)
	{
		ControllerManager.addListener(this);
		this.frame = frame;
	}

	@Override
	public void buttonPressed(String emitter)
	{
		switch (emitter)
		{
			case "login_request":
				displayForm("login");
				break;
			case "login_done":
				PanelEmployee newPanelEmployee = new PanelEmployee();
				displayContent(newPanelEmployee);
				break;
			case "logout_done":
				PanelPassanger newPanelPassanger = new PanelPassanger();
				displayContent(newPanelPassanger);
				break;
		}
	}

	public void displayForm(String emitter)
	{
		switch (emitter)
		{
			case "login":
				FormLogin d = new FormLogin(frame);
				d.setVisible(true);
				break;
		}
	}

	// TODO: Considering this might be used in more than one place,
	// TODO: putting it in a separate helper class might be appropriate.
	// Adapted from http://stackoverflow.com/a/5077773/2010258 and http://stackoverflow.com/a/11073097/2010258
	public void displayContent(Container container)
	{
		Container cp = frame.getContentPane();
		cp.removeAll();
		frame.setContentPane(container);
		frame.setVisible(true);
	}
}
