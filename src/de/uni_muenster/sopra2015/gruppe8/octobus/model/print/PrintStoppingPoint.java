package de.uni_muenster.sopra2015.gruppe8.octobus.model.print;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Triple;

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
	private ArrayList<Route> routes;

	public PrintStoppingPoint(StoppingPoint stoppingPoint)
	{
		controllerDatabase = ControllerDatabase.getInstance();
		this.stoppingPoint = stoppingPoint;
		routes = controllerDatabase.getRoutesUsingStoppingPoint(stoppingPoint.getId());
	}

	/**
	 * Gives all routeEntries for a stoppingPoint.
	 *
	 * @return arrayList with all routeEntries
	 */
	public ArrayList<RouteEntry> getRouteEntries()
	{
		ArrayList<RouteEntry> routeRecords = new ArrayList<>();

		for (Route route : routes)
		{
			RouteEntry rec = new RouteEntry(route, stoppingPoint);
			routeRecords.add(rec);
		}

		return  routeRecords;
	}

	/**
	 * Return number of routes at this stopping point
	 *
	 * @return number of routes
	 */
	public int getNumRoutes()
	{
		return routes.size();
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
			int time = 0;
			for(Triple<BusStop, StoppingPoint, Integer> triple: route.getStops())
			{
				if(arrived)
				{
					time += triple.getThird();
					nextStops.add(triple.getFirst().getName() + " (" + time + " min)");
				}
				if(triple.getFirst().getId() == busStop.getId() && triple.getSecond().getId() == stopPoint.getId())
				{
					arrived = true;
				}
			}

			return nextStops;
		}

	}
}
