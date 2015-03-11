package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.HashSet;

/**
 * Used to store the data of a bus stop.
 */
public class BusStop
{
    private int id;									// database-internal id. is set when object is added to database
    private String name;
    private Tuple<Integer, Integer> location;
    private HashSet<StoppingPoint> stoppingPoints;
    private boolean barrierFree;

	public BusStop()
	{
		this.stoppingPoints = new HashSet<>();
	}

    public BusStop(String name, Tuple<Integer, Integer> location, HashSet<StoppingPoint> stoppingPoints, boolean barrierFree)
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

    public HashSet<StoppingPoint> getStoppingPoints()
    {
        return stoppingPoints;
    }

    public void setStoppingPoints(HashSet<StoppingPoint> stoppingPoints)
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

	public int getStoppingPointsNum()
	{
		return stoppingPoints.size();
	}
}
