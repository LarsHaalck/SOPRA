package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormChangePassword;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import jdk.nashorn.internal.codegen.Emitter;

import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
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
				//TODO: Pruefe ob passwoerter identisch, wenn identisch, finalize, sonst fehler anzeigen
				removeListeners();
				break;

			case FORM_CHANGE_PASSWORD_SAVE:
				boolean oldPasswordInvalid = false; // TODO prüfe ob Passwort korrekt
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
