package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Used to group three objects.
 */
public class Triple<W,X,Y>
{
	private W first;
	private X second;
	private Y third;

	public Triple(W first, X second, Y third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public W getFirst()
	{
		return first;
	}

	public void setFirst(W first)
	{
		this.first = first;
	}

	public X getSecond()
	{
		return second;
	}

	public void setSecond(X second)
	{
		this.second = second;
	}

	public Y getThird()
	{
		return third;
	}

	public void setThird(Y third)
	{
		this.third = third;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Triple triple = (Triple) o;

		if (first != null ? !first.equals(triple.first) : triple.first != null) return false;
		if (second != null ? !second.equals(triple.second) : triple.second != null) return false;
		if (third != null ? !third.equals(triple.third) : triple.third != null) return false;

		return true;
	}

	@Override
	public String toString()
	{
		return ("(" + first + "," + second + "," + third + ")");
	}
}
