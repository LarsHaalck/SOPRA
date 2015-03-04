package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormBus extends Controller implements ListenerButton
{
	private FormBus formBus;
	private int objectID;

	public ControllerFormBus(FormBus formBus, int objectID){
		super();
		this.objectID = objectID;
		this.formBus = formBus;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_BUS_SAVE:
				//TODO finish this
				closeDialog();
				break;
			case FORM_BUS_CANCEL:
				//TODO finish this
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
		//TODO formBus.dispose();
		removeListeners();
	}
}
