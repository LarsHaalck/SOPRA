package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         lenght of textfield
 *         zip an city
 */
public class FormEmployee extends FormGeneral
{
	private ControllerFormEmployee controllerFormEmployee;

	private JPanel explanationPanel = new JPanel();
	private String explanationText = "Bitte geben Sie die Daten des Mitarbeiters ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel rightPanel = new JPanel();
	private JPanel midPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private JLabel firstName = new JLabel("Vorname");
	private JLabel lastName = new JLabel("Name");
	private JLabel address = new JLabel("Adresse");
	private JLabel zip = new JLabel("PLZ");
	private JLabel city = new JLabel("Ort");
	private JLabel date = new JLabel("Geburtsdatum");
	private JLabel phone = new JLabel("Telefon");
	private JLabel remark = new JLabel("Bemerkung");
	private JLabel username = new JLabel("Benutzername");

	private JTextField firstNameText = new JTextField();
	private JTextField lastNameText = new JTextField();
	private JTextField addressText = new JTextField();
	private JTextField zipText = new JTextField();
	private JTextField cityText = new JTextField();
	private JTextField dateText = new JTextField();
	private JTextField phoneText = new JTextField();
	private JTextArea remarkText = new JTextArea();
	private JTextField usernameText = new JTextField();

	private JLabel roleLabel = new JLabel("Rollen:");
	private JPanel rolePanel = new JPanel();

	private JLabel schedulemanager = new JLabel("Einsatzplaner");
	private JLabel networkplaner = new JLabel("Netzpaner");
	private JLabel busdriver = new JLabel("Busfahrer");
	private JLabel ticketplaner = new JLabel("Fahrkartenplaner");
	private JLabel hrmanager = new JLabel("Personalleiter");

	private JCheckBox schedulemanagerBox = new JCheckBox();
	private JCheckBox networkplanerBox = new JCheckBox();
	private JCheckBox busdriverBox = new JCheckBox();
	private JCheckBox ticketplanerBox = new JCheckBox();
	private JCheckBox hrmanagerBox = new JCheckBox();


	public FormEmployee(Frame parent)
	{
		super(parent, "Mitarbeiter verwalten");

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		controllerFormEmployee = new ControllerFormEmployee(this);

		//Add events to buttons
		save.addActionListener(e->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_SAVE);
		});
		cancel.addActionListener(e->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_CANCEL);
		});

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		explanationPanel.add(explanation);

		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));

		midPanel.setLayout(new GridLayout(9, 2));
		midPanel.add(lastName);
		midPanel.add(lastNameText);
		midPanel.add(firstName);
		midPanel.add(firstNameText);
		midPanel.add(address);
		midPanel.add(addressText);
		midPanel.add(zip);
		midPanel.add(zipText);
		midPanel.add(city);
		midPanel.add(cityText);
		midPanel.add(date);
		midPanel.add(dateText);
		midPanel.add(phone);
		midPanel.add(phoneText);
		midPanel.add(remark);
		midPanel.add(remarkText);
		midPanel.add(username);
		midPanel.add(usernameText);

		add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(roleLabel);
		rightPanel.add(rolePanel);

		rolePanel.setLayout(new GridLayout(5,2));
		Border border = rolePanel.getBorder();
		Border myBorder = new LineBorder(Color.black, 1);
		rolePanel.setBorder(new CompoundBorder(border, myBorder));
		rolePanel.add(schedulemanager);
		rolePanel.add(schedulemanagerBox);
		rolePanel.add(networkplaner);
		rolePanel.add(networkplanerBox);
		rolePanel.add(busdriver);
		rolePanel.add(busdriverBox);
		rolePanel.add(ticketplaner);
		rolePanel.add(ticketplanerBox);
		rolePanel.add(hrmanager);
		rolePanel.add(hrmanagerBox);

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(save);
		bottomPanel.add(cancel);

		pack();
	}
}
