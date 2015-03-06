package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controller for FormRoute class.
 */
public class ControllerFormRoute extends Controller implements ListenerButton, ListenerTable
{
	private ControllerDatabase controllerDatabase;
	FormRoute formRoute;
	private int objectID;
	private Route route;
	private ArrayList<Integer> contentTableCurrent = new ArrayList<>();

	public ControllerFormRoute(FormRoute formRoute, int objectID)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.formRoute = formRoute;
		this.objectID = objectID;
		if(objectID != -1)
		{
			//TODO
			//setRouteInfo();
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_ROUTE_BACK:
				//TODO save information for later use
				if (formRoute.getPanelCounter() == 1)
					formRoute.getBackButton().setEnabled(false);
				if (formRoute.getPanelCounter() == formRoute.getPanelMax())
					formRoute.getNextButton().setText("Weiter");
				formRoute.getCl().previous(formRoute.getCardPanel());
				formRoute.setPanelCounter(formRoute.getPanelCounter() - 1);
				break;

			case FORM_ROUTE_NEXT:
				if (formRoute.getPanelCounter() == 0)
					formRoute.getBackButton().setEnabled(true);
				if (formRoute.getPanelCounter() == formRoute.getPanelMax() - 1)
					formRoute.getNextButton().setText("Fertig");
					formRoute.getStep2().fillJpMain(formRoute.getStep1().getTableData());
				if (formRoute.getPanelCounter() == formRoute.getPanelMax())
				{
					//TODO lese informationen aus
					closeDialog();
				}
				formRoute.getCl().next(formRoute.getCardPanel());
				formRoute.setPanelCounter(formRoute.getPanelCounter() + 1);
				break;

			case FORM_ROUTE_CANCEL:
				//TODO: If time: Check if something was changed and ask if user really wants to cancel
				closeDialog();
				break;

			case FORM_ROUTE_STEP1_UP:

				break;

			case FORM_ROUTE_STEP1_DOWN:

				break;

			case FORM_ROUTE_STEP1_ADD:
				JTable tableAvailabe = formRoute.getStep1().getBusStopAvailable();
				JTable tableCurrent = formRoute.getStep1().getBusStopCurrent();
				ExtendedTableModel modelTableAvailable = formRoute.getStep1().getModel_2();
				ExtendedTableModel modelTableCurrent = formRoute.getStep1().getModel_1();
				//TODO kaputt
				int viewRow = tableAvailabe.getSelectedRow();
				int selectedRow = tableAvailabe.convertRowIndexToModel(viewRow);
				int selectedID = (int) modelTableAvailable.getValueAt(selectedRow, 0);
				contentTableCurrent.add(selectedID);
				tableAvailabe.clearSelection();
				initTableCurrent();
				initTableAvailable(contentTableCurrent);


				break;

			case FORM_ROUTE_STEP1_DELETE:

				break;

			case FORM_ROUTE_STEP2_ADD:

				break;

			case FORM_ROUTE_STEP2_EDIT:

				break;

			case FORM_ROUTE_STEP2_DELETE:

				break;
		}
	}

	@Override
	public void tableSelectionChanged(EmitterTable emitter)
	{

	}

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{

	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
		ControllerManager.addListener((ListenerTable) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
		ControllerManager.addListener((ListenerTable) this);
	}

	/**
	 * Closes current dialog.
	 */
	private void closeDialog()
	{
		formRoute.dispose();
		removeListeners();
	}

	/**
	 * Initializes table Available with BusStops
	 */
	public void initTableAvailable()
	{
		ArrayList<BusStop> busStops = controllerDatabase.getBusStops();
		Object[][] data = new Object[busStops.size()][2];
		for(int i=0; i<busStops.size(); i++)
		{
			BusStop busStop = busStops.get(i);
			data[i][0] = busStop.getId();
			data[i][1] = busStop.getName();
		}
		formRoute.getStep1().fillTableAvailable(data);
	}
	/**
	 * Initializes table Current with BusStops
	 */
	public void initTableCurrent()
	{
		ArrayList<BusStop> busStops = controllerDatabase.getBusStops();
		Object[][] data = new Object[contentTableCurrent.size()][2];
		for(int i=0; i<data.length; i++)
		{
			BusStop busStop = controllerDatabase.getBusStop(contentTableCurrent.get(i));
			data[i][0] = busStop.getId();
			data[i][1] = busStop.getName();
		}
		formRoute.getStep1().fillTableCurrent(data);
	}

	/**
	 * Initializes table with BusStops
	 * @param id List of BusStop IDs to be excluded
	 */
	public void initTableAvailable(ArrayList<Integer> id)
	{
		ArrayList<BusStop> busStops = controllerDatabase.getBusStops();
		Object[][] data = new Object[busStops.size() - id.size()][2];
		int k = 0;
		for(int i=0; i<busStops.size(); i++)
		{
			BusStop busStop = busStops.get(i);
			if(!id.contains(busStop.getId()))
			{
				data[k][0] = busStop.getId();
				data[k][1] = busStop.getName();
				k++;
			}
		}
		formRoute.getStep1().fillTableAvailable(data);
	}
}
