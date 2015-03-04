package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 * Class extends normal table to have methods for getting an array of specified columns and get index by column by name
 */
public abstract class ExtendedTableModel implements TableModel
{
	protected String[] columnNames = new String[]{};
	protected Object[][] data = new Object[][]{};

	/**
	 * Returns all refineable columns.
	 * @return String array with names of refineable columns.
	 */
	public abstract String[] getRefineableColumns();

	/**
	 * Returns all ids of refineable columns.
	 * @return int array with ids of refineable columns.
	 */
	public int[] getRefineableColumnsIDs()
	{
		int[] ids = new int[getRefineableColumns().length];
		String[] strings = getRefineableColumns();
		for(int i=0; i<ids.length; i++)
			ids[i] = getColumnIndex(strings[i]);
		return ids;
	}

	/**
	 * Returns count of all existing columns.
	 * @return number of columns.
	 */
	public int getColumnCount()
	{
		return columnNames.length + 1;
	}


	/**
	 * Returns name of a column by index (also id)
	 * @param columnIndex Index of requested column-name.
	 * @return name corresponding to index.
	 */
	public String getColumnName(int columnIndex)
	{
		if(columnIndex == 0)
		{
			return "id";
		}
		return columnNames[columnIndex - 1];
	}

	/**
	 * Returns index for a shown column-name.
	 * @param column column name.
	 * @return index-id
	 */
	public int getColumnIndex(String column)
	{
		for(int i=0; i<columnNames.length; i++)
			if(column.equals(columnNames[i]))
				return i+1;
		return 1;
	}


	/**
	 * Returns always false, we don't want to let user edit table
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	/*
	 * JTable uses this method to determine the default renderer/
	 * editor for each cell.  If we didn't implement this method,
	 * then the last column would contain text ("true"/"false"),
	 * rather than a check box.
	 */
	public Class getColumnClass(int column)
	{
		return getValueAt(0, column).getClass();
	}

	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		return data[rowIndex][columnIndex];
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
}
