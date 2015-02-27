package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Created by Florian on 27.02.2015.
 */
public class Tuple<X,Y>
{
	private X first;
	private Y second;

	public Tuple(X first, Y second)
	{
		this.first = first;
		this.second = second;
	}

	public X getFirst()
	{
		return first;
	}

	public Y getSecond()
	{
		return second;
	}
}
