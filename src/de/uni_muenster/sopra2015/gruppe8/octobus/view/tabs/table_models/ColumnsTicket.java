package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Lars on 04-Mar-15.
 */
public enum ColumnsTicket
{
	NAME,
	PRICE,
	NUM_PASSENGERS;

	@Override
	public String toString()
	{
		switch(this)
		{
			case NAME:
				return "Name";

			case PRICE:
				return "Preis";

			case NUM_PASSENGERS:
				return "Fahrgast-Anzahl";
		}

		return "";
	}
}
