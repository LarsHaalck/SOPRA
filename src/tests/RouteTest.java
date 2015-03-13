package tests;

import static org.junit.Assert.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import org.junit.Before;
import org.junit.Test;

public class RouteTest
{
	Route route;
	BusStop a;
	BusStop b;

	@Before
	public void setUp() throws Exception
	{
		ControllerDatabase db = ControllerDatabase.getTestingInstance("DB_for_jUnit_tests.db");
		// 2 -> Clemenshospital
		route = db.getRouteById(4);
		//Engelenschanze
		a = db.getBusStopById(18);
		b = db.getBusStopById(13);

	}

	@Test
	public void testStartEndDuration() throws Exception
	{
		assertEquals(23, route.getDuration());
	}


	@Test
	public void testById() throws Exception
	{
		assertEquals(6, route.getDuration(a.getId(), b.getId()));
	}

	@Test
	public void testByBusStops() throws Exception
	{
		assertEquals(6, route.getDuration(a, b));
	}
}