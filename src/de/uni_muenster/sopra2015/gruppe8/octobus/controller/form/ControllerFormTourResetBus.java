package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.TupleIntString;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourResetBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourResetEmployee;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Florian on 10.03.2015.
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
		ArrayList<TupleIntString> busesFinal = new ArrayList<>();
		ArrayList<Bus> busesDirect = ControllerDatabase.getInstance().getBuses();
		System.out.println(busesDirect.toString());
		for (Bus bus : busesDirect)
		{
			busesFinal.add(new TupleIntString(bus.getId(), bus.getLicencePlate()/* + ", " + employee.getFirstName()*/));
		}
		formTourResetBus.fillEmployees(busesFinal);
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
		switch(btn)
		{
			case FORM_TOUR_RESET_BUS_SAVE:
				if(parseValuesFromForm())
				{
					if (saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
						closeDialog();
					}
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
		if(startDate == null || endDate == null)
			errorFields.add("Ungültige Eingabe des Namen. Es wurden illegale Zeichen verwendet.");


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
