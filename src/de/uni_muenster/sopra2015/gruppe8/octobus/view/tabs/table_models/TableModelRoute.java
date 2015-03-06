package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 04.03.2015.
 */
public class TableModelRoute extends ExtendedTableModel
{
	public TableModelRoute()
	{
		//Add column-names from enum
		ColumnsRoute[] values = ColumnsRoute.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsRoute.NAME.toString(), ColumnsRoute.START.toString(), ColumnsRoute.END.toString()};
	}
}
