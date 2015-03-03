package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;
import jdk.nashorn.internal.codegen.Emitter;

import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormChangePassword implements ListenerButton
{
	JDialog dialog;

	public ControllerFormChangePassword(JDialog dialog)
	{
		this.dialog = dialog;
		ControllerManager.addListener((ListenerButton)this);
	}

	public void finalize()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_CHANGE_PASSWORD_CANCEL:
				dialog.dispose();
				//TODO: Pruefe ob passwoerter identisch, wenn identisch, finalize, sonst fehler anzeigen
				finalize();
				break;

			case FORM_CHANGE_PASSWORD_SAVE:
				//TODO:Save
				dialog.dispose();
				finalize();
				break;
		}
	}
}
