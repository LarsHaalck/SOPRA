package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Created by Florian on 10.03.2015.
 */
public class TupleIntString extends Tuple<Integer, String>
{
	public TupleIntString(Integer first, String second)
	{
		super(first, second);
	}

	public String toString()
	{
		return getSecond().toString();
	}
}
