package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 * Listener used to manage user state changes.
 */
public interface ListenerUserState
{
	/**
	 * Implements the actions happening after a change of the user state.
	 * @param emitter New user state.
	 */
	public void userStateChanged(EmitterUserState emitter);

	/**
	 * Implements the action happening after a change of the user state, specified by user-id.
	 * @param emitter New user state
	 * @param userId User id
	 */
	public void userStateChanged(EmitterUserState emitter, int userId);
}
