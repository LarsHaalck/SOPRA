package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.LoginDialog;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerLoginForm implements ButtonListener
{
	LoginDialog dialog;
	ControllerMainFrame controllerMainFrame;

	public ControllerLoginForm(LoginDialog dialog)
	{
		this.dialog = dialog;
	}

	@Override
	public void buttonPressed(String emitter)
	{
		switch (emitter)
		{
			case "login":
				if (dialog.getUsername().equals("herbert") && dialog.getPassword().equals("octobus"))
				{
					if (controllerMainFrame != null)
						controllerMainFrame.buttonPressed("loginRequest");
					dialog.dispose();
				} else
				{
					dialog.illegalInput();
				}
				break;

			case "cancel":
				dialog.dispose();
				break;
		}
	}

	public void setListener(ControllerMainFrame controllerMainFrame)
	{
		this.controllerMainFrame = controllerMainFrame;
	}
}
