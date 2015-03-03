package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import static de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Tables.*;
import static org.jooq.impl.DSL.*;

import java.sql.*;

import org.jooq.*;
import org.jooq.impl.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Michael Biech
 */
public class ControllerDatabaseJOOQ
{

    public void run() throws ClassNotFoundException
    {
        Class.forName("org.sqlite.JDBC");

        String url = "jdbc:sqlite:test.db";

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
        }
    }

}
