package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Set;

public class BusStop
{
    private String name;
    private Tuple<Integer, Integer> location;
    private Set<String> subStops; //?
    private boolean barrierFree;
    private int id;

    public BusStop(String name, Tuple<Integer, Integer> location, Set<String> subStops, boolean barrierFree, int id)
    {
        this.name = name;
        this.location = location;
        this.subStops = subStops;
        this.barrierFree = barrierFree;
        this.id = id;
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

    public Set<String> getSubStops()
    {
        return subStops;
    }

    public void setSubStops(Set<String> subStops)
    {
        this.subStops = subStops;
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
