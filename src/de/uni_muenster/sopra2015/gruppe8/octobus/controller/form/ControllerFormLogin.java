package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormLogin extends Controller implements ListenerButton
{
	FormLogin dialog;

	public ControllerFormLogin(FormLogin dialog)
	{
		super();
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
					removeListeners();
					dialog.dispose();
				} else
				{
					dialog.illegalInput();
				}
				break;

			case FORM_LOGIN_CANCEL:
				removeListeners();
				dialog.dispose();
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}
}
