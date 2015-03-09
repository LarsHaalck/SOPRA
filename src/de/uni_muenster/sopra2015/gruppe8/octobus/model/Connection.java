package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Joshua on 27.02.2015.
 */
public class Connection
{
	//1. Integer = startTime
	//2. BusStop = start busStop
	//3. Route to use
	//4. arrival BusStop
	private LinkedList<Quadruple<Integer, StoppingPoint, Route, StoppingPoint>> trips;
	private int duration;
	private int time;

	public Connection()
	{
		trips = new LinkedList<>();
	}

	public Connection(LinkedList<Quadruple<Integer, StoppingPoint, Route, StoppingPoint>> trips, int duration, Date date, int time)
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
}
