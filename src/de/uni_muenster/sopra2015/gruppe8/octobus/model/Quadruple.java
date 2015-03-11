package de.uni_muenster.sopra2015.gruppe8.octobus.model;

/**
 * Used to group four objects.
 */
public class Quadruple<W, X, Y, Z>
{
	private W first;
	private X second;
	private Y third;
	private Z fourth;

	public Quadruple(W first, X second, Y third, Z fourth)
	{
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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

	public Z getFourth()
	{
		return fourth;
	}

	public void setFourth(Z fourth)
	{
		this.fourth = fourth;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Quadruple quadruple = (Quadruple) o;

		if (first != null ? !first.equals(quadruple.first) : quadruple.first != null) return false;
		if (fourth != null ? !fourth.equals(quadruple.fourth) : quadruple.fourth != null) return false;
		if (second != null ? !second.equals(quadruple.second) : quadruple.second != null) return false;
		if (third != null ? !third.equals(quadruple.third) : quadruple.third != null) return false;

		return true;
	}

	@Override
	public String toString()
	{
		return ("(" + first + "," + second + "," + third + "," + fourth + ")");
	}
}
