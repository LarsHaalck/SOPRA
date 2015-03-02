package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 * lenght of textfield
 * zip an city
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
	private JPanel leftPanel1 = new JPanel();

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
		topPanel.add(leftPanel1);
		topPanel.add(rightPanel);

		leftPanel1.setLayout(new BoxLayout(leftPanel1, BoxLayout.Y_AXIS));
		leftPanel1.add(explanationPanel);
		explanationPanel.add(explanation);
		leftPanel1.add(leftPanel);

		leftPanel.setLayout(new GridLayout(9, 2));
		leftPanel.add(lastName);
		leftPanel.add(lastNameText);
		leftPanel.add(firstName);
		leftPanel.add(firstNameText);
		leftPanel.add(address);
		leftPanel.add(addressText);
		leftPanel.add(zip);
		leftPanel.add(zipText);
		leftPanel.add(city);
		leftPanel.add(cityText);
		leftPanel.add(date);
		leftPanel.add(dateText);
		leftPanel.add(phone);
		leftPanel.add(phoneText);
		leftPanel.add(remark);
		leftPanel.add(remarkText);
		leftPanel.add(username);
		leftPanel.add(usernameText);

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
