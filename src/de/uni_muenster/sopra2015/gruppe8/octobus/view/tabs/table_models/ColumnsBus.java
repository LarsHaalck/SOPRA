package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of bus-table.
 * ATTENTION: Order of enums influence order of columns in table model
 */
public enum ColumnsBus
{
	LICENCE_PLATE,
	MANUFACTURER,
	MODEL,
	ARTICULATED_BUS,
	NUMBER_OF_SEATS,
	STANDING_ROOM,
	NEXT_INSPECTION,
	NEXT_INSPECTION_STATUS;

	@Override
	public String toString()
	{
		switch(this)
		{
			case LICENCE_PLATE:
				return "Kennzeichen";
			case MANUFACTURER:
				return "Hersteller";
			case MODEL:
				return "Modell";
			case ARTICULATED_BUS:
				return "Gelenkbus";
			case NUMBER_OF_SEATS:
				return "Sitzplätze";
			case STANDING_ROOM:
				return "Stehplätze";
			case NEXT_INSPECTION:
				return "Nächste Inspektion";
			case NEXT_INSPECTION_STATUS:
				return "";
		}
		return "";
	}
}
