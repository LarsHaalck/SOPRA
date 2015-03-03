package de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners;

/**
 * EmitterButton is an enum used to describe buttons.
 * Each button has an unique value consisting of the source class and his name
 */
public enum EmitterButton
{
	PANEL_PASSENGER_SHOW_NETWORK,
	PANEL_PASSENGER_SEARCH_CONNECTION,
	PANEL_PASSENGER_SHOW_TICKETS,
	PANEL_PASSENGER_LOGIN,

	PANEL_EMPLOYEE_LOGOUT,
	PANEL_EMPLOYEE_CHANGE_PASSWORD,

	FORM_LOGIN_LOGIN,
	FORM_LOGIN_CANCEL,

	FORM_CHANGE_PASSWORD_SAVE,
	FORM_CHANGE_PASSWORD_CANCEL,

	FORM_BUS_SAVE,
	FORM_BUS_CANCEL,

	FORM_BUS_STOP_SAVE,
	FORM_BUS_STOP_CANCEL,

	FORM_EMPLOYEE_SAVE,
	FORM_EMPLOYEE_CANCEL
}
