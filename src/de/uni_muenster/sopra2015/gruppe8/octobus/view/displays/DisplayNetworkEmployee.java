package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Florian on 09.03.2015.
 */
public class DisplayNetworkEmployee extends JPanel
{
	public DisplayNetworkEmployee()
	{
		setLayout(new BorderLayout(5,5));
		DisplayNetwork displayNetwork = new DisplayNetwork();
		displayNetwork.init(560, 995);
		add(displayNetwork, BorderLayout.CENTER);

		JButton btnBack = new JButton("Zurück");
		btnBack.addActionListener(e -> {
			ControllerManager.informButtonPressed(EmitterButton.DISPLAY_NETWORK_BACK);
		});
		JPanel plButton = new JPanel();
		plButton.setLayout(new BorderLayout());
		plButton.add(btnBack, BorderLayout.EAST);
		add(plButton, BorderLayout.PAGE_END);

	}
}
