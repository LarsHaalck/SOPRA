package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


public class ControllerGraph
{
	ControllerDatabase db;
	ArrayList<BusStop> stops;
	ArrayList<Route> routes;

	int numStops;
	HashSet<TupleInt> adjSet; //stores all object id tuples which are connected

	HashMap<TupleInt, LinkedList<Route>> routesConnecting;


	/* something to store all routes per stop
	* like routesPerStop.get(stopID) //getRouteNamesUsingBusStop
	*/

	public ControllerGraph()
	{
		db = ControllerDatabase.getInstance();

		init();
	}

	private void init()
	{
		stops = db.getBusStops();
		routes = db.getRoutes();

		numStops = stops.size();
		adjSet = new HashSet<>(numStops * numStops); //size of fully connected graph (way more than needed)
		routesConnecting = new HashMap<>();

		buildAdjMatrix();
	}



	//iterates through all routes and stops
	private void buildAdjMatrix()
	{
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

		System.out.println(weight(db.getBusStop(75), db.getBusStop(13), 500));

	}


	/**
	 * calculates edge weight or rather earliest arrival time in unix timestamp at s2 for edge (s1,s2)
	 * s2 is in neighbourhood of s1
	 * @param s1 first Vertex in edge
	 * @param s2 second Vertex in edge
	 * @param time earliest departure at s1 in unix timestamp
	 * @return earliest arrival at s2 in unix timestamp
	 */
	private int weight(BusStop s1, BusStop s2, int time)
	{
		//given: routes connecting s1 and s2 where s1 and s2 are direct neighbours HashMap<Tuple<Integer, Integer>, LinkedList<Routes>> ???
		//would be helpful: another data structure where i can get start times of routes through stops
		/*
		*   for every route r connecting s1 and s2:
		*       for every start time s of r on s1:
		*           if s < time: //too early
		*               continue
		*           else: //first matching candidate is enough iff we look for one route !! if user wants more routes (f.ex. later, earlier) here is where it needs to be adjusted
		*               //do something
		*           //how do i calculate arrival at s2?
		*           break;
		*
		 */

		int arrival = -1;

		int id1 = s1.getId(); int id2 = s2.getId();
		LinkedList<Route> connectors = routesConnecting.get(new TupleInt(id1, id2)); //connectors contains all routes directed from s1 to s2

		for (Route connector : connectors)
		{
			HashMap<DayOfWeek, LinkedList<Integer>> startTimes = connector.getStartTimes();

			//TODO: remove hardcoded DayOfWeek (MONDAY)
			DayOfWeek day = DayOfWeek.MONDAY;
			LinkedList<Integer> startTimesOnDay = startTimes.get(day);
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> routeStops = connector.getStops();


			int currentArrival = 0;

			int timeDiff = 0;
			int timeDiffOnFirst = 0;

			for (Triple<BusStop, StoppingPoint, Integer> routeStop : routeStops)
			{
				timeDiff += routeStop.getThird();

				BusStop currentStop = routeStop.getFirst();

				if(currentStop.getId() == id1) //bus arrived at s1
				{
					timeDiffOnFirst = timeDiff;
				}
				else if (currentStop.getId() == id2) //bus arrived at s2 -> break
				{
					break;
				}
			}

			for (Integer start : startTimesOnDay)
			{
				if(timeDiffOnFirst + start < time) //bus arrives at s1 before specified time
					continue;
				currentArrival = start + timeDiff;
				break; //break after first match
			}

			if(currentArrival < arrival || arrival == -1)
			{
				arrival = currentArrival;
			}

			// <editor-fold desc="muell">
			/*for (Integer start : startTimesOnDay)
			{
				int currentTime = start;

				for (Triple<BusStop, StoppingPoint, Integer> routeStop : routeStops)
				{
					currentTime += routeStop.getThird();

					BusStop currentStop = routeStop.getFirst();

					if(foundMatchingTour)
					{
						currentArrival += routeStop.getThird();
					}
					if(currentStop == s1) //bus arrive at s1
					{
						if(currentTime < time) break; //bus arrive at s1 before time -> choose another start time
						foundMatchingTour = true;
						currentArrival = currentTime;
					}
					else if (currentStop == s2) //bus arrived at s2 -> break
					{
						break;
					}
				}

				if(foundMatchingTour) //other start times are now irrelevant
				{
					break;
				}
			}

			if(currentArrival < arrival || arrival == -1)
			{
				arrival = currentArrival;
			}*/
			// </editor-fold>
		}
		return arrival;
	}


}
