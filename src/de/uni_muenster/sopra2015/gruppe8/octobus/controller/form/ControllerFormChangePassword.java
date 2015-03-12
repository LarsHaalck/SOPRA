package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormChangePassword;

import java.awt.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Controller for the FormChangePassword class.
 * @pre User is logged in
 */
public class ControllerFormChangePassword extends Controller implements ListenerButton
{
	FormChangePassword dialog;
	ControllerDatabase controllerDatabase;
	Employee employee;

	public ControllerFormChangePassword(FormChangePassword dialog, int userId)
	{
		super();
		this.controllerDatabase = ControllerDatabase.getInstance();
		this.dialog = dialog;
		if(userId <= 0)
		{
			removeListeners();
			dialog.dispose();
		}
		else
		{
			employee = controllerDatabase.getEmployeeById(userId);
		}
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch(emitter)
		{
			case FORM_CHANGE_PASSWORD_CANCEL:
				dialog.dispose();
				removeListeners();
				break;

			case FORM_CHANGE_PASSWORD_SAVE:
				dialog.setCursor(new Cursor(Cursor.WAIT_CURSOR));
				String employeeSalt = employee.getSalt();
				String employeeHash = employee.getPassword();

				String generatedHash;

				try
				{
					MessageDigest digest = MessageDigest.getInstance("SHA-512");

					digest.update(dialog.getOldPassword().getBytes());
					digest.update(employeeSalt.getBytes());

					generatedHash = new BigInteger(1, digest.digest()).toString();

				} catch (NoSuchAlgorithmException e)
				{
					throw new UnsupportedOperationException(e);
				}

				boolean oldPasswordInvalid = !generatedHash.equals(employeeHash);
				boolean newPasswordInvalid = (dialog.getNewPassword().length() < 5);
				boolean newPasswordCorrectInvalid = (!dialog.getNewPasswordCorrect().equals(dialog.getNewPassword()));

				dialog.illegalChanges(oldPasswordInvalid, newPasswordInvalid, newPasswordCorrectInvalid);
				if (!oldPasswordInvalid && !newPasswordInvalid && !newPasswordCorrectInvalid)
				{
					// Take care of creating a salt and hashing the default password with that salt
					SecureRandom random = new SecureRandom();
					String salt = new BigInteger(130, random).toString(32);

					try
					{
						MessageDigest digest = MessageDigest.getInstance("SHA-512");

						digest.update(dialog.getNewPassword().getBytes());
						digest.update(salt.getBytes());

						generatedHash = new BigInteger(1, digest.digest()).toString();

					} catch (NoSuchAlgorithmException e)
					{
						throw new UnsupportedOperationException(e);
					}
					employee.setSalt(salt);
					employee.setPassword(generatedHash);

					controllerDatabase.modifyEmployee(employee);

					dialog.dispose();
					removeListeners();
					dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					break;
				}
				dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
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
}
