package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;

/**
 * Created by Florian on 03.03.2015.
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
		return new String[]{ColumnsBus.LICENCE_PLATE.toString(), ColumnsBus.MANUFACTURER.toString(), ColumnsBus.ARTICULATED_BUS.toString()};
	}
}