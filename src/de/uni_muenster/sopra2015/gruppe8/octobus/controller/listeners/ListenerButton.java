package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 *Listener used to manage button clicks.
 */
public interface ListenerButton
{
	/**
	 * Implements the action happening after a button is pressed.
	 * @param btn Pressed button.
	 */
	public void buttonPressed(EmitterButton btn);
}
