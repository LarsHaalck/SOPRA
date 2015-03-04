package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * Created by Florian on 04.03.2015.
 */
public class TableModelLine extends ExtendedTableModel
{
	public TableModelLine()
	{
		//Add column-names from enum
		ColumnsLine[] values = ColumnsLine.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsLine.NAME.toString(), ColumnsLine.START.toString(), ColumnsLine.END.toString()};
	}
}
