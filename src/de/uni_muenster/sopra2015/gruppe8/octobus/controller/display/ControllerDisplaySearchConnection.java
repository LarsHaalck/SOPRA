package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplaySearchConnection;


/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerDisplaySearchConnection extends Controller implements ListenerButton, ListenerTable
{
    //Dialog
    DisplaySearchConnection journeyDialog;

    //Variables
    private BusStop origin;
    private BusStop destination;

    public ControllerDisplaySearchConnection(DisplaySearchConnection journeyDialog)
    {
        this.journeyDialog = journeyDialog;
    }

    @Override
    public void buttonPressed(EmitterButton emitter) {

        switch (emitter)
        {
            case DISPLAY_CONNECTION_SEARCH:
                //TODO: Functionality to look for Journeys
                //Don't do anything yet.
                journeyDialog.modifyRightGridPanel();
                //journeyDialog.getRightParentGridPanel().revalidate();
                //journeyDialog.getRightParentGridPanel().repaint();
                break;
            case DISPLAY_CONNECTION_EARLIER:
                //Look for earlier Journeys and add them to the table
                break;
            case DISPLAY_CONNECTION_FIRST:
                //Look for the first Journey and add it to the table
                //night != true
                break;
            case DISPLAY_CONNECTION_LATER:
                //Look for later Journeys and add them to the table
                break;
            case DISPLAY_CONNECTION_LAST:
                //Look for the last Journey and add it to the table
                break;
			case DISPLAY_CONNECTION_BACK:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_MAIN);
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
                int index = journeyDialog.getTableSearchResults().getSelectionModel().getLeadSelectionIndex();
                int length = journeyDialog.getTableSearchResults().getColumnCount();
                String[] journeyData = new String[length];
                for (int i = 0; i < length; i++) {
                    journeyData[i] = (String) journeyDialog.getTableSearchResults().getValueAt(index, i);
                }
                journeyDialog.displayInformationInBox(journeyData);
                break;
        }
    }

	@Override
	public void tableContentChanged(EmitterTable emitter)
	{

	}

	@Override
	public void tableFocusLost(EmitterTable emitter)
	{

	}


}
