package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;


import java.util.ArrayList;

/**
 * Created by sszie_000 on 11.03.2015.
 */
public class TableModelBusStopSelect extends DefaultExtendedTableModel {

    String[] colNames = {"Names"};
    ArrayList<BusStop> busStops;

    public TableModelBusStopSelect(ArrayList<BusStop> busStops)
    {
        this.busStops = busStops;
    }

    @Override
    public int getRowCount()
    {
        return busStops.size();
    }

    @Override
    public int getColumnCount()
    {
        return 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        return busStops.get(rowIndex).getName();
    }

    public BusStop getBusStop(int index)
    {
        return busStops.get(index);
    }
}
