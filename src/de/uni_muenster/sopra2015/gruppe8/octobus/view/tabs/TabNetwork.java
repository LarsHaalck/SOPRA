package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetwork;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class TabNetwork extends JPanel
{
	public TabNetwork()
	{
		setLayout(new BorderLayout(5,5));
		DisplayNetwork displayNetwork = new DisplayNetwork();
		add(new JScrollPane(new JLabel(displayNetwork.getNetwork())), BorderLayout.CENTER);
	}
}
