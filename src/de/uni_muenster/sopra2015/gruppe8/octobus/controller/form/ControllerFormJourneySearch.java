package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormJourneySearch;

import javax.swing.event.ListSelectionListener;
import java.util.HashSet;


/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerFormJourneySearch extends Controller implements ListenerButton, ListenerTable
{
    //Dialog
    FormJourneySearch journeyDialog;

    //Variables
    private BusStop origin;
    private BusStop destination;

    @Override
    public void buttonPressed(EmitterButton emitter) {

        switch (emitter)
        {
            case FORM_SEARCH_SEARCH:
                //Don't do anything yet.
                journeyDialog.getRightParentGridPanel().setVisible(true);
                return;
            case FORM_SEARCH_EARLIER:
                //Look for earlier Journeys and add them to the table
                return;
            case FORM_SEARCH_FIRST:
                //Look for the first Journey and add it to the table
                //night != true
                return;
            case FORM_SEARCH_LATER:
                //Look for later Journeys and add them to the table
                return;
            case FORM_SEARCH_LAST:
                //Look for the last Journey and add it to the table
                return;

        }

    }

    private void lookForJourneys(){
        String originName = journeyDialog.getOrigin();
        origin = getBusStop(originName);
        String destinationName = journeyDialog.getDestination();
        destination = getBusStop(destinationName);
    }


    //BusStops are distinguished by IDs. How to find BusStops by name?

    /**
     * Looks for the BusStop specified by its name in the DB and returns
     * this (and only one?) BusStop.
     * @param name name of the BusStop
     * @return the BusStop
     * @throws IllegalArgumentException if no such BusStop can be found
     */
    private BusStop getBusStop(String name) throws IllegalArgumentException {
        return new BusStop(name, new Tuple<>(1, 1), new HashSet<String>(), true);
    }


    @Override
    protected void addListeners()
    {
        ControllerManager.addListener((ListenerButton) this);
    }

    @Override
    protected void removeListeners()
    {
        ControllerManager.removeListener((ListenerButton)this);
    }


    @Override
    public void tableSelectionChanged(EmitterTable emitter) {
        switch(emitter)
        {
            case FORM_JOURNEY_SEARCH_RESULT:
                break;
        }
    }
}
