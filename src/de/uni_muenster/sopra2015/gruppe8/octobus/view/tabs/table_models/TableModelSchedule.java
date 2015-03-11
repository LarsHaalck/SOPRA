package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

/**
 * TableModel for TabSchedule table.
 */
public class TableModelSchedule extends ExtendedTableModel
{
	public TableModelSchedule()
	{
		//Add column-names from enum
		ColumnsSchedule[] values = ColumnsSchedule.values();
		columnNames = new String[values.length];
		for (int i = 0; i < columnNames.length; i++)
			columnNames[i] = values[i].toString();

		data = new Object[][]{};
	}

	@Override
	public int getFirstSortColumn()
	{
		return getColumnIndex(ColumnsSchedule.TIME.toString());
	}

	@Override
	public String[] getRefineableColumns()
	{
		return new String[]{ColumnsSchedule.LINE.toString(), ColumnsSchedule.BUS.toString(), ColumnsSchedule.BUS_DRIVER.toString()};
	}

    @Override
    public Class getColumnClass(int column)
    {
        switch (column)
        {
            case 0:
                return Integer.class;

            case 1:
                return String.class;

            case 2:
                return TableDate.class;

			case 3:
				return Integer.class;

            case 4:
                return String.class;

            case 5:
                return String.class;
        }
        return null;
    }
}
