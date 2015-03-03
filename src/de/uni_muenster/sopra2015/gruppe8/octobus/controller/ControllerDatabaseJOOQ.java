package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusstopsRecord;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.RoutesRecord;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import org.jooq.*;
import org.jooq.impl.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.util.*;

/**
 * @author Michael Biech
 */
public class ControllerDatabaseJOOQ
{

	String url = "jdbc:sqlite:test.db";
	Connection conn;
	DSLContext create;

    public void run()
    {
        /* Class.forName("org.sqlite.JDBC");


        // Connection is the only JDBC resource that we need
        // PreparedStatement and ResultSet are handled by jOOQ, internally
        try (Connection conn = DriverManager.getConnection(url)) {

            DSLContext create = DSL.using(conn, SQLDialect.MYSQL);
            Result<Record> result = create.select().from(BUSES).fetch();

            for (Record r : result) {
                String licensePlate = r.getValue(BUSES.LICENCEPLATE);
                Integer numberOfSeats = r.getValue(BUSES.NUMBEROFSEATS);
                Integer nextInspectionDue = r.getValue(BUSES.NEXTINSPECTIONDATEDUE);

                System.out.println("ID: " + licensePlate + " first name: " + numberOfSeats + " last name: " + nextInspectionDue);
            }

        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        } */

		openDB();

		BusStop coesfeld = new BusStop("Coesfelder Kreuz",new Tuple<>(20,40),new HashSet<>(Arrays.asList("A","B","C")), false);
		BusStop wilhelm = new BusStop("Wilhelmstraße ",new Tuple<>(30,60),new HashSet<>(Arrays.asList("A","B")),true);

		addBusStop(coesfeld);
		addBusStop(wilhelm);

		/* LinkedList<Tuple<BusStop,Integer>> stops = new LinkedList<>();
		stops.add(new Tuple<>(coesfeld,0));
		stops.add(new Tuple<>(wilhelm,5));

		HashMap<DayOfWeek,LinkedList<Integer>> startTimes = new HashMap<>();
		LinkedList<Integer> startTimesTimes = new LinkedList<>();
		startTimesTimes.add(480);
		startTimesTimes.add(600);
		startTimesTimes.add(720);

		startTimes.put(DayOfWeek.MONDAY, startTimesTimes);
		startTimes.put(DayOfWeek.FRIDAY,startTimesTimes);

		Route linie = new Route("Linie Test","Testlinie",stops,false,startTimes);
		addRoute(linie); */


		/* HashSet<Role> roles = new HashSet<>(Arrays.asList(Role.BUSDRIVER,Role.HR_MANAGER));

		Employee klaus = new Employee("Schwackowiak","Herbert","Daniela-Katzenberger-Allee","48149","Münster",
				new Date(2000000),"0123456789","herbert@octobus.com","herbie","SALZ","HASH","netter Typ!",roles);

		addEmployee(klaus); */


		/* Set<String> spoints = new HashSet<>();
		spoints.add("A");
		spoints.add("B");
		spoints.add("C");

		BusStop teststop = new BusStop("Coesfelder Kreuz",new Tuple<>(20,40),spoints,false);

		addBusStop(teststop);
		*/
    }

	public void openDB()
	{
		try
		{
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

	public void addBus(Bus bus)
	{
		create.insertInto(BUSES,BUSES.LICENCEPLATE,BUSES.NUMBEROFSEATS,BUSES.STANDINGROOM,BUSES.MANUFACTURER,
				BUSES.MODEL,BUSES.NEXTINSPECTIONDUE,BUSES.ARTICULATEDBUS)
				.values(bus.getLicencePlate(),bus.getNumberOfSeats(),bus.getStandingRoom(),bus.getManufacturer(),
						bus.getModel(),(int) bus.getNextInspectionDue().getTime()/1000,(Boolean) bus.isArticulatedBus())
				.execute();
	}

	public void deleteBus(String plate)
	{
		create.delete(BUSES).where(BUSES.LICENCEPLATE.equal(plate)).execute();
	}

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

	//////////////////////////
	// Methods for BusStops //
	//////////////////////////

	public void addBusStop(BusStop bstop)
	{
		BusstopsRecord newStop = create.insertInto(BUSSTOPS,BUSSTOPS.NAME,BUSSTOPS.LOCATIONX,BUSSTOPS.LOCATIONY,
				BUSSTOPS.BARRIERFREE)
				.values(bstop.getName(), bstop.getLocation().getFirst(), bstop.getLocation().getSecond(),
						bstop.isBarrierFree())
				.returning(BUSSTOPS.BUSSTOPS_ID)
				.fetchOne();

		HashSet<String> points = bstop.getStoppingPoints();

		for (String s : points)
		{
			create.insertInto(BUSSTOPS_STOPPINGPOINTS,BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_ID,BUSSTOPS_STOPPINGPOINTS.NAME)
				.values(newStop.getBusstopsId(),s)
				.execute();
		}
	}

	public void deleteBusStop(int id)
	{
		create.delete(BUSSTOPS).where(BUSSTOPS.BUSSTOPS_ID.equal(id)).execute();
	}

	public void modifyBusStop(int id, BusStop bstop)
	{
		create.update(BUSSTOPS)
				.set(BUSSTOPS.NAME,bstop.getName())
				.set(BUSSTOPS.LOCATIONX,bstop.getLocation().getFirst())
				.set(BUSSTOPS.LOCATIONY,bstop.getLocation().getSecond())
				.set(BUSSTOPS.BARRIERFREE,bstop.isBarrierFree())
				.execute();
	}


	///////////////////////////
	// Methods for Employees //
	///////////////////////////

	public void addEmployee(Employee emp)
	{
		create.insertInto(EMPLOYEES,EMPLOYEES.NAME,EMPLOYEES.FIRSTNAME,EMPLOYEES.ADDRESS,EMPLOYEES.ZIPCODE,
				EMPLOYEES.CITY,EMPLOYEES.DATEOFBIRTH,EMPLOYEES.PHONE,EMPLOYEES.EMAIL,EMPLOYEES.USERNAME,
				EMPLOYEES.SALT,EMPLOYEES.PASSWORD,EMPLOYEES.NOTE,EMPLOYEES.ISBUSDRIVER,EMPLOYEES.ISNETWORK_PLANNER,
				EMPLOYEES.ISTICKET_PLANNER,EMPLOYEES.ISHR_MANAGER,EMPLOYEES.ISSCHEDULE_MANAGER)
				.values(emp.getName(),emp.getFirstName(),emp.getAddress(),emp.getZipCode(),emp.getCity(),
						(int) emp.getDateOfBirth().getTime(),emp.getPhone(),emp.getEmail(),emp.getUsername(),
						emp.getSalt(),emp.getPassword(),emp.getNote(),emp.isRole(Role.BUSDRIVER),
						emp.isRole(Role.NETWORK_PLANNER),emp.isRole(Role.TICKET_PLANNER),emp.isRole(Role.HR_MANAGER),
						emp.isRole(Role.SCHEDULE_MANAGER))
				.execute();
	}

	public void deleteEmployee(String username)
	{
		create.delete(EMPLOYEES).where(EMPLOYEES.USERNAME.equal(username)).execute();
	}

	public void deleteEmployee(Employee emp)
	{
		deleteEmployee(emp.getUsername());
	}

	public void deleteEmployee(int id)
	{
		create.delete(EMPLOYEES).where(EMPLOYEES.EMPLOYEES_ID.equal(id)).execute();
	}

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
				.set(EMPLOYEES.ISSCHEDULE_MANAGER,emp.isRole(Role.SCHEDULE_MANAGER))
				.execute();
	}

	////////////////////////
	// Methods for Routes //
	////////////////////////

	public void addRoute(Route r)
	{
		RoutesRecord newRoute = create.insertInto(ROUTES,ROUTES.NAME,ROUTES.NOTE,ROUTES.NIGHT)
				.values(r.getName(),r.getNote(),(Boolean) r.isNight())
				.returning(ROUTES.ROUTES_ID)
				.fetchOne();

		LinkedList<Tuple<BusStop,Integer>>  stops = r.getStops();
		for (Tuple<BusStop,Integer> pair : stops)
		{
			create.insertInto(ROUTES_STOPS, ROUTES_STOPS.ROUTES_ID, ROUTES_STOPS.BUSSTOPS_ID,
					ROUTES_STOPS.TIMETOPREVIOUS)
					.values(newRoute.getRoutesId(), pair.getFirst().getId(), pair.getSecond())
					.execute();
		}

		HashMap<DayOfWeek,LinkedList<Integer>> times = r.getStartTimes();
		for (DayOfWeek day : times.keySet())
		{
			for (Integer time : times.get(day))
			{
				create.insertInto(ROUTES_STARTTIMES,ROUTES_STARTTIMES.ROUTES_ID,ROUTES_STARTTIMES.DAYOFWEEK,
					ROUTES_STARTTIMES.STARTTIME)
					.values(newRoute.getRoutesId(), day.toString(), time)
					.execute();
			}
		}
	}

	public void deleteRoute(int id)
	{
		create.delete(ROUTES).where(ROUTES.ROUTES_ID.equal(id));
	}

	/////////////////////////////
	// Methods for soldTickets //
	/////////////////////////////

	public void addSoldTicket(SoldTicket sold)
	{
		create.insertInto(SOLDTICKETS, SOLDTICKETS.NAME, SOLDTICKETS.TIMESTAMP, SOLDTICKETS.PRICE)
			.values(sold.getName(),(int) sold.getDate().getTime()/1000,sold.getPrice())
			.execute();
	}

	public void deleteSoldTicket(int id)
	{
		create.delete(SOLDTICKETS).where(SOLDTICKETS.SOLDTICKETS_ID.equal(id)).execute();
	}

	public void modifySoldTicket(int id, SoldTicket sold)
	{
		create.update(SOLDTICKETS)
				.set(SOLDTICKETS.NAME, sold.getName())
				.set(SOLDTICKETS.TIMESTAMP,(int) sold.getDate().getTime()/1000)
				.set(SOLDTICKETS.PRICE,sold.getPrice())
				.execute();
	}


	/////////////////////////
	// Methods for Tickets //
	/////////////////////////

	public void addTickets(Ticket tick)
	{
		create.insertInto(TICKETS,TICKETS.NAME,TICKETS.PRICE,TICKETS.NUMPASSENGERS,TICKETS.DESCRIPTION)
				.values(tick.getName(),tick.getPrice(),tick.getNumPassengers(),tick.getDescription())
				.execute();
	}

	public void deleteTickets(int id)
	{
		create.delete(TICKETS).where(TICKETS.TICKETS_ID.equal(id)).execute();
	}

	public void modifyTickets(int id, Ticket tick)
	{
		create.update(TICKETS)
				.set(TICKETS.NAME, tick.getName())
				.set(TICKETS.PRICE, tick.getPrice())
				.set(TICKETS.NUMPASSENGERS, tick.getNumPassengers())
				.set(TICKETS.DESCRIPTION, tick.getDescription())
				.execute();
	}

	////////////////////////
	//	Methods for Tours //
	////////////////////////

	// not yet implemented
}
