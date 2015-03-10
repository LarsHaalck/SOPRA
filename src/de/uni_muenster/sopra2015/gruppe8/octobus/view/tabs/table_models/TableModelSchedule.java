package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 10.03.2015.
 */
public class TableModelSchedule extends ExtendedTableModel
{
	public TableModelSchedule()
	{
		//Add column-names from enum
		ColumnsSchedule[] values = ColumnsSchedule.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsSchedule.TIME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsSchedule.LINE.toString(), ColumnsSchedule.BUS.toString(), ColumnsSchedule.BUS_DRIVER.toString()};
	}
}
