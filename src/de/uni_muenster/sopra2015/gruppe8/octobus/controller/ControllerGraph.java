package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class ControllerGraph
{
	ControllerDatabase db;
	ArrayList<BusStop> stops;
	ArrayList<Route> routes;

	int numStops;
	HashSet<TupleInt> adjSet; //stores all object id tuples which are connected

	public ControllerGraph()
	{
		db = ControllerDatabase.getInstance();
		stops = db.getBusStops();
		routes = db.getRoutes();

		numStops = stops.size();
		adjSet = new HashSet<>(numStops * numStops); //size of fully connected graph

		buildAdjMatrix();
	}


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
				adjSet.add(new TupleInt(prevId, currentId));
				prevId = currentId;
			}
		}
	}


	/**
	 * calculates edge weight or rather earliest arrival time in unixtimestamp at s2 for edge (s1,s2)
	 * @param s1 first Vertex in edge
	 * @param s2 second Vertex in edge
	 * @param time earliest departure at s1 in unix timestamp
	 * @return earliest arrival at s2
	 */
	private int weight(BusStop s1, BusStop s2, int time)
	{
		return 0;
	}
}
