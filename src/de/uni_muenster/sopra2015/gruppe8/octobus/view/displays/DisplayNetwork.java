package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormGeneral;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Steen Sziegaud on 04.03.2015.
 */
public class DisplayNetwork extends FormGeneral
{
    public DisplayNetwork(){
        super(new JFrame());
    }
    //Components
    private JButton dayNightSwitch;
    private JPanel networkDisplay;

    public DisplayNetwork(Frame parent)
    {
        super(parent);

        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        initComponents();
    }

    private void initComponents()
    {
        dayNightSwitch = new JButton();
        networkDisplay = new JPanel();

        networkDisplay.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }
}
