package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourSanity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Controller for FormTourSanity class.
 * @pre User is logged in and has Schedule-Planner-Role.
 */
public class ControllerFormTourSanity extends Controller implements ListenerButton
{
	private FormTourSanity formTourSanity;
	private ControllerDatabase controllerDatabase;

	public ControllerFormTourSanity(FormTourSanity formTourSanity)
	{
		super();

		this.formTourSanity = formTourSanity;
		this.controllerDatabase = ControllerDatabase.getInstance();
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case FORM_TOUR_SANITY_BACK:
				close();
				break;
		}
	}

	/**
	 * Fills the form with data for the enxt two weeks.
	 */
	public void fillForm()
	{
		ArrayList<Tuple<String, Integer>> data = new ArrayList<>();
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd.MM.", Locale.GERMANY);

		for(int i=0; i<14; i++)
		{
			Date curDay = new Date(today.getTime() + i*(1440*60000));
			int num = controllerDatabase.getNumberOfUnplannedToursByDate(curDay);
			data.add(new Tuple<>(sdf.format(curDay),num));
		}

		formTourSanity.setSanityInfo(data);
	}

	public void setDate(int day)
	{
		close();
		ControllerManager.informWindowClose(EmitterWindow.TAB_SCHEDULE_SET_DAY, day);
	}

	/**
	 * Closes current dialog.
	 */
	private void close()
	{
		removeListeners();
		formTourSanity.dispose();
	}
}
