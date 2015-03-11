package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * TableModel for TabBus table.
 */
public class TableModelBus extends ExtendedTableModel
{
	public TableModelBus()
	{
		//Add column-names from enum
		ColumnsBus[] values = ColumnsBus.values();
		columnNames = new String[values.length];
		for(int i=0; i<columnNames.length; i++)
			columnNames[i] = values[i].toString();
		data = new Object[][]{};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsBus.LICENCE_PLATE.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsBus.LICENCE_PLATE.toString(), ColumnsBus.MANUFACTURER.toString()};
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

			case 5:
			case 6:
				return Integer.class;

			case 7:
				return TableDate.class;
		}
		return null;
	}
}