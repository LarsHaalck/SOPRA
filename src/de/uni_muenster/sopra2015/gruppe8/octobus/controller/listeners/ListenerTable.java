package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 * Listener used to manage some table-changes
 */
public interface ListenerTable {
	/**
	 * Implements the action happens after table-selection changed
	 * @param emitter Table
	 */
	public abstract void tableSelectionChanged(EmitterTable emitter);

	/**
	 * Implements the action that table-content has changed
	 * @param emitter Table
	 */
	public abstract void tableContentChanged(EmitterTable emitter);
}
