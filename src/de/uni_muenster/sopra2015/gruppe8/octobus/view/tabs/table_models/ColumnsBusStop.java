package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Lars on 04-Mar-15.
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
