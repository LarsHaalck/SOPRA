package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

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

	public ControllerFormEmployee(FormEmployee formEmployee, int objectID)
	{
		super();
		this.formEmployee = formEmployee;
		this.objectID = objectID;
		if(objectID != -1)
		{
			setEmployeeByID(objectID);
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_EMPLOYEE_SAVE:
				//TODO:Save them
				closeDialog();
				break;

			case FORM_EMPLOYEE_CANCEL:
				closeDialog();
				break;
		}
	}

	private void setEmployeeByID(int objectID)
	{
		HashSet<Role> roles = new HashSet<>();
		roles.add(Role.BUSDRIVER);
		employee = new Employee("Name","Vorname", "Adresse", "19085", "Muenster", new Date(), "015834958345", "mail@mail.de",
				"username", "pass", "salt", "note", roles);
	}

	/**
	 * Inserts the values of the Bus which is going to
	 * be changed into the form.
	 */
	public void insertValuesIntoForm()
	{
		if(objectID != -1)
		{
			formEmployee.setName(employee.getFirstName());
			formEmployee.setLastNameText(employee.getName());
			formEmployee.setAddressText(employee.getAddress());
			formEmployee.setZipText(employee.getZipCode());
			formEmployee.setCityText(employee.getCity());
			formEmployee.setDateText(employee.getDateOfBirth().toString());
			formEmployee.setPhoneText(employee.getPhone());
			formEmployee.setUsernameText(employee.getUsername());
			formEmployee.setRemarkText(employee.getNote());


			/*formEmployee.setLicencePlateText(bus.getLicencePlate());
			formBus.setNumberOfSeatsText("" + bus.getNumberOfSeats());
			formBus.setStandingRoomText("" + bus.getStandingRoom());
			formBus.setManufacturerText(bus.getManufacturer());
			formBus.setModelText(bus.getModel());
			formBus.setNextInspectionDueText("" + bus.getNextInspectionDue());
			formBus.setArticulatedBusText(bus.isArticulatedBus());*/
		}
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
		//TODO: dispose it when its really a dialog
		//dialog.dispose();
		removeListeners();
	}
}
