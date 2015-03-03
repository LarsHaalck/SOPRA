package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainFrame;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerMainPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author Michael Biech
 */
public class PanelPassanger extends JPanel
{
	private ControllerMainPanel controllerMainPanel;

	public PanelPassanger()
	{
		super();

		controllerMainPanel = new ControllerMainPanel(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 10, 10, 10);
		Dimension bigButtonSize = new Dimension(300, 300);

		JLabel lbWelcome = new JLabel("Willkommen bei OctoBUS!");
		lbWelcome.setFont(lbWelcome.getFont().deriveFont(48.0f));
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
		add(lbWelcome, c);

		JLabel lbPleaseSelect = new JLabel("Bitte treffen Sie eine Auswahl.");
		lbPleaseSelect.setFont(lbPleaseSelect.getFont().deriveFont(24.0f));
		c.insets = new Insets(10, 20, 50, 20);
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.gridwidth = 3;
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
		c.gridwidth = 1;
		add(pnMainButtons, c);

		pnMainButtons.setSize(new Dimension(950, 350));

		JButton btnLogin = new JButton("Mitarbeiter-Login");
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(50, 0, 0, 10);
		add(btnLogin, c);

		// Add action listeners to buttons
		btnLogin.addActionListener(e -> {
			if (controllerMainPanel != null)
			{
				controllerMainPanel.buttonPressed("loginRequest");
			}
		});

		btnSearchConnection.addActionListener(e -> {
			if (controllerMainPanel != null)
			{
				controllerMainPanel.buttonPressed("searchConnectionRequest");
			}
		});

		btnShowTickets.addActionListener(e -> {
			if (controllerMainPanel != null)
			{
				controllerMainPanel.buttonPressed("showTicketsRequest");
			}
		});

		btnShowNetwork.addActionListener(e -> {
			if (controllerMainPanel != null)
			{
				controllerMainPanel.buttonPressed("showNetworkRequest");
			}
		});
	}
}
