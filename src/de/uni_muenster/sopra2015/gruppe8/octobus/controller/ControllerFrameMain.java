package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelPassenger;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormChangePassword;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.*;

import java.awt.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFrameMain extends Controller implements ListenerButton, ListenerUserState, ListenerWindow
{
	private FrameMain frame;

	public ControllerFrameMain(FrameMain frame)
	{
		super();
		this.frame = frame;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{

		}
	}

	@Override
	public void userStateChanged(EmitterUserState emitter)
	{
		switch (emitter)
		{
			case LOGGED_IN:
				PanelEmployee newPanelEmployee = new PanelEmployee();
				displayContent(newPanelEmployee);
				break;
			case LOGGED_OUT:
				PanelPassenger newPanelPassenger = new PanelPassenger();
				displayContent(newPanelPassenger);
				break;
		}

	}

	@Override
	public void windowOpen(EmitterWindow emitter)
	{
		switch (emitter)
		{
			case FORM_LOGIN:
				displayForm(EmitterWindow.FORM_LOGIN);
				break;

			case FORM_CHANGE_PASSWORD:
				displayForm(EmitterWindow.FORM_CHANGE_PASSWORD);
		}


	}

	@Override
	public void windowClose(EmitterWindow emitter)
	{

	}



	private void displayForm(EmitterWindow form)
	{
		switch (form)
		{
			case FORM_LOGIN:
				FormLogin d = new FormLogin(frame);
				d.setVisible(true);
				break;

			case FORM_CHANGE_PASSWORD:
				FormChangePassword w = new FormChangePassword(frame);
				w.setVisible(true);
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

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
		ControllerManager.addListener((ListenerUserState)this);
		ControllerManager.addListener((ListenerWindow)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
		ControllerManager.removeListener((ListenerUserState)this);
		ControllerManager.removeListener((ListenerWindow)this);
	}
}
