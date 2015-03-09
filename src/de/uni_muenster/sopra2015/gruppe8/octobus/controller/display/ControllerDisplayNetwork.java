package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;
import javafx.scene.chart.XYChart;

import java.awt.*;
import java.util.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerDisplayNetwork extends Controller implements ListenerButton
{
	private DisplayNetwork displayNetwork;
	private ControllerDatabase controllerDatabase;

	private DataBusStop[] dataBusStops;
	private DataRoute[] dataRoutes;

	private int maxWidth;
	private int maxHeight;

	private Color colorDark = new Color(0,0,0);
	private Color colorLight = new Color(120,120,120);

	public ControllerDisplayNetwork(DisplayNetwork displayNetwork)
	{
		super();

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
			if(busStop.getLocation().getFirst() > maxWidth)
				maxWidth = busStop.getLocation().getFirst();
			if(busStop.getLocation().getSecond() > maxHeight)
				maxHeight = busStop.getLocation().getSecond();
		}

		//Would contains name, colors and steps from every route
		dataRoutes = new DataRoute[routes.size()];
		for(int i=0; i<routes.size(); i++)
		{
			Route route = routes.get(i);
			//Added new data-route with route-data
			dataRoutes[i] = new DataRoute(colorDark, colorLight, route.getName(), route.isNight());
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

	public Tuple<Integer, Integer> getMaxSize()
	{
		return new Tuple<>(maxWidth, maxHeight);
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
		ControllerManager.addListener((ListenerButton) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch(btn)
		{
			case DISPLAY_NETWORK_NIGHT:
				break;

			case DISPLAY_NETWORK_DAY:
				break;
		}
	}

	public class DataRoute
	{
		private Color colorDark;
		private Color colorLight;
		private String name;
		private LinkedList<Quadruple<Integer, Integer, Integer, Integer>> steps;
		private boolean nightline;

		public DataRoute(Color colorDark, Color colorLight, String name, boolean nightline)
		{
			this.colorDark = colorDark;
			this.colorLight = colorLight;
			this.name = name;
			this.nightline = nightline;

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

		public boolean isNightline()
		{
			return nightline;
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
