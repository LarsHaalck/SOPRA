package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.util.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerDisplayNetwork extends Controller
{
	private DisplayNetwork displayNetwork;
	private ControllerDatabase controllerDatabase;

	private DataBusStop[] dataBusStops;
	private DataRoute[] dataRoutes;

	private Color colorDark = new Color(0,0,0);
	private Color colorLight = new Color(120,120,120);

	public ControllerDisplayNetwork(DisplayNetwork displayNetwork)
	{
		this.displayNetwork = displayNetwork;
		this.controllerDatabase = ControllerDatabase.getInstance();

		prepareData();
	}

	private void prepareData()
	{
		ArrayList<Route> routes = controllerDatabase.getRoutes();
		ArrayList<BusStop> busStops = controllerDatabase.getBusStops();

		//Would contains name and position from every bus-stop
		dataBusStops = new DataBusStop[busStops.size()];
		for(int i=0; i<busStops.size(); i++)
		{
			BusStop busStop = busStops.get(i);
			dataBusStops[i] = new DataBusStop(busStop.getLocation().getFirst(), busStop.getLocation().getSecond(), busStop.getName());
		}

		//Would contains name, colors and steps from every route
		dataRoutes = new DataRoute[routes.size()];
		for(int i=0; i<routes.size(); i++)
		{
			Route route = routes.get(i);
			//Added new data-route with route-data
			dataRoutes[i] = new DataRoute(colorDark, colorLight, route.getName());
			//Get Stops from route
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = route.getStops();
			//First bus-stop
			BusStop last = stops.get(0).getFirst();
			for(int j=1; j<stops.size(); j++)
			{
				//Get current bus-stop
				BusStop cur = stops.get(j).getFirst();
				//Add step (way between last and cur bus-stop)
				dataRoutes[i].addStep(new Quadruple<>(last.getLocation().getFirst(), last.getLocation().getSecond(),
						                              cur.getLocation().getFirst(), cur.getLocation().getSecond()));
				//Current is now last one
				last = cur;
			}
		}
	}

	public DataBusStop[] getBusStops()
	{
		return dataBusStops;
	}

	public DataRoute[] getRoutes()
	{
		return dataRoutes;
	}

	@Override
	protected void addListeners()
	{

	}

	@Override
	protected void removeListeners()
	{

	}

	public class DataRoute
	{
		private Color colorDark;
		private Color colorLight;
		private String name;
		private LinkedList<Quadruple<Integer, Integer, Integer, Integer>> steps;

		public DataRoute(Color colorDark, Color colorLight, String name)
		{
			this.colorDark = colorDark;
			this.colorLight = colorLight;
			this.name = name;

			steps = new LinkedList<>();
		}

		public Color getColorDark()
		{
			return colorDark;
		}

		public Color getColorLight()
		{
			return colorLight;
		}

		public String getName()
		{
			return name;
		}

		public LinkedList<Quadruple<Integer, Integer, Integer, Integer>> getSteps()
		{
			return steps;
		}

		public void addStep(Quadruple<Integer, Integer, Integer, Integer> step)
		{
			steps.add(step);
		}
	}

	public class DataBusStop
	{
		private int x;
		private int y;
		private String name;

		private DataBusStop(int x, int y, String name)
		{
			this.x = x;
			this.y = y;
			this.name = name;
		}

		public int getX()
		{
			return x;
		}

		public int getY()
		{
			return y;
		}

		public String getName()
		{
			return name;
		}
	}
}
