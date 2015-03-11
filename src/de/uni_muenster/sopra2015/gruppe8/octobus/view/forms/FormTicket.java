package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.AreaText;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldPrice;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * Form used to add and edit tickets.
 */
public class FormTicket extends FormGeneral
{
	private ControllerFormTicket controllerFormTicket;

	private FieldText tfName;
	private FieldPrice tfPrice;
	private FieldNumber tfNumPassengers;
	private AreaText taDescription;

	/*
	 * Buttons for save and cancel.
	 */
	private JButton btSave;
	private JButton btCancel;

	/**
	 * Creates a new FormTicket-Object.
	 *
	 * @param parent the prent-frame
	 * @param objectID the id of the selected ticket
	 */
	public FormTicket(Frame parent, int objectID)
	{
		super(parent, "");

		getRootPane().setBorder(new EmptyBorder(10,10,10,10));

		if(objectID == -1)
			setTitle("Ticket anlegen");
		else
			setTitle("Ticket ändern");
		setResizable(false);

		controllerFormTicket = new ControllerFormTicket(this, objectID);






		setLayout(new GridBagLayout());
		GridBagConstraints cstLabel = new GridBagConstraints();
		GridBagConstraints cstTextField = new GridBagConstraints();

		//general constraints
		cstTextField.fill = GridBagConstraints.HORIZONTAL;
		cstLabel.ipadx = 20;
		cstLabel.ipady = 20;
		cstLabel.anchor = GridBagConstraints.LINE_START;

		cstLabel.gridwidth = 4;
		cstLabel.gridx = 0;
		cstLabel.gridy = 0;
		String strExplanation = "Bitte geben Sie die Daten des Tickets ein.";
		JLabel lbExplanation = new JLabel(strExplanation);
		add(lbExplanation, cstLabel);

		cstLabel.gridwidth = 2;
		cstLabel.gridx = 0;
		cstLabel.gridy = 1;
		JLabel lbName = new JLabel("Name");
		add(lbName, cstLabel);

		cstTextField.gridwidth = 2;
		cstTextField.gridx = 2;
		cstTextField.gridy = 1;
		tfName = new FieldText();
		add(tfName, cstTextField);

		cstLabel.gridy = 2;
		JLabel lbPrice = new JLabel("Preis");
		add(lbPrice, cstLabel);

		cstTextField.gridy = 2;
		tfPrice = new FieldPrice(6);
		add(tfPrice, cstTextField);

		cstLabel.gridy = 3;
		JLabel lbNumPassengers = new JLabel("Anzahl Fahrgäste");
		add(lbNumPassengers, cstLabel);

		cstTextField.gridy = 3;
		tfNumPassengers = new FieldNumber(3);
		add(tfNumPassengers, cstTextField);

		cstLabel.ipady=80;
		cstLabel.gridy = 4;
		JLabel lbDescription = new JLabel("Beschreibung");
		add(lbDescription, cstLabel);

		cstTextField.ipady=50;
		cstTextField.gridy = 4;
		taDescription = new AreaText();
		taDescription.setRows(2);
		JScrollPane scrollPane = new JScrollPane(taDescription);
		add(scrollPane, cstTextField);

		GridBagConstraints cstButton = new GridBagConstraints();
		cstButton.gridwidth = 1;

		cstButton.gridx = 0;
		cstButton.gridy = 5;
		cstButton.anchor = GridBagConstraints.LAST_LINE_START;
		btSave = new JButton("Speichern");
		add(btSave, cstButton);

		cstButton.gridx = 3;
		cstButton.gridy = 5;
		cstButton.anchor = GridBagConstraints.LAST_LINE_END;
		btCancel = new JButton("Abbrechen");
		add(btCancel, cstButton);

		btSave.addActionListener(e ->
				controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_SAVE));
		btCancel.addActionListener(e ->
				controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_CANCEL));

		pack();
		setLocationRelativeTo(null);
		controllerFormTicket.insertValuesIntoForm();
	}

	public String getName()
	{
		return tfName.getText();
	}

	public void setName(String text)
	{
		this.tfName.setText(text);
	}

	public int getPrice()
	{
		return tfPrice.getPrice();
	}

	public void setPrice(int price)
	{
		this.tfPrice.setPrice(price);
	}

	public String getDescription()
	{
		return taDescription.getText();
	}

	public void setDescription(String text)
	{
		this.taDescription.setText(text);
	}

	public int getNumPassengers()
	{
		return tfNumPassengers.getNumber();
	}

	public void setNumPassengers(int number)
	{
		this.tfNumPassengers.setNumber(number);
	}
}
