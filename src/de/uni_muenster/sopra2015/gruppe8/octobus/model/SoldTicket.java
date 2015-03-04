package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;

/**
 * Created by Florian on 27.02.2015.
 */
public class SoldTicket
{
	private int id;		// database-internal id. is set when object is added to database
	private String name;
	private Date date;
	private int price;

	public SoldTicket(int id, String name, Date date, int price)
	{
		this.id = id;
		this.name = name;
		this.date = date;
		this.price = price;
	}

	public int getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public int getPrice()
	{
		return price;
	}

	public void setPrice(int price)
	{
		this.price = price;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
