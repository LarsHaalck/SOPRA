package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

/**
 * Controller for the FormLogin class.
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
					ControllerManager.getInstance().informUserStateChanged(EmitterUserState.LOGGED_IN);
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
		ControllerManager.getInstance().addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.getInstance().removeListener((ListenerButton)this);
	}
}
