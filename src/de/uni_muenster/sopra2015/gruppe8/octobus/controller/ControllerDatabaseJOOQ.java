package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import org.jooq.*;
import org.jooq.impl.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

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

		HashSet<Role> roles = new HashSet<>(Arrays.asList(Role.BUSDRIVER,Role.HR_MANAGER));

		Employee klaus = new Employee("Schwackowiak","Herbert","Daniela-Katzenberger-Allee","48149","Münster",
				new Date(2000000),"0123456789","herbert@octobus.com","herbie","SALZ","HASH","netter Typ!",roles);

		addEmployee(klaus);


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
				BUSES.MODEL,BUSES.NEXTINSPECTIONDATEDUE,BUSES.ARTICULATEDBUS)
				.values(bus.getLicencePlate(),bus.getNumberOfSeats(),bus.getStandingRoom(),bus.getManufacturer(),
						bus.getModel(),2000000,(Boolean) bus.isArticulatedBus())
				.execute();
	}

	public void deleteBus(String plate)
	{
		create.delete(BUSES).where(BUSES.LICENCEPLATE.equal(plate)).execute();
	}

	//////////////////////////
	// Methods for BusStops //
	//////////////////////////

	public void addBusStop(BusStop bstop)
	{
		create.insertInto(BUSSTOPS,BUSSTOPS.NAME,BUSSTOPS.LOCATIONX,BUSSTOPS.LOCATIONY,BUSSTOPS.BARRIERFREE)
				.values(bstop.getName(),bstop.getLocation().getFirst(),bstop.getLocation().getSecond(),
						bstop.isBarrierFree())
				.execute();

		Set<String> stopPoints = bstop.getStoppingPoints();

		for(String sp : stopPoints)
		{
			create.insertInto(BUSSTOPS_STOPPINGPOINTS,
					BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_NAME,
					BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_LOCATIONX,
					BUSSTOPS_STOPPINGPOINTS.BUSSTOPS_LOCATIONY,
					BUSSTOPS_STOPPINGPOINTS.NAME)
					.values(bstop.getName(), bstop.getLocation().getFirst(), bstop.getLocation().getSecond(), sp)
					.execute();
		}
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

	////////////////////////
	// Methods for Routes //
	////////////////////////

	public void addRoute(Route r)
	{
		Result<?> result = create.insertInto(ROUTES,ROUTES.NAME,ROUTES.NOTE,ROUTES.NIGHT)
				.values(r.getName(),r.getNote(),(Boolean) r.isNight())
				.returning(ROUTES.ID)
				.fetch();
		result.get(0);
		System.out.println("" + result);



	}


}
