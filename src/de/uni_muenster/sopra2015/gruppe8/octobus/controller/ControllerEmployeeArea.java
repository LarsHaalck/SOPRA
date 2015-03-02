package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;
import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerEmployeeArea implements ButtonListener
{
    private JPanel panel;

    public ControllerEmployeeArea(JPanel panel)
    {
        this.panel = panel;
    }

    @Override
    public void buttonPressed(String emitter)
    {
        switch (emitter){
            case "passwordChangeRequest":
                System.out.println("User wishes to change their password");
                break;
        }
    }
}
