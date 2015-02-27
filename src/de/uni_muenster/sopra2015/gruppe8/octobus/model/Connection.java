package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Joshua on 27.02.2015.
 */
public class Connection
{
    private LinkedList<Quadruple<int, BusStop, Route, BusStop>> trips;
    private int duration;
    private Date date;
    private int time;

    public Connection(LinkedList<Quadruple<int, BusStop, Route, BusStop>> trips, int duration, Date date, int time)
    {
        this.trips = trips;
        this.duration = duration;
        this.date = date;
        this.time = time;
    }

    public LinkedList<Quadruple<int, BusStop, Route, BusStop>> getTrips()
    {
        return trips;
    }

    public void setTrips(LinkedList<Quadruple<int, BusStop, Route, BusStop>> trips)
    {
        this.trips = trips;
    }

    public int getDuration()
    {
        return duration;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }
}
