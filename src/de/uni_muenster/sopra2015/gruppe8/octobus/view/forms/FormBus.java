package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         lenght of textfield
 */
public class FormBus extends JDialog
{
	private ControllerFormBus controllerFormBus;

	private String explanationText = "Bitte geben Sie die Daten des Busses ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JPanel midPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private JLabel licencePlate = new JLabel("Kennzeichen");
	private JLabel numberOfSeats = new JLabel("Anzahl Sitzplätze");
	private JLabel standingRoom = new JLabel("Anzahl Stehplätze");
	private JLabel manufacturer = new JLabel("Hersteller");
	private JLabel model = new JLabel("Modell");
	private JLabel nextInspectionDue = new JLabel("Nächste Inspektion");
	private JLabel articulatedBus = new JLabel("?"); //????
	private JTextField licencePlateText = new JTextField();
	private JTextField numberOfSeatsText = new JTextField();
	private JTextField standingRoomText = new JTextField();
	private JTextField manufacturerText = new JTextField();
	private JTextField modelText = new JTextField();
	private JTextField nextInspectionDueText = new JTextField();
	private JToggleButton articulatedBusText = new JToggleButton();

	public FormBus()
	{
		setResizable(false);

		controllerFormBus = new ControllerFormBus(this);

		save.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_SAVE);
		});
		cancel.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_CANCEL);
		});

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		explanationPanel.add(explanation);

		midPanel.setLayout(new GridLayout(7, 2));
		midPanel.add(licencePlate);
		midPanel.add(licencePlateText);
		midPanel.add(numberOfSeats);
		midPanel.add(numberOfSeatsText);
		midPanel.add(standingRoom);
		midPanel.add(standingRoomText);
		midPanel.add(manufacturer);
		midPanel.add(manufacturerText);
		midPanel.add(model);
		midPanel.add(modelText);
		midPanel.add(nextInspectionDue);
		midPanel.add(nextInspectionDueText);
		midPanel.add(articulatedBus);
		midPanel.add(articulatedBusText);

		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(save);
		bottomPanel.add(cancel);
	}

	public String getLicencePlateText() //nicht mehr wirklich getter
	{
		return licencePlateText.getText();
	}

	public String getNumberOfSeatsText()
	{
		return numberOfSeatsText.getText();
	}

	public String getStandingRoomText()
	{
		return standingRoomText.getText();
	}

	public String getManufacturerText()
	{
		return manufacturerText.getText();
	}

	public String getModelText()
	{
		return modelText.getText();
	}

	public String getNextInspectionDueText()
	{
		return nextInspectionDueText.getText();
	}

	public String getArticulatedBusText()
	{
		return articulatedBusText.getText();
	}
}
