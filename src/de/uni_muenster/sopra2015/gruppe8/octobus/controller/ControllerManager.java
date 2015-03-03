package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ListenerButton;

import java.util.ArrayList;

/**
 * Created by Florian on 02.03.2015.
 */
public class ControllerManager
{
	private static ArrayList<ListenerButton> listenerButton;

	static
	{
		listenerButton = new ArrayList<>();
	}

	public static void informButtonPressed(String emitter)
	{
		ArrayList<ListenerButton> list = (ArrayList<ListenerButton>) listenerButton.clone();
		for (ListenerButton listener : list)
			listener.buttonPressed(emitter);
	}

	public static void addListener(ListenerButton listener)
	{
		listenerButton.add(listener);
	}

	public static void removeListener(ListenerButton listener)
	{
		listenerButton.remove(listener);
	}
}
