package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import javafx.scene.paint.Stop;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Patricia on 10.03.2015.
 */
public class ControllerFormBusStopPrint extends Controller implements ListenerButton
{
	private int objectId;
	private ControllerDatabase controllerDatabase;

	public ControllerFormBusStopPrint(int objectId)
	{
		super();
		this.objectId = objectId;
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
	public void buttonPressed(EmitterButton btn)
	{

	}

	public ArrayList<StoppingPoint> getStoppingPoints()
	{
		return controllerDatabase.getStoppingPointsByBusStopId(objectId);
	}
}
