package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;

/**
 * Created by Florian on 03.03.2015.
 */
public class TableModelBus extends ExtendedTableModel
{
	private String[] columnNames;

	private Object[][] data = {
			{11, "MS-OB-482", "MAN", "Lion's City C LE", "Normal", new Integer(44),
					new Integer(82), "25.02.2016"},
			{22, "MS-OB-7767", "MAN", "Lion's City G", "Gelenk", new Integer(51),
					new Integer(110), "27.10.2016"},
			{33, "MS-OB-546", "MAN", "Lion's City M", "Klein", new Integer(29),
					new Integer(48), "02.02.2015"}
	};

	public TableModelBus()
	{
		//Add column-names from enum
		ColumnsBus[] values = ColumnsBus.values();
		columnNames = new String[values.length];
		for(int i=0; i<columnNames.length; i++)
			columnNames[i] = values[i].toString();
	}

	@Override
	public int getRowCount()
	{
		return data.length;
	}

	@Override
	public int getShownColumnCount()
	{
		return columnNames.length;
	}

	@Override
	public String getShownColumnName(int columnIndex)
	{
		return columnNames[columnIndex];
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
	public boolean isCellEditable(int rowIndex, int columnIndex)
	{
		return false;
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsBus.LICENCE_PLATE.toString(), ColumnsBus.MANUFACTURER.toString(), ColumnsBus.TYPE.toString()};
	}

	@Override
	public int getShownColumnIndex(String column)
	{
		for(int i=0; i<columnNames.length; i++)
			if(column.equals(columnNames[i]))
				return i;
		return 0;
	}
}