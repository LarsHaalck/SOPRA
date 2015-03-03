package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterUserState;
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
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_LOGIN_LOGIN:
				if (dialog.getUsername().equals("herbert") && dialog.getPassword().equals("octobus"))
				{
					ControllerManager.informUserStateChanged(EmitterUserState.LOGGED_IN);
					dialog.dispose();
				} else
				{
					dialog.illegalInput();
				}
				break;

			case FORM_LOGIN_CANCEL:
				dialog.dispose();
				break;
		}
	}
}
