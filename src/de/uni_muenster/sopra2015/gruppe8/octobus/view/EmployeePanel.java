package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.NavigationPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.TabsPane;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.NavigationPanelListener;

import javax.swing.*;

/**
 * @author Michael Biech
 */
public class EmployeePanel extends JPanel implements NavigationPanelListener
{
    public EmployeePanel(){
        super();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        NavigationPanel navigationPanel = new NavigationPanel("Günni");
        navigationPanel.setNavigationPanelListener(this);
        add(navigationPanel);

        TabsPane tabsPane = new TabsPane();
        // ...
        // Pretty much same as above
        add(tabsPane);

        setVisible(true);
    }

    // TODO: This is still a stub!
    /**
     * Take action depending on user's choice, meaning logging out or changing their password
     *
     * @param emitter
     */
    @Override
    public void NavigationRequestEmitted(String emitter)
    {
        switch (emitter){
            case "passwordChangeRequest":
                System.out.println("User wishes to change their password");
                break;
            case "logoutRequest":
                System.out.println("User wishes to log out");
                break;
        }
    }

}
