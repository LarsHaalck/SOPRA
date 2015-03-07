package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 * Listener used to manage opening and closing windows.
 */
public interface ListenerWindow
{
	/**
	 * Creates a new window.
	 * @param wd Window to be created.
	 */
	public void windowOpen(EmitterWindow wd);

	/**
	 * Creates a new window.
	 * @param wd Window to be created
	 * @param objectID Database-ID of an object that will be used in window.
	 */
	public void windowOpen(EmitterWindow wd, int objectID);


	/**
	 * Closes an existing window.
	 * @param wd Window to be closed.
	 */
	public void windowClose(EmitterWindow wd);

	/**
	 * Switch to a given display.
	 * @param dp Display to switch to.
	 */
	public void displaySwitch(EmitterDisplay dp);
}
