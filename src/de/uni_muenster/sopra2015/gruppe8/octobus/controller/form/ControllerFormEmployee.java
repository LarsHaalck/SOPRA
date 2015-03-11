package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormEmployee;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

/**
 * Controller for FormEmploye class.
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
							formEmployee.showInformationForm("Der Benutzer wurde erfolgreich angelegt." +
                                    "\nBitte teilen Sie ihm das Standardpaswort \"octobus\" mit.");

						if(objectID != -1)
							ControllerManager.informUserStateChanged(EmitterUserState.RIGHTS_CHANGED, employee.getId());
						closeDialog();
						ControllerManager.informTableContentChanged(EmitterTable.TAB_EMPLOYEE);
						ControllerManager.informTableContentChanged(EmitterTable.TAB_SCHEDULE);
					}
				}
				break;

			case FORM_EMPLOYEE_RESET_PASSWORD:
				// Take care of creating a salt and hashing the default password with that salt
				SecureRandom random = new SecureRandom();
				String salt = new BigInteger(130, random).toString(32);
				String password = "octobus";
				String generatedHash = "";

				try
				{
					MessageDigest digest = MessageDigest.getInstance("SHA-512");

					digest.update(password.getBytes());
					digest.update(salt.getBytes());

					generatedHash = new BigInteger(1, digest.digest()).toString();

				} catch (NoSuchAlgorithmException e)
				{
					throw new UnsupportedOperationException(e);
				}

				employee.setSalt(salt);
				employee.setPassword(generatedHash);

				controllerDatabase.modifyEmployee(employee);

				formEmployee.showInformationForm("Das Passwort des Benutzers wurde" +
                        " auf \"octobus\" zurückgesetzt!");
				break;

			case FORM_EMPLOYEE_CANCEL:
				closeDialog();
				break;
		}
	}

	/**
	 * Fetches employee from DB by objectID and sets global employee.
	 */
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

	/**
	 * Checks if input is valid and parses form data into global employee.
	 * @return true on valid input.
	 */
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
		if(firstName == null)
			errorList.add("Ungültige Eingabe des Vornamen. Es wurden illegale Zeichen verwendet.");
		else if(firstName.trim().length() == 0)
			errorList.add("Der Vorname darf nicht leer sein.");
		if(lastName == null)
			errorList.add("Ungültige Eingabe des Nachnamen. Es wurden illegale Zeichen verwendet.");
		else if(lastName.trim().length() == 0)
			errorList.add("Der Nachname darf nicht leer sein.");
		if(address == null)
			errorList.add("Ungültige Eingabe der Anschrift. Es wurden illegale Zeichen verwendet.");
		else if(address.trim().length() == 0)
			errorList.add("Die Adresse darf nicht leer sein.");
		if(zipCode == null)
			errorList.add("Ungültige Eingabe der PLZ. Es wurden illegale Zeichen verwendet.");
		else if(zipCode.trim().length() == 0)
			errorList.add("Die PLZ darf nicht leer sein.");
		if(city == null)
			errorList.add("Ungültige Eingabe der Stadt. Es wurden illegale Zeichen verwendet.");
		else if(city.trim().length() == 0)
			errorList.add("Die Stadt darf nicht leer sein.");
		if(birthDate == null)
			errorList.add("Das Geburtsdatum liegt in keinem gültigen Format vor oder liegt außerhalb des gültigen Bereichs.");
		if(eMail == null)
			errorList.add("Die E-Mail-Adresse ist ungültig.");
		if(phone == null)
			errorList.add("Die Telefonnummer ist ungültig.");
		if(username == null)
			errorList.add("Ungültige Eingabe des Benutzernamens. Es wurden illegale Zeichen verwendet.");
		if(note == null)
			errorList.add("Ungültige Eingabe der Notiz. Es wurden illegale Zeichen verwendet.");
		else if(username.trim().length() == 0)
			errorList.add("Der Benutzername darf nicht leer sein.");
		else if(username.trim().length() < 3)
			errorList.add("Der Benutzername muss mehr als 3 Zeichen enthalten.");
		else if(objectID == -1 && controllerDatabase.getEmployeeByUsername(username) != null)
			errorList.add("Es existiert bereits ein Benutzer mit der gleichen Kennung.");
		if(!hrManager && !busDriver && !networkPlaner && !scheduleManager && !ticketPlaner)
			errorList.add("Ein Benutzer muss mindestens einer Rolle zugeordnet sein.");
		else if (employee.isRole(Role.HR_MANAGER) && !hrManager)
			errorList.add("Sie können sich nicht selber die Personalleiterrechte entziehen.");


		if(errorList.size() > 0)
		{
			String errorMessage = "Die eingegeben Daten sind nicht gültig.\n";
			errorMessage += errorListToString(errorList);
			formEmployee.showErrorForm(errorMessage);
			return false;
		}
		else
		{
			//Check if there are tours planned for this user
			int num = controllerDatabase.getNumberOfToursUsingEmployeeId(employee.getId());
			if(num == 0 ||
					//If user was bus-driver and isn't it any more, show confirm-dialog because he will be deleted from every tour
					(employee.isRole(Role.BUSDRIVER) && !busDriver && formEmployee.showConfirmDialog("Der Mitarbeiter ist noch in "+num+" Routen eingeplant.\nWenn Sie die Busfahrer-Rolle entfernen, wird er aus den Touren gelöscht.")))
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

				return true;
			}
		}
		return false;
	}

	/**
	 * Saves globl employee to DB
	 * @return true on success
	 */
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

	/**
	 * Closes current dialog.
	 */
	private void closeDialog()
	{
		formEmployee.dispose();
		removeListeners();
	}
}
