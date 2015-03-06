package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormEmployee extends Controller implements ListenerButton
{
	private FormEmployee formEmployee;
	private int objectID;
	private Employee employee;
	private ControllerDatabase controllerDatabase;

	public ControllerFormEmployee(FormEmployee formEmployee, int objectID)
	{
		super();
		this.controllerDatabase = ControllerDatabase.getInstance();
		this.formEmployee = formEmployee;
		this.objectID = objectID;
		if(objectID != -1)
		{
			getEmployeeFromDB();
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_EMPLOYEE_SAVE:
				if(parseValuesFromForm())
				{
					if(saveToDb())
					{
						if(objectID == -1) //only show if user is new user
							formEmployee.showInformationForm("Der Benutzer wurde erfolgreich angelegt.\nBitte teilen Sie ihm das Standardpaswort \"octobus\" mit.");
						ControllerManager.informTableContentChanged(EmitterTable.TAB_EMPLOYEE);
						closeDialog();
					}
				}
				break;

			case FORM_EMPLOYEE_CANCEL:
				closeDialog();
				break;
		}
	}

	private void getEmployeeFromDB()
	{
		if(objectID != -1)
		{
			employee = controllerDatabase.getEmployeeById(objectID);
		}
	}

	/**
	 * Inserts the values of the Bus which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formEmployee.setFirstName(employee.getFirstName());
			formEmployee.setLastName(employee.getName());
			formEmployee.setAddress(employee.getAddress());
			formEmployee.setZipCode(employee.getZipCode());
			formEmployee.setCity(employee.getCity());
			formEmployee.setBirthDate(employee.getDateOfBirth());
			formEmployee.setPhone(employee.getPhone());
			formEmployee.setUsername(employee.getUsername());
			formEmployee.setNote(employee.getNote());
			formEmployee.setEMail(employee.getEmail());
			formEmployee.setHRManager(employee.isRole(Role.HR_MANAGER));
			formEmployee.setBusDriver(employee.isRole(Role.BUSDRIVER));
			formEmployee.setNetworkPlaner(employee.isRole(Role.NETWORK_PLANNER));
			formEmployee.setScheduleManager(employee.isRole(Role.SCHEDULE_MANAGER));
			formEmployee.setTicketPlaner(employee.isRole(Role.TICKET_PLANNER));
		}
	}

	private boolean parseValuesFromForm()
	{
		if(objectID == -1)
		{
			employee = new Employee();
		}
		String firstName = formEmployee.getFirstName();
		String lastName = formEmployee.getLastName();
		String address = formEmployee.getAddress();
		String zipCode = formEmployee.getZipCode();
		String city = formEmployee.getCity();
		Date birthDate = formEmployee.getBirthDate();
		String phone = formEmployee.getPhone();
		String username = formEmployee.getUsername();
		String eMail = formEmployee.getEMail();
		String note = formEmployee.getNote();
		boolean hrManager = formEmployee.getHRManager();
		boolean busDriver = formEmployee.getBusDriver();
		boolean networkPlaner = formEmployee.getNetworkPlaner();
		boolean scheduleManager = formEmployee.getScheduleManager();
		boolean ticketPlaner = formEmployee.getTicketPlaner();

		ArrayList<String> errorList = new ArrayList<>();
		if(firstName.trim().length() == 0)
			errorList.add("Der Vorname darf nicht leer sein.");
		if(lastName.trim().length() == 0)
			errorList.add("Der Nachname darf nicht leer sein.");
		if(address.trim().length() == 0)
			errorList.add("Die Adresse darf nicht leer sein.");
		if(zipCode.trim().length() == 0)
			errorList.add("Die PLZ darf nicht leer sein.");
		if(city.trim().length() == 0)
			errorList.add("Die Stadt darf nicht leer sein.");
		if(birthDate == null)
			errorList.add("Das Geburtsdatum liegt in keinem gültigen Format vor.");
		if(username.trim().length() == 0)
			errorList.add("Der Benutzername darf nicht leer sein.");
		if(username.trim().length() < 3)
			errorList.add("Der Benutzername muss mehr als 3 Zeichen enthalten.");
		if(!hrManager && !busDriver && !networkPlaner && !scheduleManager && !ticketPlaner)
			errorList.add("Ein Benutzer muss mindestens einer Rolle zugeordnet sein.");

		if(errorList.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorList);
			formEmployee.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			employee.setFirstName(firstName);
			employee.setName(lastName);
			employee.setAddress(address);
			employee.setZipCode(zipCode);
			employee.setCity(city);
			employee.setDateOfBirth(birthDate);
			employee.setPhone(phone);
			employee.setUsername(username);
			employee.setEmail(eMail);
			employee.setNote(note);
			HashSet<Role> roles = new HashSet<>();
			if(hrManager)
				roles.add(Role.HR_MANAGER);
			if(busDriver)
				roles.add(Role.BUSDRIVER);
			if(networkPlaner)
				roles.add(Role.NETWORK_PLANNER);
			if(scheduleManager)
				roles.add(Role.SCHEDULE_MANAGER);
			if(ticketPlaner)
				roles.add(Role.TICKET_PLANNER);
			employee.setRoles(roles);
		}
		return true;
	}

	private boolean saveToDb()
	{
		if(objectID == -1)
			controllerDatabase.addEmployee(employee);
		else
			controllerDatabase.modifyEmployee(employee);

		return true;
	}

	@Override
	protected  void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	private void closeDialog()
	{
		formEmployee.dispose();
		removeListeners();
	}
}
