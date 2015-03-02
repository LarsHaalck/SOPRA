package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.LoginDialog;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;

import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerLoginForm implements ButtonListener
{
	LoginDialog dialog;

	public ControllerLoginForm(LoginDialog dialog)
	{
		this.dialog = dialog;
	}
	@Override
	public void buttonPressed(String emitter)
	{
		switch(emitter)
		{
			case "login_form_login":
				if(dialog.getUsername().equals("herbert") && dialog.getPassword().equals("octobus"))
				{
					ControllerManager.informButtonPressed("loginDone");
					dispose();
				}
				else
				{
					dialog.illegalInput();
				}
				break;

			case "login_form_cancel":
				dispose();
				break;
		}
	}

	/**
	 * Close dialog and remove this controller from manager
	 */
	public void dispose()
	{
		dialog.dispose();
		ControllerManager.removeListener(this);
	}
}
