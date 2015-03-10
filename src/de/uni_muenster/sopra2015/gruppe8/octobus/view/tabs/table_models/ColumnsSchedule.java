package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 10.03.2015.
 */
public enum ColumnsSchedule
{
	LINE,
	TIME,
	START,
	END,
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

			case START:
				return "Starthaltestelle";

			case END:
				return "Zielhaltestelle";

			case BUS:
				return "Bus";

			case BUS_DRIVER:
				return "Busfahrer";
		}

		return "";
	}
}
