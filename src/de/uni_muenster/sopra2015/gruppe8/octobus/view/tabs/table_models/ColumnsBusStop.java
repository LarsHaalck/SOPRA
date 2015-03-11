package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of busStop-table.
 * ATTENTION: Order of enums influence order of columns in table model
 */
public enum ColumnsBusStop
{
	NAME,
	BARRIER_FREE,
	NUMBER_OF_STOPPING_POINT;

	@Override
	public String toString()
	{
		switch (this)
		{
			case NAME:
				return "Name";

			case BARRIER_FREE:
				return "Barrierefrei";

			case NUMBER_OF_STOPPING_POINT:
				return "Anzahl Haltepunkte";
		}
		return "";
	}
}
