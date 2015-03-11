package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Every column of employee-table.
 * ATTENTION: Order of enums influence order of columns in table model
 */
public enum ColumnsEmployee
{
	NAME,
	FIRST_NAME,
	ADDRESS,
	ZIP_CODE,
	CITY,
	DATE_OF_BIRTH,
	EMAIL,
	USERNAME;

	@Override
	public String toString()
	{
		switch(this)
		{
			case NAME:
				return "Name";
			case FIRST_NAME:
				return "Vorname";
			case ADDRESS:
				return "Adresse";
			case ZIP_CODE:
				return "PLZ";
			case CITY:
				return "Stadt";
			case DATE_OF_BIRTH:
				return "Geburtstag";
			case EMAIL:
				return "Email-Adr.";
			case USERNAME:
				return "Username";
		}
		return "";
	}
}