package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         lenght of textfield
 */
public class FormBus extends FormGeneral
{
	private ControllerFormBus controllerFormBus;

	private String explanationText = "Bitte geben Sie die Daten des Busses ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JPanel midPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private JLabel licencePlate = new JLabel("Kennzeichen");
	private JLabel numberOfSeats = new JLabel("Anzahl Sitzplätze");
	private JLabel standingRoom = new JLabel("Anzahl Stehplätze");
	private JLabel manufacturer = new JLabel("Hersteller");
	private JLabel model = new JLabel("Modell");
	private JLabel nextInspectionDue = new JLabel("Nächste Inspektion");
	private JLabel articulatedBus = new JLabel("Beweglicher Bus");

	private JTextField licencePlateText = new JTextField();
	private JTextField numberOfSeatsText = new JTextField();
	private JTextField standingRoomText = new JTextField();
	private JTextField manufacturerText = new JTextField();
	private JTextField modelText = new JTextField();
	private JTextField nextInspectionDueText = new JTextField();
	private JCheckBox articulatedBusText = new JCheckBox();

	private JPanel licencePlatePanel = new JPanel();
	private JPanel numberOfSeatsPanel = new JPanel();
	private JPanel standingRoomPanel = new JPanel();
	private JPanel manufacturerPanel = new JPanel();
	private JPanel modelPanel = new JPanel();
	private JPanel nextInspectionDuePanel = new JPanel();
	private JPanel articulatedBusPanel = new JPanel();

	public FormBus(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Bus anlegen");
		else
			setTitle("Bus ändern");
		setResizable(false);

		controllerFormBus = new ControllerFormBus(this, objectID);
		midPanel.setBorder(new EmptyBorder(new Insets(0, 100, 0, 100)));
		
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
		add(rightPanel, BorderLayout.EAST);
		add(leftPanel, BorderLayout.WEST);

		explanationPanel.setPreferredSize(new Dimension(924, 100));
		explanationPanel.setBorder(new EmptyBorder(new Insets(40,0,40,0)));
		explanationPanel.add(explanation);

		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		midPanel.add(licencePlatePanel);
		licencePlatePanel.setLayout(new FlowLayout());
		midPanel.add(numberOfSeatsPanel);
		numberOfSeatsPanel.setLayout(new FlowLayout());
		midPanel.add(standingRoomPanel);
		standingRoomPanel.setLayout(new FlowLayout());
		midPanel.add(manufacturerPanel);
		manufacturerPanel.setLayout(new FlowLayout());
		midPanel.add(modelPanel);
		modelPanel.setLayout(new FlowLayout());
		midPanel.add(nextInspectionDuePanel);
		nextInspectionDuePanel.setLayout(new FlowLayout());
		midPanel.add(articulatedBusPanel);
		articulatedBusPanel.setLayout(new FlowLayout());

		licencePlatePanel.add(licencePlate);
		licencePlatePanel.add(licencePlateText);
		numberOfSeatsPanel.add(numberOfSeats);
		numberOfSeatsPanel.add(numberOfSeatsText);
		standingRoomPanel.add(standingRoom);
		standingRoomPanel.add(standingRoomText);
		manufacturerPanel.add(manufacturer);
		manufacturerPanel.add(manufacturerText);
		modelPanel.add(model);
		modelPanel.add(modelText);
		nextInspectionDuePanel.add(nextInspectionDue);
		nextInspectionDuePanel.add(nextInspectionDueText);
		articulatedBusPanel.add(articulatedBus);
		articulatedBusPanel.add(articulatedBusText);

		licencePlate.setPreferredSize(new Dimension(150, 20));
		numberOfSeats.setPreferredSize(new Dimension(150, 20));
		standingRoom.setPreferredSize(new Dimension(150, 20));
		manufacturer.setPreferredSize(new Dimension(150, 20));
		model.setPreferredSize(new Dimension(150, 20));
		nextInspectionDue.setPreferredSize(new Dimension(150, 20));
		articulatedBus.setPreferredSize(new Dimension(150, 20));

		licencePlateText.setPreferredSize(new Dimension(150, 20));
		numberOfSeatsText.setPreferredSize(new Dimension(150, 20));
		standingRoomText.setPreferredSize(new Dimension(150, 20));
		manufacturerText.setPreferredSize(new Dimension(150, 20));
		modelText.setPreferredSize(new Dimension(150, 20));
		nextInspectionDueText.setPreferredSize(new Dimension(150, 20));
		articulatedBusText.setPreferredSize(new Dimension(150, 20));

		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.setPreferredSize(new Dimension(924, 100));
		bottomPanel.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		bottomPanel.add(save);
		bottomPanel.add(cancel);

		controllerFormBus.insertValuesIntoForm();

		pack();
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

	public boolean getArticulatedBusText()
	{
		return articulatedBusText.isSelected();
	}

	public void setLicencePlateText(String text)
	{
		this.licencePlateText.setText(text);
	}

	public void setNumberOfSeatsText(String text)
	{
		this.numberOfSeatsText.setText(text);
	}

	public void setStandingRoomText(String text)
	{
		this.standingRoomText.setText(text);
	}

	public void setManufacturerText(String text)
	{
		this.manufacturerText.setText(text);
	}

	public void setModelText(String text)
	{
		this.modelText.setText(text);
	}

	public void setNextInspectionDueText(String text)
	{
		this.nextInspectionDueText.setText(text);
	}

	public void setArticulatedBusText(Boolean state)
	{
		this.articulatedBusText.setSelected(state);
	}

}
