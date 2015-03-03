package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.LoginDialog;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;
import javax.swing.*;

/**
 * Created by Joshua on 02.03.2015.
 */
public class ControllerMainPanel implements ButtonListener
{
    private JPanel panel;
	private ControllerMainFrame controllerMainFrame;

    public ControllerMainPanel(JPanel panel)
    {
        this.panel = panel;
    }

    @Override
    public void buttonPressed(String emitter)
    {
        switch (emitter)
        {
			case "loginRequest":
				controllerMainFrame.displayForm("login");
				break;
            case "searchConnectionRequest":
                System.out.println("Searching Connection");
                try
                {
                    (new ControllerDatabaseJOOQ()).run();
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
                break;
            case "showTicketsRequest":
                System.out.println("Show tickets");
                break;
            case "showNetworkRequest":
                System.out.println("Show network");
                break;
        }
    }

	public void setListener(ControllerMainFrame controllerMainFrame)
	{
		this.controllerMainFrame = controllerMainFrame;
	}
}
