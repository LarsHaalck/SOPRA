package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;

import javafx.scene.paint.Stop;
import org.jooq.*;
import org.jooq.impl.*;

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
                        bus.isArticulatedBus())
				.returning(BUSES.BUSES_ID)
                .fetchOne();

		return(newBus.getBusesId());
	}

    /**
     * Removes a bus entry from the database using its unique id. Assigned tours will also be modified
     *
     * @param id unique ID of the bus entry that is to be deleted from the database
	 * @return number of tours modified due to bus deletion
     */
	public int deleteBus(int id)
	{
		// Start by deleting references to this bus from all tours
        int numOfTours = deleteBusFromTours(id);
        // Then delete the bus itself
		create.delete(BUSES).where(BUSES.BUSES_ID.equal(id)).execute();
		return numOfTours;
	}

	/**
	 * Deletes a specific bus from tours within a specific range of time.
	 *
	 * @param uid unique ID of the bus being deleted from tours
	 * @param begin point in time after which drivers ought to be deleted from tours
	 * @param end point in time before which drivers ought to be deleted from tours
	 * @return number of tours edited
	 */
	public int deleteBusFromTours(int uid, Date begin, Date end)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.BUSES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.fetchOne();

		create.update(TOURS)
				.set(TOURS.BUSES_ID,(Integer) null)
				.where(TOURS.BUSES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual( (int) (end.getTime()/1000) ))
				.and(TOURS.TIMESTAMP.greaterOrEqual( (int) (begin.getTime()/1000) ))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes a specific bus from tours.
	 *
	 * @param id unique ID of the bus being deleted from tours
	 * @return number of tours edited
	 */
	public int deleteBusFromTours(int id)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.BUSES_ID.equal(id))
				.fetchOne();

		create.update(TOURS)
				.set(TOURS.BUSES_ID,(Integer) null)
				.where(TOURS.BUSES_ID.equal(id))
				.execute();

		return (Integer) record.getValue(0);
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
     * Retrieves a list of Bus objects representing all bus entries stored in the database.
     *
     * @return ArrayList containing Bus objects for every bus entry stored in the database
     */
	public ArrayList<Bus> getBuses()
	{
		Result<Record> busRecords = create.select().from(BUSES).fetch();

        // In case there are no buses - which is unlikely, but who knows
        if (busRecords == null) return null;

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
     * @return Bus object created from its corresponding entry the database, null if bus not found
     */
    public Bus getBusById(int id)
    {

        Record busRecord = create.select().from(BUSES).where(BUSES.BUSES_ID.eq(id)).fetchOne();

        if (busRecord == null) return null;

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
     * Removes a bus stop entry from the database using its unique id. If a bus stop is still
     * in use by a route, the stop should NOT be deleted! Instead, the user ought to be
     * notified that the bus stop is still in use
     *
     * @param id unique ID of the bus stop entry that is to be deleted from the database
     */
	public void deleteBusStop(int id)
	{
		create.delete(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.equal(id)).execute();
	}

	/**
	 * Deletes all stopping points of a specific bus stop.
	 *
	 * @param id unique ID of the bus stop whose stopping points ought to be deleted
	 * @return number of stopping points deleted
	 */
	public int deleteStoppingPointsUsingBusStopId(int id)
	{
		Record record = create
				.selectCount()
				.from(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(id))
				.fetchOne();

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
     */
    public BusStop getBusStopByStoppingPointId(int id)
    {
        BusstopsStoppingpointsRecord r = create.selectFrom(BUSSTOPS_STOPPINGPOINTS)
                .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
                .fetchOne();

        return getBusStopById(r.getBusstopsId());
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
        Result<BusstopsRecord> busStopRecords = create.selectFrom(BUSSTOPS).fetch();

        // In case there are no BusStops, which is unlikely, but who knows
        if (busStopRecords == null) return null;

		ArrayList<BusStop> busStopList = new ArrayList<>();

		// For each bus retrieved...
        for (BusstopsRecord rec : busStopRecords)
		{
            // ... get all corresponding stopping points...
            Result<BusstopsStoppingpointsRecord> stoppingPoints = create.selectFrom(BUSSTOPS_STOPPINGPOINTS)
                    .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

            // ... and put them all into a HashSet
            // (Here, we don't worry about checking if stoppingPoints is null, because if it is, the loop just won't run)
            HashSet<StoppingPoint> spoints = new HashSet<>();
            for (BusstopsStoppingpointsRecord sp : stoppingPoints)
			{
				spoints.add(new StoppingPoint(sp.getBusstopsStoppingpointsId(),sp.getName()));
			}

			boolean barrier = rec.getValue(BUSSTOPS.BARRIERFREE);
			BusStop busStop = new BusStop(
					rec.getValue(BUSSTOPS.NAME),
					new Tuple<Integer,Integer>(rec.getValue(BUSSTOPS.LOCATIONX),rec.getValue(BUSSTOPS.LOCATIONY)),
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
     */
    public BusStop getBusStopById(int id)
    {
        // Get desired bus from DB
        Record rec = create.select().from(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.eq(id)).fetchOne();
        if (rec == null) return null;

        // Get all associated stopping points...
        Result<Record> stoppingPoints = create.select().from(BUSSTOPS_STOPPINGPOINTS)
                .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.equal(rec.getValue(BUSSTOPS.BUSSTOPS_ID))).fetch();

        // .. and add them into a HashSet
        //  (Here, we don't worry about checking if stoppingPoints is null, because if it is, the loop just won't run)
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

	/**
	 * Returns a list of names of routes which use a specific stopping point
	 *
	 * @param id unique ID of the stopping point
	 * @return ArrayList of Strings containing names of routes
	 */
	public ArrayList<String> getRouteNamesUsingStoppingPointId(int id)
	{
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetch();

		ArrayList<String> names = new ArrayList<>();
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getValue(ROUTES_STOPS.ROUTES_ID);
			RoutesRecord route = create
					.selectFrom(ROUTES)
					.where(ROUTES.ROUTES_ID.eq(routeID))
					.groupBy(ROUTES_STOPS.ROUTES_ID)
					.fetchOne();
			names.add(route.getName());
		}
		return(names);
	}

	/**
	 * Returns a list of routes which use a specific stopping point
	 *
	 * @param id unique ID of the stopping point
	 * @return ArrayList of routes containing the routes
	 */
	public ArrayList<Route> getRoutesUsingStoppingPoint(int id)
	{
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
				.fetch();

		ArrayList<Route> listOfRoutes = new ArrayList<>();
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getValue(ROUTES_STOPS.ROUTES_ID);
			listOfRoutes.add(getRouteById(routeID));
		}
		return(listOfRoutes);
	}

	/**
	 * Returns a list of names of routes which use a specific bus stop
	 *
	 * @param id unique ID of the bus stop
	 * @return ArrayList of Strings containing names of routes
	 */
	public ArrayList<String> getRouteNamesUsingBusStopId(int id)
	{
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.BUSSTOPS_ID.eq(id))
				.groupBy(ROUTES_STOPS.ROUTES_ID)
				.fetch();

		ArrayList<String> names = new ArrayList<>();
		for (RoutesStopsRecord rec : routes)
		{
			int routeID = rec.getValue(ROUTES_STOPS.ROUTES_ID);
			RoutesRecord route = create
					.selectFrom(ROUTES)
					.where(ROUTES.ROUTES_ID.eq(routeID))
					.fetchOne();
			names.add(route.getName());
		}
		return(names);
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
	 */
	public int addStoppingPoint(int id, StoppingPoint sp)
	{
		BusstopsStoppingpointsRecord newStop = create.insertInto(
				BUSSTOPS_STOPPINGPOINTS,
				BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID,
				BUSSTOPS_STOPPINGPOINTS.NAME)
				.values(id,
						sp.getName())
				.returning(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID)
				.fetchOne();

		return(newStop.getBusstopsStoppingpointsId());
	}

	/**
	 * Returns a list of stopping points which belong to a specific bus stop
	 *
	 * @param id unique ID of the bus stop
	 * @return ArrayList of stopping points belonging to the bus stop
	 */
	public ArrayList<StoppingPoint> getStoppingPointsByBusStopId(int id)
	{
		// Start by getting all bus stops from the database
		Result<BusstopsStoppingpointsRecord> rows = create
				.selectFrom(BUSSTOPS_STOPPINGPOINTS)
				.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID.eq(id))
				.fetch();

		// In case there are no BusStops, which is unlikely, but who knows
		if (rows == null) return null;

		ArrayList<StoppingPoint> result = new ArrayList<>();

		// For each bus retrieved...
		for (BusstopsStoppingpointsRecord rec : rows)
		{
			result.add(getStoppingPointById(rec.getBusstopsId()));
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
                        (int) (emp.getDateOfBirth().getTime()/1000),
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

		return(newEmp.getEmployeesId());
	}

	/**
	 * Removes an employee entry from the database using its unique id. Assigned tours will be modified
	 *
	 * @param id unique ID of the employee entry to be deleted from the database
	 * @return number of tours modified due to employee deletion
	 */
	public int deleteEmployee(int id)
	{
		int numOfTours = deleteEmployeeFromTours(id);
		create.delete(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.equal(id)).execute();
		return numOfTours;
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

        // In case we have no employees to return
        if (empRecords == null) return null;

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
     * @return Employee object created from its corresponding entry the database, null if employee not found
     */
	public Employee getEmployeeById(int id)
	{
		Record rec = create.select().from(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.eq(id)).fetchOne();

        // Return null if employee can not be found
        if (rec == null) return null;

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

    public Employee getEmployeeByUsername(String username){
        Record rec = create.select().from(EMPLOYEES).where(EMPLOYEES.USERNAME.eq(username)).fetchOne();
        return (rec == null) ? null : getEmployeeById(rec.getValue(EMPLOYEES.EMPLOYEES_ID));
    }

	/**
	 * Deletes a specific user from tours within a specific range of time.
	 *
	 * @param uid unique ID of the user being deleted from tours
	 * @param begin point in time after which drivers ought to be deleted from tours
	 * @param end point in time before which drivers ought to be deleted from tours
	 * @return number of tours edited
	 */
	public int deleteEmployeeFromTours(int uid, Date begin, Date end)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.EMPLOYEES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual((int) (end.getTime() / 1000)))
				.and(TOURS.TIMESTAMP.greaterOrEqual((int) (begin.getTime() / 1000)))
				.fetchOne();

		create.update(TOURS)
				.set(TOURS.EMPLOYEES_ID,(Integer) null)
				.where(TOURS.EMPLOYEES_ID.equal(uid))
				.and(TOURS.TIMESTAMP.lessOrEqual( (int) (end.getTime()/1000) ))
				.and(TOURS.TIMESTAMP.greaterOrEqual( (int) (begin.getTime()/1000) ))
				.execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes a specific user from tours.
	 *
	 * @param id unique ID of the user being deleted from tours
	 * @return number of tours edited
	 */
	public int deleteEmployeeFromTours(int id)
	{
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.EMPLOYEES_ID.equal(id))
				.fetchOne();

		create.update(TOURS)
				.set(TOURS.EMPLOYEES_ID, (Integer) null)
				.where(TOURS.EMPLOYEES_ID.equal(id))
				.execute();

		return (Integer) record.getValue(0);
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
                        r.isNight())
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
	public int deleteRoute(int id)
	{
		int numOfTours = deleteToursUsingRoutesId(id);
		create.delete(ROUTES).where(ROUTES.ROUTES_ID.equal(id));
		return(numOfTours);
	}



    /**
     * Modifies an existing route entry in the database. Route entry ID will be changed if stopping points or starting
	 * times are changed
     *
     * @param r Route object whose entry is to be updated in the database
     * @param deleteTours true if associated tours ought to be deleted
	 * @return id of the changed route entry (new if deleteTours was true)
     */
	public int modifyRoute(Route r, boolean deleteTours)
	{
		if (!deleteTours)
		{
			create.update(ROUTES)
					.set(ROUTES.NAME, r.getName())
					.set(ROUTES.NOTE, r.getNote())
					.set(ROUTES.NIGHT, r.isNight())
					.where(ROUTES.ROUTES_ID.equal(r.getId()))
					.execute();
			return(r.getId());
		} else
		{
			deleteToursUsingRoutesId(r.getId());
			deleteStartTimesUsingRouteId(r.getId());
			deleteRoutesStopsUsingRouteId(r.getId());
			return(addRoute(r));
		}
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
            Result<RoutesStopsRecord> stopsRecords = create.selectFrom(ROUTES_STOPS)
                    .where(ROUTES_STOPS.ROUTES_ID.equal(rec.getValue(ROUTES.ROUTES_ID))).fetch();
			LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

            // ... and add them to the list of stops
			for (RoutesStopsRecord s : stopsRecords)
			{
				int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
				BusStop bstop = getBusStopById(stopId);
				StoppingPoint spoint = getStoppingPointById(s.getBusstopsStoppingpointsId());
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
     * @return Route object created from its corresponding entry the database, null if route not found
     */
	public Route getRouteById(int id)
	{
	    // Start by getting the desired route from the database
		Record rec = create.select().from(ROUTES).where(Routes.ROUTES.ROUTES_ID.eq(id)).fetchOne();

        if (rec == null) return null;

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
        Result<RoutesStopsRecord> stopsRecords = create.selectFrom(ROUTES_STOPS)
                .where(ROUTES_STOPS.ROUTES_ID.equal(id)).fetch();
		LinkedList<Triple<BusStop, StoppingPoint, Integer>> stops = new LinkedList<>();

        // ... and add them to the list of stops
		for (RoutesStopsRecord s : stopsRecords)
		{
			int stopId = s.getValue(ROUTES_STOPS.BUSSTOPS_ID);
			BusStop bstop = getBusStopById(stopId);
			StoppingPoint spoint = getStoppingPointById(s.getBusstopsStoppingpointsId());
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

	/**
	 * Deletes all stopping points of a specific route.
	 *
	 * @param id unique ID of the route whose stopping points to be deleted
	 * @return number of stopping points deleted
	 */
	public int deleteRoutesStopsUsingRouteId(int id)
	{
		Record record = create
				.selectCount()
				.from(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.equal(id))
				.fetchOne();

		create.delete(ROUTES_STOPS).where(ROUTES_STOPS.ROUTES_ID.eq(id)).execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Deletes all start times of a specific route.
	 *
	 * @param id unique ID of the route whose start times to be deleted.
	 * @return number of stopping points deleted
	 */
	public int deleteStartTimesUsingRouteId(int id)
	{
		Record record = create
				.selectCount()
				.from(ROUTES_STARTTIMES)
				.where(ROUTES_STARTTIMES.ROUTES_ID.equal(id))
				.fetchOne();

		create.delete(ROUTES_STARTTIMES).where(ROUTES_STARTTIMES.ROUTES_ID.eq(id)).execute();

		return (Integer) record.getValue(0);
	}

	/**
	 * Returns a list of names of bus stops and stopping points which are used by a specific route
	 *
	 * @param id unique ID of the route
	 * @return ArrayList of Strings containing names of bus stops with stopping points
	 */
	public ArrayList<String> getBusStopNamesUsingRouteId(int id)
	{
		Result<RoutesStopsRecord> routes = create
				.selectFrom(ROUTES_STOPS)
				.where(ROUTES_STOPS.ROUTES_ID.eq(id))
				.groupBy(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID)
				.fetch();

		ArrayList<String> names = new ArrayList<>();
		for (RoutesStopsRecord rec : routes)
		{
			int stopID = rec.getValue(ROUTES_STOPS.BUSSTOPS_ID);
			BusstopsRecord bstop = create
					.selectFrom(BUSSTOPS)
					.where(BUSSTOPS.BUSSTOPS_ID.eq(stopID))
					.fetchOne();
			String nameString = bstop.getName() + " ";

			stopID = rec.getValue(ROUTES_STOPS.BUSSTOPS_STOPPINGPOINTS_ID);
			BusstopsStoppingpointsRecord bstoppoint = create
					.selectFrom(BUSSTOPS_STOPPINGPOINTS)
					.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(stopID))
					.fetchOne();
			nameString += bstoppoint.getName();
			names.add(nameString);
		}
		return(names);
	}

	/**
	 * gets the number of routes with a specific stopping point assigned.
	 *
	 * @param id unique ID of the stopping point
	 * @return number of routes using the stopping point
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

    public StoppingPoint getStoppingPointById(int id)
    {
        BusstopsStoppingpointsRecord spr = create.selectFrom(BUSSTOPS_STOPPINGPOINTS)
                .where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id))
                .fetchOne();

        return new StoppingPoint(id, spr.getName());
    }

    /**
     * Retrieves list of all stopping points together with the names of their respective bus stops.
     *
     * @return ArrayList of stopping point ids with compound name of bus stop name and stopping point name
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
                .fetch();

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
	 */
	public void deleteStoppingPointById(int id)
	{
		create.delete(BUSSTOPS_STOPPINGPOINTS)
					.where(BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_STOPPINGPOINTS_ID.eq(id)).execute();
	}

	/**
	 * Modifies an existing bus stop entry in the database
	 *
	 * @param spoint StoppingPoint object whose entry is to be updated in the database
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
	public void deleteTicket(int id)
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
     * @return Ticket object created from its corresponding entry the database, null if ticket not found
     */
	public Ticket getTicketById(int id)
	{
		Record rec = create.select().from(TICKETS).where(TICKETS.TICKETS_ID.eq(id)).fetchOne();
        if (rec == null) return null;

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
	//  Methods for "Tour"s //
	//////////////////////////

    /**
     * Creates all trips for a given day.
     *
     * @param date date for which tours ought to be generated
     */
    public void createTours(Date date)
    {
		// fetch all route IDs from database
        Result<Record1<Integer>> routes = create.select(ROUTES.ROUTES_ID).from(ROUTES).fetch();

        // Modify given date to represent midnight
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        // Reset hour, minutes, seconds and milliseconds
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

		// get selected day of week in uppercase english format, e.g. MONDAY, TUESDAY, ...
        String dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH).toUpperCase();
        int timestamp = (int) (calendar.getTimeInMillis()/1000);

        create.execute("BEGIN");
		// for every route...
        for (Record1<Integer> route : routes)
        {
			// ... get all start times belonging to selected day of weeek ...
            Result<Record1<Integer>> startingTimes = create
                    .select(ROUTES_STARTTIMES.STARTTIME).from(ROUTES_STARTTIMES)
                    .where(ROUTES_STARTTIMES.DAYOFWEEK.eq(dayOfWeek)).fetch();

			// ... and add each one to the current date to get the timestamp.
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
            create.execute("END");		// TODO: is this supposed to appear INSIDE the for-loop?
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
        Result<ToursRecord> tours = create.selectFrom(TOURS).where(TOURS.EMPLOYEES_ID.eq(id)).orderBy(TOURS.TIMESTAMP).fetch();

        for (ToursRecord t : tours){
            result.add(
                    new Tour(
                            new Date((long) t.getTimestamp()*1000),
                            getRouteById(t.getRoutesId()),
                            getBusById(t.getBusesId()),
                            getEmployeeById(t.getEmployeesId())
                    )
            );
        }

        return result;
    }

	/**
	 * Deletes all tours of a specific route.
	 *
	 * @param id unique ID of the route whose tours to be deleted.
	 * @return number of tours deleted
	 */
	public int deleteToursUsingRoutesId(int id)
	{
        int numOfTours = getNumberOfToursUsingRouteId(id);
		create.delete(TOURS).where(TOURS.ROUTES_ID.eq(id)).execute();
		return numOfTours;
	}

	/**
	 * deletes all tours before today.
	 *
	 * @return number of deleted tours
	 */
	public int purgeTours()
	{
		return (purgeTours(new GregorianCalendar().getTime()));
	}

	/**
	 * deletes all tours before a specific date.
	 *
	 * @return number of deleted tours
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
		int deadline = (int) (calendar.getTimeInMillis()/1000);

		// get number of all tours before selected date
		Record record = create
				.selectCount()
				.from(TOURS)
				.where(TOURS.TIMESTAMP.lessThan(deadline))
				.fetchOne();

		// ... and delete them
		create.delete(TOURS).where(TOURS.TIMESTAMP.lessThan(deadline)).execute();
		return (Integer) record.getValue(0);
	}

	/**
	 * gets the number of tours with a specific bus assigned.
	 *
	 * @param id unique ID of the bus
	 * @return number of tours using the bus
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
	 * deletes all tours with a specific employee assigned.
	 *
	 * @param id unique ID of the employee
	 * @return number of tours deleted
	 */
	public int deleteToursUsingEmployee(int id)
	{
		int numOfTours = getNumberOfToursUsingEmployeeId(id);
		create.delete(TOURS).where(TOURS.EMPLOYEES_ID.eq(id)).execute();
		return (Integer) numOfTours;
	}
}