package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterWindow;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import org.jooq.*;
import org.jooq.impl.*;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;
import java.util.Date;

/**
 * jOOQ Controller class for database access.
 *
 * @author Phil Steinhorst
 * @author Michael Biech
 */
public class ControllerDatabase
{

	/**
	 * Name of the database file which ought to be loaded
	 */
	private static final String DB_NAME = "Octobus.db";

	/**
	 * Used for date conversions
	 */
	private final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;

	private static ControllerDatabase controllerDatabase = null;

	private DSLContext create;

	private ControllerDatabase()
	{
		openDB();
	}

	static public ControllerDatabase getInstance()
	{
		if (controllerDatabase == null)
		{
			controllerDatabase = new ControllerDatabase();
		}

		return controllerDatabase;
	}


	/**
	 * Initializes database for use inside this class
	 */
	public void openDB()
	{
		try
		{
			// check if database file exists
			boolean newFile = !checkDatabaseFile();

			String url = "jdbc:sqlite:" + DB_NAME;
			Connection conn;

			// Dynamically load SQLite driver
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			create = DSL.using(conn, SQLDialect.SQLITE);

			// create tables in case of a new file
			if (newFile)
				createDatabaseTables();
		} catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load JDBC driver.");
			e.printStackTrace();
		} catch (SQLException e)
		{
			System.out.println("SQL-Exception occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Creates a database with necessary schema if no file with preexisting data is found.
	 */
	public boolean checkDatabaseFile()
	{
		File f = new File(DB_NAME);
		if (f.exists() && !f.isDirectory())
		{
			return true;
		}
		ControllerManager.informWindowOpen(EmitterWindow.DIALOG_NO_DB);
		return false;
	}

	public void createDatabaseTables()
	{
		// create bus stops table
		create.fetch("CREATE TABLE busStops (busStops_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL ," +
				"name TEXT (200), locationX INTEGER, locationY INTEGER, barrierFree BOOLEAN NOT NULL);");

		// create bus_stops table
		create.fetch("CREATE TABLE busStops_stoppingPoints (busStops_stoppingPoints_id INTEGER PRIMARY KEY " +
				"AUTOINCREMENT UNIQUE NOT NULL, busStops_id INTEGER, name TEXT (200), CONSTRAINT " +
				"fk__busStops_stoppingPoints__busStops_id__busStops__busStops_id FOREIGN KEY (busStops_id) " +
				"REFERENCES busStops (busStops_id));");

		// create buses table
		create.fetch("CREATE TABLE buses (buses_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
				"licencePlate TEXT (10) NOT NULL UNIQUE, numberOfSeats INTEGER (3) NOT NULL, standingRoom INTEGER (3), " +
				"manufacturer TEXT (200), model TEXT (200), nextInspectionDue INTEGER NOT NULL, " +
				"articulatedBus BOOLEAN NOT NULL);");

		// create employees table
		create.fetch("CREATE TABLE employees (employees_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, name " +
				"TEXT (200), firstName TEXT (200), address TEXT (200), zipCode TEXT (10), city TEXT (200), dateOfBirth " +
				"INTEGER, phone TEXT (200), email TEXT (200), username TEXT (10) UNIQUE NOT NULL, salt TEXT, password " +
				"TEXT, note TEXT (2000), isBUSDRIVER BOOLEAN NOT NULL, isNETWORK_PLANNER BOOLEAN NOT NULL, " +
				"isTICKET_PLANNER BOOLEAN NOT NULL, isHR_MANAGER BOOLEAN NOT NULL, " +
				"isSCHEDULE_MANAGER BOOLEAN NOT NULL);");

		// create routes table
		create.fetch("CREATE TABLE routes (routes_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, name " +
				"TEXT (200), note TEXT (2000), night BOOLEAN NOT NULL);");

		// create routes_startTimes table
		create.fetch("CREATE TABLE routes_startTimes (routes_startTimes_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE " +
				"NOT NULL, routes_id INTEGER, dayOfWeek TEXT, startTime INTEGER, CONSTRAINT " +
				"fk__routes_startTimes__routes_id__routes__routes_id FOREIGN KEY (routes_id) REFERENCES " +
				"routes (routes_id));");

		// create routes_stops table
		create.fetch("CREATE TABLE routes_stops (routes_stops_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
				"routes_id INTEGER, busStops_id INTEGER, busStops_stoppingPoints_id INTEGER, timeToPrevious INTEGER " +
				"(3), CONSTRAINT fk__routes_stops__routes_id__routes__routes_id FOREIGN KEY (routes_id) REFERENCES " +
				"routes (routes_id), CONSTRAINT fk__routes_stops__busStops_id__busStops__busStops_id FOREIGN KEY " +
				"(busStops_id) REFERENCES busStops (busStops_id), CONSTRAINT " +
				"fk__routes_stops__busStops_stoppingPoints_id__busStops_stoppingPoints__stoppingPoints_id FOREIGN KEY " +
				"(busStops_stoppingPoints_id) REFERENCES busStops_stoppingPoints (busStops_stoppingPoints_id));");

		// create soldTickets table
		create.fetch("CREATE TABLE soldTickets (soldTickets_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, " +
				"name TEXT (200), timestamp INTEGER, price INTEGER (6));");

		// create tickets table
		create.fetch("CREATE TABLE tickets (tickets_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, name TEXT " +
				"(200), price INTEGER (6), numPassengers INTEGER (3), description TEXT (2000));");

		// create tours table
		create.fetch("CREATE TABLE tours (tours_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, timestamp " +
				"INTEGER, routes_id INTEGER, buses_id INTEGER, employees_id INTEGER, CONSTRAINT " +
				"fk__tours__routes_id__routes__routes_id FOREIGN KEY (routes_id) REFERENCES routes (routes_id), " +
				"CONSTRAINT fk__tours__buses_id__buses__buses_id FOREIGN KEY (buses_id) REFERENCES buses (buses_id), " +
				"CONSTRAINT fk__tours__employees_id__employees__employees_id FOREIGN KEY (employees_id) REFERENCES " +
				"employees (employees_id), UNIQUE (timestamp, routes_id) ON CONFLICT IGNORE);");

		// create root user
		create
				.insertInto(EMPLOYEES,
						EMPLOYEES.NAME,
						EMPLOYEES.FIRSTNAME,
						EMPLOYEES.ADDRESS,
						EMPLOYEES.ZIPCODE,
						EMPLOYEES.CITY,
						EMPLOYEES.DATEOFBIRTH,
						EMPLOYEES.PHONE,
						EMPLOYEES.EMAIL,
						EMPLOYEES.USERNAME,
						EMPLOYEES.SALT,
						EMPLOYEES.PASSWORD,
						EMPLOYEES.NOTE,
						EMPLOYEES.ISBUSDRIVER,
						EMPLOYEES.ISNETWORK_PLANNER,
						EMPLOYEES.ISTICKET_PLANNER,
						EMPLOYEES.ISHR_MANAGER,
						EMPLOYEES.ISSCHEDULE_MANAGER)
				.values(
						"root",
						"root",
						"",
						"",
						"",
						(int) ((new Date()).getTime() / 1000),
						"",
						"",
						"root",
						"ijr45c1rv2m95kbi00u0ruqo52",
						"610475909569363416215711383102987484971637154606543703577386484556278824048427523924086531" +
								"2823167479747659082414963498490032193710285658277974290508907051",
						"",
						true,
						true,
						true,
						true,
						true)
				.execute();
	}

	/////////////////////////
	// Methods for "Bus"es //
	/////////////////////////

	/**
	 * Creates a new database entry for a Bus object
	 *
	 * @param bus Bus object to be stored in the database
	 * @return unique ID of newly created bus entry in the database
	 * @pre no bus with the same license plate is in the database. licencePlate is not null.
	 * @post bus is successfully added to database
	 */
	public int addBus(Bus bus)
	{
		BusesRecord newBus = create.insertInto(
				BUSES,
				BUSES.LICENCEPLATE,
				BUSES.NUMBEROFSEATS,
				BUSES.STANDINGROOM,
				BUSES.MANUFACTURER,
				BUSES.MODEL,
				BUSES.NEXTINSPECTIONDUE,
				BUSES.ARTICULATEDBUS)
				.values(bus.getLicencePlate(),
						bus.getNumberOfSeats(),
						bus.getStandingRoom(),
						bus.getManufacturer(),
						bus.getModel(),
						(int) (bus.getNextInspectionDue().getTime() / 1000),
						bus.isArticulatedBus())
				.returning(BUSES.BUSES_ID)
				.fetchOne();

		return (newBus.getBusesId());
	}

	/**
	 * Removes a bus entry from the database using its unique id. Assigned tours will also be modified
	 *
	 * @param id unique ID of the bus entry that is to be deleted from the database
	 * @return number of tours modified due to bus deletion
	 * @pre true
	 * @post bus with supplied ID is removed from database. Deleted bus is removed from his former tours
	 */
	public int deleteBus(int id)
	{
		// Start by deleting references to this bus from all tours
		int numOfTours = deleteBusFromTours(id);
		// Then delete the bus itself
		create.delete(BUSES)
				.where(BUSES.BUSES_ID.equal(id))
				.execute();
		return numOfTours;
	}

	/**
	 * Deletes a specific bus from tours within a specific range of time.
	 *
	 * @param uid   unique ID of the bus being deleted from tours
	 * @param begin point in time after which drivers ought to be deleted from tours
	 * @param end   point in time before which drivers ought to be deleted from tours
	 * @return number of tours edited
	 * @pre true
	 * @post tours of specified bus in specified range of time are removed from database
	 */
	public int deleteBusFromTours(int uid, Date begin, Date end)
	{
		// get number of tours using the bus in specified time range
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.BUSES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.fetchOne();

		// reset bus attribute in those tours
		create.update(TOURS)
				.set(TOURS.BUSES_ID, (Integer) null)
				.where(TOURS.BUSES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes a specific bus from tours.
	 *
	 * @param id unique ID of the bus being deleted from tours
	 * @return number of tours edited
	 * @pre true
	 * @post tours of specified bus are removed from database
	 */
	public int deleteBusFromTours(int id)
	{
		// get number of tours using the bus
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.BUSES_ID.equal(id))
				.fetchOne();

		// reset bus attribute in those tours
		create.update(TOURS)
				.set(TOURS.BUSES_ID, (Integer) null)
				.where(TOURS.BUSES_ID.equal(id))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Modifies an existing bus entry in the database
	 *
	 * @param bus Bus object whose entry is to be updated in the database
	 * @pre true
	 * @post properties of specified bus are changed if existing
	 */
	public void modifyBus(Bus bus)
	{
		create.update(BUSES)
				.set(BUSES.LICENCEPLATE, bus.getLicencePlate())
				.set(BUSES.NUMBEROFSEATS, bus.getNumberOfSeats())
				.set(BUSES.STANDINGROOM, bus.getStandingRoom())
				.set(BUSES.MANUFACTURER, bus.getManufacturer())
				.set(BUSES.MODEL, bus.getModel())
				.set(BUSES.NEXTINSPECTIONDUE, (int) (bus.getNextInspectionDue().getTime() / 1000))
				.set(BUSES.ARTICULATEDBUS, bus.isArticulatedBus())
				.where(BUSES.BUSES_ID.equal(bus.getId()))
				.execute();
	}

	/**
	 * Retrieves a list of Bus objects representing all bus entries stored in the database
	 *
	 * @return ArrayList containing Bus objects for every bus entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<Bus> getBuses()
	{
		// get all buses stored in database
		Result<BusesRecord> busRecords = create
				.selectFrom(BUSES)
				.orderBy(BUSES.LICENCEPLATE.asc())
				.fetch();

		// In case there are no buses - which is unlikely, but who knows
		if (busRecords == null) return null;

		ArrayList<Bus> busList = new ArrayList<>();

		// create a bus object for each bus stored in database and add to list
		for (BusesRecord rec : busRecords)
		{
			Bus bus = new Bus(
					rec.getLicenceplate(),
					rec.getNumberofseats(),
					rec.getStandingroom(),
					rec.getManufacturer(),
					rec.getModel(),
					new Date((long) rec.getNextinspectiondue() * 1000),
					rec.getArticulatedbus());
			bus.setId(rec.getBusesId());
			busList.add(bus);
		}
		return busList;
	}

	/**
	 * Retrieves a single Bus object from the database entry using its unique id
	 *
	 * @param id unique ID of the bus to be retrieved
	 * @return Bus object created from its corresponding entry the database, null if bus not found
	 * @pre true
	 * @post true
	 */
	public Bus getBusById(int id)
	{
		BusesRecord busRecord = create
				.selectFrom(BUSES)
				.where(BUSES.BUSES_ID.eq(id))
				.fetchOne();

		// if bus not found, return null
		if (busRecord == null) return null;

		// create bus object
		Bus bus = new Bus(
				busRecord.getLicenceplate(),
				busRecord.getNumberofseats(),
				busRecord.getStandingroom(),
				busRecord.getManufacturer(),
				busRecord.getModel(),
				new Date((long) busRecord.getNextinspectiondue() * 1000),
				busRecord.getArticulatedbus());
		bus.setId(id);
		return bus;
	}

	////////////////////////////
	// Methods for "BusStop"s //
	////////////////////////////

	/**
	 * Creates a new database entry for a BusStop object
	 *
	 * @param bstop BusStop object to be stored in the database
	 * @return unique ID of newly created bus stop entry in the database
	 * @pre true
	 * @post bus stop is successfully added to database
	 */
	public int addBusStop(BusStop bstop)
	{
		BusstopsRecord newStop = create
				.insertInto(
						BUSSTOPS, BUSSTOPS.NAME,
						BUSSTOPS.LOCATIONX,
						BUSSTOPS.LOCATIONY,
						BUSSTOPS.BARRIERFREE)
				.values(bstop.getName(),
						bstop.getLocation().getFirst(),
						bstop.getLocation().getSecond(),
						bstop.isBarrierFree())
				.returning(BUSSTOPS.BUSSTOPS_ID)
				.fetchOne();

		// insert stopping points' data into database
		HashSet<StoppingPoint> points = bstop.getStoppingPoints();
		for (StoppingPoint s : points)
		{
			create.insertInto(
					BUSSTOPS_STOPPINGPOINTS,
					BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID,
					BUSSTOPS_STOPPINGPOINTS.NAME)
					.values(
							newStop.getBusstopsId(),
							s.getName())
					.execute();
		}

		return (newStop.getBusstopsId());
	}

	/**
	 * Removes a bus stop entry from the database using its unique id. If a bus stop is still
	 * in use by a route, the stop should NOT be deleted! Instead, the user ought to be
	 * notified that the bus stop is still in use
	 *
	 * @param id unique ID of the bus stop entry that is to be deleted from the database
	 * @pre no routes or stopping points that use the specified bus stop exist in database
	 * @post bus stop with supplied ID is removed from database
	 */
	public void deleteBusStop(int id)
	{
		create.delete(BUSSTOPS)
				.where(BUSSTOPS.BUSSTOPS_ID.equal(id))
				.execute();
	}

	/**
	 * Deletes all stopping points of a specific bus stop.
	 *
	 * @param id unique ID of the bus stop whose stopping points ought to be deleted
	 * @return number of stopping points deleted
	 * @pre no stopping point of the specified bus stop is used as a route stop.
	 * @post all stopping points of the specified bus stop are removed from database
	 */
	public int deleteStoppingPointsUsingBusStopId(int id)
	{
		// get number of stopping points associated with bus stop...
		Record record = create
				.selectCount()
				.from(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(id))
				.fetchOne();

		// ... and delete them
		create.delete(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.eq(id))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Retrieves the bus stop associated with the given stopping point. Comes in handy for
	 * displaying the complete name of a stopping point.
	 *
	 * @param id StoppingPoint for which to retrieve the associated BusStop
	 * @return BusStop associated with the stopping point
	 * @pre true
	 * @post true
	 */
	public BusStop getBusStopByStoppingPointId(int id)
	{
		BusstopsStoppingpointsRecord r = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetchOne();

		if (r == null) return null;

		return getBusStopById(r.getBusstopsId());
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TODO: StoppingPoints werden noch nicht aktualisiert! Dafür entweder eine eigene Entitätsklasse schaffen oder HashSet in BusStop modifizieren! //
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Modifies an existing bus stop entry in the database
	 *
	 * @param bstop BusStop object whose entry is to be updated in the database
	 * @pre true
	 * @post properties of specified bus stop are changed if existing
	 */
	public void modifyBusStop(BusStop bstop)
	{
		create.update(BUSSTOPS)
				.set(BUSSTOPS.NAME, bstop.getName())
				.set(BUSSTOPS.LOCATIONX, bstop.getLocation().getFirst())
				.set(BUSSTOPS.LOCATIONY, bstop.getLocation().getSecond())
				.set(BUSSTOPS.BARRIERFREE, bstop.isBarrierFree())
				.where(BUSSTOPS.BUSSTOPS_ID.eq(bstop.getId()))
				.execute();
	}

	/**
	 * Retrieves a list of BusStop objects representing all bus stop entries stored in the database
	 *
	 * @return ArrayList containing BusStop objects for every bus stop entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<BusStop> getBusStops()
	{
		// Start by getting all bus stops from the database
		Result<BusstopsRecord> busStopRecords = create
				.selectFrom(BUSSTOPS)
				.orderBy(BUSSTOPS.NAME.asc())
				.fetch();

		// In case there are no BusStops, which is unlikely, but who knows
		if (busStopRecords == null) return null;

		ArrayList<BusStop> busStopList = new ArrayList<>();

		// For each bus retrieved...
		for (BusstopsRecord rec : busStopRecords)
		{
			// ... get all corresponding stopping points...
			Result<BusstopsStoppingpointsRecord> stoppingPoints = create
					.selectFrom(BUSSTOPS_STOPPINGPOINTS)
					.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getBusstopsId()))
					.fetch();

			// ... and put them all into a HashSet
			HashSet<StoppingPoint> spoints = new HashSet<>();

			if (stoppingPoints != null)
			{
				for (BusstopsStoppingpointsRecord sp : stoppingPoints)
				{
					spoints.add(new StoppingPoint(sp.getBusstopsStoppingpointsId(), sp.getName()));
				}
			}

			boolean barrier = rec.getBarrierfree();

			// create bus stop objects belonging to that route
			BusStop busStop = new BusStop(
					rec.getName(),
					new Tuple<>(rec.getLocationx(), rec.getLocationy()),
					spoints,
					barrier);
			busStop.setId(rec.getBusstopsId());

			// Finally, create BusStop object and add it to the ArrayList
			busStopList.add(busStop);
		}
		return busStopList;
	}

	/**
	 * Retrieves a single BusStop object from the database entry using its unique id
	 *
	 * @param id unique ID of the bus stop to be retrieved
	 * @return BusStop object created from its corresponding entry the database, null if bus stop not found
	 * @pre true
	 * @post true
	 */
	public BusStop getBusStopById(int id)
	{
		// Get desired bus from data base
		BusstopsRecord rec = create
				.selectFrom(BUSSTOPS)
				.where(BUSSTOPS.BUSSTOPS_ID.eq(id))
				.fetchOne();
		if (rec == null) return null;

		// Get all associated stopping points...
		Result<BusstopsStoppingpointsRecord> stoppingPoints = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getBusstopsId()))
				.fetch();

		// .. and add them into a HashSet
		//  (Here, we don't worry about checking if stoppingPoints is null, because if it is, the loop just won't run)
		HashSet<StoppingPoint> spoints = new HashSet<>();
		if (stoppingPoints != null)
		{
			for (BusstopsStoppingpointsRecord sp : stoppingPoints)
			{
				spoints.add(new StoppingPoint(sp.getBusstopsStoppingpointsId(), sp.getName()));
			}
		}

		// create all associated bus stop objects
		BusStop busStop = new BusStop(
				rec.getName(),
				new Tuple<>(rec.getLocationx(), rec.getLocationy()),
				spoints,
				rec.getBarrierfree());

		busStop.setId(id);
		return busStop;
	}

	/**
	 * Returns a list of names of routes which use a specific stopping point
	 *
	 * @param id unique ID of the stopping point
	 * @return ArrayList of Strings containing names of routes
	 * @pre true
	 * @post true
	 */
	public ArrayList<String> getRouteNamesUsingStoppingPointId(int id)
	{
		// get all concerned route stops entries
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetch();

		// fetch names of associated routes and put them into a list
		ArrayList<String> names = new ArrayList<>();
		if (routes == null) return names; // return empty list if no routes found
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getRoutesId();
			RoutesRecord route = create
					.selectFrom(ROUTES)
					.where(ROUTES.ROUTES_ID.eq(routeID))
					.groupBy(ROUTES_STOPS.ROUTES_ID)
					.orderBy(ROUTES.ROUTES_ID.asc())
					.fetchOne();
			if (route != null) names.add(route.getName());
		}
		return (names);
	}

	/**
	 * Returns a list of routes which use a specific stopping point
	 *
	 * @param id unique ID of the stopping point
	 * @return ArrayList of routes containing the routes
	 * @pre true
	 * @post true
	 */
	public ArrayList<Route> getRoutesUsingStoppingPoint(int id)
	{
		// get all concerned route stops entries
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetch();

		// create all associated routes and put them into a list
		ArrayList<Route> listOfRoutes = new ArrayList<>();
		if (routes == null) return listOfRoutes; // return empty list if no routes found
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getRoutesId();
			listOfRoutes.add(getRouteById(routeID));
		}
		return (listOfRoutes);
	}

	/**
	 * Returns a list of names of routes which use a specific bus stop
	 *
	 * @param id unique ID of the bus stop
	 * @return ArrayList of Strings containing names of routes
	 * @pre true
	 * @post true
	 */
	public ArrayList<String> getRouteNamesUsingBusStopId(int id)
	{
		// get all concerned route stops entries
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_ID.eq(id))
				.groupBy(ROUTES_STOPS.ROUTES_ID)
				.fetch();

		// fetch names of associated routes and put them into a list
		ArrayList<String> names = new ArrayList<>();
		// return empty list if no routes found
		if (routes == null) return names;
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getRoutesId();
			RoutesRecord route = create
					.selectFrom(ROUTES)
					.where(ROUTES.ROUTES_ID.eq(routeID))
					.fetchOne();
			if (route != null) names.add(route.getName());
		}
		return (names);
	}

	/////////////////////////////
	// Methods for "StoppingPoint"s //
	/////////////////////////////

	/**
	 * Creates a new database entry for a StoppingPoint object
	 *
	 * @param id unique ID of bus stop to which the stopping point is assigned
	 * @param sp StoppingPoint object to be stored in the database
	 * @return unique ID of newly created stopping point entry in the database
	 * @pre bus stop with supplied id exists in database.
	 * @post stopping point is successfully added to database
	 */
	public int addStoppingPoint(int id, StoppingPoint sp)
	{
		BusstopsStoppingpointsRecord newStop = create
				.insertInto(
						BUSSTOPS_STOPPINGPOINTS,
						BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID,
						BUSSTOPS_STOPPINGPOINTS.NAME)
				.values(
						id,
						sp.getName())
				.returning(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID)
				.fetchOne();

		return (newStop.getBusstopsStoppingpointsId());
	}

	/**
	 * Returns a list of stopping points which belong to a specific bus stop
	 *
	 * @param id unique ID of the bus stop
	 * @return ArrayList of stopping points belonging to the bus stop
	 * @pre true
	 * @post true
	 */
	public ArrayList<StoppingPoint> getStoppingPointsByBusStopId(int id)
	{
		// get all concerned stopping point entries
		Result<BusstopsStoppingpointsRecord> rows = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.eq(id))
				.orderBy(BUSSTOPS_STOPPINGPOINTS.NAME.asc())
				.fetch();

		// create all stopping point objects and put them into a list
		ArrayList<StoppingPoint> result = new ArrayList<>();
		if (rows == null) return result; // return empty list if no rows
		for (BusstopsStoppingpointsRecord rec : rows)
		{
			result.add(getStoppingPointById(rec.getBusstopsStoppingpointsId()));
		}
		return result;
	}


	/////////////////////////////
	// Methods for "Employee"s //
	/////////////////////////////

	/**
	 * Creates a new database entry for an Employee object
	 *
	 * @param emp Employee object to be stored in the database
	 * @return unique ID of newly created employee entry in the database
	 * @pre no employee with the same user name is in the database. userName and roles are not null.
	 * @post employee is successfully added to database
	 */
	public int addEmployee(Employee emp)
	{
		// Take care of creating a salt and hashing the default password with that salt
		SecureRandom random = new SecureRandom();
		String salt = new BigInteger(130, random).toString(32);
		String password = "octobus";
		String generatedHash;

		try
		{
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.update(password.getBytes());
			digest.update(salt.getBytes());
			generatedHash = new BigInteger(1, digest.digest()).toString();

		} catch (NoSuchAlgorithmException e)
		{
			throw new UnsupportedOperationException(e);
		}

		EmployeesRecord newEmp = create
				.insertInto(EMPLOYEES,
						EMPLOYEES.NAME,
						EMPLOYEES.FIRSTNAME,
						EMPLOYEES.ADDRESS,
						EMPLOYEES.ZIPCODE,
						EMPLOYEES.CITY,
						EMPLOYEES.DATEOFBIRTH,
						EMPLOYEES.PHONE,
						EMPLOYEES.EMAIL,
						EMPLOYEES.USERNAME,
						EMPLOYEES.SALT,
						EMPLOYEES.PASSWORD,
						EMPLOYEES.NOTE,
						EMPLOYEES.ISBUSDRIVER,
						EMPLOYEES.ISNETWORK_PLANNER,
						EMPLOYEES.ISTICKET_PLANNER,
						EMPLOYEES.ISHR_MANAGER,
						EMPLOYEES.ISSCHEDULE_MANAGER)
				.values(
						emp.getName(),
						emp.getFirstName(),
						emp.getAddress(),
						emp.getZipCode(),
						emp.getCity(),
						(int) (emp.getDateOfBirth().getTime() / 1000),
						emp.getPhone(),
						emp.getEmail(),
						emp.getUsername(),
						salt,
						generatedHash,
						emp.getNote(),
						emp.isRole(Role.BUSDRIVER),
						emp.isRole(Role.NETWORK_PLANNER),
						emp.isRole(Role.TICKET_PLANNER),
						emp.isRole(Role.HR_MANAGER),
						emp.isRole(Role.SCHEDULE_MANAGER))
				.returning(EMPLOYEES.EMPLOYEES_ID)
				.fetchOne();

		return (newEmp.getEmployeesId());
	}

	/**
	 * Removes an employee entry from the database using its unique id. Assigned tours will be modified
	 *
	 * @param id unique ID of the employee entry to be deleted from the database
	 * @return number of tours modified due to employee deletion
	 * @pre true
	 * @post employee with supplied ID is removed from database. Deleted employee is removed from his former tours
	 */
	public int deleteEmployee(int id)
	{
		int numOfTours = deleteEmployeeFromTours(id);
		create.delete(EMPLOYEES)
				.where(EMPLOYEES.EMPLOYEES_ID.equal(id))
				.execute();
		return numOfTours;
	}

	/**
	 * Modifies an existing employee entry in the database
	 *
	 * @param emp Employee object whose entry is to be updated in the database
	 * @pre true
	 * @post properties of specified employee are changed if existing
	 */
	public void modifyEmployee(Employee emp)
	{
		create.update(EMPLOYEES)
				.set(EMPLOYEES.NAME, emp.getName())
				.set(EMPLOYEES.FIRSTNAME, emp.getFirstName())
				.set(EMPLOYEES.ADDRESS, emp.getAddress())
				.set(EMPLOYEES.ZIPCODE, emp.getZipCode())
				.set(EMPLOYEES.CITY, emp.getCity())
				.set(EMPLOYEES.DATEOFBIRTH, (int) (emp.getDateOfBirth().getTime() / 1000))
				.set(EMPLOYEES.PHONE, emp.getPhone())
				.set(EMPLOYEES.EMAIL, emp.getEmail())
				.set(EMPLOYEES.USERNAME, emp.getUsername())
				.set(EMPLOYEES.SALT, emp.getSalt())
				.set(EMPLOYEES.PASSWORD, emp.getPassword())
				.set(EMPLOYEES.NOTE, emp.getNote())
				.set(EMPLOYEES.ISBUSDRIVER, emp.isRole(Role.BUSDRIVER))
				.set(EMPLOYEES.ISNETWORK_PLANNER, emp.isRole(Role.NETWORK_PLANNER))
				.set(EMPLOYEES.ISTICKET_PLANNER, emp.isRole(Role.TICKET_PLANNER))
				.set(EMPLOYEES.ISHR_MANAGER, emp.isRole(Role.HR_MANAGER))
				.set(EMPLOYEES.ISSCHEDULE_MANAGER, emp.isRole(Role.SCHEDULE_MANAGER))
				.where(EMPLOYEES.EMPLOYEES_ID.equal(emp.getId()))
				.execute();
	}

	/**
	 * Retrieves a list of Employee objects representing all employee entries stored in the database
	 *
	 * @return ArrayList containing Employee objects for every employee entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<Employee> getEmployees()
	{
		// get all employee entries from database
		Result<EmployeesRecord> empRecords = create
				.selectFrom(EMPLOYEES)
				.orderBy(EMPLOYEES.NAME.asc(), EMPLOYEES.FIRSTNAME.asc())
				.fetch();

		// In case we have no employees to return
		if (empRecords == null) return null;

		// for each entry, create a employee object
		ArrayList<Employee> empList = new ArrayList<>();
		for (EmployeesRecord rec : empRecords)
		{
			// build HashSet containing roles of the current employee
			HashSet<Role> roles = new HashSet<>();
			if (rec.getIsscheduleManager())
				roles.add(Role.SCHEDULE_MANAGER);
			if (rec.getIshrManager())
				roles.add(Role.HR_MANAGER);
			if (rec.getIsticketPlanner())
				roles.add(Role.TICKET_PLANNER);
			if (rec.getIsnetworkPlanner())
				roles.add(Role.NETWORK_PLANNER);
			if (rec.getIsbusdriver())
				roles.add(Role.BUSDRIVER);

			Employee emp = new Employee(
					rec.getName(),
					rec.getFirstname(),
					rec.getAddress(),
					rec.getZipcode(),
					rec.getCity(),
					new Date((long) rec.getDateofbirth() * 1000),
					rec.getPhone(),
					rec.getEmail(),
					rec.getUsername(),
					rec.getPassword(),
					rec.getSalt(),
					rec.getNote(),
					roles);
			emp.setId(rec.getEmployeesId());
			empList.add(emp);
		}
		return empList;
	}

	/**
	 * Retrieves a single Employee object from the database entry using its unique id
	 *
	 * @param id unique ID of the employee to be retrieved
	 * @return Employee object created from its corresponding entry the database, null if employee not found
	 * @pre true
	 * @post true
	 */
	public Employee getEmployeeById(int id)
	{
		EmployeesRecord rec = create
				.selectFrom(EMPLOYEES)
				.where(EMPLOYEES.EMPLOYEES_ID.eq(id))
				.fetchOne();

		// Return null if employee can not be found
		if (rec == null) return null;

		// build HashSet containing roles
		HashSet<Role> roles = new HashSet<>();
		if (rec.getIsscheduleManager())
			roles.add(Role.SCHEDULE_MANAGER);
		if (rec.getIshrManager())
			roles.add(Role.HR_MANAGER);
		if (rec.getIsticketPlanner())
			roles.add(Role.TICKET_PLANNER);
		if (rec.getIsnetworkPlanner())
			roles.add(Role.NETWORK_PLANNER);
		if (rec.getIsbusdriver())
			roles.add(Role.BUSDRIVER);

		Employee emp = new Employee(
				rec.getName(),
				rec.getFirstname(),
				rec.getAddress(),
				rec.getZipcode(),
				rec.getCity(),
				new Date((long) rec.getDateofbirth() * 1000),
				rec.getPhone(),
				rec.getEmail(),
				rec.getUsername(),
				rec.getPassword(),
				rec.getSalt(),
				rec.getNote(),
				roles);
		emp.setId(id);

		return emp;
	}

	/**
	 * Retrieves a single Employee object from the database entry using its unique user name
	 *
	 * @param username unique username of the employee to be retrieved
	 * @return Employee object created from its corresponding entry the database, null if employee not found
	 * @pre true
	 * @post true
	 */
	public Employee getEmployeeByUsername(String username)
	{
		EmployeesRecord rec = create
				.selectFrom(EMPLOYEES)
				.where(EMPLOYEES.USERNAME.eq(username))
				.fetchOne();
		return (rec == null) ? null : getEmployeeById(rec.getEmployeesId());
	}

	/**
	 * Deletes a specific user from tours within a specific range of time.
	 *
	 * @param uid   unique ID of the user being deleted from tours
	 * @param begin point in time after which drivers ought to be deleted from tours
	 * @param end   point in time before which drivers ought to be deleted from tours
	 * @return number of tours edited
	 * @pre true
	 * @post tours of specified employee in specified range of time are removed from database
	 */
	public int deleteEmployeeFromTours(int uid, Date begin, Date end)
	{
		// count all tours using that employee in specified time range
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.EMPLOYEES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.fetchOne();

		// reset employee attribute in those tours
		create.update(TOURS)
				.set(TOURS.EMPLOYEES_ID, (Integer) null)
				.where(TOURS.EMPLOYEES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes a specific user from tours.
	 *
	 * @param id unique ID of the user being deleted from tours
	 * @return number of tours edited
	 * @pre true
	 * @post tours of specified employee are removed from database
	 */
	public int deleteEmployeeFromTours(int id)
	{
		// count all tours using that employee
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.EMPLOYEES_ID.equal(id))
				.fetchOne();

		// reset employee attribute in those tours
		create.update(TOURS)
				.set(TOURS.EMPLOYEES_ID, (Integer) null)
				.where(TOURS.EMPLOYEES_ID.equal(id))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Retrieves a list of Employee objects from database having a specified role
	 *
	 * @param role Role of requested employees
	 * @return ArrayList containing Employee objects that have the specified role
	 * @pre true
	 * @post true
	 */
	public ArrayList<Employee> getEmployeesByRole(Role role)
	{
		TableField<EmployeesRecord, Boolean> roleAttribute;

		switch (role)
		{
			case BUSDRIVER:
				roleAttribute = EMPLOYEES.ISBUSDRIVER;
				break;
			case HR_MANAGER:
				roleAttribute = EMPLOYEES.ISHR_MANAGER;
				break;
			case NETWORK_PLANNER:
				roleAttribute = EMPLOYEES.ISNETWORK_PLANNER;
				break;
			case SCHEDULE_MANAGER:
				roleAttribute = EMPLOYEES.ISSCHEDULE_MANAGER;
				break;
			case TICKET_PLANNER:
				roleAttribute = EMPLOYEES.ISTICKET_PLANNER;
				break;
			default:
				return new ArrayList<>();
		}

		Result<EmployeesRecord> rows = create
				.selectFrom(EMPLOYEES)
				.where(roleAttribute.eq(true))
				.fetch();

		ArrayList<Employee> result = new ArrayList<>();
		if (rows == null) return null;

		for (EmployeesRecord rec : rows)
		{
			result.add(getEmployeeById(rec.getEmployeesId()));
		}
		return result;

	}

	//////////////////////////
	// Methods for "Route"s //
	//////////////////////////

	/**
	 * Creates a new database entry for a Route object
	 *
	 * @param r Route object to be stored in the database
	 * @return unique ID of newly created route entry in the database
	 * @pre true
	 * @post route is successfully added to database
	 */
	public int addRoute(Route r)
	{
		// add route entry to routes table
		RoutesRecord newRoute = create
				.insertInto(ROUTES,
						ROUTES.NAME,
						ROUTES.NOTE,
						ROUTES.NIGHT)
				.values(
						r.getName(),
						r.getNote(),
						r.isNight())
				.returning(ROUTES.ROUTES_ID)
				.fetchOne();

		// add associated bus stops to routes_stops table
		LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = r.getStops();
		for (Triple<BusStop, StoppingPoint, Integer> triple : stops)
		{
			create.insertInto(ROUTES_STOPS,
					ROUTES_STOPS.ROUTES_ID,
					ROUTES_STOPS.BUSSTOPS_ID,
					ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID,
					ROUTES_STOPS.TIMETOPREVIOUS)
					.values(
							newRoute.getRoutesId(),
							triple.getFirst().getId(),
							triple.getSecond().getId(),
							triple.getThird())
					.execute();
		}

		// add associated start times to routes_startTimes table
		HashMap<DayOfWeek, LinkedList<Integer>> times = r.getStartTimes();
		for (DayOfWeek day : times.keySet())
		{
			for (Integer time : times.get(day))
			{
				create.insertInto(ROUTES_STARTTIMES,
						ROUTES_STARTTIMES.ROUTES_ID,
						ROUTES_STARTTIMES.DAYOFWEEK,
						ROUTES_STARTTIMES.STARTTIME)
						.values(
								newRoute.getRoutesId(),
								day.toString(),
								time)
						.execute();
			}
		}

		return (newRoute.getRoutesId());
	}

	/**
	 * Removes a route entry from the database using its unique id. Route stops, tours and start times will
	 * also be deleted
	 *
	 * @param id unique ID of the route entry that is to be deleted from the database
	 * @pre true
	 * @post route with supplied ID and all assigned route stops, tours and start times are removed from database
	 */
	public int deleteRoute(int id)
	{
		int numOfTours = deleteToursUsingRoutesId(id);
		deleteStartTimesUsingRouteId(id);
		deleteRoutesStopsUsingRouteId(id);
		create.delete(ROUTES)
				.where(ROUTES.ROUTES_ID.equal(id))
				.execute();
		return (numOfTours);
	}

	/**
	 * Modifies an existing route entry in the database. Route entry ID will be changed if stopping points or starting
	 * times are changed
	 *
	 * @param r Route object whose entry is to be updated in the database
	 * @param deleteTours true if associated tours ought to be deleted
	 * @return id of the changed route entry (new if deleteTours was true)
	 * @pre true
	 * @post properties of specified route are changed if existing
	 */
	public int modifyRoute(Route r, boolean deleteTours)
	{
		if (!deleteTours)
		{
			// first case: stops or start times not changed --> no tours to delete!
			create.update(ROUTES)
					.set(ROUTES.NAME, r.getName())
					.set(ROUTES.NOTE, r.getNote())
					.set(ROUTES.NIGHT, r.isNight())
					.where(ROUTES.ROUTES_ID.equal(r.getId()))
					.execute();
			return (r.getId());
		} else
		{
			// second case: stops or start times changed --> rebuild all associated entries in database
			deleteToursUsingRoutesId(r.getId());
			deleteStartTimesUsingRouteId(r.getId());
			deleteRoutesStopsUsingRouteId(r.getId());
			deleteRoute(r.getId());

			// Route gets a new ID here!
			return (addRoute(r));
		}
	}


	/**
	 * Retrieves a list of Route objects representing all route entries stored in the database
	 *
	 * @return ArrayList containing Route objects for every route entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<Route> getRoutes()
	{
		// Start by getting all routes table entries from the database
		Result<RoutesRecord> routesRecords = create
				.selectFrom(ROUTES)
				.orderBy(ROUTES.NAME.asc())
				.fetch();
		ArrayList<Route> routesList = new ArrayList<>();
		if (routesRecords == null) return (routesList); // return empty list if no routes found
		// For each route entry retrieved...
		for (RoutesRecord rec : routesRecords)
		{
			// ... get all corresponding start times ...
			Result<RoutesStarttimesRecord> startTimesRecords = create
					.selectFrom(ROUTES_STARTTIMES)
					.where(ROUTES_STARTTIMES.ROUTES_ID.equal(rec.getRoutesId()))
					.fetch();

			HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();
			startTimes.put(DayOfWeek.MONDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.TUESDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.THURSDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.FRIDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.SATURDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.SUNDAY, new LinkedList<>());

			// ... and put them into their respective lists, depending on the day
			if (startTimesRecords != null)
			{
				for (RoutesStarttimesRecord timerec : startTimesRecords)
				{
					if (timerec.getDayofweek().equals("MONDAY"))
						startTimes.get(DayOfWeek.MONDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("TUESDAY"))
						startTimes.get(DayOfWeek.TUESDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("WEDNESDAY"))
						startTimes.get(DayOfWeek.WEDNESDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("THURSDAY"))
						startTimes.get(DayOfWeek.THURSDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("FRIDAY"))
						startTimes.get(DayOfWeek.FRIDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("SATURDAY"))
						startTimes.get(DayOfWeek.SATURDAY).add(timerec.getStarttime());
					if (timerec.getDayofweek().equals("SUNDAY"))
						startTimes.get(DayOfWeek.SUNDAY).add(timerec.getStarttime());
				}
			}

			// Now get all stops on the route ...
			Result<RoutesStopsRecord> stopsRecords = create
					.selectFrom(ROUTES_STOPS)
					.where(ROUTES_STOPS.ROUTES_ID.equal(rec.getRoutesId()))
					.fetch();
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

			// ... and add them to the list of stops
			if (stopsRecords != null)
			{
				for (RoutesStopsRecord s : stopsRecords)
				{
					int stopId = s.getBusstopsId();
					BusStop bstop = getBusStopById(stopId);
					StoppingPoint spoint = getStoppingPointById(s.getBusstopsStoppingpointsId());
					stops.add(new Triple<>(bstop, spoint, s.getTimetoprevious()));
				}
			}

			// Finally, create Route object and add it to the ArrayList
			Route route = new Route(
					rec.getName(),
					rec.getNote(),
					stops,
					rec.getNight(),
					startTimes);
			route.setId(rec.getRoutesId());

			routesList.add(route);
		}
		return routesList;
	}

	/**
	 * Retrieves a single Route object from the database entry using its unique id
	 *
	 * @param id unique ID of the route to be retrieved
	 * @return Route object created from its corresponding entry the database, null if route not found
	 * @pre true
	 * @post true
	 */
	public Route getRouteById(int id)
	{
		// Start by getting the desired route from the database
		RoutesRecord rec = create
				.selectFrom(ROUTES)
				.where(Routes.ROUTES.ROUTES_ID.eq(id))
				.fetchOne();

		if (rec == null) return null;

		// Fetch its starting times
		Result<RoutesStarttimesRecord> startTimesRecords = create
				.selectFrom(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.equal(id))
				.fetch();

		HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();
		startTimes.put(DayOfWeek.MONDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.TUESDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.THURSDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.FRIDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.SATURDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.SUNDAY, new LinkedList<>());

		// Put the starting times for the route in respective lists
		if (startTimesRecords != null)
		{
			for (RoutesStarttimesRecord timerec : startTimesRecords)
			{
				if (timerec.getDayofweek().equals("MONDAY"))
					startTimes.get(DayOfWeek.MONDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("TUESDAY"))
					startTimes.get(DayOfWeek.TUESDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("WEDNESDAY"))
					startTimes.get(DayOfWeek.WEDNESDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("THURSDAY"))
					startTimes.get(DayOfWeek.THURSDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("FRIDAY"))
					startTimes.get(DayOfWeek.FRIDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("SATURDAY"))
					startTimes.get(DayOfWeek.SATURDAY).add(timerec.getStarttime());
				if (timerec.getDayofweek().equals("SUNDAY"))
					startTimes.get(DayOfWeek.SUNDAY).add(timerec.getStarttime());
			}
		}


		// Now get all stops on the route ...
		Result<RoutesStopsRecord> stopsRecords = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.equal(id))
				.fetch();
		LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

		// ... and add them to the list of stops
		if (stopsRecords != null)
		{
			for (RoutesStopsRecord s : stopsRecords)
			{
				int stopId = s.getBusstopsId();
				BusStop bstop = getBusStopById(stopId);
				StoppingPoint spoint = getStoppingPointById(s.getBusstopsStoppingpointsId());
				stops.add(new Triple<>(bstop, spoint, s.getTimetoprevious()));
			}
		}

		// create and return route object
		Route route = new Route(
				rec.getName(),
				rec.getNote(),
				stops,
				rec.getNight(),
				startTimes);
		route.setId(rec.getRoutesId());
		return route;
	}

	/**
	 * Deletes all stopping points of a specific route.
	 *
	 * @param id unique ID of the route whose stopping points to be deleted
	 * @return number of stopping points deleted
	 * @pre true
	 * @post all route stops assigned to route with specified ID are deleted from database
	 */
	public int deleteRoutesStopsUsingRouteId(int id)
	{
		// count all route stops associated to that route...
		Record record = create
				.selectCount()
				.from(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.equal(id))
				.fetchOne();

		// ... and delete them
		create.delete(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.eq(id))
				.execute();
		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes all start times of a specific route.
	 *
	 * @param id unique ID of the route whose start times to be deleted.
	 * @return number of stopping points deleted
	 * @pre true
	 * @post all start times assigned to route with specified ID are deleted from database
	 */
	public int deleteStartTimesUsingRouteId(int id)
	{
		// count all start times associated to that route...
		Record record = create
				.selectCount()
				.from(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.equal(id))
				.fetchOne();

		// ... and delete them
		create.delete(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.eq(id))
				.execute();
		return (Integer) record.getValue(0);
	}

	/**
	 * Returns a list of names of bus stops and stopping points which are used by a specific route
	 *
	 * @param id unique ID of the route
	 * @return ArrayList of Strings containing names of bus stops with stopping points
	 * @pre true
	 * @post true
	 */
	public ArrayList<String> getBusStopNamesUsingRouteId(int id)
	{
		// Get all route stops entries associated to that route
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.eq(id))
				.groupBy(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID)
				.fetch();

		// Concatenate bus stop name and stopping point name and add to list
		ArrayList<String> names = new ArrayList<>();
		// Empty list if no routes found
		if (routes == null) return names;
		for (RoutesStopsRecord rec : routes)
		{
			// Fetch bus stop name from database
			int stopID = rec.getBusstopsId();
			BusstopsRecord bstop = create
					.selectFrom(BUSSTOPS)
					.where(BUSSTOPS.BUSSTOPS_ID.eq(stopID))
					.fetchOne();
			String nameString = bstop.getName() + " ";

			// Fetch stopping point name from database
			stopID = rec.getBusstopsStoppingpointsId();
			BusstopsStoppingpointsRecord bstoppoint = create
					.selectFrom(BUSSTOPS_STOPPINGPOINTS)
					.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(stopID))
					.fetchOne();
			nameString += bstoppoint.getName();
			names.add(nameString);
		}
		return (names);
	}

	public String getCompleteStoppingPointName(int id)
	{
		// Get all route stops entries associated to that route
		BusstopsStoppingpointsRecord rec = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetchOne();

		if (rec == null) return null;
		int busStopID = rec.getBusstopsId();
		BusstopsRecord stoprecord = create
				.selectFrom(BUSSTOPS)
				.where(BUSSTOPS.BUSSTOPS_ID.eq(busStopID))
				.fetchOne();

		return stoprecord.getName() + " " + rec.getName();
	}

	/**
	 * gets the number of routes with a specific stopping point assigned.
	 *
	 * @param id unique ID of the stopping point
	 * @return number of routes using the stopping point
	 * @pre true
	 * @post true
	 */
	public int getNumberOfRoutesUsingStoppingPointId(int id)
	{
		Record record = create
				.selectCount()
				.from(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID.equal(id))
				.groupBy(ROUTES_STOPS.ROUTES_ID)
				.fetchOne();

		return (Integer) record.getValue(0);
	}

	//////////////////////////////////
	// Methods for "StoppingPoint"s //
	//////////////////////////////////

	/**
	 * Retrieves a single StoppingPoint object from the database entry using its unique id
	 *
	 * @param id unique ID of the stopping point to be retrieved
	 * @return StoppingPoint object created from its corresponding entry the database, null if stopping point not found
	 * @pre true
	 * @post true
	 */
	public StoppingPoint getStoppingPointById(int id)
	{
		BusstopsStoppingpointsRecord spr = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetchOne();

		return (spr == null) ? null : new StoppingPoint(id, spr.getName());
	}

	// TODO: add comments

	/**
	 * Retrieves list of all stopping points together with the names of their respective bus stops.
	 *
	 * @return ArrayList of stopping point ids with compound name of bus stop name and stopping point name
	 * @pre true
	 * @post true
	 */
	public ArrayList<Tuple<Integer, String>> getBusStopNamesWithStoppingPointNames()
	{
		ArrayList<Tuple<Integer, String>> resultList = new ArrayList<>();

		Result<Record3<Integer, String, String>> result = create.
				select(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID,
						BUSSTOPS.NAME,
						BUSSTOPS_STOPPINGPOINTS.NAME)
				.from(BUSSTOPS)
				.join(BUSSTOPS_STOPPINGPOINTS)
				.using(BUSSTOPS.BUSSTOPS_ID)
				.orderBy(BUSSTOPS.NAME.asc(), BUSSTOPS_STOPPINGPOINTS.NAME.asc())
				.fetch();
		// Return empty list if no stopping points found
		if (result == null) return (resultList);
		for (Record r : result)
		{
			String busStopName = r.getValue(BUSSTOPS.NAME);
			String stoppingPointName = r.getValue(BUSSTOPS_STOPPINGPOINTS.NAME);
			String compoundName;

			compoundName = busStopName.equals(stoppingPointName) ? busStopName : busStopName + " " + stoppingPointName;

			resultList.add(new Tuple<>(r.getValue(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID),
					compoundName));
		}

		return resultList;
	}

	/**
	 * Removes a stopping point entry from the database using its unique id. Assigned tours will also be modified
	 *
	 * @param id unique ID of the bus entry that is to be deleted from the database
	 * @pre stopping point with supplied ID is not used as a route stop
	 * @post stopping point with supplied ID is removed from database
	 */
	public void deleteStoppingPointById(int id)
	{
		create.delete(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.execute();
	}

	/**
	 * Modifies an existing bus stop entry in the database
	 *
	 * @param spoint StoppingPoint object whose entry is to be updated in the database
	 * @pre true
	 * @post properties of specified stopping point are changed if existing
	 */
	public void modifyStoppingPoint(StoppingPoint spoint)
	{
		create.update(BUSSTOPS_STOPPINGPOINTS)
				.set(BUSSTOPS_STOPPINGPOINTS.NAME, spoint.getName())
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.equal(spoint.getId()))
				.execute();
	}


	///////////////////////////////
	// Methods for "soldTicket"s //
	///////////////////////////////

	/**
	 * Retrieves a list of SoldTicket objects representing all sold ticket entries stored in the database
	 *
	 * @return ArrayList containing SoldTicket objects for every sold ticket entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<SoldTicket> getSoldTickets()
	{
		Result<SoldticketsRecord> soldTicketRecords = create
				.selectFrom(SOLDTICKETS)
				.orderBy(SOLDTICKETS.TIMESTAMP.desc())
				.fetch();
		ArrayList<SoldTicket> soldTicketsList = new ArrayList<>();
		if (soldTicketRecords == null) return soldTicketsList; // return empty list if no sold tickets found
		for (SoldticketsRecord rec : soldTicketRecords)
		{
			SoldTicket sold = new SoldTicket(
					rec.getSoldticketsId(),
					rec.getName(),
					new Date((long) rec.getTimestamp() * 1000),
					rec.getPrice());
			soldTicketsList.add(sold);
		}
		return soldTicketsList;
	}

	///////////////////////////
	// Methods for "Ticket"s //
	///////////////////////////

	/**
	 * Creates a new database entry for a Ticket object
	 *
	 * @param tick Ticket object to be stored in the database
	 * @return unique ID of newly created name entry in the database
	 * @pre true
	 * @post ticket is successfully added to database
	 */
	public int addTicket(Ticket tick)
	{
		TicketsRecord newTick = create
				.insertInto(TICKETS,
						TICKETS.NAME,
						TICKETS.PRICE,
						TICKETS.NUMPASSENGERS,
						TICKETS.DESCRIPTION)
				.values(
						tick.getName(),
						tick.getPrice(),
						tick.getNumPassengers(),
						tick.getDescription())
				.returning(TICKETS.TICKETS_ID)
				.fetchOne();

		return (newTick.getTicketsId());
	}

	/**
	 * Removes a name entry from the database using its unique id
	 *
	 * @param id unique ID of the name entry that is to be deleted from the database
	 * @pre true
	 * @post ticket with specified ID is removed from database
	 */
	public void deleteTicket(int id)
	{
		create.delete(TICKETS)
				.where(TICKETS.TICKETS_ID.equal(id))
				.execute();
	}

	/**
	 * Modifies an existing name entry in the database
	 *
	 * @param tick Ticket object whose entry is to be updated in the database
	 * @pre true
	 * @post properties of specified ticket are changed if existing
	 */
	public void modifyTicket(Ticket tick)
	{
		create.update(TICKETS)
				.set(TICKETS.NAME, tick.getName())
				.set(TICKETS.PRICE, tick.getPrice())
				.set(TICKETS.NUMPASSENGERS, tick.getNumPassengers())
				.set(TICKETS.DESCRIPTION, tick.getDescription())
				.where(TICKETS.TICKETS_ID.eq(tick.getId()))
				.execute();
	}

	/**
	 * Retrieves a list of Ticket objects representing all name entries stored in the database
	 *
	 * @return ArrayList containing Ticket objects for every name entry stored in the database
	 * @pre true
	 * @post true
	 */
	public ArrayList<Ticket> getTickets()
	{
		Result<TicketsRecord> ticketRecords = create
				.selectFrom(TICKETS)
				.orderBy(TICKETS.NAME.asc())
				.fetch();

		ArrayList<Ticket> ticketList = new ArrayList<>();
		if (ticketRecords == null) return ticketList; // return empty list if no tickets found

		for (TicketsRecord rec : ticketRecords)
		{
			Ticket tick = new Ticket(
					rec.getPrice(),
					rec.getName(),
					rec.getNumpassengers(),
					rec.getDescription(),
					rec.getTicketsId());
			ticketList.add(tick);
		}
		return ticketList;
	}

	/**
	 * Retrieves a single Ticket object from the database entry using its unique id
	 *
	 * @param id unique ID of the name to be retrieved
	 * @return Ticket object created from its corresponding entry the database, null if ticket not found
	 * @pre true
	 * @post true
	 */
	public Ticket getTicketById(int id)
	{
		TicketsRecord rec = create
				.selectFrom(TICKETS)
				.where(TICKETS.TICKETS_ID.eq(id))
				.fetchOne();
		if (rec == null) return null;

		Ticket tick = new Ticket(
				rec.getPrice(),
				rec.getName(),
				rec.getNumpassengers(),
				rec.getDescription(),
				rec.getTicketsId());
		tick.setId(id);
		return (tick);
	}

	//////////////////////////
	//  Methods for "Tour"s //
	//////////////////////////

	private int daysBetween(Date d1, Date d2)
	{
		return (int) ((d2.getTime() - d1.getTime()) / DAY_IN_MILLIS);
	}

	/**
	 * Creates all trips for a given day.
	 *
	 * @param date date for which tours ought to be generated
	 * @pre true
	 * @post tours of the specified day are successfully added to database
	 */
	public void createTours(Date date)
	{
		createTours(date, date);
	}

	/**
	 * Creates all tours for a given range of days.
	 *
	 * @param dateFrom  date from which tours ought to be generated
	 * @param dateUntil date until which tours ought to be generated
	 * @pre dateForm is prior to dateUntil
	 * @post database contains all tours for the specified range of days
	 */
	public void createTours(Date dateFrom, Date dateUntil)
	{
		int daysBetween = daysBetween(dateFrom, dateUntil);

		// Notify database of loads of incoming queries
		create.execute("BEGIN");

		// Variable to modify during the loop, so as not to change the original fromDate
		Date loopDate = new Date();
		GregorianCalendar calendar = new GregorianCalendar();

		// Repeat the same thing for every day within the given range
		for (int i = 0; i <= daysBetween; i++)
		{
			// Modify given date to represent midnight
			// Create for each start of day
			loopDate.setTime(dateFrom.getTime() + DAY_IN_MILLIS * i);
			calendar.setTime(loopDate);
			// Reset hour, minutes, seconds and milliseconds
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			// Get selected day of week in uppercase english format, e.g. MONDAY, TUESDAY, ...
			String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).toUpperCase();
			// Get timestamp for the start of that particular day
			int timestamp = (int) (calendar.getTimeInMillis() / 1000);

			// Fetch all route IDs from database
			Result<Record1<Integer>> routes = create.select(ROUTES.ROUTES_ID).from(ROUTES).fetch();

			// for every route...
			if (routes != null)
			{
				for (Record1<Integer> route : routes)
				{
					// ... get all starting times belonging to selected day of week ...
					Result<Record1<Integer>> startingTimes = create
							.select(ROUTES_STARTTIMES.STARTTIME).from(ROUTES_STARTTIMES)
							.where(ROUTES_STARTTIMES.DAYOFWEEK.eq(dayOfWeek))
							.and(ROUTES_STARTTIMES.ROUTES_ID.eq(route.getValue(ROUTES.ROUTES_ID)))
							.fetch();

					// ... and add each one to the current date to get the timestamp.
					if (startingTimes != null)
					{
						for (Record1<Integer> startingTime : startingTimes)
						{
							create.insertInto(TOURS,
									TOURS.TIMESTAMP,
									TOURS.ROUTES_ID)
									.values(
											timestamp + startingTime.getValue(ROUTES_STARTTIMES.STARTTIME) * 60,
											route.getValue(ROUTES.ROUTES_ID))
									.execute();
						}
					}
				}
			}
		}

		// Finally execute all the previously queued queries
		create.execute("END");
	}

	/**
	 * Retrieves a list of buses which are available for the given tour.
	 *
	 * @param tour tour for which available buses ought to be shown
	 * @return list of buses available for given tour
	 */
	public ArrayList<Bus> getAvailableBusesForTour(Tour tour)
	{
		ArrayList<Bus> result = new ArrayList<>();
		ArrayList<Bus> buses = getBuses();

		for (Bus bus : buses)
		{
			ArrayList<Tour> tours = getToursForBusId(bus.getId());

			boolean qualifies = true;

			for (Tour tourExisting : tours)
			{
				// Stop as soon as we find even just one conflict
				if ((tour.getEndTimestampAsInt() >= tourExisting.getStartTimestampAsInt()) &&
						(tour.getStartTimestampAsInt() <= tourExisting.getEndTimestampAsInt()))
				{
					qualifies = false;
					break;
				}
			}

			if (qualifies) result.add(bus);
		}

		return result;
	}

	/**
	 * Retrieves a list of bus drivers who are available to drive the given tour.
	 *
	 * @param tour tour for which available bus drivers ought to be shown
	 * @return list of bus drivers available to drive given tour
	 */
	public ArrayList<Employee> getAvailableBusDriversForTour(Tour tour)
	{
		ArrayList<Employee> result = new ArrayList<>();
		ArrayList<Employee> busdrivers = getEmployeesByRole(Role.BUSDRIVER);

		for (Employee busdriver : busdrivers)
		{
			ArrayList<Tour> tours = getToursForEmployeeId(busdriver.getId());

			boolean qualifies = true;

			// TODO: (Enhancement) Check for:
			//              No more than 4 hours of "consecutive" work (what defines as such - only just back to back?)
			//              No more than 12 hours of work within the last 24 hours
			for (Tour tourExisting : tours)
			{
				// Stop as soon as we find even just one conflict
				if ((tour.getEndTimestampAsInt() >= tourExisting.getStartTimestampAsInt()) &&
						(tour.getStartTimestampAsInt() <= tourExisting.getEndTimestampAsInt()))
				{
					qualifies = false;
					break;
				}
			}

			if (qualifies) result.add(busdriver);
		}

		return result;
	}

	public ArrayList<Object[]> getToursForDate(Date date)
	{

		GregorianCalendar calendar = new GregorianCalendar();

		Date from = new Date(date.getTime());

		calendar.setTime(from);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return getToursWithinDateRange((int) (calendar.getTimeInMillis() / 1000),
				(int) ((calendar.getTimeInMillis() + DAY_IN_MILLIS) / 1000));
	}

	private ArrayList<Object[]> getToursWithinDateRange(int dateFrom, int dateUntil)
	{
		ArrayList<Object[]> result = new ArrayList<>();

		Result<Record> records = create
				.select()
				.from(TOURS)
				.leftOuterJoin(EMPLOYEES)
				.using(EMPLOYEES.EMPLOYEES_ID)
				.leftOuterJoin(BUSES)
				.using(BUSES.BUSES_ID)
				.leftOuterJoin(ROUTES)
				.using(ROUTES.ROUTES_ID)
				.where(TOURS.TIMESTAMP
						.between(dateFrom, dateUntil))
				.fetch();

		if (records != null)
		{
			for (Record r : records)
			{
				Object[] content = new Object[5];

				// Tour ID
				content[0] = r.getValue(TOURS.TOURS_ID);
				// Route's name
				content[1] = r.getValue(ROUTES.NAME);
				// Start time
				content[2] = new Date((long) r.getValue(TOURS.TIMESTAMP) * 1000);
				// Duration of route
				Route route = getRouteById(r.getValue(TOURS.ROUTES_ID));
				content[3] = route.getDuration();
				// License plate
				content[4] = r.getValue(BUSES.LICENCEPLATE);
				// Bus driver
				content[5] = r.getValue(EMPLOYEES.NAME) == null ? null : r.getValue(EMPLOYEES.NAME) + ", " + r.getValue(EMPLOYEES.FIRSTNAME);

				result.add(content);
			}
		}

		return result;
	}

	/**
	 * Retrieves all tours to which the given user is assigned from the database
	 *
	 * @param id unique ID of the user whose tours are to be retrieved
	 * @return ArrayList of all tours associated with the given user
	 * @pre true
	 * @post true
	 */
	public ArrayList<Tour> getToursForEmployeeId(int id)
	{
		ArrayList<Tour> result = new ArrayList<>();
		Result<ToursRecord> tours = create
				.selectFrom(TOURS)
				.where(TOURS.EMPLOYEES_ID.eq(id))
				.orderBy(TOURS.TIMESTAMP)
				.fetch();

		// Return empty list if no tours found
		if (tours == null) return result;

		for (ToursRecord t : tours)
		{
			Tour newTour = new Tour(
					new Date((long) t.getTimestamp() * 1000),
					(t.getRoutesId() == null) ? null : getRouteById(t.getRoutesId()),
					(t.getBusesId() == null) ? null : getBusById(t.getBusesId()),
					(t.getEmployeesId() == null) ? null : getEmployeeById(t.getEmployeesId()));
			newTour.setId(t.getToursId());
			result.add(newTour);
		}

		return result;
	}

	/**
	 * Retrieves all tours to which the given bus is assigned from the database
	 *
	 * @param id unique ID of the bus whose tours are to be retrieved
	 * @return ArrayList of all tours associated with the given bus
	 * @pre true
	 * @post true
	 */
	public ArrayList<Tour> getToursForBusId(int id)
	{
		ArrayList<Tour> result = new ArrayList<>();
		Result<ToursRecord> tours = create
				.selectFrom(TOURS)
				.where(TOURS.BUSES_ID.eq(id))
				.orderBy(TOURS.TIMESTAMP)
				.fetch();

		// Return empty list if no tours found
		if (tours == null) return result;

		for (ToursRecord t : tours)
		{
			Tour newTour = new Tour(
					new Date((long) t.getTimestamp() * 1000),
					getRouteById(t.getRoutesId()),

					(t.getBusesId() == null) ? null : getBusById(t.getBusesId()),
					(t.getEmployeesId() == null) ? null : getEmployeeById(t.getEmployeesId()));
			newTour.setId(t.getToursId());
			result.add(newTour);
		}

		return result;
	}

	/**
	 * Deletes all tours of a specific route.
	 *
	 * @param id unique ID of the route whose tours to be deleted.
	 * @return number of tours deleted
	 * @pre true
	 * @post all tours assigned to specified route are removed from database
	 */
	public int deleteToursUsingRoutesId(int id)
	{
		int numOfTours = getNumberOfToursUsingRouteId(id);
		create.delete(TOURS)
				.where(TOURS.ROUTES_ID.eq(id))
				.execute();
		return numOfTours;
	}

	/**
	 * Deletes all tours before today.
	 *
	 * @return number of deleted tours
	 * @pre true
	 * @post all tours with start times before today are removed from database
	 */
	public int purgeTours()
	{
		return (purgeTours(new GregorianCalendar().getTime()));
	}

	/**
	 * Deletes all tours before a specific date.
	 *
	 * @return number of deleted tours
	 * @pre true
	 * @post all tours with start times before the specified date are removed from database
	 */
	public int purgeTours(Date date)
	{
		// set specific date to midnight
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int deadline = (int) (calendar.getTimeInMillis() / 1000);

		// get number of all tours before selected date
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.TIMESTAMP.lessThan(deadline))
				.fetchOne();

		// ... and delete them
		create.delete(TOURS)
				.where(TOURS.TIMESTAMP.lessThan(deadline))
				.execute();
		return (Integer) record.getValue(0);
	}

	/**
	 * gets the number of tours with a specific bus assigned.
	 *
	 * @param id unique ID of the bus
	 * @return number of tours using the bus
	 * @pre true
	 * @post true
	 */
	public int getNumberOfToursUsingBusId(int id)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.BUSES_ID.equal(id))
				.fetchOne();

		return (Integer) record.getValue(0);
	}

	/**
	 * gets the number of tours with a specific route assigned.
	 *
	 * @param id unique ID of the route
	 * @return number of tours using the route
	 * @pre true
	 * @post true
	 */
	public int getNumberOfToursUsingRouteId(int id)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.ROUTES_ID.equal(id))
				.fetchOne();

		return (Integer) record.getValue(0);
	}


	/**
	 * gets the number of tours with a specific employee assigned.
	 *
	 * @param id unique ID of the employee
	 * @return number of tours using the employee
	 * @pre true
	 * @post true
	 */
	public int getNumberOfToursUsingEmployeeId(int id)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.EMPLOYEES_ID.equal(id))
				.fetchOne();

		return (Integer) record.getValue(0);
	}

	/**
	 * Retrieves a single Tour object from the database entry using its unique id
	 *
	 * @param id unique ID of the tour to be retrieved
	 * @return Tour object created from its corresponding entry the database, null if tour not found.
	 * @pre true
	 * @post true
	 */
	public Tour getTourById(int id)
	{
		ToursRecord rec = create
				.selectFrom(TOURS)
				.where(TOURS.TOURS_ID.eq(id))
				.fetchOne();

		if (rec == null) return null;

		Tour result = new Tour(
				new Date((long) rec.getTimestamp() * 1000),
				(rec.getRoutesId() == null) ? null : getRouteById(rec.getRoutesId()),
				(rec.getBusesId() == null) ? null : getBusById(rec.getBusesId()),
				(rec.getEmployeesId() == null) ? null : getEmployeeById(rec.getEmployeesId()));
		result.setId(id);
		return result;
	}

	/**
	 * Change assigned bus and driver for a specific tour
	 *
	 * @param tourID unique ID of the tour to be modified
	 * @param busID  unique ID of the bus to be assigned to tour
	 * @param empID  unique ID of the employee to be assigned to tour
	 * @pre bus and employee with supplied ID exist in database
	 * @post bus and employee with supplied ID are assigned to tour
	 */
	public void modifyTour(int tourID, int busID, int empID)
	{
		create.update(TOURS)
				.set(TOURS.BUSES_ID, busID)
				.set(TOURS.EMPLOYEES_ID, empID)
				.where(TOURS.TOURS_ID.eq(tourID))
				.execute();
	}

	/**
	 * gets the number of tours which are unplanned for a specific day.
	 *
	 * @param date contains the day whose unplanned tours to be counted
	 * @return -1 if no tours for supplied date in database, else number of unplanned tours for supplied date
	 * @pre true
	 * @post true
	 */
	public int getNumberOfUnplannedToursByDate(Date date)
	{
		// set specific date to midnight
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		int startOfDay = (int) (calendar.getTimeInMillis() / 1000);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		int endOfDay = (int) (calendar.getTimeInMillis() / 1000);

		Condition inRange = TOURS.TIMESTAMP.between(startOfDay, endOfDay);

		Result<ToursRecord> rows = create
				.selectFrom(TOURS)
				.where(inRange)
				.fetch();

		if (rows == null || rows.size() == 0) return -1;

		Condition anyNull = TOURS.BUSES_ID.isNull().or(TOURS.EMPLOYEES_ID.isNull());

		Record count = create
				.selectCount()
				.from(TOURS)
				.where(inRange)
				.and(anyNull)
				.fetchOne();

		return (Integer) count.getValue(0);
	}
}