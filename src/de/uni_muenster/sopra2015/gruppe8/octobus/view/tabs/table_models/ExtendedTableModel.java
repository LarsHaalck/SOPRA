package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.table.TableModel;

/**
 * Class extends normal table to have methods for getting an array of specified columns and get index by column by name
 */
public abstract class ExtendedTableModel implements TableModel
{
	public abstract String[] getRefineableColumns();
	public abstract int getColumnIndex(String column);

	public int[] getRefineableColumnsIDs()
	{
		int[] ids = new int[getRefineableColumns().length];
		String[] strings = getRefineableColumns();
		for(int i=0; i<ids.length; i++)
			ids[i] = getColumnIndex(strings[i]);
		return ids;
	}
}
