package de.uni_muenster.sopra2015.gruppe8.octobus.controller.print;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerPrint;

/**
 * Controller will handle every print-request
 */
public class ControllerPrint implements ListenerPrint
{

	@Override
	public void printDocument(EmitterPrint emitter, int objectId)
	{
		switch (emitter)
		{
			case WORK_PLAN:
				break;
		}
	}
}
