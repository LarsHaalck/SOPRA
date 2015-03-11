package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;

/**
 * Contains the welcome message and navigation buttons.
 * Is displayed for logged in users.
 */
public class PanelNavigation extends JPanel
{
	private JLabel lbWelcomeText;

	public PanelNavigation(String username)
	{
		super();

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		lbWelcomeText = new JLabel("Willkommen, " + username + "!");
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
				ControllerManager.informButtonPressed(EmitterButton.PANEL_EMPLOYEE_CHANGE_PASSWORD));

		btnLogout.addActionListener(e ->
				ControllerManager.informButtonPressed(EmitterButton.PANEL_EMPLOYEE_LOGOUT));
	}

	public void setWelcomeText(String text)
	{
		lbWelcomeText.setText(text);
	}
}
