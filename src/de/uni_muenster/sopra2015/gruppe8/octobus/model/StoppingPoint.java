package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * @author Michael Biech
 */
public class StoppingPoint
{

    private int id;
    private String name;

    public StoppingPoint(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
