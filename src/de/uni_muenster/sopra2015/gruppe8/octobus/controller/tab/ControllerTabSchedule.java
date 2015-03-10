package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabSchedule;

import java.util.Date;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabSchedule extends Controller implements ListenerButton
{
	private TabSchedule tabSchedule;
	private ControllerDatabase controllerDatabase;

	public ControllerTabSchedule(TabSchedule tabSchedule)
	{
		super();
		this.tabSchedule = tabSchedule;
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
		ControllerManager.removeListener((ListenerButton) this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_SCHEDULE_EDIT:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_EDIT, -1);
				break;

			case TAB_SCHEDULE_FILTER:
				newFilterSelected();
				break;
		}
	}

	public void fillTable()
	{

	}

	private void newFilterSelected()
	{
		Date start = tabSchedule.getDateStart();
		boolean onlyUnassigned = tabSchedule.getOnlyUnassigned();

		//Check if start-date is invalid
		if(start == null)
			tabSchedule.showMessageDialog("Das Start-Datum liegt in keinem gültigen Format vor.");
		else {
			//TODO: Get them from db
		}

	}
}
