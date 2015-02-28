package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.choices.MainPanel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.MainPanelListener;

import javax.swing.*;
import java.awt.*;

/**
 * Handles the overall application and takes care of changing the main
 * content panel depending on whether a user is logged in or not, as
 * well as handling the options available to a passenger.
 *
 * @author Michael Biech
 */
public class MainFrame extends JFrame implements MainPanelListener
{

    public MainFrame(){
        super("OctoBUS");

        MainPanel mainPanel = new MainPanel();
        mainPanel.setMainPanelListener(this);
        displayContent(mainPanel);

        ////////////////////////////////////////////////
        // Anything below this line is just GUI stuff //
        ////////////////////////////////////////////////

        // Set cross-platform Java Look and Feel (also called "Metal")
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        setResizable(false);
        // Make sure we don't lose any space to window decorations
        getContentPane().setPreferredSize(new Dimension(1024, 640));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        // Center frame on screen (as per http://stackoverflow.com/a/2442614/2010258)
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // TODO: This is still a stub!
    /**
     * Take action depending on passenger's choice, namely:
     *
     * - Display entire network on a map
     * - Search for a connection between points A and B
     * - Display all tickets available
     *
     * @param emitter
     */
    @Override
    public void MainPanelRequestEmitted(String emitter)
    {
        if (emitter == "loginRequest") displayContent(new EmployeePanel());
        else System.out.println(emitter);
    }

    // TODO: Considering this might be used in more than one place,
    // TODO: putting it in a separate helper class might be appropriate.
    // Adapted from http://stackoverflow.com/a/5077773/2010258 and http://stackoverflow.com/a/11073097/2010258
    private void displayContent(Container container){
        Container cp = getContentPane();
        cp.removeAll();
        setContentPane(container);
        setVisible(true);
    }

}
