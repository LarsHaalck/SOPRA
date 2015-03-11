package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBusStopPrint;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Controller for FormBusStopPrint class.
 */
public class ControllerFormBusStopPrint extends Controller implements ListenerButton
{
	private int objectId;
	private FormBusStopPrint formBusStopPrint;
	private ControllerDatabase controllerDatabase;

	/**
	 * Creates a controllerFormusStopPrint-object.
	 *
	 * @param formBusStopPrint form to be controlled
	 * @param objectId of the selected busStop
	 */
	public ControllerFormBusStopPrint(FormBusStopPrint formBusStopPrint, int objectId)
	{
		super();
		this.objectId = objectId;
		this.formBusStopPrint = formBusStopPrint;
		controllerDatabase = ControllerDatabase.getInstance();
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
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_BUS_STOP_PRINT_PRINT:
				printBusStop();
				closeDialog();
			break;
			case FORM_BUS_STOP_PRINT_CANCEL:
				closeDialog();
			break;
		}
	}

	/**
	 * Gives a printRequest to the controllerManager for an array with every selected stoppingPoint
	 */
	public void printBusStop(){
		ArrayList<Tuple<JCheckBox, Integer>> stops = formBusStopPrint.getStops();
		ArrayList<Integer> ids = new ArrayList<>();

		for(Tuple<JCheckBox, Integer> stop: stops)
		{
			if(stop.getFirst().isSelected())
			{
				ids.add(stop.getSecond());
			}
		}
		ControllerManager.informPrintRequested(EmitterPrint.STOPPING_POINT, ids);
	}

	/**
	 * Closes the form and removes all Listeners.
	 */
	private void closeDialog()
	{
		formBusStopPrint.dispose();
		removeListeners();
	}

	/**
	 * Gives an arrayList with all stoppingPoints of the selected busStop.
	 *
	 * @return arrayList of all stoppingPoints
	 */
	public ArrayList<StoppingPoint> getStoppingPoints()
	{
		return controllerDatabase.getStoppingPointsByBusStopId(objectId);
	}
}
