package de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners;

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
	 * Closes an existing window.
	 * @param wd Window to be closed.
	 */
	public void windowClose(EmitterWindow wd);
}
