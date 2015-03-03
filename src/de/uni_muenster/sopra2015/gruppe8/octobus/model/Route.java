package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by PhiSt on 27.02.2015.
 */
public class Route
{
	private String name;
	private String note;
	private LinkedList<Tuple<BusStop, Integer>> stops;
	private boolean night;
	private HashMap<DayOfWeek, Integer> startTimes;
	private int id;

	public Route(String name, String note, LinkedList<Tuple<BusStop, Integer>> stops, boolean night, HashMap<DayOfWeek, Integer> startTimes)
	{
		this.name = name;
		this.note = note;
		this.stops = stops;
		this.night = night;
		this.startTimes = startTimes;

		//TODO: Create empty list and hashmap if parameters are null??
	}

	public Route()
	{
		name = "";
		note = "";
		stops = new LinkedList<Tuple<BusStop, Integer>>();
		night = false;
		startTimes = new HashMap<DayOfWeek, Integer>();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	public LinkedList<Tuple<BusStop, Integer>> getStops()
	{
		return stops;
	}

	public void setStops(LinkedList<Tuple<BusStop, Integer>> stops)
	{
		this.stops = stops;
	}

	public boolean isNight()
	{
		return night;
	}

	public void setNight(boolean night)
	{
		this.night = night;
	}

	public HashMap<DayOfWeek, Integer> getStartTimes()
	{
		return startTimes;
	}

	public void setStartTimes(HashMap<DayOfWeek, Integer> startTimes)
	{
		this.startTimes = startTimes;
	}

	public int getId()
	{
		return id;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Route route = (Route) o;

		if (id != route.id) return false;

		return true;
	}

	public BusStop getStart()
	{
		if (stops.size() > 0)
			return stops.getFirst().getFirst();
		return null;
	}

	public BusStop getEnd()
	{
		if (stops.size() > 0)
			return stops.getLast().getFirst();
		return null;
	}

	/**
	 * Returns duration bus would need to go from first to last stop
	 *
	 * @return duration in minutes
	 */
	public int getDuration()
	{
		return getDuration(getStart(), getEnd());
	}

	/**
	 * Returns duration between start and end
	 *
	 * @param start
	 * @param end
	 * @return duration in minutes
	 */
	public int getDuration(BusStop start, BusStop end)
	{
		int duration = 0;
		boolean sumUp = false;
		for (Tuple<BusStop, Integer> t : stops)
		{
			if (sumUp)
				duration += t.getSecond();
			if (t.getFirst().equals(start))
				sumUp = true;
			if (t.getFirst().equals(end))
				break;
		}
		return duration;
	}

}
