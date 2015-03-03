package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;

/**
 * Created by Florian on 03.03.2015.
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
	public int getColumnCount()
	{
		return 0;
	}

	@Override
	public String getColumnName(int columnIndex)
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
	public int getColumnIndex(String column)
	{
		return 0;
	}
}
