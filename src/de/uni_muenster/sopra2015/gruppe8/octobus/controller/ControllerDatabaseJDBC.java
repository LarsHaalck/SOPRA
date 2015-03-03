package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author Michael Biech
 */
public class ControllerDatabaseJDBC
{

    public void run() throws ClassNotFoundException{
        // Load JDBC driver
        Class.forName("org.sqlite.JDBC");

        Connection connection = null;
        try
        {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:octobase.sqlite3");
            //Statement statement = connection.createStatement();
            //statement.setQueryTimeout(5);  // set timeout to 30 sec.

            /*statement.executeUpdate("drop table if exists person");
            statement.executeUpdate("create table person (id integer, name string)");
            statement.executeUpdate("insert into person values(1, 'leo')");
            statement.executeUpdate("insert into person values(2, 'yui')");*/
            //ResultSet rs = statement.executeQuery("SELECT * FROM busStops");
            /*ResultSet rs = statement.executeQuery(
                    "SELECT a.name, locationX, locationY FROM busStops as a " +
                    "JOIN busStops_stoppingPoints as b " +
                    "ON a.name = b.busStops_name " +
                    "AND b.busStops_locationX = a.locationX " +
                    "AND b.busStops_locationY = a.locationY;"
            );*/

            Bus bus = new Bus("MS-A 17", 17, 20, "MAN", "Ultrabus 27", Calendar.getInstance().getTime() , true);

            /* PreparedStatement statement = connection.prepareStatement(
                    "insert into buses ("
                    + bus.getLicencePlate() + " "
                    + bus.getNumberOfSeats() + " "
                    + bus.getStandingRoom() + " "
                    + bus.getManufacturer() + " "
                    + bus.getModel() + " "
                    + bus.getNextInspectionDue().getTime()/1000 + " "
                    + bus.isArticulatedBus() + " "
                    + ")"
            );*/

            PreparedStatement statement = connection.prepareStatement(
                    "insert into buses ("
                            + bus.getLicencePlate() + " "
                            + bus.getNumberOfSeats() + " "
                            + bus.getStandingRoom() + " "
                            + bus.getManufacturer() + " "
                            + bus.getModel() + " "
                            + bus.getNextInspectionDue().getTime()/1000 + " "
                            + bus.isArticulatedBus() + " "
                            + ")"
            );
            statement.executeUpdate();

            /*while(rs.next())
            {
                // read the result set
                System.out.println("name = " + rs.getString("name"));
                System.out.println("X = " + rs.getInt("locationX"));
                System.out.println("Y = " + rs.getInt("locationY"));
            }*/
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        }
        finally
        {
            try
            {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e)
            {
                // connection close failed.
                System.err.println(e);
            }
        }
    }
}
