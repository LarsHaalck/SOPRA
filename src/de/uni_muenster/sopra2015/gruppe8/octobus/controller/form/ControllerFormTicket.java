package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormTicket extends Controller implements ListenerButton
{
	FormTicket dialog;

	public ControllerFormTicket(FormTicket dialog)
	{
		super();
		this.dialog = dialog;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_EMPLOYEE_SAVE:
				//TODO:Save them
				closeDialog();
				break;

			case FORM_EMPLOYEE_CANCEL:
				closeDialog();
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
	}

	private void closeDialog()
	{
		dialog.dispose();
		removeListeners();
	}
}