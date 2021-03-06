package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;

import javax.swing.*;
import java.awt.*;

/**
 * Tab used to display the bus network.
 */
public class TabNetwork extends JPanel
{
	public TabNetwork()
	{
		setLayout(new BorderLayout(5,5));
		DisplayNetwork displayNetwork = new DisplayNetwork();
		displayNetwork.init(555,995);

		add(displayNetwork, BorderLayout.CENTER);
	}
}
