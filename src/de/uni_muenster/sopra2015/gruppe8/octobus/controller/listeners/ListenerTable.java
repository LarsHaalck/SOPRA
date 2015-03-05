package de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners;

/**
 * Created by sszie_000 on 05.03.2015.
 */
public interface ListenerTable {
    public abstract void tableSelectionChanged(EmitterTable emitter);
	public abstract void tableContentChanged(EmitterTable emitter);
}
