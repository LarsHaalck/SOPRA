package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourEdit;

import java.util.ArrayList;

/**
 * Created by Florian on 10.03.2015.
 */
public class ControllerFormTourEdit extends Controller implements ListenerButton
{
	private FormTourEdit formTourEdit;
	private ControllerDatabase controllerDatabase;
	private int objectId;
	private Route route;

	public ControllerFormTourEdit(FormTourEdit formTourEdit, int objectId)
	{
		super();

		this.formTourEdit = formTourEdit;
		this.objectId = objectId;
		this.controllerDatabase = ControllerDatabase.getInstance();

		getDataFromDB();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case FORM_TOUR_EDIT_CANCEL:
				close();
				break;

			case FORM_TOUR_EDIT_SAVE:
				ArrayList<String> errors = new ArrayList<>();
				if(formTourEdit.getSelectedBus() == -1)
					errors.add("Es wurde kein Bus ausgewählt.");
				if(formTourEdit.getSelectedBusDriver() == -1)
					errors.add("Es wurde kein Bus-Fahrer ausgewählt.");

				//controllerDatabase.modifyRoute(objectId, formTourEdit.getSelectedBus(), formTourEdit.getSelectedBusDrive()

				close();
				break;
		}
	}

	private void close()
	{
		removeListeners();
		formTourEdit.dispose();
	}

	private void getDataFromDB()
	{
		//tour = controllerDatabase.getTour(objectId);

	}

	public void fillForm()
	{
		//Fill tables
		formTourEdit.setSelectedBus(0);
		formTourEdit.setSelectedBusDriver(0);
	}
}
