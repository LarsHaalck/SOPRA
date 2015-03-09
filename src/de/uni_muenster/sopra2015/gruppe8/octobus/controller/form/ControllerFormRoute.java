package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormDepartureTime;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.Collator;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Controller for FormRoute class.
 */
public class ControllerFormRoute extends Controller implements ListenerButton, ListenerTable, ListenerWindow
{
	private ControllerDatabase controllerDatabase;
	FormRoute formRoute;
	private int objectID;
	private Route route;
	private ArrayList<Tuple<Integer, String>> contentTableCurrent = new ArrayList<>();
	private JTable tableCurrent;
	private int viewRow;

	public ControllerFormRoute(FormRoute formRoute, int objectID)
	{
		super();
		route = new Route();
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
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if (viewRow == -1)
					break;
				if (viewRow == 0)
					break;
				Tuple<Integer, String> contentOld = contentTableCurrent.get(viewRow - 1);
				contentTableCurrent.remove(viewRow - 1);
				contentTableCurrent.add(viewRow, contentOld);
				initTableCurrent();
				tableCurrent.clearSelection();
				tableCurrent.changeSelection(viewRow - 1, 1, true, false);
				break;

			case FORM_ROUTE_STEP1_DOWN:
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if (viewRow == -1)
					break;
				if (viewRow == contentTableCurrent.size() -1)
					break;
				Tuple<Integer, String> old = contentTableCurrent.get(viewRow + 1);
				contentTableCurrent.remove(viewRow + 1);
				contentTableCurrent.add(viewRow, old);
				initTableCurrent();
				tableCurrent.changeSelection(viewRow + 1, 1, true, false);
				break;

			case FORM_ROUTE_STEP1_ADD:

				JTable tableAvailabe = formRoute.getStep1().getBusStopAvailable();
				JTable tableCurrent = formRoute.getStep1().getBusStopCurrent();
				ExtendedTableModel modelTableAvailable = formRoute.getStep1().getModel_2();
				ExtendedTableModel modelTableCurrent = formRoute.getStep1().getModel_1();
				int viewRow = tableAvailabe.getSelectedRow();
				if (viewRow == -1)
					break;
				int selectedRow = tableAvailabe.convertRowIndexToModel(viewRow);
				int selectedID = (int) modelTableAvailable.getValueAt(selectedRow, 0);
				String selectedName = (String) modelTableAvailable.getValueAt(selectedRow, 1);
				contentTableCurrent.add(new Tuple<Integer, String>(selectedID, selectedName));
				initTableCurrent();
				break;

			case FORM_ROUTE_STEP1_DELETE:
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if (viewRow == -1)
					break;
				contentTableCurrent.remove(viewRow);
				tableCurrent.clearSelection();
				initTableCurrent();
				break;

			case FORM_ROUTE_STEP2_ADD:
				new FormDepartureTime(formRoute, route);
				break;

			case FORM_ROUTE_STEP2_EDIT:
				System.out.println(route.getStartTimes().get(DayOfWeek.FRIDAY));
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
		ControllerManager.addListener((ListenerWindow) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerTable) this);
		ControllerManager.removeListener((ListenerWindow) this);
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
	 * Initializes table Available with StoppingPoints
	 */
	public void initTableAvailable()
	{
		ArrayList<Tuple<Integer, String>> stoppingPoints = controllerDatabase.getBusStopNamesWithStoppingPointNames();

		Object[][] data = new Object[stoppingPoints.size()][2];
		for(int i=0; i<stoppingPoints.size(); i++)
		{
			data[i][0] = stoppingPoints.get(i).getFirst();
			data[i][1] = stoppingPoints.get(i).getSecond();
		}
		formRoute.getStep1().fillTableAvailable(data);
	}

	/**
	 * Initializes table Current with StoppingPoints
	 */
	public void initTableCurrent()
	{
		Object[][] data = new Object[contentTableCurrent.size()][2];
		for(int i=0; i<data.length; i++)
		{
			data[i][0] = contentTableCurrent.get(i).getFirst();
			data[i][1] = contentTableCurrent.get(i).getSecond();
		}
		formRoute.getStep1().fillTableCurrent(data);
	}
	//TODO Currently not used
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
	private void refreshTablesStep2()
	{
		ArrayList<Tuple<DayOfWeek, DefaultTableModel>> models = new ArrayList<>();

		models.add(new Tuple<>(DayOfWeek.MONDAY, formRoute.getStep2().getDtmMo()));
		models.add(new Tuple<>(DayOfWeek.TUESDAY, formRoute.getStep2().getDtmDi()));
		models.add(new Tuple<>(DayOfWeek.WEDNESDAY, formRoute.getStep2().getDtmMi()));
		models.add(new Tuple<>(DayOfWeek.THURSDAY, formRoute.getStep2().getDtmDo()));
		models.add(new Tuple<>(DayOfWeek.FRIDAY, formRoute.getStep2().getDtmFr()));
		models.add(new Tuple<>(DayOfWeek.SATURDAY, formRoute.getStep2().getDtmSa()));
		models.add(new Tuple<>(DayOfWeek.SUNDAY, formRoute.getStep2().getDtmSo()));

		for (Tuple<DayOfWeek, DefaultTableModel> model : models)
		{
			model.getSecond().setRowCount(0);
		}

		HashMap<DayOfWeek,LinkedList<Integer>> startTimes = route.getStartTimes();

		for (Tuple<DayOfWeek, DefaultTableModel> model : models)
		{
			LinkedList<Integer> times = startTimes.get(model.getFirst());
			times.sort(new Comparator<Integer>()
			{
				@Override
				public int compare(Integer o1, Integer o2)
				{
					if(o1 < o2)
						return -1;
					else if(o1 == o2)
						return 0;
					else
						return 1;
				}
			});

			for (Integer time : times)
			{
				int hours = (int) time/60;
				int minutes = time - hours*60;
				String[] rowTime = new String[1];
				if(minutes < 10)
				{
					if(hours < 10)
						rowTime[0] = "0" + hours + ":0" + minutes;
					else
						rowTime[0] = hours + ":0" + minutes;
				}
				else
				{
					if(hours < 10)
						rowTime[0] = "0" + hours + ":" + minutes;
					else
						rowTime[0] = hours + ":" + minutes;
				}
				model.getSecond().addRow(rowTime);
			}
		}
	}

	@Override
	public void windowOpen(EmitterWindow wd)
	{

	}

	@Override
	public void windowOpen(EmitterWindow wd, int objectID)
	{

	}

	@Override
	public void windowClose(EmitterWindow wd)
	{
		switch (wd)
		{
			case FORM_ROUTE_STEP2_DEPARTURE_CLOSE:
				System.out.println("check");
				refreshTablesStep2();
				break;
		}
	}

	@Override
	public void displaySwitch(EmitterDisplay dp)
	{

	}
}
