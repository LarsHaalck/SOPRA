package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormBusStopPrint;
import javafx.scene.paint.Stop;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Patricia on 10.03.2015.
 */
public class ControllerFormBusStopPrint extends Controller implements ListenerButton
{
	private int objectId;
	private FormBusStopPrint formBusStopPrint;
	private ControllerDatabase controllerDatabase;

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
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
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

	public void printBusStop(){
		ArrayList<Tuple<JCheckBox, Integer>> stops = formBusStopPrint.getStops();
		for(Tuple<JCheckBox, Integer> stop: stops)
		{
			if(stop.getFirst().isSelected())
			{
				ControllerManager.informPrintRequested(EmitterPrint.STOPPING_POINT, new ArrayList<Integer>());
			}
		}
	}

	private void closeDialog()
	{
		formBusStopPrint.dispose();
		removeListeners();
	}

	public ArrayList<StoppingPoint> getStoppingPoints()
	{
		return controllerDatabase.getStoppingPointsByBusStopId(objectId);
	}
}
