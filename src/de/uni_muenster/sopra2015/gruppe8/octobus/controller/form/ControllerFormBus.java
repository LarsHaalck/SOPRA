package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import java.util.Date;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormBus extends Controller implements ListenerButton
{
	private FormBus formBus;
	private int objectID;
	private Bus bus;

	public ControllerFormBus(FormBus formBus, int objectID){
		super();
		this.objectID = objectID;
		this.formBus = formBus;
		if(objectID != -1)
		{
			setBusById(objectID);
		}
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
				closeDialog();
				break;
		}
	}

	/**
	 * Fetch a Bus object from the DB.
	 * @param id Bus-ID.
	 */
	private void setBusById(int id)
	{
		//TODO db request
		bus = new Bus("TestNummernschild", 42, 32, "Ich", "cooler Bus", new Date(2015,5,29),true);
	}

	/**
	 * Inserts the values of the Bus which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formBus.setLicencePlateText(bus.getLicencePlate());
			formBus.setNumberOfSeatsText("" + bus.getNumberOfSeats());
			formBus.setStandingRoomText("" + bus.getStandingRoom());
			formBus.setManufacturerText(bus.getManufacturer());
			formBus.setModelText(bus.getModel());
			formBus.setNextInspectionDueText("" + bus.getNextInspectionDue());
			formBus.setArticulatedBusText(bus.isArticulatedBus());
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
		formBus.dispose();
		removeListeners();
	}
}
