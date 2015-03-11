package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of workPlan-table.
 * ATTENTION: Order of enums influence order of columns in table model
 */
public enum ColumnsWorkPlan
{
	DATE,
	ROUTE,
	START,
	END,
	DURATION,
	BUS;

	@Override
	public String toString()
	{
		switch(this)
		{
			case DATE:
				return "Zeit";

			case ROUTE:
				return "Linie";

			case START:
				return "Start-Haltestelle";

			case END:
				return "End-Haltestelle";

			case DURATION:
				return "Dauer";

			case BUS:
				return "Bus";
		}
		return "";
	}
}
