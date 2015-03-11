package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of ticket-table.
 * ATTENTION: Order of enums influence order of columns in table model
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
