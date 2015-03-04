package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import javax.swing.event.TableModelListener;

public class TableModelEmployee extends ExtendedTableModel
{
	private String[] columnNames;

	private Object[][] data = {
			{1, "Schwakowiak", "Herbert", "Daniela-Katzenberger-Allee 42c", 45894, "Gelsenkirchen-Buer",
					"02.03.1975", "0190 / 666 666", "herbi.schwak@mail.de", "h_schwak"}
	};

	public TableModelEmployee()
	{
		//Add column-names from enum
		ColumnsEmployee[] values = ColumnsEmployee.values();
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
		return new String[]{ColumnsEmployee.NAME.toString(), ColumnsEmployee.FIRST_NAME.toString(),
				ColumnsEmployee.ADDRESS.toString(), ColumnsEmployee.ZIP_CODE.toString(), ColumnsEmployee.CITY.toString(),
				ColumnsEmployee.DATE_OF_BIRTH.toString(), ColumnsEmployee.EMAIL.toString(), ColumnsEmployee.USERNAME.toString()};
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
