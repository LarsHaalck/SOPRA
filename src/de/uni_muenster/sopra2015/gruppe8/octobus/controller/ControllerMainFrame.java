package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.EmployeePanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.MainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.MainPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.LoginDialog;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.ButtonListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerMainFrame implements ButtonListener
{
	private MainFrame frame;

	public ControllerMainFrame(MainFrame frame)
	{
		this.frame = frame;
	}
	@Override
	public void buttonPressed(String emitter)
	{
		System.out.println(emitter);
		if (emitter == "loginRequest") displayContent(new EmployeePanel());
		else System.out.println(emitter);
        switch (emitter)
        {
            case "loginRequest":
                EmployeePanel newEmployeePanel = new EmployeePanel();
                newEmployeePanel.setListener(frame.getController());
                displayContent(newEmployeePanel);
                System.out.println("login");
                break;
            case "logoutRequest":
                MainPanel newMainPanel = new MainPanel();
                newMainPanel.setListener(frame.getController());
                displayContent(newMainPanel);
                System.out.println("User wishes to log out");
                break;
        }
	}

	public void displayForm(String emitter)
	{
		switch(emitter)
		{
			case "login":
				LoginDialog d = new LoginDialog(frame);
				d.setListener(this);
				d.setVisible(true);
				break;
		}
	}

	// TODO: Considering this might be used in more than one place,
	// TODO: putting it in a separate helper class might be appropriate.
	// Adapted from http://stackoverflow.com/a/5077773/2010258 and http://stackoverflow.com/a/11073097/2010258
	public void displayContent(Container container)
	{
		Container cp = frame.getContentPane();
		cp.removeAll();
		frame.setContentPane(container);
		frame.setVisible(true);
	}
}
