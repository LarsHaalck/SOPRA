package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Lars on 04-Mar-15.
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
