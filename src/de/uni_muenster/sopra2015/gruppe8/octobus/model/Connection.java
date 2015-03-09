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
}
