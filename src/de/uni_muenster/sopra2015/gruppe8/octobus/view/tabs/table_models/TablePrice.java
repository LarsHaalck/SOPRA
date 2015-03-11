package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import java.util.Comparator;

//TODO JavaDoc
/**
 * Created by Lars on 07.03.2015.
 */
public class TablePrice implements Comparator<TablePrice>, Comparable<TablePrice>
{

	private int euro;
	private int cent;


	private int totalCents;

	public TablePrice(int cents)
	{
		this.euro = cents/100;
		this.cent = cents%100;
		this.totalCents = cents;
	}


	@Override
	public int compareTo(TablePrice o)
	{
		return compare(this, o);
	}

	@Override
	public int compare(TablePrice o1, TablePrice o2)
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
