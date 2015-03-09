package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormBusStop extends Controller implements ListenerButton
{
	private FormBusStop formBusStop;
	ControllerDatabase controllerDatabase;
	BusStop busStop;
	int objectID;

	public ControllerFormBusStop(FormBusStop formBusStop, int objectID)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.formBusStop = formBusStop;
		this.objectID = objectID;
		if(objectID != -1)
		{
			setBusInfo();
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_BUS_STOP_SAVE:
				if(parseValuesFromForm())
				{
					if (saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_BUSSTOP);
						closeDialog();
					}
				}
				break;

			case FORM_BUS_STOP_CANCEL:
				closeDialog();
				break;

			case FORM_BUS_STOP_ADD_POINT:
				while(true)
				{
					String newAnswer = formBusStop.showNewStoppingPointDialog();
					if (newAnswer != null)
					{
						if (newAnswer.length() == 0)
						{
							formBusStop.showErrorForm("Bitte geben Sie einen Namen für den Haltepunkt ein.");
						} else
						{
							formBusStop.addStoppingPoint(newAnswer);
							break;
						}
					}
					else
					{
						break;
					}
				}
				break;

			case FORM_BUS_STOP_DELETE_POINT:
				if(formBusStop.getSelectedStoppingPoint() == -1)
					break;
				if(formBusStop.showDeleteStoppingPointDialog() == JOptionPane.YES_OPTION)
					formBusStop.removeStoppingPoint(formBusStop.getSelectedStoppingPoint());
				break;

			case FORM_BUS_STOP_EDIT_POINT:
				//Don't do anything if no stop-point is selected
				if(formBusStop.getSelectedStoppingPoint() == -1)
					break;
				while(true)
				{
					String newAnswer = formBusStop.showEditStoppingPointDialog(formBusStop.getSelectedStoppingPointName());
					if (newAnswer != null)
					{
						if (newAnswer.length() == 0)
						{
							formBusStop.showErrorForm("Bitte geben Sie einen Namen für den Haltepunkt ein.");
						} else
						{
							formBusStop.editStoppingPoint(formBusStop.getSelectedStoppingPoint(), newAnswer);
							break;
						}
					}
					else
					{
						break;
					}
				}
				break;
		}
	}
	/**
	 * Fetch a BusStop object from the DB.
	 */
	private void setBusInfo()
	{
		busStop = controllerDatabase.getBusStop(objectID);
	}

	/**
	 * Inserts the values of the BusStop which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formBusStop.setNameBusStop(busStop.getName());
			formBusStop.setLocationX(busStop.getLocation().getFirst());
			formBusStop.setLocationY(busStop.getLocation().getSecond());

			List list = new List();
			for(StoppingPoint p: busStop.getStoppingPoints())
				formBusStop.addStoppingPoint(p.getName());
			formBusStop.setBarrierFree(busStop.isBarrierFree());
		}
	}

	/**
	 *Checks if form input is correct and adds values to local busStop.
	 * @return Returns true on correct input.
	 */
	private boolean parseValuesFromForm()
	{
		if(objectID == -1)
		{
			busStop = new BusStop();
		}

		String name = formBusStop.getNameBusStop();
		int x = formBusStop.getLocationX();
		int y = formBusStop.getLocationY();

		HashSet<StoppingPoint> stoppingPoints = new HashSet<>();
		List listStoppingPoints = formBusStop.getStoppingPoints();
		for(int i = 0; i < listStoppingPoints.getItemCount(); i++)
		{
			StoppingPoint temp = new StoppingPoint();
			temp.setName(listStoppingPoints.getItem(i));
			stoppingPoints.add(temp);
		}
		boolean barrierFree = formBusStop.getBarrierFree();

		ArrayList<String> errorFields = new ArrayList<>();
		if(name == null)
			errorFields.add("Ungültige Eingabe des Namen. Es wurden illegale Zeichen verwendet.");
		else if(name.trim().length() == 0)
			errorFields.add("Das Kennzeichen darf nicht leer sein.");
		else if(name.trim().length() < 5)
			errorFields.add("Der Name muss mindestens 5 Zeichen umfassen.");
		if(x == -1)
			errorFields.add("Die X Koordinate darf nicht leer sein.");
		if(y == -1)
			errorFields.add("Die Y Koordinate darf nicht leer sein.");
		if(stoppingPoints.isEmpty())
			errorFields.add("Es muss mindestens ein Haltepunkt existieren");

		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formBusStop.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			busStop.setName(name);
			busStop.setLocation(new Tuple<>(x, y));
			busStop.setBarrierFree(barrierFree);
			busStop.setStoppingPoints(stoppingPoints);
			return true;
		}
	}

	/**
	 * Saves the current busStop to the DB.
	 * @return
	 */
	private boolean saveToDB()
	{
		if(objectID == -1)
			controllerDatabase.addBusStop(busStop);
		else
			controllerDatabase.modifyBusStop(busStop);
		return true;
	}

	@Override
	protected  void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	private void closeDialog()
	{
		formBusStop.dispose();
		removeListeners();
	}
}
