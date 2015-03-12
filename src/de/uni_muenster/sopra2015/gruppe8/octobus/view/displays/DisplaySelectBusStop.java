package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplaySelectBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelBusStop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Created by sszie_000 on 11.03.2015.
 */
public class DisplaySelectBusStop extends JDialog {

    ControllerDisplaySelectBusStop controllerDisplaySelectBusStop;

    private JTable busstops;
    private JButton acceptSelection;

    public DisplaySelectBusStop(){

    }

    private void initComponents(){
        acceptSelection = new JButton("Bestätigen");
        acceptSelection.addActionListener(e ->
                controllerDisplaySelectBusStop.buttonPressed(EmitterButton.DISPLAY_SELECT_BUSSTOP_ACCEPT));

        TableModelBusStop tableModelBusStop = new TableModelBusStop();



        busstops = new JTable(tableModelBusStop);
        busstops.getSelectionModel().setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    }
}
