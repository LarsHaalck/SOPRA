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
import java.util.Date;

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

		/*
		BusStop coesfeld = new BusStop("Coesfelder Kreuz",new Tuple<>(20,40),new HashSet<>(Arrays.asList("A","B","C")), false);
		BusStop wilhelm = new BusStop("Wilhelmstraße ",new Tuple<>(30,60),new HashSet<>(Arrays.asList("A","B")),true);

		addBusStop(coesfeld);
		addBusStop(wilhelm); */

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

			// TODO: zu Ende bringen!
//			for (Record s : stopsRecords)
//			{
//				String stopName =
//				stops.add(new Tuple<BusStop,Integer>());		//.. and put them into a HashSet
//			}

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

	////////////////////////
	//	Methods for Tours //
	////////////////////////

	// not yet implemented
}
