package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Used to store the data of a tour.
 */
public class Tour
{
    // ID assigned to the object in the database (set on first write to DB)
    private int id;
	private Date startTimestamp;
    private Date endTimestamp;
	private Route route;
	private Bus bus;
	private Employee driver;

	public Tour()
	{
		this.startTimestamp = new Date(0);
	}

	public Tour(Date startTimestamp, Route route, Bus bus, Employee driver)
	{
		this.startTimestamp = startTimestamp;
		this.route = route;
		this.bus = bus;
		this.driver = driver;

        endTimestamp = new Date(this.startTimestamp.getTime() + ((long) route.getDuration()*60*1000));
	}

	public Bus getBus()
	{
		return bus;
	}

	public void setBus(Bus bus)
	{
		this.bus = bus;
	}

	public Date getStartTimestamp()
	{
		return startTimestamp;
	}

    public int getStartTimestampAsInt()
    {
        return (int) (startTimestamp.getTime()/1000);
    }

    public int getEndTimestampAsInt()
    {
        return (int) (endTimestamp.getTime()/1000);
    }

	public void setStartTimestamp(Date startTimestamp)
	{
		this.startTimestamp = startTimestamp;
	}

	public Route getRoute()
	{
		return route;
	}

	public void setRoute(Route route)
	{
		this.route = route;
	}

	public Employee getDriver()
	{
		return driver;
	}

	public void setDriver(Employee driver)
	{
		this.driver = driver;
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
