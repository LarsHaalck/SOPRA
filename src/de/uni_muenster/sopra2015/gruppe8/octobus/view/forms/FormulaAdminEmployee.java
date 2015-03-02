package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 * lenght of textfield
 * test?
 */
public class FormulaAdminEmployee extends JPanel
{
	private JPanel explanationPanel = new JPanel();
	private String explanationText = "Bitte geben Sie die Daten des Mitarbeiters ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel rightPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel topPanel = new JPanel();
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
	private JTextField remarkText = new JTextField();
	private JTextField usernameText = new JTextField();

	private JPanel firstNamePanel = new JPanel();
	private JPanel lastNamePanel = new JPanel();
	private JPanel addressPanel = new JPanel();
	private JPanel zipPanel = new JPanel(); //with city
	private JPanel datePanel = new JPanel();
	private JPanel phonePanel = new JPanel();
	private JPanel remarkPanel = new JPanel();
	private JPanel usernamePanel = new JPanel();

	private JPanel picturePanel = new JPanel();
	private JLabel roleLabel = new JLabel("Rollen:");
	private JPanel rolePanel = new JPanel();

	private JToggleButton schedulemanager = new JToggleButton("Einsatzplaner");
	private JToggleButton networkplaner = new JToggleButton("Netzpaner");
	private JToggleButton busdriver = new JToggleButton("Busfahrer");
	private JToggleButton ticketplaner = new JToggleButton("Fahrkartenplaner");
	private JToggleButton hrmanager = new JToggleButton("Personalleiter");

	public FormulaAdminEmployee()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(topPanel);
		add(bottomPanel);

		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);

		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.add(explanationPanel);
		explanationPanel.add(explanation);
		leftPanel.add(lastNamePanel);
		lastNamePanel.setLayout(new FlowLayout());
		lastNamePanel.add(lastName);
		lastNamePanel.add(lastNameText);
		leftPanel.add(firstNamePanel);
		firstNamePanel.setLayout(new FlowLayout());
		firstNamePanel.add(firstName);
		firstNamePanel.add(firstNameText);
		leftPanel.add(addressPanel);
		addressPanel.setLayout(new FlowLayout());
		addressPanel.add(address);
		addressPanel.add(addressText);
		leftPanel.add(zipPanel);
		zipPanel.setLayout(new FlowLayout());
		zipPanel.add(zip);
		zipPanel.add(zipText);
		zipPanel.add(city);
		zipPanel.add(cityText);
		leftPanel.add(datePanel);
		datePanel.setLayout(new FlowLayout());
		datePanel.add(date);
		datePanel.add(dateText);
		leftPanel.add(phonePanel);
		phonePanel.setLayout(new FlowLayout());
		phonePanel.add(phone);
		phonePanel.add(phoneText);
		leftPanel.add(remarkPanel);
		remarkPanel.setLayout(new FlowLayout());
		remarkPanel.add(remark);
		remarkPanel.add(remarkText);
		leftPanel.add(usernamePanel);
		usernamePanel.setLayout(new FlowLayout());
		usernamePanel.add(username);
		usernamePanel.add(usernameText);

		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(picturePanel);
		rightPanel.add(roleLabel);
		rightPanel.add(rolePanel);
		rolePanel.setLayout(new BoxLayout(rolePanel, BoxLayout.Y_AXIS));
		rolePanel.add(schedulemanager);
		rolePanel.add(networkplaner);
		rolePanel.add(busdriver);
		rolePanel.add(ticketplaner);
		rolePanel.add(hrmanager);

		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.add(save);
		bottomPanel.add(cancel);
	}

}
