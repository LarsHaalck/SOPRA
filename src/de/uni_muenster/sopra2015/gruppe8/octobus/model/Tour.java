package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Created by Lars on 27-Feb-15.
 */
public class Tour
{
    // ID assigned to the object in the database (set on first write to DB)
    private int id;
	private Date timestamp;
	private Route route;
	private Bus bus;
	private Employee driver;

	public Tour()
	{
		this.timestamp = new Date(0);
	}

	public Tour(Date timestamp, Route route, Bus bus, Employee driver)
	{
		this.timestamp = timestamp;
		this.route = route;
		this.bus = bus;
		this.driver = driver;
	}

	public Bus getBus()
	{
		return bus;
	}

	public void setBus(Bus bus)
	{
		this.bus = bus;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
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
