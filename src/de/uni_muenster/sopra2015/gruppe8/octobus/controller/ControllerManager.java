package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;

import java.util.ArrayList;

/**
 * Created by Florian on 02.03.2015.
 */
public class ControllerManager
{
	private static ArrayList<ButtonListener> buttonListener;

	static
	{
		buttonListener = new ArrayList<>();
	}

	public static void informButtonPressed(String emitter)
	{
		ArrayList<ButtonListener> list = (ArrayList<ButtonListener>)buttonListener.clone();
		for(ButtonListener listener: list)
			listener.buttonPressed(emitter);
	}

	public static void addListener(ButtonListener listener)
	{
		buttonListener.add(listener);
	}

	public static void removeListener(ButtonListener listener)
	{
		buttonListener.remove(listener);
	}
}
