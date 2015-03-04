package de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners;

/**
 * Used to communicate with the ControllerFrameMain class.
 * Each value tells the ControllerMainFrame to open a certain JDialog.
 */
public enum EmitterWindow
{
	FORM_LOGIN,
	FORM_CHANGE_PASSWORD,

	FORM_BUS_NEW,
	FORM_BUS_EDIT,

	FORM_EMPLOYEE_NEW,
	FORM_EMPLOYEE_EDIT,

	FORM_BUS_STOP,
	FORM_EMPLOYEE,
	FORM_TICKET
}
