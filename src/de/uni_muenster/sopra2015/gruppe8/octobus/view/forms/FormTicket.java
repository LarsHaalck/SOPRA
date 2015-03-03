package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;


/** @author Patricia Schinke
 * lenght of textfield
 */
public class FormTicket extends JPanel
{
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

	}
}
