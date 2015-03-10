package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourResetEmployee;

/**
 * Created by Florian on 10.03.2015.
 */
public class ControllerFormTourResetEmployee extends Controller implements ListenerButton
{
	private FormTourResetEmployee formTourResetEmployee;
	private ControllerDatabase controllerDatabase;

	public ControllerFormTourResetEmployee(FormTourResetEmployee formTourResetEmployee)
	{
		this.formTourResetEmployee = formTourResetEmployee;
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

	@Override
	public void buttonPressed(EmitterButton btn)
	{

	}

	private void close()
	{
		removeListeners();
		formTourResetEmployee.dispose();
	}
}
