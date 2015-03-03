package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerEmployeeArea;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Biech
 */
public class NavigationPanel extends JPanel
{
	public NavigationPanel(String username)
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
			ControllerManager.informButtonPressed("change_password_request");
		});

		btnLogout.addActionListener(e ->
		{
			ControllerManager.informButtonPressed("logout_done");
		});
	}
}
