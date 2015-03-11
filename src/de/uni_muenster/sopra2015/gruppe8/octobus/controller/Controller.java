package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Abstract Controller to ensure that each controller will add itself to the ControllerManager with its constructor
 * and that each controller has the ability to remove itself from the ControllerManager.
 */
public abstract class Controller
{
	public Controller()
	{
		addListeners();
	}

	/**
	 * Adds every Listener implemented by the controller to ControllerManager
	 */
	protected abstract void addListeners();

	/**
	 * Removes every Listener implemented by the controller from ControllerManager
	 */
	protected abstract void removeListeners();

	/**
	 * Helper-methods. Implode all error-strings into one.
	 * String is separated by new lines.
	 * @param errors List of all error-strings.
	 * @return String with all error-strings.
	 */
	protected String errorListToString(ArrayList<String> errors)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("      ").append(errors.remove(0));
		for( String s : errors) {
			builder.append( "\n");
			builder.append("      ").append(s);
		}
		return builder.toString();
	}

	/**
	 * Parses date to string.
	 * @param d Date to parse.
	 * @return String.
	 */
	protected String parseDate(Date d)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.YYYY", Locale.GERMANY);
		return simpleDateFormat.format(d);
	}
}
