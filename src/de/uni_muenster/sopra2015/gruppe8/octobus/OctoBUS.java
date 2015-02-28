package de.uni_muenster.sopra2015.gruppe8.octobus;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.MainFrame;

import javax.swing.*;

/**
 * Main entry point for the OctoBUS application
 *
 * @author Michael Biech
 */
public class OctoBUS
{
    public static void main(String[] args)
    {
        // For thread safety and adhering to best practices
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new MainFrame();
            }
        });
    }
}
