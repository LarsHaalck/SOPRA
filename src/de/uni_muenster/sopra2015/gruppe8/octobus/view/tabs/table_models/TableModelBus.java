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
		data = new Object[][]{
				{11, "MS-OB-482", "MAN", "Lion's City C LE", "Normal", new Integer(44),
						new Integer(82), "25.02.2016"},
				{22, "MS-OB-7767", "MAN", "Lion's City G", "Gelenk", new Integer(51),
						new Integer(110), "27.10.2016"},
				{33, "MS-OB-546", "MAN", "Lion's City M", "Klein", new Integer(29),
						new Integer(48), "02.02.2015"}
		};
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsBus.LICENCE_PLATE.toString(), ColumnsBus.MANUFACTURER.toString(), ColumnsBus.TYPE.toString()};
	}
}