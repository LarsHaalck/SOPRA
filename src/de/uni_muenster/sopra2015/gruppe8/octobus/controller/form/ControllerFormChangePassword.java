package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormChangePassword;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

/**
 * Controller for the FormChangePassword class.
 */
public class ControllerFormChangePassword extends Controller implements ListenerButton
{
	FormChangePassword dialog;

	public ControllerFormChangePassword(FormChangePassword dialog)
	{
		super();
		this.dialog = dialog;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_CHANGE_PASSWORD_CANCEL:
				dialog.dispose();
                // TODO: Check to see if passwords are identical, finalize, show error message otherwise
				removeListeners();
				break;

			case FORM_CHANGE_PASSWORD_SAVE:
				//TODO: Save
				boolean oldPasswordInvalid = false; // TODO: Check if password is correct
				boolean newPasswordInvalid = (dialog.getNewPassword().length() < 8);
				boolean newPasswordCorrectInvalid = (!dialog.getNewPasswordCorrect().equals(dialog.getNewPassword()));

				dialog.illegalChanges(oldPasswordInvalid, newPasswordInvalid, newPasswordCorrectInvalid);
				if (!oldPasswordInvalid && !newPasswordInvalid && !newPasswordCorrectInvalid)
				{
					//TODO neues Passwort abspeichern
					dialog.dispose();
					removeListeners();
					break;
				}
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
