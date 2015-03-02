package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.EmployeePanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.MainPanelListener;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerMainFrame implements MainPanelListener
{
	private JFrame frame;

	public ControllerMainFrame(JFrame frame)
	{
		this.frame = frame;
	}
	/**
	 * Take action depending on passenger's choice, namely:
	 *
	 * - Display entire network on a map
	 * - Search for a connection between points A and B
	 * - Display all tickets available
	 *
	 * @param emitter
	 */
	@Override
	public void MainPanelRequestEmitted(String emitter)
	{
		if (emitter == "loginRequest") displayContent(new EmployeePanel());
		else System.out.println(emitter);
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
