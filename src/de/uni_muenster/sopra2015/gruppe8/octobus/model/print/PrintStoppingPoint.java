package de.uni_muenster.sopra2015.gruppe8.octobus.model.print;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import javafx.scene.paint.Stop;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Patricia
 */
public class PrintStoppingPoint
{
	private StoppingPoint stoppingPoint;
	private ControllerDatabase controllerDatabase;

	public PrintStoppingPoint(StoppingPoint stoppingPoint)
	{
		controllerDatabase = ControllerDatabase.getInstance();
		this.stoppingPoint = stoppingPoint;
	}

	/**
	 * Gives all routeEntries for a stoppingPoint.
	 *
	 * @return arrayList with all routeEntries
	 */
	public ArrayList<RouteEntry> getRouteEntries()
	{
		ArrayList<Route> routes = controllerDatabase.getRoutesUsingStoppingPoint(stoppingPoint.getId());
		ArrayList<RouteEntry> routeRecords = new ArrayList<>();

		for (Route route : routes)
		{
			RouteEntry rec = new RouteEntry(route, stoppingPoint);
			routeRecords.add(rec);
		}

		return  routeRecords;
	}

	public StoppingPoint getStoppingPoint()
	{
		return stoppingPoint;
	}

	/**
	 *
	 */
	public class RouteEntry
	{

		private BusStop busStop;
		private StoppingPoint stopPoint;
		private Route route;

		public RouteEntry(Route route, StoppingPoint stopPoint)
		{
			busStop = controllerDatabase.getBusStopByStoppingPointId(stopPoint.getId());
			this.stopPoint = stopPoint;
			this.route = route;
		}

		public BusStop getBusStop()
		{
			return busStop;
		}

		public StoppingPoint getStopPoint()
		{
			return stopPoint;
		}

		public Route getRoute()
		{
			return route;
		}

		/**
		 * gives all startTimes for a single tour on a single stoppingPoint for one single day.
		 *
		 * @param day of the week
		 * @return arrayList of startTimes
		 */
		public ArrayList<Integer> getStartTimes(DayOfWeek day)
		{
			ArrayList<Integer> startTimes = new ArrayList<>();

			LinkedList<Integer> departureTimes = route.getStartTimes().get(day);

			int timeToStop = 0;
			for(Triple<BusStop, StoppingPoint, Integer> triple: route.getStops())
			{
				timeToStop += triple.getThird();
				if((triple.getFirst().getId() == busStop.getId() && triple.getSecond().getId() == stopPoint.getId()))
				{
					break;
				}
			}

			for(int departureTime: departureTimes)
			{
				startTimes.add(timeToStop + departureTime);
			}
			return startTimes;
		}

		/**
		 * Gives the names of all stops a route will arrive at after a specific stoppingPoint.
		 *
		 * @return arrayList of stoppingPointNames
		 */
		public ArrayList<String> getNextStops()
		{
			ArrayList<String> nextStops = new ArrayList<>();
			boolean arrived = false;
			for(Triple<BusStop, StoppingPoint, Integer> triple: route.getStops())
			{
				if(triple.getFirst().getId() == busStop.getId() && triple.getSecond().getId() == stopPoint.getId())
				{
					arrived = true;
				}
				if(arrived)
					nextStops.add(triple.getFirst().getName() + ": " + triple.getSecond().getName());
			}

			return nextStops;
		}

	}
}
