package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

import java.util.ArrayList;

/**
 * Listener used to manage print-request
 */
public interface ListenerPrint
{
	/**
	 * Implements action for a print-request
	 * @param emitter What has to print.
	 * @param objectId Corresponding id to print-job.
	 */
	public abstract void printDocument(EmitterPrint emitter, int objectId);

	/**
	 * Implements action for a print-request
	 * @param emitter What has to print.
	 * @param objectIds Corresponding ids to print-job.
	 */
	public abstract void printDocument(EmitterPrint emitter, ArrayList<Integer> objectIds);
}
