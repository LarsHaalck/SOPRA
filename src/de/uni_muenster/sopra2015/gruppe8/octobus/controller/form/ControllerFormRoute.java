package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormDepartureTime;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.ExpandVetoException;
import java.lang.reflect.Array;
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
	private ArrayList<Tuple<Integer, String>> routeStoppingPoints;
	private boolean stopsChanged;

	public ControllerFormRoute(FormRoute formRoute, int objectID)
	{
		super();
		route = new Route();
		controllerDatabase = ControllerDatabase.getInstance();
		this.formRoute = formRoute;
		this.objectID = objectID;
		stopsChanged = false;
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
					//Step 1
					if(parseValuesFromFormRouteStep1())
					{
						formRoute.getBackButton().setEnabled(true);
						formRoute.getNextButton().setText("Fertig");

						String[] busStops = new String[routeStoppingPoints.size()];

						for (int i = 0; i < busStops.length; i++)
						{
							busStops[i] = routeStoppingPoints.get(i).getSecond();
						}

						formRoute.getStep2().fillJpMain(busStops);
					}
					else
					{
						break;
					}
				if (formRoute.getPanelCounter() == formRoute.getPanelMax())
				{
					if(parseValuesFromFormRouteStep2())
					{
						if (saveToDB())
						{
							ControllerManager.informTableContentChanged(EmitterTable.TAB_ROUTE);
							closeDialog();
						}
					}
					break;
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
				try
				{
					if (viewRow == -1)
						break;
					contentTableCurrent.remove(viewRow);
					initTableCurrent();
				}
				catch (IndexOutOfBoundsException e){}
				break;

			case FORM_ROUTE_STEP2_ADD:
				new FormDepartureTime(formRoute, route);
				break;

			case FORM_ROUTE_STEP2_EDIT:
				JTable activeEdit = formRoute.getStep2().getTableActive();
				if(activeEdit != null)
				{
					if (activeEdit.getSelectedRowCount() > 1)
					{
						String errorMessage = "Die eingegeben Daten sind nicht gültig:\n	" +
								"Bitte nur einen Eintrag zum ändern wählen.";
						formRoute.showErrorForm(errorMessage);
					} else if (activeEdit.getSelectedRowCount() != 0)
					{
						LinkedList<Integer> tempTimes = route.getStartTimes().get(formRoute.getStep2().getActiveDay());
						String editedTimeString = activeEdit.getModel().getValueAt(activeEdit.getSelectedRow(), 0).toString();
						int editedHours = Integer.parseInt(editedTimeString.substring(0, 2));
						int editedMinutes = Integer.parseInt(editedTimeString.substring(3, 5));
						int editedTime = editedHours * 60 + editedMinutes;
						Tuple<Integer, Integer> newTimeTuple = formRoute.getStep2().showEditDialog(editedHours, editedMinutes);
						if(newTimeTuple != null)
						{
							tempTimes.remove((Object) editedTime);
							int newTime = newTimeTuple.getFirst() * 60 + newTimeTuple.getSecond();
							tempTimes.add(newTime);
							route.getStartTimes().put(formRoute.getStep2().getActiveDay(), tempTimes);
							refreshTablesStep2();
						}
					}
				}
				break;

			case FORM_ROUTE_STEP2_DELETE:
				JTable activeDel = formRoute.getStep2().getTableActive();
				DayOfWeek activeDelDay = formRoute.getStep2().getActiveDay();
				int[] selectedRows = activeDel.getSelectedRows();
				if(formRoute.getStep2().showDeleteDialog() == JOptionPane.YES_OPTION)
				{
					if (activeDel != null)
					{
						if (selectedRows.length != 0)
						{
							LinkedList<Integer> tempTimes = route.getStartTimes().get(activeDelDay);
							for (int row : selectedRows)
							{
								String delTimeString = activeDel.getModel().getValueAt(row, 0).toString();
								int delHours = Integer.parseInt(delTimeString.substring(0, 2));
								int delMinutes = Integer.parseInt(delTimeString.substring(3, 5));
								int delTime = delHours * 60 + delMinutes;
								tempTimes.remove((Object) delTime);
							}
							route.getStartTimes().put(activeDelDay, tempTimes);
							refreshTablesStep2();
						}
					}
				}
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{
		switch(emitter)
		{
			case FORM_ROUTE_STEP1_CURRENT:
				formRoute.getStep1().getBusStopCurrent().clearSelection();
				break;

			case FORM_ROUTE_STEP1_AVAILABLE:
				formRoute.getStep1().getBusStopAvailable().clearSelection();
				break;

			case FORM_ROUTE_STEP2_MONDAY:
				formRoute.getStep2().getJtMo().clearSelection();
				break;

			case FORM_ROUTE_STEP2_TUESDAY :
				formRoute.getStep2().getJtDi().clearSelection();
				break;

			case FORM_ROUTE_STEP2_WEDNESDAY:
				formRoute.getStep2().getJtMi().clearSelection();
				break;

			case FORM_ROUTE_STEP2_THURSDAY:
				formRoute.getStep2().getJtDo().clearSelection();
				break;

			case FORM_ROUTE_STEP2_FRIDAY:
				formRoute.getStep2().getJtFr().clearSelection();
				break;

			case FORM_ROUTE_STEP2_SATURDAY:
				formRoute.getStep2().getJtSa().clearSelection();
				break;

			case FORM_ROUTE_STEP2_SUNDAY:
				formRoute.getStep2().getJtSo().clearSelection();
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
		ControllerManager.addListener((ListenerTable) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerTable) this);
		ControllerManager.removeListener((ListenerWindow) this);
		ControllerManager.removeListener((ListenerTable) this);
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
	 * Saves the current route to the DB.
	 * @return
	 */
	private boolean saveToDB()
	{
		if(objectID == -1)
			controllerDatabase.addRoute(route);
		else
			controllerDatabase.modifyRoute(route, stopsChanged);
		return true;
	}

	/**
	 * Parses values from FormRouteStep1.
	 * @return Returns false on wrong input.
	 */
	private boolean parseValuesFromFormRouteStep1()
	{
		String name = formRoute.getStep1().getNameRoute();
		boolean night = formRoute.getStep1().isNightLine();
		ArrayList<Tuple<Integer, String>> stoppingPoints = formRoute.getStep1().getTableData();

		ArrayList<String> errorFields = new ArrayList<>();
		if(name == null)
			errorFields.add("Ungültige Eingabe des Namen. Es wurden illegale Zeichen verwendet.");
		else if(name.trim().length() == 0)
			errorFields.add("Der Name darf nicht leer sein.");
		else if (name.trim().length() < 5)
			errorFields.add("Der Name muss mindestens 5 Zeichen umfassen.");
		if(stoppingPoints.size() < 2)
		{
			errorFields.add("Es müssen mindestens zwei Haltepunkte angegeben werden.");
		}
		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig:\n";
			errorMessage += errorListToString(errorFields);
			formRoute.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			route.setName(name);
			route.setNight(night);
			routeStoppingPoints = stoppingPoints;
			return true;
		}
	}

	/**
	 * Parses values from FormRouteStep2.
	 * @return Returns false on wrong input
	 */
	private boolean parseValuesFromFormRouteStep2()
	{
		int[] departureTimes = formRoute.getStep2().getDepartureTime();


		ArrayList<String> errorFields = new ArrayList<>();
		boolean departureEmpty = false;
		for (int departureTime : departureTimes)
		{
			if(departureTime == -1)
				departureEmpty = true;
		}
		if(departureEmpty)
			errorFields.add("Alle Zwischenzeiten müssen angegeben sein.");
		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig:\n";
			errorMessage += errorListToString(errorFields);
			formRoute.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stoppingPoints = new LinkedList<>();
			int id = routeStoppingPoints.get(0).getFirst();
			BusStop busStop = controllerDatabase.getBusStopByStoppingPointId(id);
			StoppingPoint stoppingPoint = controllerDatabase.getStoppingPointById(id);
			int time = 0;
			stoppingPoints.add(new Triple<BusStop, StoppingPoint, Integer>(busStop,stoppingPoint,time));
			for (int i = 0; i < departureTimes.length; i++)
			{
				id = routeStoppingPoints.get(i+1).getFirst();
				busStop = controllerDatabase.getBusStopByStoppingPointId(id);
				stoppingPoint = controllerDatabase.getStoppingPointById(id);
				time = departureTimes[i];
				stoppingPoints.add(new Triple<BusStop, StoppingPoint, Integer>(busStop,stoppingPoint,time));
			}
			route.setStops(stoppingPoints);
			route.setNote(null);

			return true;
		}
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

	/**
	 * Refreshes the tables in the second step displaying the starting times.
	 */
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
				refreshTablesStep2();
				break;
		}
	}

	@Override
	public void displaySwitch(EmitterDisplay dp)
	{

	}
}
