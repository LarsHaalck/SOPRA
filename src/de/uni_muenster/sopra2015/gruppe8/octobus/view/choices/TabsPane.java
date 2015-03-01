package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.BusesTab;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.ExampleTab;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class TabsPane extends JTabbedPane
{
    public TabsPane(){
        super();

        addTab("Allgemein", new ExampleTab());
        addTab("Allgemein", new JPanel());
        addTab("Allgemein", new JPanel());
        addTab("Allgemein", new JPanel());
        addTab("Allgemein", new JPanel());
    }
}
