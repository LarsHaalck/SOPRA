package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerPanelPassenger;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Displays the content available to non logged in users.
 */
public class PanelPassenger extends JPanel
{
	private ControllerPanelPassenger controllerPanelPassenger;

	public PanelPassenger()
	{
		super();

		controllerPanelPassenger = new ControllerPanelPassenger(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		Dimension bigButtonSize = new Dimension(300, 300);

		ImageIcon icon = new ImageIcon("res/images/icon_128.png");
		JLabel lbLogo = new JLabel(icon);
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridwidth = 1;
		add(lbLogo, c);

		JLabel lbWelcome = new JLabel("Willkommen bei OctoBUS!");
		lbWelcome.setFont(lbWelcome.getFont().deriveFont(48.0f));
		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 1;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 5;
		add(lbWelcome, c);

		JLabel lbPleaseSelect = new JLabel("Bitte treffen Sie eine Auswahl.");
		lbPleaseSelect.setFont(lbPleaseSelect.getFont().deriveFont(24.0f));
		c.insets = new Insets(10, 20, 50, 20);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 6;
		add(lbPleaseSelect, c);

		// main button panel
		JPanel pnMainButtons = new JPanel(new GridBagLayout());

		JButton btnShowNetwork = new JButton("Busnetz anzeigen");
		btnShowNetwork.setPreferredSize(bigButtonSize);
		c.gridy = 0;
		c.gridwidth = 1;
		c.insets = new Insets(10, 10, 10, 10);
		pnMainButtons.add(btnShowNetwork, c);

		JButton btnSearchConnection = new JButton("Verbindung suchen");
		btnSearchConnection.setPreferredSize(bigButtonSize);
		c.gridx = 1;
		pnMainButtons.add(btnSearchConnection, c);

		JButton btnShowTickets = new JButton("Tickets anzeigen");
		btnShowTickets.setPreferredSize(bigButtonSize);
		c.gridx = 2;
		pnMainButtons.add(btnShowTickets, c);

		pnMainButtons.setBorder(BorderFactory.createEtchedBorder());
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 6;
		add(pnMainButtons, c);

		pnMainButtons.setSize(new Dimension(950, 350));

		JButton btnLogin = new JButton("Mitarbeiter-Login");
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(50, 0, 0, 10);
		add(btnLogin, c);

		// Add action listeners to buttons
		btnLogin.addActionListener(e -> {
			if (controllerPanelPassenger != null)
				controllerPanelPassenger.buttonPressed(EmitterButton.PANEL_PASSENGER_LOGIN);
		});

		btnSearchConnection.addActionListener(e -> {
			if (controllerPanelPassenger != null)
				controllerPanelPassenger.buttonPressed(EmitterButton.PANEL_PASSENGER_SEARCH_CONNECTION);
		});

		btnShowTickets.addActionListener(e -> {
			if (controllerPanelPassenger != null)
				controllerPanelPassenger.buttonPressed(EmitterButton.PANEL_PASSENGER_SHOW_TICKETS);
		});

		btnShowNetwork.addActionListener(e -> {
			if (controllerPanelPassenger != null)
				controllerPanelPassenger.buttonPressed(EmitterButton.PANEL_PASSENGER_SHOW_NETWORK);
		});
	}
}
