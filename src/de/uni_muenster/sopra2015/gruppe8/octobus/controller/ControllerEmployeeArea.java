package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.NavigationPanelListener;

import javax.swing.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerEmployeeArea implements NavigationPanelListener
{
    private JPanel panel;

    public ControllerEmployeeArea(JPanel panel)
    {
        this.panel = panel;
    }

    @Override
    public void NavigationRequestEmitted(String emitter)
    {
        switch (emitter){
            case "passwordChangeRequest":
                System.out.println("User wishes to change their password");
                break;
            case "logoutRequest":
                System.out.println("User wishes to log out");
                break;
        }
    }
}
