package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Biech
 */
public class PanelNavigation extends JPanel
{
	public PanelNavigation(String username)
	{
		super();

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel lbWelcomeText = new JLabel("Willkommen, " + username + "!");
		JButton btnChangePassword = new JButton("Passwort ändern");
		JButton btnLogout = new JButton("Ausloggen");

		add(Box.createRigidArea(new Dimension(5, 0)));
		add(lbWelcomeText);
		// Makes the space in between the text and the buttons stretch to fill the whole pane
		add(Box.createHorizontalGlue());
		add(btnChangePassword);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(btnLogout);
		add(Box.createRigidArea(new Dimension(5, 0)));

		btnChangePassword.addActionListener(e ->
		{
			ControllerManager.informButtonPressed(EmitterButton.PANEL_EMPLOYEE_CHANGE_PASSWORD);
		});

		btnLogout.addActionListener(e ->
		{
			ControllerManager.informButtonPressed(EmitterButton.PANEL_EMPLOYEE_LOGOUT);
		});
	}
}
