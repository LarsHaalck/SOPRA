package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabEmployee;

import java.util.ArrayList;

/**
 * Controller for TabEmployee class.
 * @pre User is logged in and has HR-Manager-Role
 */
public class ControllerTabEmployee extends Controller implements ListenerButton, ListenerTable
{
	int uid;
	private ControllerDatabase controllerDatabase;
	private TabEmployee tabEmployee;

	/**
	 * Constructor, saves parameter to attributes
	 * @param tabEmployee
	 * @param uid
	 * @pre uid is valid id in employee-db-table, corresponding employee has HR-Manager-Role.
	 */
	public ControllerTabEmployee(TabEmployee tabEmployee, int uid)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.tabEmployee = tabEmployee;
		this.uid = uid;
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case TAB_EMPLOYEE_NEW:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_EMPLOYEE_NEW);
				break;

			case TAB_EMPLOYEE_EDIT:
				if(tabEmployee.getSelectedID() != -1)
				{
					ControllerManager.informWindowOpen(EmitterWindow.FORM_EMPLOYEE_EDIT, tabEmployee.getSelectedID());
				}
				else
				{
					tabEmployee.showMessageDialog("Um einen Mitarbeiter zu bearbeiten wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;

			case TAB_EMPLOYEE_DELETE:
				if(tabEmployee.getSelectedID() != -1)
				{
					if (tabEmployee.getSelectedID() == uid)
					{
						tabEmployee.showMessageDialog("Sie können sich nicht selbst löschen!");
						break;
					}
					int num = controllerDatabase.getNumberOfToursUsingEmployeeId(tabEmployee.getSelectedID());
					String add = "";
					if(num > 0)
						add = " Der Mitarbeiter ist noch in "+num+" Fahrten eingeplant.";
					if(tabEmployee.showConfirmDialog("Wirklich löschen?"+add))
					{
						controllerDatabase.deleteEmployee(tabEmployee.getSelectedID());
						fillTable();
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
					}
				}
				else
				{
					tabEmployee.showMessageDialog("Um einen Mitarbeiter zu löschen wählen Sie bitte einen Eintrag aus der Tabelle.");
				}
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
		ControllerManager.addListener((ListenerTable)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerTable) this);
	}


	/**
	 * Used to fill table with employees from DB.
	 */
	public void fillTable()
	{
		ArrayList<Employee> employees = controllerDatabase.getEmployees();
		Object[][] data = new Object[employees.size()][9];
		for(int i=0; i<employees.size(); i++)
		{
			Employee employee = employees.get(i);
			data[i][0] = employee.getId();
			data[i][1] = employee.getName();
			data[i][2] = employee.getFirstName();
			data[i][3] = employee.getAddress();
			data[i][4] = employee.getZipCode();
			data[i][5] = employee.getCity();
			data[i][6] = parseDate(employee.getDateOfBirth());
			data[i][7] = employee.getEmail();
			data[i][8] = employee.getUsername();
		}
		tabEmployee.fillTable(data);
	}

	@Override
	public void tableSelectionChanged(EmitterTable emitter)
	{

	}

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{
		if(emitter == EmitterTable.TAB_EMPLOYEE)
		{
			fillTable();
		}
	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}

}
