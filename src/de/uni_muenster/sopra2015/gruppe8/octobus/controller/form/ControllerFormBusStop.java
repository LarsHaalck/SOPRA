package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormBusStop extends Controller implements ListenerButton
{
	private FormBusStop dialog;
	int objectId;

	public ControllerFormBusStop(FormBusStop dialog, int objectId)
	{
		super();
		this.dialog = dialog;
		this.objectId = objectId;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_BUS_STOP_SAVE:
				//TODO:Save them
				closeDialog();
				break;

			case FORM_BUS_STOP_CANCEL:
				closeDialog();
				break;
		}
	}

	@Override
	protected  void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	private void closeDialog()
	{
		//TODO: dispose it when its really a dialog
		//dialog.dispose();
		removeListeners();
	}
}
