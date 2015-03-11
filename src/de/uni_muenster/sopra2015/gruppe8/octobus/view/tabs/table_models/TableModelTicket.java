package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 04.03.2015.
 */
public class TableModelTicket extends ExtendedTableModel
{
	public TableModelTicket()
	{
		//Add column-names from enum
		ColumnsTicket[] values = ColumnsTicket.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsTicket.NAME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsTicket.NAME.toString()};
	}

	@Override
	public Class getColumnClass(int column)
	{
		switch(column)
		{
			case 0:
				return Integer.class;

			case 1:
				return String.class;

			case 2:
			case 3:
				return Integer.class;
		}
		return null;
	}
}
