package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.LinkedList;

public class Connection
{

	private LinkedList<Quadruple<Integer, StoppingPoint, Route, StoppingPoint>> trips;
	private int duration;
	private int time;

	public Connection()
	{
		trips = new LinkedList<>();
	}

	public Connection(LinkedList<Quadruple<Integer, StoppingPoint, Route, StoppingPoint>> trips, int duration, int time)
	{
		this.trips = trips;
		this.duration = duration;
		this.time = time;
	}

	public LinkedList<Quadruple<Integer, StoppingPoint, Route, StoppingPoint>> getTrips()
	{
		return trips;
	}

	public int getDuration()
	{
		return duration;
	}

	public int getTime()
	{
		return time;
	}

	/**
	 * Returns total number of transitions needed in connection
	 * @return number of transitions if trips contains more than one Quadruple, -1 otherwise
	 */
	public int getNumberOfTransitions()
	{
		if(trips != null && trips.size() > 0)
			return trips.size()-1;
		else
			return -1;
	}
}
