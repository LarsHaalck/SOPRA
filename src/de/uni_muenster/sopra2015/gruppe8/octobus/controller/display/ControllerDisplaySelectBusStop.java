package de.uni_muenster.sopra2015.gruppe8.octobus.controller.display;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplaySelectBusStop;

import javax.swing.*;


/**
 * Created by sszie_000 on 11.03.2015.
 */
public class ControllerDisplaySelectBusStop extends Controller implements ListenerButton, ListenerTable {

    private ControllerDatabase db;
    private DisplaySelectBusStop displaySelectBusStop;
    //private JDialog

    public ControllerDisplaySelectBusStop(DisplaySelectBusStop displaySelectBusStop)
    {
        this.displaySelectBusStop = displaySelectBusStop;
        db = ControllerDatabase.getInstance();
        fillTable();
    }

    @Override
    protected void addListeners() {
        ControllerManager.addListener((ListenerButton) this);
    }

    @Override
    protected void removeListeners() {
        ControllerManager.removeListener((ListenerButton) this);
    }

    @Override
    public void buttonPressed(EmitterButton emitter) {
        switch (emitter){
            case DISPLAY_SELECT_BUSSTOP_ACCEPT:

        }

    }

    @Override
    public void tableSelectionChanged(EmitterTable emitter) {

    }

    @Override
    public void tableContentChanged(EmitterTable emitter) {

    }

    @Override
    public void tableFocusLost(EmitterTable emitter) {

    }

    public void fillTable(){

    }

}
