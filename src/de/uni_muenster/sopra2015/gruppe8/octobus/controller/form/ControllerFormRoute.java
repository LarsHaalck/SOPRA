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
import java.time.DayOfWeek;
import java.util.*;

/**
 * Controller for FormRoute class.
 * @pre User is logged in and has Network-Planner-Role.
 */
public class ControllerFormRoute extends Controller implements ListenerButton, ListenerTable, ListenerWindow
{
	FormRoute formRoute;
	private int objectID;
	private Route route;
	private ArrayList<Tuple<Integer, String>> contentTableCurrent = new ArrayList<>();
	private JTable tableCurrent;
	private int viewRow;
	private ArrayList<Tuple<Integer, String>> routeStoppingPoints;

	//Used to prevent displaying a warning when creating a new route and initially selecting
	//stopping points.
	private boolean initialChanges;

	//Used to save times in second step if no stopping points were changed.
	private boolean stopsChanged;

	//Used to use a more lightweight method for modifying routes in database.
	private boolean stopsChangedOnEdit;

	private int[] initialDepartureTimes;

	public ControllerFormRoute(FormRoute formRoute, int objectID)
	{
		super();
		route = new Route();
		this.formRoute = formRoute;
		this.objectID = objectID;
		initialChanges = false;
		stopsChanged = false;
		stopsChangedOnEdit = false;

		//Sets the global route to the passed objectID
		if(objectID != -1)
		{
			setRouteInfo();
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_ROUTE_BACK:
				//Disabled when on first step, enabled on second.
				if (formRoute.getPanelCounter() == 1)
					formRoute.getBackButton().setEnabled(false);
				if (formRoute.getPanelCounter() == formRoute.getPanelMax())
					formRoute.getNextButton().setText("Weiter");
				formRoute.getCl().previous(formRoute.getCardPanel());
				formRoute.setPanelCounter(formRoute.getPanelCounter() - 1);
				break;

			case FORM_ROUTE_NEXT:
				if (formRoute.getPanelCounter() == 0)
				{
					//Step 1
					boolean confirmation = true;
					if(initialChanges && stopsChanged)
					{
						confirmation = formRoute.showConfirmDialog("Sie haben Änderungen an den Haltestellen vorgenommen. \nDadurch werden die Zeiten zwischen den Haltepunkten zurückgesetzt. \nFortfahren? (Zum Wiederherstellen des alten Zustands danach Abbrechen)");
					}
					if (confirmation)
					{
						//Check if data input is valid
						if (parseValuesFromFormRouteStep1())
						{
							formRoute.getBackButton().setEnabled(true);
							formRoute.getNextButton().setText("Fertig");

							//Array containing every selected stopping point name
							String[] busStops = new String[routeStoppingPoints.size()];

							for (int i = 0; i < busStops.length; i++)
							{
								busStops[i] = routeStoppingPoints.get(i).getSecond();
							}
							//only used when user applied changes to the selected stopping points
							if (stopsChanged)
							{
								//refreshes the form used to set the time between stopping points
								formRoute.getStep2().fillJpMain(busStops);
								stopsChanged = false;
							} else
							{
								//also refreshes form used to set the time between stopping points
								//and inserts preexisting values if it is used to change a route.
								if (objectID != -1 && !initialChanges)
								{
									formRoute.getStep2().fillJpMain(busStops);
									insertDepartureTimes();
								}
							}
							refreshTablesStep2();
							initialChanges = true;
							formRoute.getCl().next(formRoute.getCardPanel());
							formRoute.setPanelCounter(formRoute.getPanelCounter() + 1);
							break;
						}
					}
					break;
				}
				else if (formRoute.getPanelCounter() == formRoute.getPanelMax())
				{
					//Step 2
					//Checks if data input is valid
					if(parseValuesFromFormRouteStep2())
					{
						formRoute.setCursor(true);
						boolean save = true;
						if(stopsChangedOnEdit && objectID != -1)
						{
							save = formRoute.showConfirmDialog("Änderungen an der Linie führen dazu, dass alle Fahrten der Linie in der Datenbank zurückgesetzt werden.\nFortfahren? (Zum Wiederherstellen des alten Zustands danach abbrechen)");
						}
						//save new/changed route to DB
						if (save && saveToDB())
						{
							//Refreshes the table in TAB_ROUTE
							ControllerManager.informTableContentChanged(EmitterTable.TAB_ROUTE);
							ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
							ControllerManager.informTableContentChanged(EmitterTable.TAB_WORKPLAN);
							closeDialog();
						}
						formRoute.setCursor(false);
						formRoute.getCl().next(formRoute.getCardPanel());
						formRoute.setPanelCounter(formRoute.getPanelCounter() + 1);
						break;
					}
				}
				break;

			case FORM_ROUTE_CANCEL:
				closeDialog();
				break;

			case FORM_ROUTE_STEP1_UP:
				//Implements the up arrow button from Step1
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if (viewRow == -1)
					break;
				if (viewRow == 0)
					break;
				//Removes selected row and adds it above its previous predecessor
				Tuple<Integer, String> contentOld = contentTableCurrent.get(viewRow - 1);
				contentTableCurrent.remove(viewRow - 1);
				contentTableCurrent.add(viewRow, contentOld);
				initTableCurrent();
				//Moves selection to newly moved up row.
				tableCurrent.clearSelection();
				tableCurrent.changeSelection(viewRow - 1, 1, true, false);
				stopsChanged = true;
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP1_DOWN:
				//Implements the down arrow button from Step1
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if (viewRow == -1)
					break;
				if (viewRow == contentTableCurrent.size() -1)
					break;
				//Removes selected row and adds it beneath its successor
				Tuple<Integer, String> old = contentTableCurrent.get(viewRow + 1);
				contentTableCurrent.remove(viewRow + 1);
				contentTableCurrent.add(viewRow, old);
				initTableCurrent();
				//Changes selection to previously moved row.
				tableCurrent.changeSelection(viewRow + 1, 1, true, false);
				stopsChanged = true;
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP1_ADD:
				//Implements the left arrow button from Step1
				JTable tableAvailable = formRoute.getStep1().getBusStopAvailable();
				ExtendedTableModel modelTableAvailable = formRoute.getStep1().getModel_2();
				int viewRow = tableAvailable.getSelectedRow();
				if (viewRow == -1)
					break;
				int selectedRow = tableAvailable.convertRowIndexToModel(viewRow);
				int selectedID = (int) modelTableAvailable.getValueAt(selectedRow, 0);
				String selectedName = (String) modelTableAvailable.getValueAt(selectedRow, 1);
				//Adds selected stopping point to the list of content for the "table current".
				contentTableCurrent.add(new Tuple<>(selectedID, selectedName));
				//Refreshes "table current" with edited content list.
				initTableCurrent();
				stopsChanged = true;
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP1_DELETE:
				//Implements right arrow button from Step1
				tableCurrent = formRoute.getStep1().getBusStopCurrent();
				viewRow = tableCurrent.getSelectedRow();
				if(tableCurrent.getModel().getRowCount() == 0)
					break;
				if (viewRow == -1)
					break;
				//removes stopping point from list of content of "table current"
				contentTableCurrent.remove(viewRow);
				//Refreshes "table current" with new content list
				initTableCurrent();
				stopsChanged = true;
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP2_ADD:
				//Opens form to add a new departure time
				new FormDepartureTime(formRoute, route);
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP2_EDIT:
				//Opens form to change a departure time
				JTable activeEdit = formRoute.getStep2().getTableActive();
				if(activeEdit != null)
				{
					//Only edit time if a single one is selected
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
						//Displays edit dialog returning edited values
						Tuple<Integer, Integer> newTimeTuple = formRoute.getStep2().showEditDialog(editedHours, editedMinutes);
						if(newTimeTuple != null)
						{
							//sets edited time to the new value
							tempTimes.remove((Object) editedTime);
							int newTime = newTimeTuple.getFirst() * 60 + newTimeTuple.getSecond();
							tempTimes.add(newTime);
							route.getStartTimes().put(formRoute.getStep2().getActiveDay(), tempTimes);
							refreshTablesStep2();
						}
					}
				}
				stopsChangedOnEdit = true;
				break;

			case FORM_ROUTE_STEP2_DELETE:
				//Removes departure times from the table
				JTable activeDel = formRoute.getStep2().getTableActive();
				DayOfWeek activeDelDay = formRoute.getStep2().getActiveDay();
				int[] selectedRows = activeDel.getSelectedRows();
				//User has to confirm his action
				if(formRoute.getStep2().showDeleteDialog() == JOptionPane.YES_OPTION)
				{
					if (activeDel != null)
					{
						if (selectedRows.length != 0)
						{
							//each selected cell gets removed
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
				stopsChangedOnEdit = true;
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{
		//This method is used to make sure that only one table in the currently active step is selectable.
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
	 * Fetch route object from DB
	 */
	private void setRouteInfo()
	{
		route = ControllerDatabase.getInstance().getRouteById(objectID);
		int[] times = new int[route.getStops().size() - 1];
		for (int i = 0; i < times.length; i++)
		{
			times[i] = route.getStops().get(i+1).getThird();
		}
		initialDepartureTimes = times;
	}

	/**
	 * Inserts the values of the Route which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formRoute.getStep1().setNameRoute(route.getName());
			formRoute.getStep1().setNightLine(route.isNight());
			for (Triple<BusStop,StoppingPoint,Integer> stops : route.getStops())
			{
				String name = ControllerDatabase.getInstance().getCompleteStoppingPointName(stops.getSecond().getId());
				contentTableCurrent.add(new Tuple<>(stops.getSecond().getId(),name));
			}
			initTableCurrent();
		}
	}

	/**
	 * Used when a route is edited. This method inserts the existing departure times into the form.
	 */
	private void insertDepartureTimes()
	{
		if(objectID != -1)
		{
			int[] times = new int[route.getStops().size() - 1];
			for (int i = 0; i < times.length; i++)
			{
				times[i] = route.getStops().get(i + 1).getThird();
			}
			formRoute.getStep2().setDepartureTimes(times);
			refreshTablesStep2();
		}
	}

	/**
	 * Saves the current route to the DB.
	 * @return true on success
	 */
	private boolean saveToDB()
	{
		if(objectID == -1)
			ControllerDatabase.getInstance().addRoute(route);
		else
			ControllerDatabase.getInstance().modifyRoute(route, stopsChangedOnEdit);
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

		//Checking if input is valid
		ArrayList<String> errorFields = new ArrayList<>();
		if(name == null)
			errorFields.add("Ungültige Eingabe des Namen. Es wurden illegale Zeichen verwendet.");
		else if(name.trim().length() == 0)
			errorFields.add("Der Name darf nicht leer sein.");
		else if (name.trim().length() < 1)
			errorFields.add("Der Name muss mindestens 1 Zeichen umfassen.");
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
	 * @return Returns false on wrong input.
	 */
	private boolean parseValuesFromFormRouteStep2()
	{
		int[] departureTimes = formRoute.getStep2().getDepartureTime();

		if(!Arrays.equals(departureTimes,initialDepartureTimes))
			stopsChangedOnEdit = true;

		//Checks if input is valid
		ArrayList<String> errorFields = new ArrayList<>();
		//Checks if all departure times are set
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
			//Converts list of stopping points to LinkedList<Triple<BusStop, StoppingPoint, Integer>>
			//which is needed to set the locas routes stopping points.
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stoppingPoints = new LinkedList<>();
			int id = routeStoppingPoints.get(0).getFirst();
			BusStop busStop = ControllerDatabase.getInstance().getBusStopByStoppingPointId(id);
			StoppingPoint stoppingPoint = ControllerDatabase.getInstance().getStoppingPointById(id);
			int time = 0;
			stoppingPoints.add(new Triple<>(busStop,stoppingPoint,time));
			for (int i = 0; i < departureTimes.length; i++)
			{
				id = routeStoppingPoints.get(i+1).getFirst();
				busStop = ControllerDatabase.getInstance().getBusStopByStoppingPointId(id);
				stoppingPoint = ControllerDatabase.getInstance().getStoppingPointById(id);
				time = departureTimes[i];
				stoppingPoints.add(new Triple<>(busStop,stoppingPoint,time));
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
		ArrayList<Tuple<Integer, String>> stoppingPoints = ControllerDatabase.getInstance().getBusStopNamesWithStoppingPointNames();

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

	/**
	 * Refreshes the tables in the second step displaying the departure times.
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

		//Clears tables containing departure times
		for (Tuple<DayOfWeek, DefaultTableModel> model : models)
		{
			model.getSecond().setRowCount(0);
		}

		HashMap<DayOfWeek,LinkedList<Integer>> startTimes = route.getStartTimes();
		//Each table is filled with existing departure times
		for (Tuple<DayOfWeek, DefaultTableModel> model : models)
		{
			LinkedList<Integer> times = startTimes.get(model.getFirst());
			//Sorting list
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

			//Formats the time values into XX:XX strings and adds them to current table.
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
	public void windowClose(EmitterWindow wd, int objectID)
	{

	}

	@Override
	public void displaySwitch(EmitterDisplay dp)
	{

	}
}
