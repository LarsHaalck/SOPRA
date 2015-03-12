package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.TupleIntString;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourResetBus;

import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for FormTourResetBus class.
 * @pre User is logged in and has Schedule-Planner-Role.
 */
public class ControllerFormTourResetBus extends Controller implements ListenerButton
{
	private FormTourResetBus formTourResetBus;
	private Date startDate;
	private Date endDate;
	private int busId;

	public ControllerFormTourResetBus(FormTourResetBus formTourResetBus)
	{
		this.formTourResetBus = formTourResetBus;
		ArrayList<Bus> busesDirect = ControllerDatabase.getInstance().getBuses();
		TupleIntString[] busesFinal = new TupleIntString[busesDirect.size()];
		System.out.println(busesDirect.toString());
		int i = 0;
		for (Bus bus : busesDirect)
		{
			busesFinal[i++] = new TupleIntString(bus.getId(), bus.getLicencePlate());
		}
		formTourResetBus.fillBuses(busesFinal);
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
			case FORM_TOUR_RESET_BUS_SAVE:
				if(parseValuesFromForm())
				{
					formTourResetBus.setCursor(true);
					if (saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
						closeDialog();
					}
					formTourResetBus.setCursor(false);
				}
				break;

			case FORM_TOUR_RESET_BUS_CANCEL:
				closeDialog();
				break;
		}
	}

	/**
	 * Removes all tours containing the selected bus driver.
	 * @return true if successful
	 */
	private boolean saveToDB()
	{
		ControllerDatabase.getInstance().deleteBusFromTours(busId, startDate, endDate);
		return true;
	}

	/**
	 * Checks if form input is correct and parses them to local variables.
	 * @return Returns true on correct input.
	 */
	private boolean parseValuesFromForm()
	{
		Date tempStartDate = formTourResetBus.getDateStart();
		Date tempEndDate = formTourResetBus.getDateEnd();
		TupleIntString tempBus = formTourResetBus.getBus();

		ArrayList<String> errorFields = new ArrayList<>();
		if(tempStartDate == null)
			errorFields.add("Ungültige Eingabe der Startzeit.");
		if(tempEndDate == null)
			errorFields.add("Ungültige Eingabe der Endzeit.");


		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formTourResetBus.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			startDate = tempStartDate;
			endDate = tempEndDate;
			busId = tempBus.getFirst();
			return true;
		}
	}


	/**
	 * Closes the current Dialog.
	 */
	private void closeDialog()
	{
		removeListeners();
		formTourResetBus.dispose();
	}
}
