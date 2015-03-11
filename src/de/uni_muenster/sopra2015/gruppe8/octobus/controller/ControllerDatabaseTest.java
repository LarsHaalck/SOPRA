package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;

import static org.junit.Assert.*;

public class ControllerDatabaseTest
{

	private final String DB_NAME = "test.db";

	ControllerDatabase cdb = ControllerDatabase.getTestingInstance(DB_NAME);

	@Before
	public void setUp() throws Exception
	{
		cdb.createDatabaseTables();
	}

	@Test
	public void testBuses(){
		// Inspection date is 11.03.2015 14:34:21 in UTC / GMT
		Bus testBus = new Bus("MS-OB 1337", 100, 120, "MAN", "City Line 25", new Date(1426084461000l), false);

		// Write bus to database
		cdb.addBus(testBus);

		// Retrieve bus from database
		Bus retrievedBus = cdb.getBusById(1);

		// Make sure the bus is being retrieved properly from the database
		busEquals(testBus, retrievedBus);

		// Modify the bus into a completely different beast
		testBus.setId(1);
		testBus.setLicencePlate("PQ-RT 4");
		testBus.setNumberOfSeats(200);
		testBus.setStandingRoom(300);
		testBus.setManufacturer("CAT");
		testBus.setModel("Business Ultra");
		// That's 03.10.2021 08:43:29 in UTC / GMT
		testBus.setNextInspectionDue(new Date(1633250609000l));
		testBus.setArticulatedBus(true);

		// Tell the database to modify the bus accordingly
		cdb.modifyBus(testBus);
		// Retrieve the bus from the database again
		retrievedBus = cdb.getBusById(1);

		// Make sure the bus is being retrieved properly from the database
		busEquals(testBus, retrievedBus);

		// Delete bus from database
		cdb.deleteBus(1);

		// Finally make sure that the bus was deleted successfully
		assertEquals(cdb.getBusById(1), null);
	}

	private void busEquals(Bus bus1, Bus bus2)
	{
		assertEquals(bus1.getLicencePlate(), bus2.getLicencePlate());
		assertEquals(bus1.getNumberOfSeats(), bus2.getNumberOfSeats());
		assertEquals(bus1.getStandingRoom(), bus2.getStandingRoom());
		assertEquals(bus1.getManufacturer(), bus2.getManufacturer());
		assertEquals(bus1.getModel(), bus2.getModel());
		assertEquals(bus1.getNextInspectionDue(), bus2.getNextInspectionDue());
	}

	@Test
	public void testTours()
	{

	}

	@After
	public void tearDown() throws Exception
	{
		new File(DB_NAME).delete();
	}
}