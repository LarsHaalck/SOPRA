package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.TupleIntString;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormTourResetEmployee;

import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for FormTourResetEmployee class.
 */
public class ControllerFormTourResetEmployee extends Controller implements ListenerButton
{
	private FormTourResetEmployee formTourResetEmployee;
	private Date startDate;
	private Date endDate;
	private int employeeId;

	public ControllerFormTourResetEmployee(FormTourResetEmployee formTourResetEmployee)
	{
		this.formTourResetEmployee = formTourResetEmployee;
		ArrayList<TupleIntString> employeesFinal = new ArrayList<>();
		ArrayList<Employee> employeesDirect = ControllerDatabase.getInstance().getEmployeesByRole(Role.BUSDRIVER);
		for (Employee employee : employeesDirect)
		{
			employeesFinal.add(new TupleIntString(employee.getId(),employee.getName() + ", " + employee.getFirstName()));
		}
		formTourResetEmployee.fillEmployees(employeesFinal);
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
			case FORM_TOUR_RESET_EMPLOYEE_SAVE:
				if(parseValuesFromForm())
				{
					formTourResetEmployee.setCursor(true);
					if (saveToDB())
					{
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
						closeDialog();
					}
					formTourResetEmployee.setCursor(false);
				}
				break;

			case FORM_TOUR_RESET_EMPLOYEE_CANCEL:
				closeDialog();
				break;
		}
	}

	/**
	 * Removes all tours containing the selected bus driver.
	 * @return true if successful
	 */
	private boolean saveToDB()
	{
		ControllerDatabase.getInstance().deleteEmployeeFromTours(employeeId, startDate, endDate);
		return true;
	}

	/**
	 * Checks if form input is correct and parses them to local variables.
	 * @return Returns true on correct input.
	 */
	private boolean parseValuesFromForm()
	{
		Date tempStartDate = formTourResetEmployee.getDateStart();
		Date tempEndDate = formTourResetEmployee.getDateEnd();
		TupleIntString temployee = formTourResetEmployee.getEmployee();

		ArrayList<String> errorFields = new ArrayList<>();
		if(tempStartDate == null)
			errorFields.add("Ungültige Eingabe der Startzeit.");
		if(tempEndDate == null)
			errorFields.add("Ungültige Eingabe der Endzeit.");

		if(errorFields.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorFields);
			formTourResetEmployee.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			startDate = tempStartDate;
			endDate = tempEndDate;
			employeeId = temployee.getFirst();
			return true;
		}
	}


	/**
	 * Closes the current Dialog.
	 */
	private void closeDialog()
	{
		removeListeners();
		formTourResetEmployee.dispose();
	}
}
