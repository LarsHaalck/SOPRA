package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

/**
 * Abstract Controller to ensure that each controller will add itself to the ControllerManager with its constructor
 * and that each controller has the ability to remove itself from the ControllerManager.getInstance().
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

}
