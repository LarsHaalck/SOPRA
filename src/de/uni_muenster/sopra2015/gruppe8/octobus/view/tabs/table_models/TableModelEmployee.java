package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * TableModel for TabEmployee table.
 */
public class TableModelEmployee extends ExtendedTableModel
{
	public TableModelEmployee()
	{
		//Add column-names from enum
		ColumnsEmployee[] values = ColumnsEmployee.values();
		columnNames = new String[values.length];
		for(int i=0; i<columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{
				{1, "Schwakowiak", "Herbert", "Daniela-Katzenberger-Allee 42c", 45894, "Gelsenkirchen-Buer",
						"02.03.1975", "0190 / 666 666", "herbi.schwak@mail.de", "h_schwak"}
		};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsEmployee.NAME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsEmployee.NAME.toString(), ColumnsEmployee.FIRST_NAME.toString(),
				ColumnsEmployee.ADDRESS.toString(), ColumnsEmployee.ZIP_CODE.toString(), ColumnsEmployee.CITY.toString(),
				ColumnsEmployee.DATE_OF_BIRTH.toString(), ColumnsEmployee.EMAIL.toString(), ColumnsEmployee.USERNAME.toString()};
	}

	@Override
	public Class getColumnClass(int column)
	{
		switch(column)
		{
			case 0:
				return Integer.class;

			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return String.class;

			case 6:
				return TableDate.class;

			case 7:
			case 8:
				return String.class;
		}
		return null;
	}
}
