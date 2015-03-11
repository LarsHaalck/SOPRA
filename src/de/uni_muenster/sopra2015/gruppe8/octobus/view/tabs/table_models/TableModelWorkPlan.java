package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * TableModel for TabWorkPlan table.
 */
public class TableModelWorkPlan extends ExtendedTableModel
{
	public TableModelWorkPlan()
	{
        //Add column-names from enum
		ColumnsWorkPlan[] values = ColumnsWorkPlan.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsWorkPlan.DATE.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsWorkPlan.DATE.toString(), ColumnsWorkPlan.ROUTE.toString(), ColumnsWorkPlan.START.toString(), ColumnsWorkPlan.END.toString(), ColumnsWorkPlan.BUS.toString()};
	}

	@Override
	public Class getColumnClass(int column)
	{
		switch(column)
		{
			case 0:
				return Integer.class;

			case 1:
				return TableDate.class;

			case 2:
			case 3:
			case 4:
				return String.class;

			case 5:
				return Integer.class;

			case 6:
				return String.class;
		}
		return null;
	}
}
