package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldDate;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/** @author Patricia Schinke
 * lenght of textfield
 */
public class FormTicket extends FormGeneral
{
	private ControllerFormTicket controllerFormTicket;

	/*
	 * at the top of the form is an explanation what you should do
	 */
	private String strExplanation = "Bitte geben Sie die Daten des Tickets ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);

	/*
	 * every input has an own label, inputfield and panel
	 */

	private JTextField tfName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNumPassengers = new JTextField();
	private JTextArea taDescription = new JTextArea();

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");


	public FormTicket(Frame parent, int objectID)
	{
		super(parent, "");

		getRootPane().setBorder(new EmptyBorder(10,10,10,10));

		if(objectID == -1)
			setTitle("Ticket anlegen");
		else
			setTitle("Ticket ändern");
		setResizable(false);

		controllerFormTicket = new ControllerFormTicket(this);

		btSave.addActionListener(e ->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_CANCEL);
		});




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
		tfName = new FieldText(10);
		add(tfName, cstTextField);

		cstLabel.gridy = 2;
		JLabel lbPrice = new JLabel("Preis");
		add(lbPrice, cstLabel);

		cstTextField.gridy = 2;
		tfPrice = new FieldNumber(3);
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
		JScrollPane scrollPane = new JScrollPane(taDescription);
		add(scrollPane, cstTextField);

		GridBagConstraints cstButton = new GridBagConstraints();
		cstButton.gridwidth = 1;

		cstButton.gridx = 0;
		cstButton.gridy = 5;
		cstButton.anchor = GridBagConstraints.LAST_LINE_START;
		add(btCancel, cstButton);

		cstButton.gridx = 3;
		cstButton.gridy = 5;
		cstButton.anchor = GridBagConstraints.LAST_LINE_END;
		add(btSave, cstButton);

		pack();
		setLocationRelativeTo(null);
	}

	public String getNameTicket()
	{
		return tfName.getText();
	}

	public void setNameTicket(String text)
	{
		this.tfName.setText(text);
	}

	public String getPrice()
	{
		return tfPrice.getText();
	}

	public void setPrice(String text)
	{
		this.tfPrice.setText(text);
	}

	public String getDescription()
	{
		return taDescription.getText();
	}

	public void setDescription(String text)
	{
		this.taDescription.setText(text);
	}

	public String getNumPassengers()
	{
		return tfNumPassengers.getText();
	}

	public void setNumPassengers(String text)
	{
		this.tfNumPassengers.setText(text);
	}
}
