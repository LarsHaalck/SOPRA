package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormChangePassword;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 03.03.2015.
 */
public class FormChangePassword extends JDialog
{
	private ControllerFormChangePassword controllerFormChangePassword;

	private JPanel panel;
	private GridBagConstraints cs;
	private JLabel lbOldPassword, lbNewPassword, lbNewPasswordCorrect, lbOldPassword_Error,
			lbNewPassword_Error, lbNewPasswordCorrect_Error;
	private JTextField tfOldPassword, tfNewPassword, tfNewPasswordCorrect;
	private JButton btnSave, btnCancel;

	public FormChangePassword(Frame parent)
	{
		super(parent, "Passwort ändern", true);

		controllerFormChangePassword = new ControllerFormChangePassword(this);

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

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbOldPassword = new JLabel("Altes Passwort: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbOldPassword, cs);

		tfOldPassword = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfOldPassword, cs);

		cs.fill = GridBagConstraints.CENTER;

		lbOldPassword_Error = new JLabel(" ");
		lbOldPassword_Error.setForeground(Color.red);
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 3;
		panel.add(lbOldPassword_Error, cs);

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbNewPassword = new JLabel("Neues Passwort: ");
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 1;
		panel.add(lbNewPassword, cs);

		tfNewPassword = new JTextField(20);
		tfNewPassword.setToolTipText("Das Passwort muss aus 8 Zeichen bestehen");
		cs.gridx = 1;
		cs.gridy = 2;
		cs.gridwidth = 2;
		panel.add(tfNewPassword, cs);

		cs.fill = GridBagConstraints.CENTER;

		lbNewPassword_Error = new JLabel(" ");
		lbNewPassword_Error.setForeground(Color.red);
		cs.gridx = 0;
		cs.gridy = 3;
		cs.gridwidth = 3;
		panel.add(lbNewPassword_Error, cs);

		cs.fill = GridBagConstraints.HORIZONTAL;

		lbNewPasswordCorrect = new JLabel("Erneut eingeben: ");
		cs.gridx = 0;
		cs.gridy = 4;
		cs.gridwidth = 1;
		panel.add(lbNewPasswordCorrect, cs);

		tfNewPasswordCorrect = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 4;
		cs.gridwidth = 2;
		panel.add(tfNewPasswordCorrect, cs);

		cs.fill = GridBagConstraints.CENTER;


		lbNewPasswordCorrect_Error = new JLabel(" ");
		lbNewPasswordCorrect_Error.setForeground(Color.red);
		cs.gridx = 0;
		cs.gridy = 5;
		cs.gridwidth = 3;
		panel.add(lbNewPasswordCorrect_Error, cs);

		btnSave = new JButton("Speichern");
		btnSave.addActionListener(e -> {
			controllerFormChangePassword.buttonPressed(EmitterButton.FORM_CHANGE_PASSWORD_SAVE);
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			controllerFormChangePassword.buttonPressed(EmitterButton.FORM_CHANGE_PASSWORD_CANCEL);
		});
		JPanel bp = new JPanel();
		bp.add(btnSave);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		getRootPane().setDefaultButton(btnSave);

		illegalChanges(false, false, true);
	}

	public void illegalChanges(boolean OldPassword, boolean NewPassword, boolean NewPasswordCorrect)
	{
		if (OldPassword)
			lbOldPassword_Error.setText("Das Passwort ist falsch!");
		else
			lbOldPassword_Error.setText(" ");
		if (NewPassword)
			lbNewPassword_Error.setText("Das Passwort ist falsch!");
		else
			lbNewPassword_Error.setText(" ");
		if (NewPasswordCorrect)
			lbNewPasswordCorrect_Error.setText("Das Passwort ist falsch!");
		else
			lbNewPasswordCorrect_Error.setText(" ");
	}

	public String getOldPassword()
	{
		return tfOldPassword.getText().trim();
	}

	public String getNewPassword()
	{
		return tfNewPassword.getText().trim();
	}

	public String getNewPasswordCorrect()
	{
		return tfNewPasswordCorrect.getText().trim();
	}
}
