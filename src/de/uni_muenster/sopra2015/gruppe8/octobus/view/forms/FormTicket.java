package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

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
	private JPanel plExplanation = new JPanel();

	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();

	/*
	 * every input has an own label, inputfield and panel
	 */
	private JLabel lbName = new JLabel("Name");
	private JLabel lbPrice = new JLabel("Preis");
	private JLabel lbNumPassengers = new JLabel("Anzahl Fahrgäste");
	private JLabel lbDescription = new JLabel("Beschreibung");

	private JTextField tfName = new JTextField();
	private JTextField tfPrice = new JTextField();
	private JTextField tfNumPassengers = new JTextField();
	private JTextArea taDescription = new JTextArea();

	private JPanel plName = new JPanel();
	private JPanel plPrice = new JPanel();
	private JPanel plNumPassengers = new JPanel();
	private JPanel plDescription = new JPanel();

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

	private int iTextHeight = 25;
	private int iAreaHeight = 50;
	private int iTextWidth = 150;

	public FormTicket(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Ticket anlegen");
		else
			setTitle("Ticket ändern");

		controllerFormTicket = new ControllerFormTicket(this);

		btSave.addActionListener(e ->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormTicket.buttonPressed(EmitterButton.FORM_TICKET_CANCEL);
		});

		setLayout(new BorderLayout());
		add(plExplanation, BorderLayout.NORTH);
		add(plMid, BorderLayout.CENTER);
		add(plBottom, BorderLayout.SOUTH);

		plExplanation.setPreferredSize(new Dimension(924, 100));
		plExplanation.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		plExplanation.add(lbExplanation);

		plMid.setLayout(new BoxLayout(plMid, BoxLayout.Y_AXIS));
		plMid.add(plName);
		plName.setLayout(new FlowLayout());
		plMid.add(plPrice);
		plPrice.setLayout(new FlowLayout());
		plMid.add(plNumPassengers);
		plNumPassengers.setLayout(new FlowLayout());
		plMid.add(plDescription);
		plDescription.setLayout(new FlowLayout());

		plName.add(lbName);
		plName.add(tfName);
		plPrice.add(lbPrice);
		plPrice.add(tfPrice);
		plNumPassengers.add(lbNumPassengers);
		plNumPassengers.add(tfNumPassengers);
		plDescription.add(lbDescription);

		JScrollPane scrollPane = new JScrollPane(taDescription);
		plDescription.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(iTextWidth, iAreaHeight));

		lbName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbPrice.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfPrice.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbNumPassengers.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfNumPassengers.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbDescription.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);

		pack();
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
