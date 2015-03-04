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

	public ControllerFormBus(FormBus formBus){
		super();
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
		ControllerManager.getInstance().addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.getInstance().removeListener((ListenerButton) this);
	}
	private void closeDialog()
	{
		//TODO formBus.dispose();
		removeListeners();
	}
}
