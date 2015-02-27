package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Created by Lars on 27-Feb-15.
 */
public class Tour
{
	private Date date;
	private int time;
	private Route route;
	private Bus bus;
	private Employee driver;

	public Tour(Date date, int time, Route route, Bus bus, Employee driver)
	{
		this.date = date;
		this.time = time;
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
}
