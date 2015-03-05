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
	 * Helper-method. Parses a string to an integer
	 * @param s String to parese.
	 * @return String as integer, or -1 if it fails
	 */
	protected int parseInt(String s)
	{
		try
		{
			int i = Integer.parseInt(s);
			return i;
		}
		catch (Exception e)
		{
			return -1;
		}
	}

	/**
	 * Helper-methods. Implode all error-strings into one.
	 * String is separated by new lines.
	 * @param errors List of all error-strings.
	 * @return String with all error-strings.
	 */
	protected String errorListToString(ArrayList<String> errors)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("      " + errors.remove(0));
		for( String s : errors) {
			builder.append( "\n");
			builder.append("      "+s);
		}
		return builder.toString();
	}


	/**
	 * Parses string into date
	 * @param s String to parse.
	 * @return Date.
	 */
	protected Date parseDate(String s)
	{
		try
		{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d.M.Y", Locale.GERMANY);
			Date d = simpleDateFormat.parse(s);
			return d;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
