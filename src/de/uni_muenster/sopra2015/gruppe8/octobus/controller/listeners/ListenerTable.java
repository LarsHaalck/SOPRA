package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 * Listener used to manage some table-changes
 */
public interface ListenerTable {
	/**
	 * Implements the action happening after the table-selection changed.
	 * @param emitter Table
	 */
	public abstract void tableSelectionChanged(EmitterTable emitter);

	/**
	 * Implements the action happening after table-content has changed.
	 * @param emitter Table
	 */
	public abstract void tableContentChanged(EmitterTable emitter);

	/**
	 * Implements the action happening after table-focus has been lost.
	 * @param emitter Table
	 */
	public abstract void tableFocusLost(EmitterTable emitter);
}
