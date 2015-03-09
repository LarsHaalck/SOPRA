package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabWorkPlan;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import java.util.ArrayList;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabWorkPlan extends Controller implements ListenerButton
{
	private ControllerDatabase controllerDatabase;
	private TabWorkPlan tabWorkPlan;
	private int userId;

	public ControllerTabWorkPlan(TabWorkPlan tabWorkPlan, int userId)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.userId = userId;
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
				ControllerManager.informPrintRequested(EmitterPrint.WORK_PLAN, userId);
				break;
		}
	}

	public void fillTable()
	{
		ArrayList<Tour> tours = controllerDatabase.getUserTours(userId);
		Object[][] data = new Object[tours.size()][7];
		for(int i=0; i<tours.size(); i++)
		{
			Tour tour = tours.get(i);
			data[i][0] = tour.getId();
			data[i][1] = new TableDate(tour.getTimestamp(), TableDate.Type.DATE_TIME);
			data[i][2] = tour.getRoute().getName();
			data[i][3] = tour.getRoute().getStart().getName();
			data[i][4] = tour.getRoute().getEnd().getName();
			data[i][5] = tour.getRoute().getDuration();
			data[i][6] = tour.getBus().getLicencePlate();
		}
		tabWorkPlan.fillTable(data);
		tabWorkPlan.enableButtons(data.length > 0);
	}
}
