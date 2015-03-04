package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/** @author Patricia Schinke
 * lenght of textfield
 */
public class FormTicket extends FormGeneral
{
	private ControllerFormTicket controllerFormTicket;

	private String explanationText = "Bitte geben Sie die Daten des Tickets ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JPanel mid = new JPanel();
	private JPanel bottom = new JPanel();

	private JLabel name = new JLabel("Name");
	private JLabel price = new JLabel("Preis");
	private JLabel numPassengers = new JLabel("Anzahl Fahrgäste");
	private JLabel description = new JLabel("Beschreibung");

	private JTextField nameText = new JTextField();
	private JTextField priceText = new JTextField();
	private JTextField numPassengersText = new JTextField();
	private JTextArea descriptionText = new JTextArea();

	private JPanel namePanel = new JPanel();
	private JPanel pricePanel = new JPanel();
	private JPanel numPassengersPanel = new JPanel();
	private JPanel descriptionPanel = new JPanel();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private int textHeight = 25;
	private int areaHeight = 50;
	private int textWidth = 150;

	public FormTicket(Frame parent, int objectID)
	{
		//super(parent, "Mitarbeiter verwalten");
		super(parent, "");
		if(objectID == -1)
			setTitle("Mitarbeiter anlegen");
		else
			setTitle("Mitarbeiter ändern");

		controllerFormTicket = new ControllerFormTicket(this);

		save.addActionListener(e->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_SAVE);
		});
		cancel.addActionListener(e->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_CANCEL);
		});

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		explanationPanel.setPreferredSize(new Dimension(924, 100));
		explanationPanel.setBorder(new EmptyBorder(new Insets(40,0,40,0)));
		explanationPanel.add(explanation);

		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		mid.add(namePanel);
		namePanel.setLayout(new FlowLayout());
		mid.add(pricePanel);
		pricePanel.setLayout(new FlowLayout());
		mid.add(numPassengersPanel);
		numPassengersPanel.setLayout(new FlowLayout());
		mid.add(descriptionPanel);
		descriptionPanel.setLayout(new FlowLayout());

		namePanel.add(name);
		namePanel.add(nameText);
		pricePanel.add(price);
		pricePanel.add(priceText);
		numPassengersPanel.add(numPassengers);
		numPassengersPanel.add(numPassengersText);
		descriptionPanel.add(description);
		descriptionPanel.add(descriptionText);

		name.setPreferredSize(new Dimension(textWidth, textHeight));
		nameText.setPreferredSize(new Dimension(textWidth, textHeight));
		price.setPreferredSize(new Dimension(textWidth, textHeight));
		priceText.setPreferredSize(new Dimension(textWidth, textHeight));
		numPassengers.setPreferredSize(new Dimension(textWidth, textHeight));
		numPassengersText.setPreferredSize(new Dimension(textWidth, textHeight));
		description.setPreferredSize(new Dimension(textWidth, textHeight));
		descriptionText.setPreferredSize(new Dimension(textWidth, areaHeight));

		bottom.setLayout(new FlowLayout());
		bottom.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		bottom.setPreferredSize(new Dimension(924, 100));
		bottom.add(save);
		bottom.add(cancel);

		pack();
	}
	public String getNameText()
	{
		return nameText.getText();
	}

	public void setNameText(String text)
	{
		this.nameText.setText(text);
	}

	public String getPriceText()
	{
		return priceText.getText();
	}

	public void setPriceText(String text)
	{
		this.priceText.setText(text);
	}

	public String getDescriptionText()
	{
		return descriptionText.getText();
	}

	public void setDescriptionText(String text)
	{
		this.descriptionText.setText(text);
	}

	public String getNumPassengersText()
	{
		return numPassengersText.getText();
	}

	public void setNumPassengersText(String text)
	{
		this.numPassengersText.setText(text);
	}
}
