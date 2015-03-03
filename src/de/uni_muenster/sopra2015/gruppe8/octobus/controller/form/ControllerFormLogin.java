package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormLogin implements ListenerButton
{
	FormLogin dialog;

	public ControllerFormLogin(FormLogin dialog)
	{
		this.dialog = dialog;
	}

	@Override
	public void buttonPressed(String emitter)
	{
		switch (emitter)
		{
			case "login_button_pressed":
				if (dialog.getUsername().equals("herbert") && dialog.getPassword().equals("octobus"))
				{
					ControllerManager.informButtonPressed("login_done");
					dialog.dispose();
				} else
				{
					dialog.illegalInput();
				}
				break;

			case "cancel_button_pressed":
				dialog.dispose();
				break;
		}
	}
}
