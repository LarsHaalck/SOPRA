package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import org.jooq.*;
import org.jooq.impl.*;

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

    private static final String DB_NAME = "Octobus.db";

    private static ControllerDatabase controllerDatabase = null;

    private DSLContext create;

    private ControllerDatabase()
    {
        openDB();
    }

    static public ControllerDatabase getInstance()
    {
        if (controllerDatabase == null){
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
            String url = "jdbc:sqlite:" + DB_NAME;
            Connection conn;

            // Dynamically load SQLite driver
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			create = DSL.using(conn, SQLDialect.SQLITE);
		}

		catch (ClassNotFoundException e)
		{
			System.out.println("Unable to load JDBC driver.");
			e.printStackTrace();
		}

		catch (SQLException e)
		{
			System.out.println("SQL-Exception occurred.");
			e.printStackTrace();
		}
	}

	/////////////////////////
	// Methods for "Bus"es //
	/////////////////////////

    /**
     * Creates a new database entry for a Bus object
     *
     * @param bus Bus object to be stored in the database
     * @return unique ID of newly created bus entry in the database
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
                        (int) (bus.getNextInspectionDue().getTime()/1000),
                        (Boolean) bus.isArticulatedBus())
				.returning(BUSES.BUSES_ID)
                .fetchOne();

		return(newBus.getBusesId());
	}

    /**
     * Removes a bus entry from the database using its unique id
     *
     * @param id unique ID of the bus entry that is to be deleted from the database
     */
	public void deleteBus(int id)
	{
		create.delete(BUSES).where(BUSES.BUSES_ID.equal(id)).execute();
	}

    /**
     * Modifies an existing bus entry in the database
     *
     * @param bus Bus object whose entry is to be updated in the database
     */
	public void modifyBus(Bus bus)
	{
		create.update(BUSES)
				.set(BUSES.LICENCEPLATE,bus.getLicencePlate())
				.set(BUSES.NUMBEROFSEATS,bus.getNumberOfSeats())
				.set(BUSES.STANDINGROOM, bus.getStandingRoom())
				.set(BUSES.MANUFACTURER,bus.getManufacturer())
				.set(BUSES.MODEL,bus.getModel())
				.set(BUSES.NEXTINSPECTIONDUE,(int) (bus.getNextInspectionDue().getTime()/1000))
				.set(BUSES.ARTICULATEDBUS, bus.isArticulatedBus())
				.where(BUSES.BUSES_ID.equal(bus.getId()))
				.execute();
	}

    /**
     * Retrieves a list of Bus objects representing all bus entries stored in the database
     *
     * @return ArrayList containing Bus objects for every bus entry stored in the database
     */
	public ArrayList<Bus> getBuses()
	{
		Result<Record> busRecords = create.select().from(BUSES).fetch();

		ArrayList<Bus> busList = new ArrayList<>();

		for (Record rec : busRecords)
		{
			Bus bus = new Bus(
					rec.getValue(BUSES.LICENCEPLATE),
					rec.getValue(BUSES.NUMBEROFSEATS),
					rec.getValue(BUSES.STANDINGROOM),
					rec.getValue(BUSES.MANUFACTURER),
					rec.getValue(BUSES.MODEL),
					new Date((long) rec.getValue(BUSES.NEXTINSPECTIONDUE)*1000),
					rec.getValue(BUSES.ARTICULATEDBUS));
            bus.setId(rec.getValue(BUSES.BUSES_ID));
			busList.add(bus);
		}
		return busList;
	}

    /**
     * Retrieves a single Bus object from the database entry using its unique id
     *
     * @param id unique ID of the bus to be retrieved
     * @return Bus object created from its corresponding entry the database
     */
    public Bus getBus(int id)
    {

        Record busRecord = create.select().from(BUSES).where(BUSES.BUSES_ID.eq(id)).fetchOne();

        Bus bus = new Bus(
                busRecord.getValue(BUSES.LICENCEPLATE),
                busRecord.getValue(BUSES.NUMBEROFSEATS),
                busRecord.getValue(BUSES.STANDINGROOM),
                busRecord.getValue(BUSES.MANUFACTURER),
                busRecord.getValue(BUSES.MODEL),
				new Date((long) busRecord.getValue(BUSES.NEXTINSPECTIONDUE)*1000),
                busRecord.getValue(BUSES.ARTICULATEDBUS));
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
     */
    public int addBusStop(BusStop bstop)
	{
		BusstopsRecord newStop = create.insertInto(
                BUSSTOPS,BUSSTOPS.NAME,
                BUSSTOPS.LOCATIONX,
                BUSSTOPS.LOCATIONY,
				BUSSTOPS.BARRIERFREE)
				.values(bstop.getName(),
                        bstop.getLocation().getFirst(),
                        bstop.getLocation().getSecond(),
						bstop.isBarrierFree())
				.returning(BUSSTOPS.BUSSTOPS_ID)
				.fetchOne();

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

		return(newStop.getBusstopsId());
	}

    /**
     * Removes a bus stop entry from the database using its unique id
     *
     * @param id unique ID of the bus stop entry that is to be deleted from the database
     */
	public void deleteBusStop(int id)
	{
		create.delete(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.equal(id)).execute();
	}

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // TODO: StoppingPoints werden noch nicht aktualisiert! Dafür entweder eine eigene Entitätsklasse schaffen oder HashSet in BusStop modifizieren! //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Modifies an existing bus stop entry in the database
     *
     * @param bstop BusStop object whose entry is to be updated in the database
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
     */
	public ArrayList<BusStop> getBusStops()
	{
		// Start by getting all bus stops from the database
        Result<Record> busStopRecords = create.select().from(BUSSTOPS).fetch();
		ArrayList<BusStop> busStopList = new ArrayList<>();

		// For each bus retrieved...
        for (Record rec : busStopRecords)
		{
            // ... get all corresponding stopping points...
            Result<Record> stoppingPoints = create.select().from(BUSSTOPS_STOPPINGPOINTS)
                    .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

            // ... and put them all into a HashSet
            HashSet<StoppingPoint> spoints = new HashSet<>();
            for (Record sp : stoppingPoints)
			{
				spoints.add(new StoppingPoint(sp.getValue(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID),sp.getValue(BUSSTOPS_STOPPINGPOINTS.NAME)));
			}

			//TODO Remove this.
			boolean barrier = false;
			if(rec.getValue(BUSSTOPS.BARRIERFREE) != null)
				barrier = rec.getValue(BUSSTOPS.BARRIERFREE);
			BusStop busStop = new BusStop(
					rec.getValue(BUSSTOPS.NAME),
					new Tuple<Integer,Integer>(rec.getValue(BUSSTOPS.LOCATIONX),rec.getValue(BUSSTOPS.LOCATIONY)),
					spoints,
					barrier);

            // Finally, create BusStop object and add it to the ArrayList
            busStopList.add(busStop);
		}
		return busStopList;
	}

    /**
     * Retrieves a single BusStop object from the database entry using its unique id
     *
     * @param id unique ID of the bus stop to be retrieved
     * @return BusStop object created from its corresponding entry the database
     */
    public BusStop getBusStop(int id)
    {
        // Get desired bus from DB
        Record rec = create.select().from(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.eq(id)).fetchOne();

        // Get all associated stopping points...
        Result<Record> stoppingPoints = create.select().from(BUSSTOPS_STOPPINGPOINTS)
                .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

        // .. and add them into a HashSet
        HashSet<StoppingPoint> spoints = new HashSet<>();
		for (Record sp : stoppingPoints)
		{
			spoints.add(new StoppingPoint(sp.getValue(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID),sp.getValue(BUSSTOPS_STOPPINGPOINTS.NAME)));
		}

		BusStop busStop = new BusStop(
				rec.getValue(BUSSTOPS.NAME),
				new Tuple<Integer,Integer>(rec.getValue(BUSSTOPS.LOCATIONX),rec.getValue(BUSSTOPS.LOCATIONY)),
				spoints,
				rec.getValue(BUSSTOPS.BARRIERFREE));

		busStop.setId(id);
        return busStop;

    }

	/////////////////////////////
	// Methods for "Employee"s //
	/////////////////////////////

    /**
     * Creates a new database entry for an Employee object
     *
     * @param emp Employee object to be stored in the database
     * @return unique ID of newly created employee entry in the database
     */
	public int addEmployee(Employee emp)
	{
		EmployeesRecord newEmp = create.insertInto(EMPLOYEES,
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
				.values(emp.getName(),
                        emp.getFirstName(),
                        emp.getAddress(),
                        emp.getZipCode(),
                        emp.getCity(),
						(int) emp.getDateOfBirth().getTime(),
                        emp.getPhone(),
                        emp.getEmail(),
                        emp.getUsername(),
						emp.getSalt(),
                        emp.getPassword(),
                        emp.getNote(),
                        emp.isRole(Role.BUSDRIVER),
						emp.isRole(Role.NETWORK_PLANNER),
                        emp.isRole(Role.TICKET_PLANNER),
                        emp.isRole(Role.HR_MANAGER),
						emp.isRole(Role.SCHEDULE_MANAGER))
				.returning(EMPLOYEES.EMPLOYEES_ID)
				.fetchOne();

		return(newEmp.getEmployeesId());
	}

    /**
     * Removes an employee entry from the database using its unique id
     *
     * @param id unique ID of the employee entry that is to be deleted from the database
     */
	public void deleteEmployee(int id)
	{
		create.delete(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.equal(id)).execute();
	}

    /**
     * Modifies an existing employee entry in the database
     *
     * @param emp Employee object whose entry is to be updated in the database
     */
	public void modifyEmployee(Employee emp)
	{
		create.update(EMPLOYEES)
				.set(EMPLOYEES.NAME, emp.getName())
				.set(EMPLOYEES.FIRSTNAME,emp.getFirstName())
				.set(EMPLOYEES.ADDRESS,emp.getAddress())
				.set(EMPLOYEES.ZIPCODE,emp.getZipCode())
				.set(EMPLOYEES.CITY,emp.getCity())
				.set(EMPLOYEES.DATEOFBIRTH,(int) (emp.getDateOfBirth().getTime()/1000))
				.set(EMPLOYEES.PHONE,emp.getPhone())
				.set(EMPLOYEES.EMAIL,emp.getEmail())
				.set(EMPLOYEES.USERNAME,emp.getUsername())
				.set(EMPLOYEES.SALT,emp.getSalt())
				.set(EMPLOYEES.PASSWORD,emp.getPassword())
				.set(EMPLOYEES.NOTE,emp.getNote())
				.set(EMPLOYEES.ISBUSDRIVER, emp.isRole(Role.BUSDRIVER))
				.set(EMPLOYEES.ISNETWORK_PLANNER,emp.isRole(Role.NETWORK_PLANNER))
				.set(EMPLOYEES.ISTICKET_PLANNER,emp.isRole(Role.TICKET_PLANNER))
				.set(EMPLOYEES.ISHR_MANAGER,emp.isRole(Role.HR_MANAGER))
				.set(EMPLOYEES.ISSCHEDULE_MANAGER, emp.isRole(Role.SCHEDULE_MANAGER))
				.where(EMPLOYEES.EMPLOYEES_ID.equal(emp.getId()))
				.execute();
	}

    /**
     * Retrieves a list of Employee objects representing all employee entries stored in the database
     *
     * @return ArrayList containing Employee objects for every employee entry stored in the database
     */
	public ArrayList<Employee> getEmployees()
	{
		Result<Record> empRecords = create.select().from(EMPLOYEES).fetch();

		ArrayList<Employee> empList = new ArrayList<>();

		for (Record rec : empRecords)
		{
			HashSet<Role> roles = new HashSet<>();
			if (rec.getValue(EMPLOYEES.ISSCHEDULE_MANAGER))
				roles.add(Role.SCHEDULE_MANAGER);
			if (rec.getValue(EMPLOYEES.ISHR_MANAGER))
				roles.add(Role.HR_MANAGER);
			if (rec.getValue(EMPLOYEES.ISTICKET_PLANNER))
				roles.add(Role.TICKET_PLANNER);
			if (rec.getValue(EMPLOYEES.ISNETWORK_PLANNER))
				roles.add(Role.NETWORK_PLANNER);
			if (rec.getValue(EMPLOYEES.ISBUSDRIVER))
				roles.add(Role.BUSDRIVER);

			Employee emp = new Employee(
					rec.getValue(EMPLOYEES.NAME),
					rec.getValue(EMPLOYEES.FIRSTNAME),
					rec.getValue(EMPLOYEES.ADDRESS),
					rec.getValue(EMPLOYEES.ZIPCODE),
					rec.getValue(EMPLOYEES.CITY),
					new Date((long) rec.getValue(EMPLOYEES.DATEOFBIRTH)*1000),
					rec.getValue(EMPLOYEES.PHONE),
					rec.getValue(EMPLOYEES.EMAIL),
					rec.getValue(EMPLOYEES.USERNAME),
					rec.getValue(EMPLOYEES.PASSWORD),
					rec.getValue(EMPLOYEES.SALT),
					rec.getValue(EMPLOYEES.NOTE),
					roles);
			emp.setId(rec.getValue(EMPLOYEES.EMPLOYEES_ID));
			empList.add(emp);
		}
		return empList;
	}

    /**
     * Retrieves a single Employee object from the database entry using its unique id
     *
     * @param id unique ID of the employee to be retrieved
     * @return Employee object created from its corresponding entry the database
     */
	public Employee getEmployee(int id)
	{
		Record rec = create.select().from(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.eq(id)).fetchOne();

		HashSet<Role> roles = new HashSet<>();
		if (rec.getValue(EMPLOYEES.ISSCHEDULE_MANAGER))
			roles.add(Role.SCHEDULE_MANAGER);
		if (rec.getValue(EMPLOYEES.ISHR_MANAGER))
			roles.add(Role.HR_MANAGER);
		if (rec.getValue(EMPLOYEES.ISTICKET_PLANNER))
			roles.add(Role.TICKET_PLANNER);
		if (rec.getValue(EMPLOYEES.ISNETWORK_PLANNER))
			roles.add(Role.NETWORK_PLANNER);
		if (rec.getValue(EMPLOYEES.ISBUSDRIVER))
			roles.add(Role.BUSDRIVER);

		Employee emp = new Employee(
				rec.getValue(EMPLOYEES.NAME),
				rec.getValue(EMPLOYEES.FIRSTNAME),
				rec.getValue(EMPLOYEES.ADDRESS),
				rec.getValue(EMPLOYEES.ZIPCODE),
				rec.getValue(EMPLOYEES.CITY),
				new Date((long) rec.getValue(EMPLOYEES.DATEOFBIRTH)*1000),
				rec.getValue(EMPLOYEES.PHONE),
				rec.getValue(EMPLOYEES.EMAIL),
				rec.getValue(EMPLOYEES.USERNAME),
				rec.getValue(EMPLOYEES.PASSWORD),
				rec.getValue(EMPLOYEES.SALT),
				rec.getValue(EMPLOYEES.NOTE),
				roles);

		emp.setId(id);
		return emp;
	}

    public Employee getEmployee(String username){
        Record rec = create.select().from(EMPLOYEES).where(EMPLOYEES.USERNAME.eq(username)).fetchOne();
        return getEmployee(rec.getValue(EMPLOYEES.EMPLOYEES_ID));
    }


	//////////////////////////
	// Methods for "Route"s //
	//////////////////////////

    /**
     * Creates a new database entry for a Route object
     *
     * @param r Route object to be stored in the database
     * @return unique ID of newly created route entry in the database
     */
	public int addRoute(Route r)
	{
		RoutesRecord newRoute = create.insertInto(ROUTES,
                ROUTES.NAME,
                ROUTES.NOTE,
                ROUTES.NIGHT)
				.values(r.getName(),
                        r.getNote(),
                        (Boolean) r.isNight())
				.returning(ROUTES.ROUTES_ID)
				.fetchOne();

		LinkedList<Triple<BusStop,StoppingPoint,Integer>>  stops = r.getStops();
		for (Triple<BusStop,StoppingPoint,Integer> triple : stops)
		{
			create.insertInto(ROUTES_STOPS,
                    ROUTES_STOPS.ROUTES_ID,
                    ROUTES_STOPS.BUSSTOPS_ID,
					ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID,
					ROUTES_STOPS.TIMETOPREVIOUS)
					.values(newRoute.getRoutesId(),
							triple.getFirst().getId(),
							triple.getSecond().getId(),
                            triple.getThird())
					.execute();
		}

		HashMap<DayOfWeek,LinkedList<Integer>> times = r.getStartTimes();
		for (DayOfWeek day : times.keySet())
		{
			for (Integer time : times.get(day))
			{
				create.insertInto(ROUTES_STARTTIMES,
                        ROUTES_STARTTIMES.ROUTES_ID,
                        ROUTES_STARTTIMES.DAYOFWEEK,
                        ROUTES_STARTTIMES.STARTTIME)
					.values(newRoute.getRoutesId(),
                            day.toString(),
                            time)
					.execute();
			}
		}

		return(newRoute.getRoutesId());
	}

    /**
     * Removes a route entry from the database using its unique id
     *
     * @param id unique ID of the route entry that is to be deleted from the database
     */
	public void deleteRoute(int id)
	{
		create.delete(ROUTES).where(ROUTES.ROUTES_ID.equal(id));
	}

    // TODO: Implementierten!
    /**
     * Modifies an existing route entry in the database
     *
     * @param r Route object whose entry is to be updated in the database
     */
	public void modifyRoute(Route r)
	{
	}

    /**
     * Retrieves a list of Route objects representing all route entries stored in the database
     *
     * @return ArrayList containing Route objects for every route entry stored in the database
     */
	public ArrayList<Route> getRoutes()
	{
        // Start by getting all bus stops from the database
        Result<Record> routesRecords = create.select().from(ROUTES).fetch();
		ArrayList<Route> routesList = new ArrayList<>();

        // For each bus stop retrieved...
        for (Record rec : routesRecords)
		{
            // ... get all corresponding start times ...
            Result<Record> startTimesRecords = create.select().from(ROUTES_STARTTIMES)
                    .where(ROUTES_STARTTIMES.ROUTES_ID.equal(rec.getValue(ROUTES.ROUTES_ID))).fetch();

			HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();
			startTimes.put(DayOfWeek.MONDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.TUESDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.THURSDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.FRIDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.SATURDAY, new LinkedList<>());
			startTimes.put(DayOfWeek.SUNDAY, new LinkedList<>());

            // ... and put them into their respective lists, depending on the day
			for (Record timerec : startTimesRecords)
			{
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("MONDAY"))
					startTimes.get(DayOfWeek.MONDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("TUESDAY"))
					startTimes.get(DayOfWeek.TUESDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("WEDNESDAY"))
					startTimes.get(DayOfWeek.WEDNESDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("THURSDAY"))
					startTimes.get(DayOfWeek.THURSDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("FRIDAY"))
					startTimes.get(DayOfWeek.FRIDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("SATURDAY"))
					startTimes.get(DayOfWeek.SATURDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
				if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("SUNDAY"))
					startTimes.get(DayOfWeek.SUNDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			}


            // Now get all stops on the route ...
            Result<Record> stopsRecords = create.select().from(ROUTES_STOPS)
                    .where(ROUTES_STOPS.ROUTES_ID.equal(rec.getValue(ROUTES.ROUTES_ID))).fetch();
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

            // ... and add them to the list of stops
			for (Record s : stopsRecords)
			{
				int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
				BusStop bstop = getBusStop(stopId);
				StoppingPoint spoint = new StoppingPoint(s.getValue(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID),s.getValue(BUSSTOPS_STOPPINGPOINTS.NAME));
				stops.add(new Triple<>(bstop,spoint,s.getValue(ROUTES_STOPS.TIMETOPREVIOUS)));
			}

			Route route = new Route(
					rec.getValue(ROUTES.NAME),
					rec.getValue(ROUTES.NOTE),
					stops,
					rec.getValue(ROUTES.NIGHT),
					startTimes);

            // Finally, create Route object and add it to the ArrayList
            routesList.add(route);
		}
		return routesList;
	}

    /**
     * Retrieves a single Route object from the database entry using its unique id
     *
     * @param id unique ID of the route to be retrieved
     * @return Route object created from its corresponding entry the database
     */
	public Route getRoute(int id)
	{
	    // Start by getting the desired route from the database
		Record rec = create.select().from(ROUTES).where(ROUTES.ROUTES.ROUTES_ID.eq(id)).fetchOne();

        // Fetch its starting times
        Result<Record> startTimesRecords = create.select().from(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.equal(id)).fetch();

		HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();
		startTimes.put(DayOfWeek.MONDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.TUESDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.THURSDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.FRIDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.SATURDAY, new LinkedList<>());
		startTimes.put(DayOfWeek.SUNDAY, new LinkedList<>());

        // Put the starting times for the route in respective lists
		for (Record timerec : startTimesRecords)
		{
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("MONDAY"))
				startTimes.get(DayOfWeek.MONDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("TUESDAY"))
				startTimes.get(DayOfWeek.TUESDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("WEDNESDAY"))
				startTimes.get(DayOfWeek.WEDNESDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("THURSDAY"))
				startTimes.get(DayOfWeek.THURSDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("FRIDAY"))
				startTimes.get(DayOfWeek.FRIDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("SATURDAY"))
				startTimes.get(DayOfWeek.SATURDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
			if (timerec.getValue(ROUTES_STARTTIMES.DAYOFWEEK).equals("SUNDAY"))
				startTimes.get(DayOfWeek.SUNDAY).add(timerec.getValue(ROUTES_STARTTIMES.STARTTIME));
		}


        // Now get all stops on the route ...
        Result<Record> stopsRecords = create.select().from(ROUTES_STOPS)
                .where(ROUTES_STOPS.ROUTES_ID.equal(id)).fetch();
		LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

        // ... and add them to the list of stops
		for (Record s : stopsRecords)
		{
			int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
			BusStop bstop = getBusStop(stopId);
			StoppingPoint spoint = new StoppingPoint(s.getValue(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID),s.getValue(BUSSTOPS_STOPPINGPOINTS.NAME));
			stops.add(new Triple<>(bstop,spoint,s.getValue(ROUTES_STOPS.TIMETOPREVIOUS)));
		}

		Route route = new Route(
				rec.getValue(ROUTES.NAME),
				rec.getValue(ROUTES.NOTE),
				stops,
				rec.getValue(ROUTES.NIGHT),
				startTimes);

		return route;
	}


	///////////////////////////////
	// Methods for "soldTicket"s //
	///////////////////////////////

    /**
     * Retrieves a list of SoldTicket objects representing all sold ticket entries stored in the database
     *
     * @return ArrayList containing SoldTicket objects for every sold ticket entry stored in the database
     */
	public ArrayList<SoldTicket> getSoldTickets()
	{
		Result<Record> soldTicketRecords = create.select().from(SOLDTICKETS).fetch();

		ArrayList<SoldTicket> soldTicketsList = new ArrayList<>();

		for (Record rec : soldTicketRecords)
		{
			SoldTicket sold = new SoldTicket(
					rec.getValue(SOLDTICKETS.SOLDTICKETS_ID),
					rec.getValue(SOLDTICKETS.NAME),
					new Date((long) rec.getValue(SOLDTICKETS.TIMESTAMP)*1000),
					rec.getValue(SOLDTICKETS.PRICE));
			soldTicketsList.add(sold);
		}
		return soldTicketsList;
	}

    /**
     * Retrieves a single SoldTicket object from the database entry using its unique id
     *
     * @param id unique ID of the sold ticket to be retrieved
     * @return SoldTicket object created from its corresponding entry the database
     */
    public SoldTicket getSoldTicket(int id)
	{
		Record rec = create.select().from(SOLDTICKETS).where(SOLDTICKETS.SOLDTICKETS_ID.eq(id)).fetchOne();

		SoldTicket sold = new SoldTicket(
				rec.getValue(SOLDTICKETS.SOLDTICKETS_ID),
				rec.getValue(SOLDTICKETS.NAME),
				new Date((long) rec.getValue(SOLDTICKETS.TIMESTAMP)*1000),
				rec.getValue(SOLDTICKETS.PRICE));
		sold.setId(id);
		return(sold);
	}

	///////////////////////////
	// Methods for "Ticket"s //
	///////////////////////////

    /**
     * Creates a new database entry for a Ticket object
     *
     * @param tick Ticket object to be stored in the database
     * @return unique ID of newly created name entry in the database
     */
	public int addTicket(Ticket tick)
	{
		TicketsRecord newTick = create.insertInto(TICKETS,
                TICKETS.NAME,
                TICKETS.PRICE,
                TICKETS.NUMPASSENGERS,
                TICKETS.DESCRIPTION)
				.values(tick.getName(),
                        tick.getPrice(),
                        tick.getNumPassengers(),
                        tick.getDescription())
				.returning(TICKETS.TICKETS_ID)
				.fetchOne();

		return(newTick.getTicketsId());
	}

    /**
     * Removes a name entry from the database using its unique id
     *
     * @param id unique ID of the name entry that is to be deleted from the database
     */
	public void deleteTickets(int id)
	{
		create.delete(TICKETS).where(TICKETS.TICKETS_ID.equal(id)).execute();
	}

    /**
     * Modifies an existing name entry in the database
     *
     * @param tick Ticket object whose entry is to be updated in the database
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
     */
	public ArrayList<Ticket> getTickets()
	{
		Result<Record> ticketRecords = create.select().from(TICKETS).fetch();

		ArrayList<Ticket> ticketList = new ArrayList<>();

		for (Record rec : ticketRecords)
		{
			Ticket tick = new Ticket(
					rec.getValue(TICKETS.PRICE),
					rec.getValue(TICKETS.NAME),
					rec.getValue(TICKETS.NUMPASSENGERS),
					rec.getValue(TICKETS.DESCRIPTION),
					rec.getValue(TICKETS.TICKETS_ID));
			ticketList.add(tick);
		}
		return ticketList;
	}

    /**
     * Retrieves a single Ticket object from the database entry using its unique id
     *
     * @param id unique ID of the name to be retrieved
     * @return Ticket object created from its corresponding entry the database
     */
	public Ticket getTicket(int id)
	{
		Record rec = create.select().from(TICKETS).where(TICKETS.TICKETS_ID.eq(id)).fetchOne();

		Ticket tick = new Ticket(
				rec.getValue(TICKETS.PRICE),
				rec.getValue(TICKETS.NAME),
				rec.getValue(TICKETS.NUMPASSENGERS),
				rec.getValue(TICKETS.DESCRIPTION),
				rec.getValue(TICKETS.TICKETS_ID));
		tick.setId(id);
		return(tick);
	}

	//////////////////////////
	//	Methods for "Tour"s //
	//////////////////////////

	// TODO: Implementieren!
    // TODO: 2015-03-05 - Most of this should work by now...

    /**
     * Creates all trips for a given day
     *
     * @param date date for which tours ought to be generated
     */
    public void createTours(Date date){

        Result<Record1<Integer>> routes = create.select(ROUTES.ROUTES_ID).from(ROUTES).fetch();

        // Modify given date to represent midnight
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // Reset hour, minutes, seconds and milliseconds
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).toUpperCase();

        int timestamp = (int) (calendar.getTimeInMillis()/1000);

        create.execute("BEGIN");

        for (Record1<Integer> route : routes)
        {
            Result<Record1<Integer>> startingTimes = create
                    .select(ROUTES_STARTTIMES.STARTTIME).from(ROUTES_STARTTIMES)
                    .where(ROUTES_STARTTIMES.DAYOFWEEK.eq(dayOfWeek)).fetch();

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

            create.execute("END");
        }
    }

    /**
     * Retrieves all tours to which the given user is assigned from the database
     *
     * @param id unique ID of the user whose tours are to be retrieved
     * @return ArrayList of all tours associated with the given user
     */
    public ArrayList<Tour> getUserTours(int id)
    {
        ArrayList<Tour> result = new ArrayList<>();

        Result<ToursRecord> tours = create.selectFrom(TOURS).where(TOURS.EMPLOYEES_ID.eq(id)).fetch();

        for (ToursRecord t : tours){
            result.add(
                    new Tour(
                            new Date((long) t.getTimestamp()*1000),
                            getRoute(t.getRoutesId()),
                            getBus(t.getBusesId()),
                            getEmployee(t.getEmployeesId())
                    )
            );
        }

        return result;
    }
}