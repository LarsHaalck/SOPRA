package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Created by Joshua on 27.02.2015.
 */
public class Ticket
{
    private int id;
    private String name;
    private int price;
    private int numPassengers;
    private String description;

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
}
