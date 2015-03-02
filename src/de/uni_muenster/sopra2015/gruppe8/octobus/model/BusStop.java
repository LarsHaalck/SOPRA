package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Set;

/**
 * Created by Michael Biech on 27.02.15
 */
public class BusStop
{
	private int id;
	private String name;
	private Tuple<Integer, Integer> location;
	private Set<String> stoppingPoints;
	private boolean barrierFree;

	public BusStop(String name, Tuple<Integer, Integer> location, Set<String> stoppingPoints, boolean barrierFree)
	{
		this.name = name;
		this.location = location;
		this.stoppingPoints = stoppingPoints;
		this.barrierFree = barrierFree;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Tuple<Integer, Integer> getLocation()
	{
		return location;
	}

	public void setLocation(Tuple<Integer, Integer> location)
	{
		this.location = location;
	}

	public Set<String> getStoppingPoints()
	{
		return stoppingPoints;
	}

	public void setStoppingPoints(Set<String> stoppingPoints)
	{
		this.stoppingPoints = stoppingPoints;
	}

	public boolean isBarrierFree()
	{
		return barrierFree;
	}

	public void setBarrierFree(boolean barrierFree)
	{
		this.barrierFree = barrierFree;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
