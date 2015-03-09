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
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormDepartureTime;
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
	private ArrayList<Tuple<Integer, String>> contentTableCurrent = new ArrayList<>();
	private JTable tableCurrent;
	private int viewRow;

	private FormDepartureTime formDepartureTime;
	private int departureTimeStart;
	private int departureTimeEnd;
	private boolean[] departureDays;
	private int departureFreqency;

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
				tableAvailabe.clearSelection();
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
				formDepartureTime = new FormDepartureTime(formRoute);
				formDepartureTime.setControllerFormRoute(this);
				break;

			case FORM_ROUTE_STEP2_EDIT:

				break;

			case FORM_ROUTE_STEP2_DELETE:

				break;

			case FORM_ROUTE_STEP2_DEPARTURE_SAVE:
				if(parseValuesFromFormDeparture() == 1)
				{

				}
				else if(parseValuesFromFormDeparture() == 2)
				{

				}
				break;

			case FORM_ROUTE_STEP2_DEPARTURE_CANCEL:

				break;
		}
	}

	/**
	 * Parses values from FormDepartureTime.
	 * @return Returns 0 on wrong input; 1 on only start time; 2 on start time, end time and frequency
	 */
	private int parseValuesFromFormDeparture()
	{
		System.out.println(formDepartureTime.toString());
		int freqency = formDepartureTime.getFnFrequency();

		boolean[] days = {formDepartureTime.getJcbMo(), formDepartureTime.getJcbDi(),
							formDepartureTime.getJcbMi(), formDepartureTime.getJcbDo(),
							formDepartureTime.getJcbFr(), formDepartureTime.getJcbSa(),
							formDepartureTime.getJcbSo()};

		ArrayList<String> errorFields = new ArrayList<>();
		if(formDepartureTime.getFnStartTime_Hour() == -1 || formDepartureTime.getFnStartTime_Minute() == -1)
			errorFields.add("Startzeit muss vollständig ausgefüllt sein..");
		if(formDepartureTime.getFnEndTime_Hour() != -1 && formDepartureTime.getFnEndTime_Minute() != -1)
		{
			if(freqency == -1)
				errorFields.add("Frequenz darf nicht leer sein, wenn eine Endzeit angegeben ist.");
		}
		else
		{
			if(freqency != -1)
				errorFields.add("Frequenz muss zusammen mit einer vollständigen Endzeit angegeben werden.");
		}
		boolean minDaysChecked = false;
		for (boolean day : days)
		{
			if(day)
				minDaysChecked = true;
		}
		if(!minDaysChecked)
			errorFields.add("Mindestens ein Tag muss ausgewählt sein.");

		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formDepartureTime.showErrorForm(errorMessage);
			return 0;
		}
		else
		{
			if(formDepartureTime.getFnEndTime_Hour() == -1 || formDepartureTime.getFnEndTime_Minute() == -1){
				departureTimeStart = formDepartureTime.getFnStartTime_Hour() * 60 + formDepartureTime.getFnStartTime_Minute();
				departureDays = days;
				return 1;
			}
			else
			{
				departureTimeStart = formDepartureTime.getFnStartTime_Hour() * 60 + formDepartureTime.getFnStartTime_Minute();
				departureTimeEnd = formDepartureTime.getFnEndTime_Hour() * 60 + formDepartureTime.getFnEndTime_Minute();
				departureFreqency = freqency;
				departureDays = days;
				return 2;
			}
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
		ArrayList<Tuple<Integer, String>> stoppingPoints = controllerDatabase.getBusStopNamesWithStoppingPointNames();

		Object[][] data = new Object[stoppingPoints.size()][2];
		for(int i=0; i<stoppingPoints.size(); i++)
		{
			data[i][0] = stoppingPoints.get(i).getFirst();
			data[i][1] =stoppingPoints.get(i).getSecond();
		}
		formRoute.getStep1().fillTableAvailable(data);
	}

	/**
	 * Initializes table Current with BusStops
	 */
	public void initTableCurrent()
	{
		Object[][] data = new Object[contentTableCurrent.size()][2];
		for(int i=0; i<data.length; i++)
		{
			BusStop busStop = controllerDatabase.getBusStopById(contentTableCurrent.get(i).getFirst());
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
