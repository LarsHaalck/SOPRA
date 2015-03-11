package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of route-table.
 * ATTENTION: Order of enums influence order of columns in table model
 */
public enum ColumnsRoute
{
	NAME,
	START,
	END,
	NIGHTLINE;

	@Override
	public String toString()
	{
		switch(this)
		{
			case NAME:
				return "Name";
			case START:
				return "Starthaltestelle";
			case END:
				return "Endhaltestelle";
			case NIGHTLINE:
				return "Nachtlinie";
		}
		return "";
	}
}
