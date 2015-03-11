package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Connection;

import javax.swing.table.AbstractTableModel;
import java.util.LinkedList;

/**
 * Created by sszie_000 on 09.03.2015.
 */
public class TableModelSearchConnection extends AbstractTableModel {

    String[] colNames = {"Fahrt", "Abfahrt", "Ankunft", "Dauer", "Umsteigen"};
    LinkedList<Connection> connections;

    public TableModelSearchConnection(LinkedList<Connection> connections)
    {
        this.connections = connections;
    }


    @Override
    public int getRowCount() {
        return connections.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Connection connection = connections.get(rowIndex);
        int time = connection.getTime();
        int arrivalTime = connection.getTrips().get(connection.getTrips().size() - 1).getFifth();
        int duration = connection.getDuration();



        if (connection != null)
            switch (columnIndex) {
                case 0:
                    return (rowIndex + 1) + ". Fahrt";
                case 1:
                    return formatDate(time / 60, time % 60);
                case 2:
                    return formatDate(arrivalTime / 60, arrivalTime % 60);
                case 3:
                    return formatDate(duration / 60, duration % 60);
                case 4:
                    return connection.getNumberOfTransitions() + "x";
            }

        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col){
        return false;
    }

    public void addLastConnection(Connection connection)
    {
        connections.add(connection);
    }

    public void addFirstConnection(Connection connection)
    {
        connections.addFirst(connection);
    }

    private String formatDate(int hours, int minutes){
        String hourString;
        String minuteString;
        if (hours < 10)
            hourString = "0" + hours;
        else
            hourString = Integer.toString(hours);
        if (minutes < 10)
            minuteString = "0" + minutes;
        else
            minuteString = Integer.toString(minutes);
        return hourString + ":" + minuteString;
    }

    private String formatTime(int hours, int minutes){
        String hourString;
        String minuteString;
        if (hours < 10)
            hourString = "0" + hours;
        else
            hourString = Integer.toString(hours);
        if (minutes < 10)
            minuteString = "0" + minutes;
        else
            minuteString = Integer.toString(minutes);
        return hourString + ":" + minuteString;
    }

    public void clearTableModel(){
        connections.clear();
    }
}
