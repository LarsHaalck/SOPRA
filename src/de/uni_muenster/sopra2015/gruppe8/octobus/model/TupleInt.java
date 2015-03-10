package de.uni_muenster.sopra2015.gruppe8.octobus.model;


/**
 * Tuple specialized for Int,Int for usage in HashSets
 */
public class TupleInt extends Tuple<Integer, Integer>
{
	int first;
	int second;

	public TupleInt(int first, int second)
	{
		super(first, second);
		this.first = first;
		this.second = second;
	}

	@Override
	public int hashCode()
	{
		return Integer.hashCode(first) ^ Integer.hashCode(second);
	}
}
