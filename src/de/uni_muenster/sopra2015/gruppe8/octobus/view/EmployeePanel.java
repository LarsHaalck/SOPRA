package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerEmployeeArea;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.NavigationPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.TabsPane;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.NavigationPanelListener;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class EmployeePanel extends JPanel
{
    private ControllerEmployeeArea controllerEmployeeArea;

    public EmployeePanel(){
        super();

        controllerEmployeeArea = new ControllerEmployeeArea(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        NavigationPanel navigationPanel = new NavigationPanel("Günni");
        navigationPanel.setNavigationPanelListener(controllerEmployeeArea);
        add(navigationPanel);

        TabsPane tabsPane = new TabsPane();
        // ...
        // Pretty much same as above
        add(tabsPane);

        setVisible(true);
    }
}
