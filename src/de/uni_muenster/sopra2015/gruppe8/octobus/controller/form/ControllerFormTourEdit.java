package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourEdit;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import java.util.ArrayList;

/**
 * Controller for FormTourEdit class.
 */
public class ControllerFormTourEdit extends Controller implements ListenerButton
{
	private FormTourEdit formTourEdit;
	private ControllerDatabase controllerDatabase;
	private int objectId;
	private Tour tour;

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
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener(this);
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
				if(formTourEdit.getSelectedBus() == 0)
					errors.add("Es wurde kein Bus ausgewählt.");
				if(formTourEdit.getSelectedBusDriver() == 0)
					errors.add("Es wurde kein Bus-Fahrer ausgewählt.");

				if(errors.size() == 0)
				{
					controllerDatabase.modifyTour(objectId, formTourEdit.getSelectedBus(), formTourEdit.getSelectedBusDriver());
					close();
				}
				else
				{
					String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
					errorMessage += errorListToString(errors);
					formTourEdit.showErrorForm(errorMessage);
				}
				break;
		}
	}

	/**
	 * Closes current dialog.
	 */
	private void close()
	{
		removeListeners();
		formTourEdit.dispose();
	}

	/**
	 * Fetches tour by objectID from DB.
	 * Sets local tour to it.
	 */
	private void getDataFromDB()
	{
		tour = controllerDatabase.getTourById(objectId);
	}

	/**
	 * Fills forms with data from DB.
	 */
	public void fillForm()
	{
		//Fill tables
		ArrayList<Bus> buses = controllerDatabase.getAvailableBusesForTour(tour);
		ArrayList<Employee> employees = controllerDatabase.getAvailableBusDriversForTour(tour);

		//TODO remove this
		if(buses == null)
			buses = new ArrayList<>();
		if(employees == null)
			employees = new ArrayList<>();

		Object[][] data = new Object[buses.size()][2];
		for (int i=0; i<buses.size(); i++)
		{
			data[i][0] = buses.get(i).getId();
			data[i][1] = buses.get(i).getLicencePlate();
		}
		formTourEdit.setBusData(data);

		data = new Object[employees.size()][2];
		for(int i=0; i<employees.size(); i++)
		{
			data[i][0] = employees.get(i).getId();
			data[i][1] = employees.get(i).getName() + ", " + employees.get(i).getFirstName();
		}
		formTourEdit.setBusDriverData(data);

		//formTourEdit.setSelectedBus(0);
		//formTourEdit.setSelectedBusDriver(0);
		Route route = tour.getRoute();
		formTourEdit.setLabelTourDesc(route.getName() + " (" + route.getStart().getName() + " - " + route.getEnd().getName() + ")");
		formTourEdit.setLabelTourTime(new TableDate(tour.getStartTimestamp(), TableDate.Type.DATE_TIME).toString());
	}
}
