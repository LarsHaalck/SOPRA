package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Used to store the data of a ticket.
 */
public class Ticket
{
    private int id;			// database-internal id. is set when object is added to database
    private String name;
    private int price;
    private int numPassengers;
    private String description;

	public Ticket()
	{

	}

    public Ticket(int price, String name, int numPassengers, String description, int id)
    {
        this.price = price;
        this.name = name;
        this.numPassengers = numPassengers;
        this.description = description;
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public int getNumPassengers()
    {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers)
    {
        this.numPassengers = numPassengers;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

	public void setId(int id)
	{
		this.id = id;
	}
}
