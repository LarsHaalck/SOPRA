package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 06.03.2015.
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
		return new String[]{ColumnsWorkPlan.DATE.toString(), ColumnsWorkPlan.START.toString(), ColumnsWorkPlan.END.toString()};
	}

	@Override
	public Class getColumnClass(int column)
	{
		switch (column)
		{
			case 0:
				return Integer.class;

			case 1:
				return String.class;

			case 2:
				return TableDate.class;

			case 3:
				return String.class;

			case 4:
				return String.class;
		}
		return null;
	}
}
