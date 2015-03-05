package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldDate;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Form used for adding and editing buses.
 */
public class FormBus extends FormGeneral
{
	private ControllerFormBus controllerFormBus;

	private FieldText tfLicencePlate;
	private FieldNumber tfNumberOfSeats;
	private FieldNumber tfStandingRoom;
	private FieldText tfManufacturer;
	private FieldText tfModel;
	private FieldDate tfNextInspectionDue;
	private JCheckBox cbArticulatedBus = new JCheckBox("Bus mit Gelenk");
	private JButton btSave;
	private JButton btCancel;

	public FormBus(Frame parent, int objectID)
	{
		super(parent, "");

		getRootPane().setBorder(new EmptyBorder(10,10,10,10));

		if(objectID == -1)
			setTitle("Bus anlegen");
		else
			setTitle("Bus ändern");
		setResizable(false);

		controllerFormBus = new ControllerFormBus(this, objectID);

		btSave = new JButton("Speichern");
		btSave.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_SAVE);
		});
		btCancel = new JButton("Abbrechen");
		btCancel.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_CANCEL);
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
		String strExplanation = "Bitte geben Sie die Daten des Busses ein.";
		JLabel lbExplanation = new JLabel(strExplanation);
		add(lbExplanation, cstLabel);

		cstLabel.gridwidth = 2;
		cstLabel.gridx = 0;
		cstLabel.gridy = 1;
		JLabel lbLicencePlate = new JLabel("Kennzeichen");
		add(lbLicencePlate, cstLabel);

		cstTextField.gridwidth = 2;
		cstTextField.gridx = 2;
		cstTextField.gridy = 1;
		tfLicencePlate = new FieldText(10);
		add(tfLicencePlate, cstTextField);

		cstLabel.gridy = 2;
		JLabel lbNumberOfSeats = new JLabel("Anzahl Sitzplätze");
		add(lbNumberOfSeats, cstLabel);

		cstTextField.gridy = 2;
		//TODO 3 seats
		tfNumberOfSeats = new FieldNumber();
		add(tfNumberOfSeats, cstTextField);

		cstLabel.gridy = 3;
		JLabel lbStandingRoom = new JLabel("Anzahl Stehplätze");
		add(lbStandingRoom, cstLabel);

		cstTextField.gridy = 3;
		//TODO 3
		tfStandingRoom = new FieldNumber();
		add(tfStandingRoom, cstTextField);

		cstLabel.gridy = 4;
		JLabel lbManufacturer = new JLabel("Hersteller");
		add(lbManufacturer, cstLabel);

		cstTextField.gridy = 4;
		tfManufacturer = new FieldText();
		add(tfManufacturer, cstTextField);

		cstLabel.gridy = 5;
		JLabel lbModel = new JLabel("Modell");
		add(lbModel, cstLabel);

		cstTextField.gridy = 5;
		tfModel = new FieldText();
		add(tfModel, cstTextField);

		cstLabel.gridy = 6;
		JLabel lbNextInspectionDue = new JLabel("Nächste Inspektion");
		add(lbNextInspectionDue, cstLabel);

		cstTextField.gridy = 6;
		tfNextInspectionDue = new FieldDate();
		add(tfNextInspectionDue, cstTextField);

		cstTextField.gridwidth = 2;
		cstTextField.gridy = 7;
		add(cbArticulatedBus, cstTextField);

		GridBagConstraints cstButton = new GridBagConstraints();
		cstButton.gridwidth = 1;

		cstButton.gridx = 0;
		cstButton.gridy = 8;
		cstButton.anchor = GridBagConstraints.LAST_LINE_START;
		add(btCancel, cstButton);

		cstButton.gridx = 3;
		cstButton.gridy = 8;
		cstButton.anchor = GridBagConstraints.LAST_LINE_END;
		add(btSave, cstButton);

		pack();

		setLocationRelativeTo(null);

		controllerFormBus.insertValuesIntoForm();
	}

	public String getLicencePlate()
	{
		return tfLicencePlate.getText();
	}

	public String getNumberOfSeats()
	{
		return tfNumberOfSeats.getText();
	}

	public String getStandingRoom()
	{
		return tfStandingRoom.getText();
	}

	public String getManufacturer()
	{
		return tfManufacturer.getText();
	}

	public String getModel()
	{
		return tfModel.getText();
	}

	public String getNextInspectionDue()
	{
		return tfNextInspectionDue.getText();
	}

	public boolean getArticulatedBus()
	{
		return cbArticulatedBus.isSelected();
	}

	public void setLicencePlate(String text)
	{
		this.tfLicencePlate.setText(text);
	}

	public void setNumberOfSeats(String text)
	{
		this.tfNumberOfSeats.setText(text);
	}

	public void setStandingRoom(String text)
	{
		this.tfStandingRoom.setText(text);
	}

	public void setManufacturer(String text)
	{
		this.tfManufacturer.setText(text);
	}

	public void setModel(String text)
	{
		this.tfModel.setText(text);
	}

	public void setNextInspectionDue(String text)
	{
		this.tfNextInspectionDue.setText(text);
	}

	public void setArticulatedBus(Boolean state)
	{
		this.cbArticulatedBus.setSelected(state);
	}

}
