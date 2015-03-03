package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.*;
import jdk.nashorn.internal.codegen.Emitter;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Florian on 02.03.2015.
 */
public class ControllerManager
{
	private static ArrayList<ListenerButton> listenerButton;
	private static ArrayList<ListenerUserState> listenerUserState;
	private static ArrayList<ListenerWindow> listenerWindow;

	static
	{
		listenerButton = new ArrayList<>();
		listenerUserState = new ArrayList<>();
		listenerWindow = new ArrayList<>();
	}

	public static void informButtonPressed(EmitterButton emitter)
	{
		ArrayList<ListenerButton> list = (ArrayList<ListenerButton>) listenerButton.clone();
		for (ListenerButton listener : list)
			listener.buttonPressed(emitter);
	}

	public static void informUserStateChanged(EmitterUserState emitter)
	{
		ArrayList<ListenerUserState> list = (ArrayList<ListenerUserState>) listenerUserState.clone();
		for (ListenerUserState listener : list)
			listener.userStateChanged(emitter);
	}

	public static void informWindowOpen(EmitterWindow emitter)
	{
		ArrayList<ListenerWindow> list = (ArrayList<ListenerWindow>) listenerWindow.clone();
		for (ListenerWindow listener : list)
			listener.windowOpen(emitter);
	}

	public static void informWindowClose(EmitterWindow emitter)
	{
		ArrayList<ListenerWindow> list = (ArrayList<ListenerWindow>) listenerWindow.clone();
		for (ListenerWindow listener : list)
			listener.windowClose(emitter);
	}



	public static void addListener(ListenerButton listener)
	{
		if(!listenerButton.contains(listener))
			listenerButton.add(listener);
	}

	public static void removeListener(ListenerButton listener)
	{
		listenerButton.remove(listener);
	}

	public static void addListener(ListenerUserState listener)
	{
		if(!listenerUserState.contains(listener))
			listenerUserState.add(listener);
	}

	public static void removeListener(ListenerUserState listener)
	{
		listenerUserState.remove(listener);
	}

	public static void addListener(ListenerWindow listener)
	{
		if(!listenerWindow.contains(listener))
			listenerWindow.add(listener);
	}

	public static void removeListener(ListenerWindow listener)
	{
		listenerWindow.remove(listener);
	}
}
