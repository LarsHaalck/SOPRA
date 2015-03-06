package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 *
 */
public class TableModelBusStop extends ExtendedTableModel
{
	public TableModelBusStop()
	{
		//Add column-names from enum
		ColumnsBusStop[] values = ColumnsBusStop.values();
		columnNames = new String[values.length];
		for(int i=0; i<columnNames.length; i++)
			columnNames[i] = values[i].toString();
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsBusStop.NAME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsBusStop.NAME.toString()};
	}
}
