package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Observer used to keep track of each active Controller and to manage interactions between them.
 */
public class ControllerManager
{
	private static ArrayList<ListenerButton> listenerButton = new ArrayList<>();
	private static ArrayList<ListenerUserState> listenerUserState = new ArrayList<>();
	private static ArrayList<ListenerWindow> listenerWindow = new ArrayList<>();
    private static ArrayList<ListenerTable> listenerTable = new ArrayList<>();
	private static ArrayList<ListenerPrint> listenerPrint = new ArrayList<>();

	/**
	 * Doesn't allow creating a single instance of ControllerManager
	 */
	private ControllerManager()
	{

	}

	/**
	 * Informs every active ListenerButton.
	 * @param emitter pressed Button.
	 */
	public static void informButtonPressed(EmitterButton emitter)
	{
		ArrayList<ListenerButton> list = new ArrayList<>(listenerButton);
		for (ListenerButton listener : list)
			listener.buttonPressed(emitter);
	}


	// following functions copy their corresponding ArrayList first, because event triggers could
	// lead to new listeners, which could lead to infinite loops!!

	/**
	 * Informs every active ListenerUserState.
	 * @param emitter changed UserState.
	 */
	public static void informUserStateChanged(EmitterUserState emitter)
	{
		ArrayList<ListenerUserState> list = new ArrayList<>(listenerUserState);
		for (ListenerUserState listener : list)
			listener.userStateChanged(emitter);
	}
	/**
	 * Informs every active ListenerUserState.
	 * @param emitter changed UserState.
	 */
	public static void informUserStateChanged(EmitterUserState emitter, int userId)
	{
		ArrayList<ListenerUserState> list = new ArrayList<>(listenerUserState);
		for (ListenerUserState listener : list)
			listener.userStateChanged(emitter, userId);
	}
	/**
	 * Informs every active ListenerWindow to open a new dialog window.
     *
	 * @param emitter window to open.
	 */
	public static void informWindowOpen(EmitterWindow emitter)
	{
		ArrayList<ListenerWindow> list = new ArrayList<>(listenerWindow);
		for (ListenerWindow listener : list)
			listener.windowOpen(emitter);
	}

	/**
	 * Informs every active ListenerWindow to open a new window.
	 *
	 * @param emitter window to open.
	 * @param objectID Database-ID that will be needed in the opened window.
	 */
	public static void informWindowOpen(EmitterWindow emitter, int objectID)
	{
		ArrayList<ListenerWindow> list = new ArrayList<>(listenerWindow);
		for (ListenerWindow listener : list)
			listener.windowOpen(emitter, objectID);
	}

	/**
	 * Informs every active ListenerWindow to close a certain window.
     *
	 * @param emitter window to be closed.
	 */
	public static void informWindowClose(EmitterWindow emitter)
	{
		ArrayList<ListenerWindow> list = new ArrayList<>(listenerWindow);
		for (ListenerWindow listener : list)
			listener.windowClose(emitter);
	}

	/**
	 * Informs every active ListenerTable that selection has changed.
	 *
	 * @param emitter table with changed selection
	 */
	public static void informTableSelectionChanged(EmitterTable emitter)
    {
        ArrayList<ListenerTable> list = new ArrayList<>(listenerTable);
        for(ListenerTable listener : list)
            listener.tableSelectionChanged(emitter);
    }

	/**
	 * Informs every active ListenerTable that content needs to update.
	 *
	 * @param emitter table that needs to update
	 */
	public static void informTableContentChanged(EmitterTable emitter)
	{
		ArrayList<ListenerTable> list = new ArrayList<>(listenerTable);
		for(ListenerTable listener : list)
			listener.tableContentChanged(emitter);
	}

	/**
	 * Informs every active ListenerPrint that manages print-requests
	 * @param emitter document that should be printed.
	 * @param objectId objectId corresponding to document.
	 */
	public static void informPrintRequested(EmitterPrint emitter, int objectId)
	{
		ArrayList<ListenerPrint> list = new ArrayList<>(listenerPrint);
		for(ListenerPrint listener : list)
			listener.printDocument(emitter, objectId);
	}

	/**
	 * Clears lists of listeners.
	 * Evil method, only use by user-state-change.
	 */
	public static void clearListeners()
	{
		listenerUserState.clear();
		listenerButton.clear();
		listenerWindow.clear();
        listenerTable.clear();
		listenerPrint.clear();
	}

	/**
	 * Adds a ListenerButton to the listenerButton list.
     *
	 * @param listener Listener to be added.
	 */
	public static void addListener(ListenerButton listener)
	{
		listenerButton.add(listener);
	}

	/**
	 * Removes a ListenerButton from the listenerButton list.
     *
	 * @param listener Listener to be removed.
	 */
	public static void removeListener(ListenerButton listener)
	{
		listenerButton.remove(listener);
	}

	/**
	 * Adds a ListenerUserState to the listenerUserState list.
     *
	 * @param listener Listener to be added.
	 */
	public static void addListener(ListenerUserState listener)
	{
		listenerUserState.add(listener);
	}

	/**
	 * Removes a ListenerUserState from the listenerUserState list.
     *
	 * @param listener Listener to be removed
	 */
	public static void removeListener(ListenerUserState listener)
	{
		listenerUserState.remove(listener);
	}

	/**
	 * Adds a ListenerWindow to the listenerWindow list.
     *
	 * @param listener Listener to be added.
	 */
	public static void addListener(ListenerWindow listener)
	{
		listenerWindow.add(listener);
	}

	/**
	 * Removes a ListenerWindow to the listenerWindow list.
     *
	 * @param listener Listener to be removed
	 */
	public static void removeListener(ListenerWindow listener)
	{
		listenerWindow.remove(listener);
	}

    /**
     * Adds a ListenerTable to the listenerTable list.
	 * 
     * @param listener Listener to be added.
     */
    public static void addListener(ListenerTable listener)
    {
        listenerTable.add(listener);
    }

	/**
	 * Removes a ListenerTable to the listenerTable list.
	 * 
	 * @param listener Listener to be removed.
	 */
	public static void removeListener(ListenerTable listener)
    {
        listenerTable.remove(listener);
    }

	/**
	 * Adds a ListenerPrint to the listenerPrint list.
	 *
	 * @param listener Listener to be added.
	 */
	public static void addListener(ListenerPrint listener)
	{
		listenerPrint.add(listener);
	}

	/**
	 * Removes a ListenerPrint to the listenerPrint list.
	 *
	 * @param listener Listener to be removed.
	 */
	public static void removeListener(ListenerPrint listener)
	{
		listenerPrint.remove(listener);
	}
}
