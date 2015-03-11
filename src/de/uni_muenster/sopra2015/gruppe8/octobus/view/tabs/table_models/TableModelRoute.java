package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * TableModel for TabRoute table.
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
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsRoute.NAME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsRoute.NAME.toString(), ColumnsRoute.START.toString(), ColumnsRoute.END.toString()};
	}

	@Override
	public Class getColumnClass(int column)
	{
		switch(column)
		{
			case 0:
				return Integer.class;

			case 1:
			case 2:
			case 3:
				return String.class;

			case 4:
				return Boolean.class;
		}
		return null;
	}
}
