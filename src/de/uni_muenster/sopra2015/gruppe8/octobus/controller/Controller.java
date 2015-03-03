package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

/**
 * Created by Joshua on 03.03.2015.
 */
public abstract class Controller
{
	public Controller()
	{
		addListeners();
	}

	protected abstract void addListeners();
	protected abstract void removeListeners();

}
