package de.uni_muenster.sopra2015.gruppe8.octobus.view.choices;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.NavigationPanelListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Michael Biech
 */
public class NavigationPanel extends JPanel
{

    private NavigationPanelListener navigationPanelListener;

    public NavigationPanel(String username){
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        //
        JLabel lbWelcomeText = new JLabel("Willkommen, " + username + "!");
        JButton btnChangePassword = new JButton("Passwort ändern");
        JButton btnLogout = new JButton("Ausloggen");

        add(Box.createRigidArea(new Dimension(5,0)));
        add(lbWelcomeText);
        // Makes the space in between the text and the buttons stretch to fill the whole pane
        add(Box.createHorizontalGlue());
        add(btnChangePassword);
        add(Box.createRigidArea(new Dimension(5,0)));
        add(btnLogout);
        add(Box.createRigidArea(new Dimension(5, 0)));

        btnChangePassword.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (navigationPanelListener != null) navigationPanelListener
                        // TODO: I suggest getText-internationalisation for consistency
                        // Stub
                        .NavigationRequestEmitted("passwordChangeRequest");
            }
        });

        btnLogout.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (navigationPanelListener != null) navigationPanelListener
                        // TODO: I suggest getText-internationalisation for consistency
                        // Stub
                        .NavigationRequestEmitted("logoutRequest");
            }
        });
    }

    public void setNavigationPanelListener(NavigationPanelListener listener){
        navigationPanelListener = listener;
    }

}
