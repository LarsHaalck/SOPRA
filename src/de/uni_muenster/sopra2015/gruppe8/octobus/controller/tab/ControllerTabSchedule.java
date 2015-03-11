package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for TabSchedule class.
 */
public class ControllerTabSchedule extends Controller implements ListenerButton, ListenerTable
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
		ControllerManager.addListener((ListenerButton) this);
		ControllerManager.addListener((ListenerTable) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerTable) this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_SCHEDULE_EDIT:
				if(tabSchedule.getSelectedID() == -1)
					tabSchedule.showMessageDialog("Zum Bearbeiten bitte erst eine Fahrt auswählen.");
				else
					ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_EDIT, tabSchedule.getSelectedID());
				break;

			case TAB_SCHEDULE_FILTER:
				newFilterSelected();
				break;

			case TAB_SCHEDULE_RESET_EMPLOYEE:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_RESET_EMPLOYEE);
				break;

			case TAB_SCHEDULE_RESET_BUS:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_RESET_BUS);
				break;

			case TAB_SCHEDULE_SANITY:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_TOUR_SANITY);
				break;
		}
	}

	/**
	 * Used to fill table with tours for a certain date from DB.
	 */
	private void fillTable()
	{
		ImageIcon red = new ImageIcon("res/images/red.png");
		ImageIcon green = new ImageIcon("res/images/green.png");

        controllerDatabase.createTours(tabSchedule.getDateStart());
		ArrayList<Object[]> tours = controllerDatabase.getToursForDate(tabSchedule.getDateStart());

		Object data[][] = new Object[tours.size()][7];
		for (int i=0; i < tours.size(); i++)
		{
			Object[] content = tours.get(i);
			data[i][0] = content[0];
			data[i][1] = (content[4] == null || content[5] == null)? red : green;
			data[i][2] = content[1];
			data[i][3] = new TableDate((Date) content[2], TableDate.Type.TIME);
			data[i][4] = content[3];
			data[i][5] = content[4];
			data[i][6] = content[5];
		}
		tabSchedule.fillTable(data);
	}

	/**
	 * Fills the table after a new date is selected.
	 */
	private void newFilterSelected()
	{
		Date start = tabSchedule.getDateStart();
		//Check if start-date is invalid
		if(start == null)
			tabSchedule.showMessageDialog("Das Start-Datum liegt in keinem gültigen Format vor.");
		else {
			fillTable();
		}

	}

	@Override
	public void tableSelectionChanged(EmitterTable emitter)
	{

	}

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{
		switch(emitter)
		{
			case TAB_SCHEDULE:
				fillTable();
				break;
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}
}
