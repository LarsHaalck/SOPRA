package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 03.03.2015.
 */
public class FormulaChangePassword extends JDialog
{
    private JPanel panel;
    private GridBagConstraints cs;
    private JLabel lbOldPassword, lbNewPassword, lbNewPasswordCorrect, lbOldPassword_Error,
            lbNewPassword_Error, lbNewPasswordCorrect_Error;
    private JTextField tfOldPassword, tfNewPassword, tfNewPasswordCorrect;

    public FormulaChangePassword(Frame parent)
    {
        super(parent, "Passwort ändern", true);


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

        panel = new JPanel(new GridBagLayout());
        cs = new GridBagConstraints();

        lbOldPassword = new JLabel("Altes Passwort: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbOldPassword);

        tfOldPassword = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(tfOldPassword);

        lbOldPassword_Error = new JLabel(" ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 3;
        panel.add(lbOldPassword_Error);

        lbNewPassword = new JLabel("Neues Passwort: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(lbNewPassword);

        tfNewPassword = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(tfNewPassword);

        lbNewPassword_Error = new JLabel(" ");
        cs.gridx = 0;
        cs.gridy = 3;
        cs.gridwidth = 3;
        panel.add(lbNewPassword_Error);

        lbNewPasswordCorrect = new JLabel("Erneut eingeben: ");
        cs.gridx = 0;
        cs.gridy = 4;
        cs.gridwidth = 1;
        panel.add(lbNewPasswordCorrect);

        tfNewPasswordCorrect = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 4;
        cs.gridwidth = 2;
        panel.add(tfNewPasswordCorrect);

        lbNewPasswordCorrect_Error = new JLabel(" ");
        cs.gridx = 0;
        cs.gridy = 5;
        cs.gridwidth = 3;
        panel.add(lbNewPasswordCorrect_Error);
    }

    public static void main(String[] args)
    {
        FormulaChangePassword fenster = new FormulaChangePassword(null);
        fenster.setVisible(true);
    }
}
