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
 * @author Michael Biech, Phil Steinhorst
 */
public class ControllerDatabaseJOOQ
{

	DSLContext create;

    public ControllerDatabaseJOOQ(){
        openDB();
    }

	/**
	 * Initialize database for use inside this class
	 */
	public void openDB()
	{
		try
		{
            String url = "jdbc:sqlite:test.db";
            Connection conn;

			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection(url);
			create = DSL.using(conn, SQLDialect.SQLITE);
		}

		catch (ClassNotFoundException e)
		{
			System.out.println("Fehler beim Laden des JDBC-Treibers!");
			e.printStackTrace();
		}

		catch (SQLException e)
		{
			System.out.println("SQL-Exception");
			e.printStackTrace();
		}
	}

	///////////////////////
	// Methods for Buses //
	///////////////////////

	/**
	 * Adds a new bus into database
	 *
	 * @param bus Bus object to be saved in database
	 * @return Database ID of added bus
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
                        (int) bus.getNextInspectionDue().getTime()/1000,
                        (Boolean) bus.isArticulatedBus())
				.returning(BUSES.BUSES_ID)
				.fetchOne();

		return(newBus.getBusesId());
	}

	/**
	 * Deletes a bus from database by using its database-internal id
	 *
	 * @param id contains the unique internal ID of the bus to be deleted
	 */
	public void deleteBus(int id)
	{
		create.delete(BUSES).where(BUSES.BUSES_ID.equal(id)).execute();
	}

	/**
	 * Modifies a bus in database by using its database-internal id
	 *
	 * @param id contains the unique internal ID of the bus to be modified
	 * @param bus bus object containing the new properties
	 */
	public void modifyBus(int id, Bus bus)
	{
		create.update(BUSES)
				.set(BUSES.LICENCEPLATE,bus.getLicencePlate())
				.set(BUSES.NUMBEROFSEATS,bus.getNumberOfSeats())
				.set(BUSES.STANDINGROOM, bus.getStandingRoom())
				.set(BUSES.MANUFACTURER,bus.getManufacturer())
				.set(BUSES.MODEL,bus.getModel())
				.set(BUSES.NEXTINSPECTIONDUE,(int) bus.getNextInspectionDue().getTime()/1000)
				.set(BUSES.ARTICULATEDBUS, bus.isArticulatedBus())
				.where(BUSES.BUSES_ID.equal(id))
				.execute();
	}

	/**
	 * Returns an ArrayList of Bus objects containing all buses stored in database
	 *
	 * @return contains every bus object stored in database
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
					new Date(rec.getValue(BUSES.NEXTINSPECTIONDUE)*1000),
					rec.getValue(BUSES.ARTICULATEDBUS));
			busList.add(bus);
		}
		return busList;
	}

    /**
     * Returns a single bus from the database by using its database-internal id
     *
     * @param id the id of the bus which is to be retrieved
     * @return a bus object from the database
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
                new Date(busRecord.getValue(BUSES.NEXTINSPECTIONDUE)*1000),
                busRecord.getValue(BUSES.ARTICULATEDBUS));
        bus.setId(id);
		return bus;
    }

	//////////////////////////
	// Methods for BusStops //
	//////////////////////////

	/**
	 * Adds a new bus stop to the database
	 *
	 * @param bstop BusStop object to be saved in database
	 * @return database ID of added bus
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

		HashSet<String> points = bstop.getStoppingPoints();

		for (String s : points)
		{
			create.insertInto(
                    BUSSTOPS_STOPPINGPOINTS,
                    BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID,
                    BUSSTOPS_STOPPINGPOINTS.NAME)
				.values(
                        newStop.getBusstopsId(),
                        s)
				.execute();
		}

		return(newStop.getBusstopsId());
	}

	/**
	 * deletes a bus stop from database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the bus stop to be deleted.
	 */
	public void deleteBusStop(int id)
	{
		create.delete(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.equal(id)).execute();
	}

	/**
	 * modifies a bus stop in database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the bus stop to be modified.
	 * @param bstop Bus stop object containing the new properties.
	 */
	public void modifyBusStop(int id, BusStop bstop)
	{
		create.update(BUSSTOPS)
				.set(BUSSTOPS.NAME, bstop.getName())
				.set(BUSSTOPS.LOCATIONX, bstop.getLocation().getFirst())
				.set(BUSSTOPS.LOCATIONY, bstop.getLocation().getSecond())
				.set(BUSSTOPS.BARRIERFREE, bstop.isBarrierFree())
				.execute();

		// TODO: StoppingPoints werden noch nicht aktualisiert! Dafür entweder eine eigene Entitätsklasse schaffen oder HashSet in BusStop modifizieren!
	}

	/**
	 * returns an ArrayList of BusStop objects containing all bus stops stored in database.
	 *
	 * @return Contains every bus stop object stored in database.
	 */
	public ArrayList<BusStop> getBusStops()
	{
		Result<Record> busStopRecords = create.select().from(BUSSTOPS).fetch();		// get all busStops from DB
		ArrayList<BusStop> busStopList = new ArrayList<>();

		for (Record rec : busStopRecords)			// for each busStop in DB...
		{
			Result<Record> stoppingPoints = create.select().from(BUSSTOPS_STOPPINGPOINTS)	//.. get all corresponding stoppingPoints ...
					.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

			HashSet<String> spoints = new HashSet<>();
			for (Record sp : stoppingPoints)
			{
				spoints.add(sp.getValue(BUSSTOPS_STOPPINGPOINTS.NAME));		//.. and put them into a HashSet
			}

			BusStop busStop = new BusStop(
					rec.getValue(BUSSTOPS.NAME),
					new Tuple<Integer,Integer>(rec.getValue(BUSSTOPS.LOCATIONX),rec.getValue(BUSSTOPS.LOCATIONY)),
					spoints,
					rec.getValue(BUSSTOPS.BARRIERFREE));
			busStopList.add(busStop);			// create busStop object and add to ArrayList
		}
		return busStopList;
	}

    /**
     * Returns a single bus stop and its stopping points from the database according to its ID
     *
     * @param id bus stop to retrieve from the database
     * @return the requested bus stop as an object
     */
    public BusStop getBusStop(int id)
    {
        Record busStopRecord = create.select().from(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.eq(id)).fetchOne();		// get all busStops from DB
        Result<Record> stoppingPoints = create.select().from(BUSSTOPS_STOPPINGPOINTS)	//.. get all corresponding stoppingPoints ...
                .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(busStopRecord.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

        HashSet<String> spoints = new HashSet<>();
        for (Record sp : stoppingPoints)
        {
            spoints.add(sp.getValue(BUSSTOPS_STOPPINGPOINTS.NAME));		//.. and put them into a HashSet
        }

        BusStop busStop = new BusStop(
                busStopRecord.getValue(BUSSTOPS.NAME),
                new Tuple<Integer,Integer>(
                        busStopRecord.getValue(BUSSTOPS.LOCATIONX),
                        busStopRecord.getValue(BUSSTOPS.LOCATIONY)
                ),
                spoints,
                busStopRecord.getValue(BUSSTOPS.BARRIERFREE)
        );
		busStop.setId(id);
        return busStop;

    }

	///////////////////////////
	// Methods for Employees //
	///////////////////////////

	/**
	 * adds a new employee into database.
	 *
	 * @param emp Employee object to be saved in database.
	 * @return Database ID of added employee.
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
	 * deletes an employee from database by using its unique user name.
	 *
	 * @param username Contains the unique user name of the employee to be deleted.
	 */
	@Deprecated
	public void deleteEmployee(String username)
	{
		create.delete(EMPLOYEES).where(EMPLOYEES.USERNAME.equal(username)).execute();
	}

	/**
	 * deletes an employee from database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the employee to be deleted.
	 */
	public void deleteEmployee(int id)
	{
		create.delete(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.equal(id)).execute();
	}

	/**
	 * modifies an employee in database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the employee to be modified.
	 * @param emp Employee object containing the new properties.
	 */
	public void modifyEmployee(int id, Employee emp)
	{
		create.update(EMPLOYEES)
				.set(EMPLOYEES.NAME, emp.getName())
				.set(EMPLOYEES.FIRSTNAME,emp.getFirstName())
				.set(EMPLOYEES.ADDRESS,emp.getAddress())
				.set(EMPLOYEES.ZIPCODE,emp.getZipCode())
				.set(EMPLOYEES.CITY,emp.getCity())
				.set(EMPLOYEES.DATEOFBIRTH,(int) emp.getDateOfBirth().getTime()/1000)
				.set(EMPLOYEES.PHONE,emp.getPhone())
				.set(EMPLOYEES.EMAIL,emp.getEmail())
				.set(EMPLOYEES.USERNAME,emp.getUsername())
				.set(EMPLOYEES.SALT,emp.getSalt())
				.set(EMPLOYEES.PASSWORD,emp.getPassword())
				.set(EMPLOYEES.NOTE,emp.getNote())
				.set(EMPLOYEES.ISBUSDRIVER,emp.isRole(Role.BUSDRIVER))
				.set(EMPLOYEES.ISNETWORK_PLANNER,emp.isRole(Role.NETWORK_PLANNER))
				.set(EMPLOYEES.ISTICKET_PLANNER,emp.isRole(Role.TICKET_PLANNER))
				.set(EMPLOYEES.ISHR_MANAGER,emp.isRole(Role.HR_MANAGER))
				.set(EMPLOYEES.ISSCHEDULE_MANAGER, emp.isRole(Role.SCHEDULE_MANAGER))
				.where(EMPLOYEES.EMPLOYEES_ID.equal(id))
				.execute();
	}

	/**
	 * modifies an employee in database by using its user name.
	 *
	 * @param username Contains the unique user name of the employee to be modified.
	 * @param emp Employee object containing the new properties.
	 */
	@Deprecated
	public void modifyEmployee(String username, Employee emp)
	{
		create.update(EMPLOYEES)
				.set(EMPLOYEES.NAME, emp.getName())
				.set(EMPLOYEES.FIRSTNAME,emp.getFirstName())
				.set(EMPLOYEES.ADDRESS,emp.getAddress())
				.set(EMPLOYEES.ZIPCODE,emp.getZipCode())
				.set(EMPLOYEES.CITY,emp.getCity())
				.set(EMPLOYEES.DATEOFBIRTH,(int) emp.getDateOfBirth().getTime()/1000)
				.set(EMPLOYEES.PHONE,emp.getPhone())
				.set(EMPLOYEES.EMAIL,emp.getEmail())
				.set(EMPLOYEES.USERNAME,emp.getUsername())
				.set(EMPLOYEES.SALT,emp.getSalt())
				.set(EMPLOYEES.PASSWORD,emp.getPassword())
				.set(EMPLOYEES.NOTE,emp.getNote())
				.set(EMPLOYEES.ISBUSDRIVER,emp.isRole(Role.BUSDRIVER))
				.set(EMPLOYEES.ISNETWORK_PLANNER,emp.isRole(Role.NETWORK_PLANNER))
				.set(EMPLOYEES.ISTICKET_PLANNER,emp.isRole(Role.TICKET_PLANNER))
				.set(EMPLOYEES.ISHR_MANAGER,emp.isRole(Role.HR_MANAGER))
				.set(EMPLOYEES.ISSCHEDULE_MANAGER,emp.isRole(Role.SCHEDULE_MANAGER))
				.where(EMPLOYEES.USERNAME.equal(username))
				.execute();
	}

	/**
	 * returns an ArrayList of Employee objects containing all employees stored in database.
	 *
	 * @return Contains every employee object stored in database.
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
					new Date(rec.getValue(EMPLOYEES.DATEOFBIRTH)*1000),
					rec.getValue(EMPLOYEES.PHONE),
					rec.getValue(EMPLOYEES.EMAIL),
					rec.getValue(EMPLOYEES.USERNAME),
					rec.getValue(EMPLOYEES.PASSWORD),
					rec.getValue(EMPLOYEES.SALT),
					rec.getValue(EMPLOYEES.NOTE),
					roles);
			empList.add(emp);
		}
		return empList;
	}

	/**
	 * Get a single employee object from database.
	 *
	 * @param id Unique database-internal ID of requested employee object.
	 * @return Requested employee object.
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
				new Date(rec.getValue(EMPLOYEES.DATEOFBIRTH)*1000),
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


	////////////////////////
	// Methods for Routes //
	////////////////////////

	/**
	 * adds a new route into database.
	 *
	 * @param r Route object to be saved in database.
	 * @return Database ID of added route.
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

		LinkedList<Tuple<BusStop,Integer>>  stops = r.getStops();
		for (Tuple<BusStop,Integer> pair : stops)
		{
			create.insertInto(ROUTES_STOPS,
                    ROUTES_STOPS.ROUTES_ID,
                    ROUTES_STOPS.BUSSTOPS_ID,
					ROUTES_STOPS.TIMETOPREVIOUS)
					.values(newRoute.getRoutesId(),
                            pair.getFirst().getId(),
                            pair.getSecond())
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
	 * deletes a route from database by using its unique ID.
	 *
	 * @param id Contains the unique ID of the employee to be deleted.
	 */
	public void deleteRoute(int id)
	{
		create.delete(ROUTES).where(ROUTES.ROUTES_ID.equal(id));
	}

	/**
	 * modifies a route in database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the route to be modified.
	 * @param r Route object containing the new properties.
	 */
	public void modifyRoute(int id, Route r)
	{
		// TODO: Implementierten!
	}

	/**
	 * returns an ArrayList of Route objects containing all routes stored in database.
	 *
	 * @return Contains every route object stored in database.
	 *
	 */
	public ArrayList<Route> getRoutes()
	{
		Result<Record> routesRecords = create.select().from(ROUTES).fetch();        // get all busStops from DB
		ArrayList<Route> routesList = new ArrayList<>();

		for (Record rec : routesRecords)            // for each busStop in DB...
		{
			Result<Record> startTimesRecords = create.select().from(ROUTES_STARTTIMES)    //.. get all corresponding stoppingPoints ...
					.where(ROUTES_STARTTIMES.ROUTES_ID.equal(rec.getValue(ROUTES.ROUTES_ID))).fetch();

			HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();
			startTimes.put(DayOfWeek.MONDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.TUESDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.THURSDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.FRIDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.SATURDAY, new LinkedList<Integer>());
			startTimes.put(DayOfWeek.SUNDAY, new LinkedList<Integer>());

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


			Result<Record> stopsRecords = create.select().from(ROUTES_STOPS)    //.. get all corresponding stoppingPoints ...
					.where(ROUTES_STOPS.ROUTES_ID.equal(rec.getValue(ROUTES.ROUTES_ID))).fetch();
			LinkedList<Tuple<BusStop, Integer>> stops = new LinkedList<>();


			for (Record s : stopsRecords)
			{
				int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
				BusStop bstop = getBusStop(stopId);
				stops.add(new Tuple<>(bstop,s.getValue(ROUTES_STOPS.TIMETOPREVIOUS)));
			}

			Route route = new Route(
					rec.getValue(ROUTES.NAME),
					rec.getValue(ROUTES.NOTE),
					stops,
					rec.getValue(ROUTES.NIGHT),
					startTimes);
			routesList.add(route);            // create busStop object and add to ArrayList
		}
		return routesList;
	}

	/**
	 * Get a route object from database.
	 *
	 * @param id Unique database-internal ID of requested route object.
	 * @return Requested route object.
	 */
	public Route getRoute(int id)
	{
		Record rec = create.select().from(ROUTES).where(ROUTES.ROUTES.ROUTES_ID.eq(id)).fetchOne();
		Result<Record> startTimesRecords = create.select().from(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.equal(id)).fetch();

		HashMap<DayOfWeek, LinkedList<Integer>> startTimes = new HashMap<>();

		startTimes.put(DayOfWeek.MONDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.TUESDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.WEDNESDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.THURSDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.FRIDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.SATURDAY, new LinkedList<Integer>());
		startTimes.put(DayOfWeek.SUNDAY, new LinkedList<Integer>());

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


		Result<Record> stopsRecords = create.select().from(ROUTES_STOPS)    //.. get all corresponding stoppingPoints ...
				.where(ROUTES_STOPS.ROUTES_ID.equal(id)).fetch();
		LinkedList<Tuple<BusStop, Integer>> stops = new LinkedList<>();

		for (Record s : stopsRecords)
		{
			int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
			BusStop bstop = getBusStop(stopId);
			stops.add(new Tuple<>(bstop,s.getValue(ROUTES_STOPS.TIMETOPREVIOUS)));
		}

		Route route = new Route(
				rec.getValue(ROUTES.NAME),
				rec.getValue(ROUTES.NOTE),
				stops,
				rec.getValue(ROUTES.NIGHT),
				startTimes);

		return route;
	}


	/////////////////////////////
	// Methods for soldTickets //
	/////////////////////////////

	/**
	 * adds a new sold ticket into database.
	 *
	 * @param sold Sold ticket object to be saved in database.
	 * @return Database ID of added sold ticket.
	 */
	public int addSoldTicket(SoldTicket sold)
	{
		SoldticketsRecord newSold = create.insertInto(SOLDTICKETS,
                SOLDTICKETS.NAME,
                SOLDTICKETS.TIMESTAMP,
                SOLDTICKETS.PRICE)
			.values(sold.getName(),
                    (int) sold.getDate().getTime() / 1000,
                    sold.getPrice())
			.returning(SOLDTICKETS.SOLDTICKETS_ID)
			.fetchOne();

		return(newSold.getSoldticketsId());
	}

	/**
	 * deletes a sold ticket from database by using its unique ID.
	 *
	 * @param id Contains the unique ID of the sold ticket to be deleted.
	 */
	public void deleteSoldTicket(int id)
	{
		create.delete(SOLDTICKETS).where(SOLDTICKETS.SOLDTICKETS_ID.equal(id)).execute();
	}

	/**
	 * modifies a sold ticket in database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the sold ticket to be modified.
	 * @param sold Sold ticket object containing the new properties.
	 */
	public void modifySoldTicket(int id, SoldTicket sold)
	{
		create.update(SOLDTICKETS)
				.set(SOLDTICKETS.NAME, sold.getName())
				.set(SOLDTICKETS.TIMESTAMP,(int) sold.getDate().getTime()/1000)
				.set(SOLDTICKETS.PRICE,sold.getPrice())
				.execute();
	}

	/**
	 * returns an ArrayList of SoldTicket objects containing all sold tickets stored in database.
	 *
	 * @return Contains every sold ticket stored in database.
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
					new Date(rec.getValue(SOLDTICKETS.TIMESTAMP)*1000),
					rec.getValue(SOLDTICKETS.PRICE));
			soldTicketsList.add(sold);
		}
		return soldTicketsList;
	}

	/**
	 * Get a single sold ticket object from database.
	 *
	 * @param id Unique database-internal ID of requested sold ticket object.
	 * @return Requested sold ticket object.
	 */
	public SoldTicket getSoldTicket(int id)
	{
		Record rec = create.select().from(SOLDTICKETS).where(SOLDTICKETS.SOLDTICKETS_ID.eq(id)).fetchOne();

		SoldTicket sold = new SoldTicket(
				rec.getValue(SOLDTICKETS.SOLDTICKETS_ID),
				rec.getValue(SOLDTICKETS.NAME),
				new Date(rec.getValue(SOLDTICKETS.TIMESTAMP)*1000),
				rec.getValue(SOLDTICKETS.PRICE));
		sold.setId(id);
		return(sold);
	}

	/////////////////////////
	// Methods for Tickets //
	/////////////////////////

	/**
	 * adds a new ticket into database.
	 *
	 * @param tick Ticket object to be saved in database.
	 * @return Database ID of added ticket.
	 */
	public int addTickets(Ticket tick)
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
	 * deletes a ticket from database by using its unique ID.
	 *
	 * @param id Contains the unique ID of the ticket to be deleted.
	 */
	public void deleteTickets(int id)
	{
		create.delete(TICKETS).where(TICKETS.TICKETS_ID.equal(id)).execute();
	}

	/**
	 * modifies a ticket in database by using its database-internal id.
	 *
	 * @param id Contains the unique internal ID of the ticket to be modified.
	 * @param tick Ticket object containing the new properties.
	 */
	public void modifyTickets(int id, Ticket tick)
	{
		create.update(TICKETS)
				.set(TICKETS.NAME, tick.getName())
				.set(TICKETS.PRICE, tick.getPrice())
				.set(TICKETS.NUMPASSENGERS, tick.getNumPassengers())
				.set(TICKETS.DESCRIPTION, tick.getDescription())
				.execute();
	}

	/**
	 * returns an ArrayList of Ticket objects containing all tickets stored in database.
	 *
	 * @return Contains every ticket stored in database.
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
	 * Get a single ticket object from database.
	 *
	 * @param id Unique database-internal ID of requested ticket object.
	 * @return Requested ticket object.
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

	////////////////////////
	//	Methods for Tours //
	////////////////////////

	// TODO: Implementieren!

    /**
     * In the future, this method will create all the trips for a given day
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
}
