package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 10.03.2015.
 */
public enum ColumnsSchedule
{
	LINE,
	TIME,
	DURATION,
	BUS,
	BUS_DRIVER;

	@Override
	public String toString()
	{
		switch(this)
		{
			case LINE:
				return "Linie";

			case TIME:
				return "Startzeit";

			case DURATION:
				return "Dauer";

			case BUS:
				return "Bus";

			case BUS_DRIVER:
				return "Busfahrer";
		}

		return "";
	}
}
