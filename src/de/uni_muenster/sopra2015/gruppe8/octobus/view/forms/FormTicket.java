package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;


/** @author Patricia Schinke
 * lenght of textfield
 */
public class FormTicket extends JPanel
{
	private ControllerFormTicket controllerFormTicket;

	private String explanationText = "Bitte geben Sie die Daten des Tickets ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JPanel mid = new JPanel();
	private JPanel botttom = new JPanel();

	private JLabel name = new JLabel("Name");
	private JLabel price = new JLabel("Preis");
	private JLabel numPassengers = new JLabel("Anzahl Fahrgäste");
	private JLabel description = new JLabel("Beschreibung");

	private JTextField nameText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField numPassengersText = new JTextField();
	private JTextArea descriptionText = new JTextArea();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	public FormTicket(){
		controllerFormTicket = new ControllerFormTicket(this);

		save.addActionListener(e->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_SAVE);
		});
		cancel.addActionListener(e->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_CANCEL);
		});

		//setResizable(false);

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(botttom, BorderLayout.SOUTH);

		explanationPanel.add(explanation);

		mid.setLayout(new GridLayout(4,2));
		mid.add(name);
		mid.add(nameText);
		mid.add(price);
		mid.add(priceText);
		mid.add(numPassengers);
		mid.add(numPassengersText);
		mid.add(description);
		mid.add(descriptionText);

		botttom.setLayout(new FlowLayout());
		botttom.add(save);
		botttom.add(cancel);
	}
}
