package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import java.util.Comparator;

/**
 * Created by Lars on 07.03.2015.
 */
public class TabPrice implements Comparator<TabPrice>, Comparable<TabPrice>
{

	private int euro;
	private int cent;


	private int totalCents;

	public TabPrice(int cents)
	{
		this.euro = cents/100;
		this.cent = cents%100;
		this.totalCents = cents;
	}


	@Override
	public int compareTo(TabPrice o)
	{
		return compare(this, o);
	}

	@Override
	public int compare(TabPrice o1, TabPrice o2)
	{
		return Integer.compare(o1.getTotalCents(), o2.getTotalCents());
	}

	@Override
	public String toString()
	{
		String text = "" + this.euro;

		if(this.cent == 0)
		{
			return text + " €";
		}
		else if(this.cent < 10)
		{
			return text + ",0" + this.cent + " €";
		}
		else
		{
			return text + "," + this.cent + " €";
		}
	}



	public int getEuro()
	{
		return euro;
	}

	public void setEuro(int euro)
	{
		this.euro = euro;
	}

	public int getCent()
	{
		return cent;
	}

	public void setCent(int cent)
	{
		this.cent = cent;
	}

	public int getTotalCents()
	{
		return totalCents;
	}

	public void setTotalCents(int totalCents)
	{
		this.totalCents = totalCents;
	}
}
