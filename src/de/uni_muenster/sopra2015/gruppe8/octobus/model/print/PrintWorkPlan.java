package de.uni_muenster.sopra2015.gruppe8.octobus.model.print;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Florian on 06.03.2015.
 */
public class PrintWorkPlan
{
	private String employeeName;
	private ArrayList<Quadruple<String, Date, Integer, String>> tours;

	public PrintWorkPlan(String employeeName, ArrayList<Quadruple<String, Date, Integer, String>> tours)
	{
		this.employeeName = employeeName;
		this.tours = tours;
	}

	public String getEmployeeName()
	{
		return employeeName;
	}

	public ArrayList<Quadruple<String, Date, Integer, String>> getTours()
	{
		return tours;
	}

	public int numTours()
	{
		return tours.size();
	}
}
