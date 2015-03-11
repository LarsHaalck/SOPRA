package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourSanity;

/**
 * Created by Florian on 11.03.2015.
 */
public class ControllerFormTourSanity extends Controller implements ListenerButton
{
	private FormTourSanity formTourSanity;
	private ControllerDatabase controllerDatabase;

	public ControllerFormTourSanity(FormTourSanity formTourSanity)
	{
		super();

		this.formTourSanity = formTourSanity;
		this.controllerDatabase = ControllerDatabase.getInstance();

		fillForm();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case FORM_TOUR_SANITY_BACK:
				close();
				break;
		}
	}

	private void fillForm()
	{

	}

	private void close()
	{
		removeListeners();
		formTourSanity.dispose();
	}
}
