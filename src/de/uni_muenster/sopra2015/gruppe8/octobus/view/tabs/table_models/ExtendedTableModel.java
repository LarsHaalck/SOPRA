package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.table.TableModel;

/**
 * Created by Florian on 03.03.2015.
 */
public abstract class ExtendedTableModel implements TableModel
{
	public abstract String[] getRefineableColumns();
	public abstract int getColumnIndex(String column);
}
