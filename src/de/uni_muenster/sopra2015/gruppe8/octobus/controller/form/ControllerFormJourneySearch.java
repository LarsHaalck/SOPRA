package de.uni_muenster.sopra2015.gruppe8.octobus.controller.form;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Triple;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormJourneySearch;

import java.text.Normalizer;
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

    public ControllerFormJourneySearch(FormJourneySearch journeyDialog)
    {
        this.journeyDialog = journeyDialog;
    }

    @Override
    public void buttonPressed(EmitterButton emitter) {

        switch (emitter)
        {
            case FORM_JOURNEY_SEARCH_SEARCH:
                //TODO: Functionality to look for Journeys
                //Don't do anything yet.
                journeyDialog.modifyRightGridPanel();
                //journeyDialog.getRightParentGridPanel().revalidate();
                //journeyDialog.getRightParentGridPanel().repaint();
                break;
            case FORM_JOURNEY_SEARCH_EARLIER:
                //Look for earlier Journeys and add them to the table
                break;
            case FORM_JOURNEY_SEARCH_FIRST:
                //Look for the first Journey and add it to the table
                //night != true
                break;
            case FORM_JOURNEY_SEARCH_LATER:
                //Look for later Journeys and add them to the table
                break;
            case FORM_JOURNEY_SEARCH_LAST:
                //Look for the last Journey and add it to the table
                break;

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
    private BusStop getBusStop(String name) {
		return null;
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
                int index = journeyDialog.getJourneySearchResultTable().getSelectionModel().getLeadSelectionIndex();
                int length = journeyDialog.getJourneySearchResultTable().getColumnCount();
                String[] journeyData = new String[length];
                for (int i = 0; i < length; i++) {
                    journeyData[i] = (String) journeyDialog.getJourneySearchResultTable().getValueAt(index, i);
                }
                journeyDialog.displayInformationInBox(journeyData);
                break;
        }
    }

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{

	}


}
