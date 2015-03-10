package de.uni_muenster.sopra2015.gruppe8.octobus.model;


/**
 * Tuple specialized for Int,Int for usage in HashSets
 */
public class TupleInt extends Tuple<Integer, Integer>
{
	public TupleInt(Integer first, Integer second)
	{
		super(first, second);
	}

	@Override
	public int hashCode()
	{
		return Integer.hashCode(first) ^ Integer.hashCode(second);
	}
}
