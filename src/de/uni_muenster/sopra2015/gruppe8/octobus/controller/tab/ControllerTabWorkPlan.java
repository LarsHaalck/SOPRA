package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabWorkPlan;

import java.util.ArrayList;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabWorkPlan extends Controller implements ListenerButton
{
	ControllerDatabase controllerDatabase;
	TabWorkPlan tabWorkPlan;

	public ControllerTabWorkPlan(TabWorkPlan tabWorkPlan)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.tabWorkPlan = tabWorkPlan;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case TAB_WORK_PLAN_EXPORT:
				break;

			case TAB_WORK_PLAN_PRINT:
				System.out.println("Butn pressed");
				ControllerManager.informPrintRequested(EmitterPrint.WORK_PLAN, 1);
				break;
		}
	}

	public void fillTable()
	{
		//TODO Set logged-in user
		ArrayList<Tour> tours = controllerDatabase.getUserTours(1);
		Object[][] data = new Object[tours.size()][7];
		for(int i=0; i<tours.size(); i++)
		{
			Tour tour = tours.get(i);
			data[i][0] = tour.getId();
			data[i][1] = parseDate(tour.getTimestamp());
			data[i][2] = tour.getRoute().getName();
			data[i][3] = tour.getRoute().getStart().getName();
			data[i][4] = tour.getRoute().getEnd().getName();
			data[i][5] = tour.getRoute().getDuration();
			data[i][6] = tour.getBus().getLicencePlate();
		}
		tabWorkPlan.fillTable(data);
	}
}
