package de.uni_muenster.sopra2015.gruppe8.octobus.model;


public class Quintuple<V, W, X, Y, Z>
{
	private V first;
	private W second;
	private X third;
	private Y fourth;
	private Z fifth;

	public Quintuple(V first, W second, X third, Y fourth, Z fifth)
	{
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;
	}

	public V getFirst()
	{
		return first;
	}

	public void setFirst(V first)
	{
		this.first = first;
	}

	public W getSecond()
	{
		return second;
	}

	public void setSecond(W second)
	{
		this.second = second;
	}

	public X getThird()
	{
		return third;
	}

	public void setThird(X third)
	{
		this.third = third;
	}

	public Y getFourth()
	{
		return fourth;
	}

	public void setFourth(Y fourth)
	{
		this.fourth = fourth;
	}

	public Z getFifth()
	{
		return fifth;
	}

	public void setFifth(Z fifth)
	{
		this.fifth = fifth;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Quintuple quintuple = (Quintuple) o;

		if (first != null ? !first.equals(quintuple.first) : quintuple.first != null) return false;
		if (fourth != null ? !fourth.equals(quintuple.fourth) : quintuple.fourth != null) return false;
		if (second != null ? !second.equals(quintuple.second) : quintuple.second != null) return false;
		if (third != null ? !third.equals(quintuple.third) : quintuple.third != null) return false;
		if (fifth != null ? !fifth.equals(quintuple.fifth) : quintuple.fifth != null) return false;

		return true;
	}

	@Override
	public String toString()
	{
		return ("(" + first + "," + second + "," + third + "," + fourth + "," + fifth +")");
	}
}
