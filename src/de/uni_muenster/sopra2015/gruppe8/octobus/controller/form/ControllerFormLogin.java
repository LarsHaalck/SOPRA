package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormLogin;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterUserState;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Controller for the FormLogin class.
 */
public class ControllerFormLogin extends Controller implements ListenerButton
{
	FormLogin dialog;
    ControllerDatabase controllerDatabase;

	public ControllerFormLogin(FormLogin dialog)
	{
		super();
		this.dialog = dialog;
        controllerDatabase = controllerDatabase.getInstance();
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case FORM_LOGIN_LOGIN:

                String usernameFromForm = dialog.getUsername().trim();
                if (usernameFromForm.equals(""))
                {
                    dialog.illegalInput("Benutzername darf nicht leer sein!");
                    return;
                }

                String passwordFromForm = dialog.getPassword();
                if (passwordFromForm.equals(""))
                {
                    dialog.illegalInput("Passwort darf nicht leer sein!");
                    return;
                }

                Employee candidateEmployee = controllerDatabase.getEmployeeByUsername(usernameFromForm);

                if (candidateEmployee == null)
                {
                    dialog.illegalInput("Benutzer existiert nicht!");
                    return;
                }

                String candidateSalt = candidateEmployee.getSalt();
                String candidateHash = candidateEmployee.getPassword();

                String generatedHash;

                try
                {
                    MessageDigest digest = MessageDigest.getInstance("SHA-512");

                    digest.update(passwordFromForm.getBytes());
                    digest.update(candidateSalt.getBytes());

                    generatedHash = new BigInteger(1, digest.digest()).toString();

                } catch (NoSuchAlgorithmException e)
                {
                    throw new UnsupportedOperationException(e);
                }

                if (generatedHash.equals(candidateHash))
				{
					ControllerManager.informUserStateChanged(EmitterUserState.LOGGED_IN);
					ControllerManager.informUserStateChanged(EmitterUserState.RIGHTS_CHANGED, candidateEmployee.getId());
					removeListeners();
					dialog.dispose();
				} else
				{
					dialog.illegalInput("Falsches Kennwort!");
				}
				break;

			case FORM_LOGIN_CANCEL:
				removeListeners();
				dialog.dispose();
				break;
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
