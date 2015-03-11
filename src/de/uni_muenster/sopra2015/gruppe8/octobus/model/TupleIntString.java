package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Used to group an id and a String for combo boxes.
 */
public class TupleIntString
{
	private int id;
	private String name;

	public TupleIntString(Integer first, String second)
	{
		id = first;
		name = second;
	}

	public int getFirst()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSecond()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return name;
	}
}
