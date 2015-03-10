package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabSchedule extends Controller implements ListenerButton
{
	private TabSchedule tabSchedule;
	private ControllerDatabase controllerDatabase;

	public ControllerTabSchedule(TabSchedule tabSchedule)
	{
		super();
		this.tabSchedule = tabSchedule;
		controllerDatabase = ControllerDatabase.getInstance();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
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
			case TAB_SCHEDULE_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_EDIT, -1);
				break;

			case TAB_SCHEDULE_FILTER:
				newFilterSelected();
				break;
		}
	}

	private void fillTable()
	{
		ArrayList<Tuple<Tour, Boolean>> tours = controllerDatabase.getToursWithinDateRange(1426004896,1426210096);
		Object data[][] = new Object[tours.size()][7];
		for (int i=0; i<tours.size(); i++)
		{
			Tuple<Tour, Boolean> tuple = tours.get(i);
			Tour tour = tuple.getFirst();
			data[i][0] = tour.getId();
			data[i][1] = tour.getRoute().getName();
			data[i][2] = new TableDate(tour.getStartTimestamp(), TableDate.Type.DATE_TIME);
			data[i][3] = tour.getRoute().getStart().getName();
			data[i][4] = tour.getRoute().getEnd().getName();
			data[i][5] = tour.getBus().getLicencePlate();
			data[i][6] = tour.getDriver().getName() +", "+ tour.getDriver().getFirstName();
		}
		tabSchedule.fillTable(data);
	}

	private void newFilterSelected()
	{
		Date start = tabSchedule.getDateStart();
		boolean onlyUnassigned = tabSchedule.getOnlyUnassigned();

		//Check if start-date is invalid
		if(start == null)
			tabSchedule.showMessageDialog("Das Start-Datum liegt in keinem gültigen Format vor.");
		else {
			fillTable();
		}

	}
}
