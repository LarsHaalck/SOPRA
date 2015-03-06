package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Created by Florian on 27.02.2015.
 */
public class Bus
{
	private int id;					// database-internal id. is set when object is added to database
	private String licencePlate;
	private int numberOfSeats;
	private int standingRoom;
	private String manufacturer;
	private String model;
	private Date nextInspectionDue;
	private boolean articulatedBus;

	public Bus()
	{

	}

	public Bus(String licencePlate, int numberOfSeats, int standingRoom, String manufacturer, String model,
			   Date nextInspectionDue, boolean articulatedBus)
	{
		this.licencePlate = licencePlate;
		this.numberOfSeats = numberOfSeats;
		this.standingRoom = standingRoom;
		this.manufacturer = manufacturer;
		this.model = model;
		this.nextInspectionDue = nextInspectionDue;
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

	public int getStandingRoom()
	{
		return standingRoom;
	}

	public void setStandingRoom(int standingRoom)
	{
		this.standingRoom = standingRoom;
	}

	public String getManufacturer()
	{
		return manufacturer;
	}

	public void setManufacturer(String manufacturer)
	{
		this.manufacturer = manufacturer;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}

	public Date getNextInspectionDue()
	{
		return nextInspectionDue;
	}

	public void setNextInspectionDue(Date nextInspectionDue)
	{
		this.nextInspectionDue = nextInspectionDue;
	}

	public boolean isArticulatedBus()
	{
		return articulatedBus;
	}

	public void setArticulatedBus(boolean articulatedBus)
	{
		this.articulatedBus = articulatedBus;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setLicencePlate(String licencePlate)
	{
		this.licencePlate = licencePlate;
	}
}
