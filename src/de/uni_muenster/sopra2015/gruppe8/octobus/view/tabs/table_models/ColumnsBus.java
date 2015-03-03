package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 03.03.2015.
 */
public enum ColumnsBus
{
	LICENCE_PLATE,
	MANUFACTURER,
	MODEL,
	TYPE,
	NUMBER_OF_SEATS,
	STANDING_ROOM,
	NEXT_INSPECTION;

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
			case TYPE:
				return "Typ";
			case NUMBER_OF_SEATS:
				return "Sitzplätze";
			case STANDING_ROOM:
				return "Stehplätze";
			case NEXT_INSPECTION:
				return "Nächste Inspektion";
		}
		return "";
	}
}
