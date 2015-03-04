package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;

/**
 * We only need this to catch errors while parsing generics.
 * Hopefully this isn't used anytime.
 */
public class DefaultExtendedTableModel extends ExtendedTableModel
{
	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{};
	}

	@Override
	public int getRowCount()
	{
		return 0;
	}

	@Override
	public int getShownColumnCount()
	{
		return 0;
	}

	@Override
	public String getShownColumnName(int columnIndex)
	{
		return null;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex)
	{
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex)
	{

	}

	@Override
	public void addTableModelListener(TableModelListener l)
	{

	}

	@Override
	public void removeTableModelListener(TableModelListener l)
	{

	}

	@Override
	public int getShownColumnIndex(String column)
	{
		return 0;
	}
}
