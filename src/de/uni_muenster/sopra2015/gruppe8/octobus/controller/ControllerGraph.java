package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import java.time.DayOfWeek;
import java.util.*;


/**
 * Controller for connection search algorithm. Builds adjacency set and uses a variation of dijkstra to determine
 * optimal route (minimizes earliest arrival)
 */
public class ControllerGraph
{
	int numStops;
	HashSet<TupleInt> adjSet;
	HashMap<TupleInt, LinkedList<Route>> routesConnecting;
	private ControllerDatabase db;
	private ArrayList<BusStop> stops;
	private ArrayList<Route> routes;


	/**
	 * Establishes DB connection and calls init
	 */
	public ControllerGraph()
	{
		db = ControllerDatabase.getInstance();
	}

	/**
	 * sets DB connection to test db. Only for testing!!
	 */
	public void setTest()
	{
		db.closeDB();
		db = ControllerDatabase.getTestingInstance("DB_for_jUnit_tests.db");
	}

	/**
	 * Reinitializes all variables and rebuilds adjacency set. Should be called after changing BusStops or Routes
	 */
	public void init()
	{
		stops = db.getBusStops();
		routes = db.getRoutes();

		numStops = stops.size();
		adjSet = new HashSet<>(numStops * numStops); //size of fully connected graph (way more than needed)
		routesConnecting = new HashMap<>();

		buildAdjSet();
	}

	/**
	 * Builds set of directly connected BusStops and stores connecting routes for later use in Dijkstra-Algorithm
	 */
	private void buildAdjSet()
	{
		if(routes == null || stops == null) return;
		for(Route route : routes)
		{
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> routeStops = route.getStops();

			int prevId = route.getStart().getId();

			for(int i = 1; i < routeStops.size(); i++)
			{
				int currentId = routeStops.get(i).getFirst().getId();


				//prevId and currentId are connected
				TupleInt tuple = new TupleInt(prevId, currentId);

				//mark route as connecting route for prevID, currentID
				if(routesConnecting.containsKey(tuple))
				{
					routesConnecting.get(tuple).add(route);
				}
				else
				{
					LinkedList<Route> routeList = new LinkedList<>();
					routeList.add(route);
					routesConnecting.put(tuple, routeList);
				}

				//put them in adjSet
				adjSet.add(tuple);
				prevId = currentId;
			}
		}
	}


	/**
	 * Calculates connection between specified BusStop ids on day starting on time (earliest departure)
	 * @param id_start id of BusStop which marks the start of the requested connection
	 * @param id_end id of BusStop which marks the end of the requested connection
	 * @param day DayOfWeek of requested connection
	 * @param time earliest starting time at startId
	 * @return Connection object if connection exists, null otherwise
	 * @pre id_start and id_end must be existing BusStop ids. time must be in [0, 1439]
	 */
	public Connection getConnection(int id_start, int id_end, DayOfWeek day, int time)
	{
		if(routes == null || stops == null) return null;
		ConnectionRequest request = new ConnectionRequest();
		return request.findConnection(id_start, id_end, day, time);
	}


	/**
	 * ConnectionRequest stands for one specific 'search connection' - request which
	 * uses the already initialized 'graph'
	 */
	private class ConnectionRequest
	{
		int startId;
		DayOfWeek day;
		//somethings need to be global in this class because they are used by multiple functions
		private HashMap<TupleInt, Route> bestRoutes; //contains "winning" routes on edges
		private HashMap<Integer, StoppingPoint> bestStoppingPoints; //contains used StoppingPoints on "winning routes"
		private HashMap<Integer, Double> dist; //contains dist of BusStop (via id) from starting point
		private HashMap<Integer, Integer> prev; //contains previous BusStops from BusStop (via id) in "shortest" Path

		/**
		 * Initializes HashMaps
		 */
		public ConnectionRequest()
		{
			bestRoutes = new HashMap<>();
			bestStoppingPoints = new HashMap<>();
			dist = new HashMap<>();
			prev = new HashMap<>();
		}

		/**
		 * Calculates how many days need to be added to day, requested in connetion, in order to get to
		 * passed day
		 * @param day
		 * @return number of days to be added
		 */
		private int getValueInWeek(DayOfWeek day)
		{
			DayOfWeek current = this.day;
			int i = 0;
			while (current != day)
			{
				current = current.plus(1);
				i++;
			}

			return i;
		}

		/**
		 * Weight function for Dijkstra, calculates earliest arrival time at id2, when going from id1
		 * with earliest start of time
		 * @param id1 start BusStop (id)
		 * @param id2 end BusStop (id)
		 * @param time earliest start time
		 * @return earliest arrival
		 * @pre id1 and id2 must be ids in database, time must be non-negative
		 */
		private int arrivalTime(int id1, int id2, int time)
		{
			int arrival = -1;

			//connectors contains all routes going from s1 to s2
			LinkedList<Route> connectors = routesConnecting.get(new TupleInt(id1, id2));


			StoppingPoint stoppingPoint1 = null;
			StoppingPoint stoppingPoint2 = null;
			for (Route connector : connectors)
			{
				HashMap<DayOfWeek, LinkedList<Integer>> startTimes = connector.getStartTimes();

				LinkedList<Triple<BusStop, StoppingPoint, Integer>> routeStops = connector.getStops();

				int currentArrival = -1;

				int timeDiff = 0;
				int timeDiffOnFirst = 0;

				boolean arrived1 = false;
				for (Triple<BusStop, StoppingPoint, Integer> routeStop : routeStops)
				{
					timeDiff += routeStop.getThird();

					BusStop currentStop = routeStop.getFirst();

					if(currentStop.getId() == id1) //bus arrived at s1
					{
						timeDiffOnFirst = timeDiff;
						stoppingPoint1 = routeStop.getSecond();
						arrived1 = true;
					}
					else if (currentStop.getId() == id2 && arrived1) //bus arrived at s2 -> break
					{
						stoppingPoint2 = routeStop.getSecond();
						break;
					}
				}

				ArrayList<Integer> temp = new ArrayList<>();

				DayOfWeek currentDay = this.day.plus(time/1440);


				LinkedList<Integer> startTimesOnDay = startTimes.get(currentDay);
				LinkedList<Integer> starTimesTomorrow = startTimes.get(currentDay.plus(1));

				for (Integer start : startTimesOnDay)
				{
					int startTime = start + 1440 * getValueInWeek(currentDay);

					if(timeDiffOnFirst + startTime >= time)
					{
						temp.add(startTime + timeDiff);
						break;
					}
				}

				for (Integer startTomorrow : starTimesTomorrow)
				{
					int startTime = startTomorrow + 1440 * getValueInWeek(currentDay.plus(1));

					temp.add(startTime + timeDiff);
				}


				if(temp.size() != 0)
					currentArrival = Collections.min(temp);
				else
					currentArrival = -1;

				if(currentArrival < arrival || arrival == -1 && currentArrival != -1)
				{
					arrival = currentArrival;
					TupleInt tuple = new TupleInt(id1, id2);
					bestRoutes.put(tuple, connector);


					//only add/modify existing entry, if new weight is smaller than existing weight
					if(currentArrival < dist.get(id2))
					{
						bestStoppingPoints.put(id2, stoppingPoint2);
						if(id1 == startId)
							bestStoppingPoints.put(id1, stoppingPoint1);
					}
				}
				//else leave arrival alone

			}

			return arrival;
		}

		/**
		 * Determines all direct neighbours of specified BusStop
		 * @param stopId
		 * @return ArrayList of neighbours
		 */
		private ArrayList<BusStop> getNeighbours(int stopId)
		{
			ArrayList<BusStop> neighbours = new ArrayList<>();

			for (BusStop stop : stops)
			{
				if(adjSet.contains(new TupleInt(stopId, stop.getId())))
				{
					neighbours.add(stop);
				}
			}

			return neighbours;
		}

		/**
		 * Implementation of dijkstra algorithm where distances are arrival times
		 * @param startId id of BusStop which marks the start of the requested connection
		 * @param endId id of BusStop which marks the end of the requested connection
		 * @param startTime earliest starting time at startId
		 * @return Connection object if connection exists, null otherwise
		 * @pre startId and endId must be existing BusStop ids. start time must be in [0, 1439]
		 */
		public Connection findConnection(int startId, int endId, DayOfWeek day, int startTime)
		{
			this.startId = startId; //needed to determine StoppingPoint on start BusStop
			this.day = day;

			FibonacciHeap<Integer> fibHeap = new FibonacciHeap<>();

			// <editor-fold desc="Dijkstra init">
			fibHeap.enqueue(startId, startTime);
			dist.put(startId, (double) startTime);

			for (BusStop stop : stops)
			{
				if(stop.getId() == startId) continue;
				dist.put(stop.getId(), Double.POSITIVE_INFINITY); //set all distances to infinity
				fibHeap.enqueue(stop.getId(), Double.POSITIVE_INFINITY);
			}
			// </editor-fold>


			while(!fibHeap.isEmpty())
			{
				int stopId = fibHeap.dequeueMin().getValue();

				if(stopId == endId && !dist.get(stopId).isInfinite())
				{
					return reconstructConnection(stopId);
				}

				ArrayList<BusStop> neighbours = this.getNeighbours(stopId);

				for (BusStop neighbour : neighbours) //edge (v,w)
				{
					int neighbourId = neighbour.getId();
					double arrivalAtNeighbour;
					if(Double.isInfinite(dist.get(stopId)))
						arrivalAtNeighbour = Double.POSITIVE_INFINITY;
					else
						arrivalAtNeighbour = arrivalTime(stopId, neighbourId, dist.get(stopId).intValue()); //arrivalTime contains earliest arrival at w


					if(arrivalAtNeighbour == -1) continue;
					if(arrivalAtNeighbour < dist.get(neighbour.getId()))
					{
						fibHeap.enqueue(neighbourId, arrivalAtNeighbour);
						dist.put(neighbourId, arrivalAtNeighbour);
						prev.put(neighbourId, stopId);
					}
				}
			}
			return null;
		}


		/**
		 * Reconstructs Connection by stepping backwards through prev, after findConnection successfully found a connection
		 * @param stopId last BusStop id in connection
		 * @return Connection object containing all important connection information
		 */
		private Connection reconstructConnection(int stopId)
		{
			int time = 0;
			int duration = 0;
			int dayOffset = 0;

			LinkedList<Quintuple<Integer, StoppingPoint, Route, StoppingPoint, Integer>> trips = new LinkedList<>();

			int currentStop = stopId;
			int prevStop = prev.get(currentStop) == null ? -1 : prev.get(currentStop);

			Route prevRoute = null;
			Quintuple<Integer, StoppingPoint, Route, StoppingPoint, Integer> prevQuintuple = null;
			StoppingPoint endStoppingPoint;


			while(prevStop != -1) //reconstruct route
			{
				Route currentRoute = bestRoutes.get(new TupleInt(prevStop, currentStop));
				if(prev.get(prevStop) == null)
				{
					time = (dist.get(currentStop).intValue() - currentRoute.getDuration(prevStop, currentStop));
					dayOffset = time / 1440;
					if(dayOffset >= 2)
						return null;
					duration = dist.get(stopId).intValue() - time;
					time = time >= 1440 ? time % 1440 : time;
				}


				//no change of route -> no transition -> delete old Quadruple but save its end stopping point
				endStoppingPoint = bestStoppingPoints.get(currentStop);
				if(prevRoute != null && currentRoute.getId() == prevRoute.getId()) //prevRoute != null -> prevQuadruple != null
				{
					endStoppingPoint = prevQuintuple.getFourth();
					trips.remove(prevQuintuple);
				}

				int calcStart = (dist.get(currentStop).intValue() - currentRoute.getDuration(prevStop, currentStop));
				calcStart = calcStart >= 1440 ? calcStart % 1440 : calcStart;

				//int calcEnd = dist.get(end.getId()).intValue(); //dist.get(db.).intValue()
				int calcEnd = dist.get(db.getBusStopByStoppingPointId(endStoppingPoint.getId()).getId()).intValue();
				calcEnd = calcEnd >= 1440 ? calcEnd % 1440 : calcEnd;

				prevQuintuple = new Quintuple<>(
						calcStart,
						bestStoppingPoints.get(prevStop),
						bestRoutes.get(new TupleInt(prevStop, currentStop)),
						endStoppingPoint,
						calcEnd
				);
				//add them in reverse order
				trips.addFirst(prevQuintuple);

				prevRoute = currentRoute;

				currentStop = prevStop;
				prevStop = prev.get(currentStop) == null ? -1 : prev.get(currentStop);
			}

			return new Connection(trips, duration, time, day.plus(dayOffset));
		}
	}
}
