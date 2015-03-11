package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for the FormBus class.
 */
public class ControllerFormBus extends Controller implements ListenerButton
{
	private ControllerDatabase controllerDatabase;
	private FormBus formBus;
	private int objectID;
	private Bus bus;

	public ControllerFormBus(FormBus formBus, int objectID){
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.objectID = objectID;
		this.formBus = formBus;
		//Sets global bus to the bus given in objectID
		if(objectID != -1)
		{
			setBusInfo();
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_BUS_SAVE:
				//Checks if input is valid
				if(parseValuesFromForm())
				{
					//Saves bus to DB and refreshes TAB_BUS
					if(saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_BUS);
						closeDialog();
					}
				}
				break;
			case FORM_BUS_CANCEL:
				//TODO: If time: Check if something was changed and ask if user really wants to cancel
				closeDialog();
				break;
		}
	}

	/**
	 * Fetch a Bus object from the DB.
	 */
	private void setBusInfo()
	{
		bus = controllerDatabase.getBusById(objectID);
	}

	/**
	 * Inserts the values of the Bus which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formBus.setLicencePlate(bus.getLicencePlate());
			formBus.setNumberOfSeats(bus.getNumberOfSeats());
			formBus.setStandingRoom(bus.getStandingRoom());
			formBus.setManufacturer(bus.getManufacturer());
			formBus.setModel(bus.getModel());
			formBus.setNextInspectionDue(bus.getNextInspectionDue());
			formBus.setArticulatedBus(bus.isArticulatedBus());
		}
	}

	/**
	 *Checks if form input is correct and adds values to local bus.
	 * @return Returns true on correct input.
	 */
	private boolean parseValuesFromForm()
	{
		if(objectID == -1)
		{
			bus = new Bus();
		}

		String licencePlate = formBus.getLicencePlate();
		int numberOfSeats = formBus.getNumberOfSeats();
		int standingRoom = formBus.getStandingRoom();
		String manufacturer = formBus.getManufacturer();
		String model = formBus.getModel();
		Date nextInspectionDue = formBus.getNextInspectionDue();
		boolean articulatedBus = formBus.getArticulatedBus();

		ArrayList<String> errorFields = new ArrayList<>();

		if(licencePlate == null)
			errorFields.add("Ungültige Eingabe des Kennzeichen. Es wurden illegale Zeichen verwendet.");
		else if(licencePlate.trim().length() == 0)
			errorFields.add("Das Kennzeichen darf nicht leer sein.");
		else if(licencePlate.trim().length() < 5)
			errorFields.add("Das Kennzeichen muss mindestens 5 Zeichen umfassen.");
		if(numberOfSeats == -1)
			errorFields.add("Die Anzahl der Sitzplätze darf nicht leer sein.");
		if(standingRoom == -1)
			errorFields.add("Die Anzahl der Stehplätze darf nicht leer sein.");
		if(manufacturer == null)
			errorFields.add("Ungültige Eingabe des Herstellers. Es wurden illegale Zeichen verwendet.");
		else if(manufacturer.trim().length() == 0)
			errorFields.add("Der Hersteller-Name darf nicht leer sein.");
		if(model == null)
			errorFields.add("Ungültige Eingabe des Modells. Es wurden illegale Zeichen verwendet.");
		else if(model.trim().length() == 0)
			errorFields.add("Der Modell-Name darf nicht leer sein.");
		if(nextInspectionDue == null)
			errorFields.add("Das Datum der nächsten Inspektion ist in keinem gültigen Format.");

		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formBus.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			bus.setLicencePlate(licencePlate);
			bus.setNumberOfSeats(numberOfSeats);
			bus.setStandingRoom(standingRoom);
			bus.setManufacturer(manufacturer);
			bus.setModel(model);
			bus.setNextInspectionDue(nextInspectionDue);
			bus.setArticulatedBus(articulatedBus);
			return true;
		}
	}

	/**
	 * Saves the current bus to the DB.
	 * @return true if it worked
	 */
	private boolean saveToDB()
	{
		if(objectID == -1)
			controllerDatabase.addBus(bus);
		else
			controllerDatabase.modifyBus(bus);
		return true;
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

	/**
	 * Closes current dialog.
	 */
	private void closeDialog()
	{
		formBus.dispose();
		removeListeners();
	}
}
