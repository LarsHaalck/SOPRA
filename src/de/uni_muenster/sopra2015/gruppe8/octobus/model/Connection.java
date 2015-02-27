package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Joshua on 27.02.2015.
 */
public class Connection
{
    private LinkedList<Quadruple<Integer, BusStop, Route, BusStop>> trips;
    private int duration;
    private Date date;
    private int time;

    public Connection(LinkedList<Quadruple<Integer, BusStop, Route, BusStop>> trips, int duration, Date date, int time)
    {
        this.trips = trips;
        this.duration = duration;
        this.date = date;
        this.time = time;
    }

    public LinkedList<Quadruple<Integer, BusStop, Route, BusStop>> getTrips()
    {
        return trips;
    }

    public int getDuration()
    {
        return duration;
    }

    public Date getDate()
    {
        return date;
    }

    public int getTime()
    {
        return time;
    }
}
