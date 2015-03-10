package de.uni_muenster.sopra2015.gruppe8.octobus.model.print;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Patricia
 */
public class PrintStoppingPoint
{
	private StoppingPoint stoppingPoint;
	private DayOfWeek day;
	private Route route;
	private ArrayList<Integer> departureTimes;
	private ControllerDatabase controllerDatabase;

	public PrintStoppingPoint(StoppingPoint stoppingPoint, Route route, DayOfWeek day)
	{
		controllerDatabase = ControllerDatabase.getInstance();
		this.stoppingPoint = stoppingPoint;
		this.route = route;
		this.day = day;
		departureTimes = getDepartureTimes(stoppingPoint,route,day);
	}

	//gives departure Times for a specific day, route and stopPoint
	public ArrayList<Integer> getDepartureTimes(StoppingPoint stop, Route route, DayOfWeek day)
	{
		ArrayList<Integer> departureTimes = new ArrayList<>();
		DayOfWeek myDay = day;
		LinkedList<Integer> startTimes;
		BusStop busStop = controllerDatabase.getBusStopByStoppingPointId(stop.getId());
		int timeToStop = 0;

		for(Triple<BusStop, StoppingPoint, Integer> triple: route.getStops())
		{
			if((triple.getFirst()==busStop&& triple.getSecond()==stop))
			{
				timeToStop = triple.getThird();
				break;
			}
		}
		startTimes = route.getStartTimes().get(myDay);
		int i = 0;
		for(int startTime: startTimes)
		{
			departureTimes.add(i, startTime+timeToStop);
			i++;
		}
		return departureTimes;
	}
	public StoppingPoint getStoppingPoint()
	{
		return stoppingPoint;
	}

	public DayOfWeek getDay()
	{
		return day;
	}

	public Route getRoute()
	{
		return route;
	}

	public ArrayList<Integer> getDepartureTimes()
	{
		return departureTimes;
	}

	public String getBusStopNameByStoppingPointId(Integer id)
	{
		return controllerDatabase.getBusStopByStoppingPointId(id).getName();
	}
}
