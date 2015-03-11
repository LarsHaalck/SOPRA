package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerGraph;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplaySearchConnection;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelSearchConnection;

import java.time.DayOfWeek;
import java.util.LinkedList;


/**
 * Controller for DisplaySearchConnection class.
 */
public class ControllerDisplaySearchConnection extends Controller implements ListenerButton, ListenerTable
{
    //Dialog
    private DisplaySearchConnection journeyDialog;
    private ControllerGraph cg;
    private ControllerDatabase db;


    //Variables
    private BusStop origin;
    private BusStop destination;
    private int time;
    private int latestTime;
    private Connection earliestConnection;
    private LinkedList<Connection> connectionsFound;

    public ControllerDisplaySearchConnection(DisplaySearchConnection journeyDialog)
    {
        this.journeyDialog = journeyDialog;
        this.cg = new ControllerGraph();
        db = ControllerDatabase.getInstance();
        this.connectionsFound = new LinkedList<>();
    }

    @Override
    public void buttonPressed(EmitterButton emitter) {

        switch (emitter)
        {
            case DISPLAY_CONNECTION_SEARCH:
                searchJourney();
                break;
            case DISPLAY_CONNECTION_EARLIER:
                //Look for earlier Journeys and add them to the table
                int currentEarliest = earliestConnection.getTime();
                Connection currentConnection = earliestConnection;
                while (currentConnection.equals(earliestConnection) && currentEarliest > 0)
                {
                    currentEarliest--;
                    currentConnection = cg.getConnection(origin.getId(), destination.getId(), DayOfWeek.MONDAY, currentEarliest);
                }
                if (earliestConnection.equals(currentConnection)) break;
                earliestConnection = currentConnection;
                journeyDialog.addFirstConnectionAndUpdateTable(currentConnection);
                break;
            case DISPLAY_CONNECTION_FIRST:
                //Look for the first Journey and add it to the table
                if (! (origin == null || destination == null))
                    journeyDialog.addFirstConnectionAndUpdateTable(earliestConnection = cg.getConnection(origin.getId(), destination.getId(), DayOfWeek.MONDAY, 0));

                break;
            case DISPLAY_CONNECTION_LATER:
                //Look for later Journeys and add them to the table
                latestTime++;
                if (!(latestTime < 1440)) break;
                Connection currentConnectionSearch = cg.getConnection(origin.getId(), destination.getId(), DayOfWeek.MONDAY, latestTime);
                journeyDialog.addLastConnectionAndUpdateTable(currentConnectionSearch);
                connectionsFound.add(currentConnectionSearch);
                latestTime = currentConnectionSearch.getTime();
                break;
            case DISPLAY_CONNECTION_LAST:
                //Look for the last Journey and add it to the table
                break;
			case DISPLAY_CONNECTION_BACK:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_MAIN);
				break;
        }
    }

	//TODO JavaDoc
	/**
	 *
	 */
	private void searchJourney(){
        int orig = journeyDialog.getOrigin().getNumber();
        int dest = journeyDialog.getDestination().getNumber();
        ((TableModelSearchConnection)journeyDialog.getTableSearchResults().getModel()).clearTableModel();
        origin = db.getBusStopById(orig);
        destination = db.getBusStopById(dest);
        time = journeyDialog.getTime();
        if (! (origin == null || destination == null))
        {

            //TODO: Change DayOfWeek.MONDAY
            Connection currentConnectionSearch = cg.getConnection(origin.getId(), destination.getId(), DayOfWeek.MONDAY, time);
            if (currentConnectionSearch == null ) return;
            journeyDialog.modifyRightGridPanel();
            earliestConnection = currentConnectionSearch;
            journeyDialog.addLastConnectionAndUpdateTable(currentConnectionSearch);
            connectionsFound.add(currentConnectionSearch);
            latestTime = currentConnectionSearch.getTime();
        }

    }


    //BusStops are distinguished by IDs. How to find BusStops by name?
	//TODO clean up
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

                String result = "";
                int rowSelected = journeyDialog.getTableSearchResults().getSelectedRow();
                LinkedList<Quintuple<Integer, StoppingPoint, Route, StoppingPoint, Integer>> trips = connectionsFound.get(rowSelected).getTrips();
                System.out.println(trips.size());
                for (Quintuple<Integer, StoppingPoint, Route, StoppingPoint, Integer> trip : trips) {
                    StoppingPoint spFirst = trip.getSecond();
                    StoppingPoint spSecond = trip.getFourth();
                    String bsFirstName = db.getBusStopByStoppingPointId(spFirst.getId()).getName();
                    String bsSecondName = db.getBusStopByStoppingPointId(spSecond.getId()).getName();
                    String spFirstName = spFirst.getName();
                    String spSecondName = spSecond.getName();
                    String routeName = trip.getThird().getName();

                    String s =
                            formatTime(trip.getFirst() / 60, trip.getFirst() % 60)
                                    + " ab " + bsFirstName + " Bstg. " + spFirstName
                                    + "\n              " //2 TABs 4 TABS
                                    + routeName
                                    + "\n"
                                    + formatTime(trip.getFifth() / 60, trip.getFifth() % 60)
                                    + " nach "
                                    + bsSecondName + " Bstg. " + spSecondName + "\n----------------------------------------\n";
                    result = result + s;
                }
                journeyDialog.showSelectedConnection(result);
                break;
        }
    }

	/**
	 * Formats input into XX:XX String .
	 * @param hours hours to be formatted.
	 * @param minutes minutes tobe formatted.
	 * @return String with formatted time.
	 */
	private String formatTime(int hours, int minutes){
        String hourString;
        String minuteString;
        if (hours < 10)
            hourString = "0" + hours;
        else
            hourString = Integer.toString(hours);
        if (minutes < 10)
            minuteString = "0" + minutes;
        else
            minuteString = Integer.toString(minutes);
        return hourString + ":" + minuteString;
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
