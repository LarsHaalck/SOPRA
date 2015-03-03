package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormLogin;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 02.03.2015.
 */
public class FormLogin extends JDialog
{
	private ControllerFormLogin controllerFormLogin;

	private JTextField tfUsername;
	private JPasswordField pfPassword;
	private JLabel lbUsername, lbPassword, lbError;
	private JButton btnLogin, btnCancel;
	private JPanel panel;
	private GridBagConstraints cs;

	public FormLogin(Frame parent)
	{
		super(parent, "Login", true);

		controllerFormLogin = new ControllerFormLogin(this);

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

		//cs.fill = GridBagConstraints.HORIZONTAL;

		lbUsername = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lbUsername);

		tfUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tfUsername, cs);

		lbPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lbPassword, cs);

		pfPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pfPassword, cs);

		String text = " ";
		lbError = new JLabel(text);
		lbError.setForeground(Color.red);
		cs.gridx = 0;
		cs.gridy = 2;
		cs.gridwidth = 3;
		panel.add(lbError, cs);

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(e -> {
			controllerFormLogin.buttonPressed("login_button_pressed");
		});

		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> {
			controllerFormLogin.buttonPressed("cancel_button_pressed");
		});
		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		getRootPane().setDefaultButton(btnLogin);

		setDefaultValues();
	}

	public void illegalInput()
	{
		lbError.setText("Der Username oder das Passwort ist falsch!");
	}

	public String getUsername()
	{
		return tfUsername.getText().trim();
	}

	public String getPassword()
	{
		return new String(pfPassword.getPassword());
	}

	private void setDefaultValues()
	{
		tfUsername.setText("herbert");
		pfPassword.setText("octobus");
	}
}
