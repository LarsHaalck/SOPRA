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
import java.util.ArrayList;
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
    private TupleIntString[] arrayBusStops;
    private int time;
    private Connection latestConnection;
    private Connection earliestConnection;
    private DayOfWeek curDay;
    private DayOfWeek latestDay;
    private DayOfWeek earliestDay;

    public ControllerDisplaySearchConnection(DisplaySearchConnection journeyDialog)
    {
        this.journeyDialog = journeyDialog;
        this.cg = new ControllerGraph();
        this.cg.init();
        earliestConnection = new Connection();
        latestConnection = new Connection();
        db = ControllerDatabase.getInstance();
        ArrayList<BusStop> busStops = db.getBusStops();
        arrayBusStops = new TupleIntString[busStops.size()];
        int i = 0;
        for (BusStop busStop : busStops) {
            arrayBusStops[i] = new TupleIntString(busStop.getId(), busStop.getName());
            i++;
        }
        journeyDialog.fillBusStops(arrayBusStops);

    }

    @Override
    public void buttonPressed(EmitterButton emitter)
    {

        switch (emitter)
        {
            case DISPLAY_CONNECTION_SEARCH:
                searchConnection();
                break;
            case DISPLAY_CONNECTION_EARLIER:
                //Look for earlier Journeys and add them to the table
                searchEarlierConnection();
                break;
            case DISPLAY_CONNECTION_FIRST:
                //Look for the first Journey and add it to the table
                searchFirstConnection();
                break;
            case DISPLAY_CONNECTION_LATER:
                //Look for later Journeys and add them to the table
                searchLaterConnection();
                break;
            case DISPLAY_CONNECTION_LAST:
                //Look for the last Journey and add it to the table
                searchLatestConnection();
                break;
			case DISPLAY_CONNECTION_BACK:
				ControllerManager.informDisplaySwitch(EmitterDisplay.DISPLAY_MAIN);
				removeListeners();
				break;
            case DISPLAY_CONNECTION_SELECT_ORIGIN:

        }
    }


	/**
	 *  Looks for the earliest arrival connection between the specified origin and destination bus stops.
	 */
	private void searchConnection()
    {
        //Get BusStops from IDs specified by the comboBox
        origin = db.getBusStopById(
                ((TupleIntString) journeyDialog.getOrigin().getSelectedItem())
                        .getFirst());
        destination = db.getBusStopById(
                ((TupleIntString)journeyDialog.getDestination().getSelectedItem())
                        .getFirst());

        curDay = journeyDialog.getDayOfWeek();
        latestDay = curDay;
        earliestDay = curDay;

        time = journeyDialog.getTime();

        //Clear if previous content is displayed in the textPane and clear selection
        if (!(journeyDialog.getTableSearchResults().getSelectionModel().isSelectionEmpty()))
        {
            journeyDialog.getTableSearchResults().getSelectionModel().clearSelection();
            updateTextPane();
        }

        ((TableModelSearchConnection)journeyDialog.getTableSearchResults().getModel()).clearTableModel();

        if (origin.getId() == destination.getId())
        {
            journeyDialog.removeRightGridPanel();
            return;
        }


        while (true)
        {
            Connection currentConnectionSearch = cg.getConnection(origin.getId(), destination.getId(), latestDay, time);
            //What happens if Dijkstra doesn't find any connection?
            if (currentConnectionSearch == null)
            {
                latestDay = latestDay.plus(1);
                time = 0;
                if (latestDay == curDay) break;
                continue;
            }/*else if (currentConnectionSearch.changedDay()) //Needs to be implemented
                latestDay = curDay.plus(1);*/

            earliestConnection = currentConnectionSearch;
            latestConnection = currentConnectionSearch;
            curDay = latestDay;
            earliestDay = latestDay;
            journeyDialog.modifyRightGridPanel();
            journeyDialog.addLastConnectionAndUpdateTable(currentConnectionSearch);
            break;
        }
    }


    /**
     * Looks for earlier departures than the earliest connection in the table for connections between
     * the specified origin and destination bus stops.
     */
    private void searchEarlierConnection()
    {
        boolean hasOnlyOneConnection = false;
        Connection possiblePreviousConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, 0);
        Connection nextConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, possiblePreviousConnection.getTime() + 1);



        if (possiblePreviousConnection != null && !(possiblePreviousConnection.equals(earliestConnection)))   //There are earlier connections on the same day.
        {
            while (!(nextConnection.equals(earliestConnection)))
            {
                possiblePreviousConnection = nextConnection;
                nextConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, possiblePreviousConnection.getTime() + 1);
            }
        } else //Earlier connections on earlier days.
        {
            DayOfWeek curEarliest = earliestDay;
            earliestDay = earliestDay.minus(1);
            possiblePreviousConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, 0);

            while ((possiblePreviousConnection.getStartingDay() != earliestDay)) //possiblePreviousConnection == null || possiblePreviousConnection.changedDay()
            {
                earliestDay = earliestDay.minus(1);
                //There is only one connection.
                if (earliestDay == curEarliest)
                {
                    hasOnlyOneConnection = true;
                    break;
                }
                possiblePreviousConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, 0);
            }

            nextConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, possiblePreviousConnection.getTime() + 1);

            while (!(nextConnection == null || nextConnection.getStartingDay() != earliestDay)) //!(nextConnection == null || nextConnection.changedDay())
            {
                if (hasOnlyOneConnection) break;
                possiblePreviousConnection = nextConnection;
                nextConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, possiblePreviousConnection.getTime() + 1);
            }
        }

        if (hasOnlyOneConnection) possiblePreviousConnection = earliestConnection;

        earliestConnection = possiblePreviousConnection;
        journeyDialog.addFirstConnectionAndUpdateTable(earliestConnection);
    }

    /**
     * Looks for the first departure on earliest day.
     */
    private void searchFirstConnection()
    {
        Connection currentEarliest = earliestConnection;
        earliestConnection = cg.getConnection(origin.getId(), destination.getId(), earliestDay, 0);

        if (currentEarliest.equals(earliestConnection)) return;

        journeyDialog.addFirstConnectionAndUpdateTable(earliestConnection);
    }

    /**
     * Looks for the latest departure on latest day.
     */
    public void searchLatestConnection()
    {
        Connection currentLatest = latestConnection;

        while (true) {
            Connection nextConnection = cg.getConnection(origin.getId(), destination.getId(), latestDay, latestConnection.getTime() + 1);
            if (nextConnection == null || nextConnection.getStartingDay() != latestDay) break; //nextConnection.changedDay() || nextConnection == null
            latestConnection = nextConnection;
        }

        if (currentLatest.equals(latestConnection)) return;

        journeyDialog.addLastConnectionAndUpdateTable(latestConnection);
    }

    /**
     * Looks for connections later than the currently latest connection.
     */
    public void searchLaterConnection()
    {
        int latestTime = latestConnection.getTime() + 1;
        do {
            Connection currentConnectionSearch = cg.getConnection(origin.getId(), destination.getId(), latestDay, latestTime);
            //Dijkstra haven't found one yet.
            if (currentConnectionSearch == null)
            {
                latestTime = 0;
                latestDay = latestDay.plus(1);
                continue;
            } else if (false) //currentConnectionSearch.changedDay()
            {
                latestDay = latestDay.plus(1);
            } else
            {
                latestConnection = currentConnectionSearch;
                journeyDialog.addLastConnectionAndUpdateTable(latestConnection);
                break;
            }

        } while(true);
    }



    @Override
    protected void addListeners()
    {
        ControllerManager.addListener((ListenerButton) this);
        ControllerManager.addListener((ListenerTable) this);
    }

    @Override
    protected void removeListeners()
    {
        ControllerManager.removeListener((ListenerButton)this);
        ControllerManager.removeListener((ListenerTable) this);
    }




    @Override
    public void tableSelectionChanged(EmitterTable emitter) {
        switch(emitter)
        {
            case FORM_JOURNEY_SEARCH_RESULT:
                updateTextPane();
                break;
        }
    }

    /**
     * Updates the textPane with the information of the selected connection.
     */
    private void updateTextPane(){
        String result = "";
        int rowSelected = journeyDialog.getTableSearchResults().getSelectedRow();
        if (rowSelected < 0)
        {
            journeyDialog.removeTextPaneInformation();
            return;
        }
        result = result +
                formatDayOfWeekToGerman(
                        ((TableModelSearchConnection)journeyDialog
                                .getTableSearchResults()
                                .getModel())
                                .getConnectionByIndex(rowSelected)
                                .getStartingDay()
                )
                + "\n\n";
        LinkedList<Quintuple<Integer, StoppingPoint, Route, StoppingPoint, Integer>> trips =
                ((TableModelSearchConnection)journeyDialog
                        .getTableSearchResults()
                        .getModel())
                        .getConnectionByIndex(rowSelected)
                        .getTrips();
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
                            + " an "
                            + bsSecondName + " Bstg. " + spSecondName + "\n----------------------------------------\n";
            result = result + s;
        }
        result = result + "\n Informationen zu unseren günstigsten Fahrkarten erhalten Sie unter dem Reiter \"Fahrkarten anzeigen\"";
        journeyDialog.showSelectedConnection(result);
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

    private String formatDayOfWeekToGerman(DayOfWeek dayOfWeek)
    {
        switch (dayOfWeek)
        {
            case MONDAY:
                return "Montag";
            case TUESDAY:
                return "Dienstag";
            case WEDNESDAY:
                return "Mittwoch";
            case THURSDAY:
                return "Donnerstag";
            case FRIDAY:
                return "Freitag";
            case SATURDAY:
                return "Samstag";
            case SUNDAY:
                return "Sonntag";
        }
        return "";
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
