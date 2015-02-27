package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Created by Florian on 27.02.2015.
 */
public class Bus
{
	private String licencePlate;
	private int numberOfSeats;
	private int standingRooms;
	private String manufactuer;
	private String model;
	private Date nexInspectionDue;
	private boolean articulatedBus;

	public Bus(String licencePlate, int numberOfSeats, int standingRooms, String manufactuer, String model, Date nexInspectionDue, boolean articulatedBus)
	{
		this.licencePlate = licencePlate;
		this.numberOfSeats = numberOfSeats;
		this.standingRooms = standingRooms;
		this.manufactuer = manufactuer;
		this.model = model;
		this.nexInspectionDue = nexInspectionDue;
		this.articulatedBus = articulatedBus;
	}

	public String getLicencePlate()
	{
		return licencePlate;
	}

	public int getNumberOfSeats()
	{
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats)
	{
		this.numberOfSeats = numberOfSeats;
	}

	public int getStandingRooms()
	{
		return standingRooms;
	}

	public void setStandingRooms(int standingRooms)
	{
		this.standingRooms = standingRooms;
	}

	public String getManufactuer()
	{
		return manufactuer;
	}

	public void setManufactuer(String manufactuer)
	{
		this.manufactuer = manufactuer;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public Date getNexInspectionDue()
	{
		return nexInspectionDue;
	}

	public void setNexInspectionDue(Date nexInspectionDue)
	{
		this.nexInspectionDue = nexInspectionDue;
	}

	public boolean isArticulatedBus()
	{
		return articulatedBus;
	}

	public void setArticulatedBus(boolean articulatedBus)
	{
		this.articulatedBus = articulatedBus;
	}
}
