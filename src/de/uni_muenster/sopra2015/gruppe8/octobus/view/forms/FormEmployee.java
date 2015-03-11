package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;

/**
 * Form used to add and edit employees.
 */
public class FormEmployee extends FormGeneral
{
	private ControllerFormEmployee controllerFormEmployee;

	private FieldText tfName;
	private FieldText tfFirstName;
	private FieldText tfAddress;
	private FieldNumber tfZipCode;
	private FieldText tfCity;
	private FieldDate tfBirthDate;
	private FieldText tfPhone;
	private FieldEmail tfMail;
	private AreaText tfNote;
	private FieldText tfUsername;
	private JCheckBox cbScheduleManager;
	private JCheckBox cbNetworkPlaner;
	private JCheckBox cbBusDriver;
	private JCheckBox cbTicketPlaner;
	private JCheckBox cbHRManager;

	/*
	 * the buttons for save, cancel and reset password
	 */
	private JButton btSave;
	private JButton btCancel;
	private JButton btPassword;

	/**
	 * Creates a new FormEmployee-Object
	 *
	 * @param parent the parent-frame
	 * @param objectID the id of the selected employee
	 */
	public FormEmployee(Frame parent, int objectID)
	{
		super(parent, "");

		getRootPane().setBorder(new EmptyBorder(10,10,10,10));

		if(objectID == -1)
			setTitle("Mitarbeiter anlegen");
		else
			setTitle("Mitarbeiter ändern");

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		controllerFormEmployee = new ControllerFormEmployee(this, objectID);

		setLayout(new GridBagLayout());
		GridBagConstraints cstLabel = new GridBagConstraints();
		GridBagConstraints cstTextField = new GridBagConstraints();

		//general constraints
		cstTextField.fill = GridBagConstraints.HORIZONTAL;
		cstLabel.fill = GridBagConstraints.HORIZONTAL;
		cstLabel.ipadx = 10;
		cstLabel.ipady = 20;
		cstLabel.anchor = GridBagConstraints.LINE_START;
		cstLabel.weighty = 1;

		cstLabel.gridwidth = 6;
		cstLabel.gridx = 0;
		cstLabel.gridy = 0;
		String strExplanation = "Bitte geben Sie die Daten des Mitarbeiters ein.";
		JLabel lbExplanation = new JLabel(strExplanation);
		add(lbExplanation, cstLabel);

		cstLabel.gridwidth = 1;
		cstLabel.gridx = 0;
		cstLabel.gridy = 1;
		cstLabel.weightx = 0.1;
		JLabel lbName = new JLabel("Nachname");
		add(lbName, cstLabel);

		cstTextField.gridwidth = 5;
		cstTextField.gridx = 1;
		cstTextField.gridy = 1;
		cstTextField.weightx = 1;
		tfName = new FieldText(200);
		add(tfName, cstTextField);

		cstLabel.gridy = 2;
		JLabel lbFirstName = new JLabel("Vorname");
		add(lbFirstName, cstLabel);

		cstTextField.gridy = 2;
		tfFirstName = new FieldText(200);
		add(tfFirstName, cstTextField);

		cstLabel.gridy=3;
		JLabel lbAddressComplete = new JLabel("Anschrift");
		add(lbAddressComplete, cstLabel);

		cstLabel.gridy = 3;
		cstLabel.gridx = 1;
		cstLabel.gridwidth = 3;
		cstLabel.ipadx = 0;
		JLabel lbAddress = new JLabel("Adresse");
		add(lbAddress, cstLabel);

		cstTextField.gridy = 3;
		cstTextField.gridx = 4;
		tfAddress = new FieldText(200);
		add(tfAddress, cstTextField);

		cstLabel.gridy = 4;
		cstLabel.weightx=0.1;
		cstLabel.gridwidth = 1;
		JLabel lbZipCode = new JLabel("PLZ");
		add(lbZipCode, cstLabel);

		cstTextField.gridy = 4;
		cstTextField.weightx = 0.2;
		cstTextField.gridwidth = 1;
		cstTextField.gridx=2;
		tfZipCode = new FieldNumber(10);
		tfZipCode.setColumns(5);
		add(tfZipCode, cstTextField);

		cstTextField.weightx=0.1;
		cstTextField.gridx=3;
		JPanel plEmpty = new JPanel();
		add(plEmpty, cstTextField);

		cstLabel.gridx = 4;
		cstLabel.ipadx = 0;
		JLabel lbCity = new JLabel("Ort");
		add(lbCity, cstLabel);

		cstTextField.gridx = 5;
		cstTextField.weightx = 1;
		tfCity = new FieldText(200);
		add(tfCity, cstTextField);

		cstLabel.weightx=1;
		cstLabel.ipadx = 20;
		cstTextField.weightx=1;

		cstLabel.gridy = 5;
		cstLabel.gridx = 0;
		JLabel lbBirthDate = new JLabel("Geburtsdatum");
		add(lbBirthDate, cstLabel);

		cstTextField.gridy = 5;
		cstTextField.gridwidth = 5;
		cstTextField.gridx = 1;
		tfBirthDate = new FieldDate();
		add(tfBirthDate, cstTextField);

		cstLabel.gridy = 6;
		JLabel lbPhone = new JLabel("Telefon");
		add(lbPhone, cstLabel);

		cstTextField.gridy = 6;
		tfPhone = new FieldText();
		add(tfPhone, cstTextField);

		cstLabel.gridy = 7;
		JLabel lbMail = new JLabel("E-Mail");
		add(lbMail, cstLabel);

		cstTextField.gridy = 7;
		tfMail = new FieldEmail();
		add(tfMail, cstTextField);

		cstLabel.gridy = 8;
		cstLabel.weighty = 2;
		JLabel lbNote = new JLabel("Bemerkung");
		add(lbNote, cstLabel);

		cstTextField.gridy = 8;
		tfNote = new AreaText();
		tfNote.setRows(2);
		JScrollPane scrollPane = new JScrollPane(tfNote);
		add(scrollPane, cstTextField);

		cstLabel.gridy = 9;
		JLabel lbUserName = new JLabel("Benutzername");
		add(lbUserName, cstLabel);

		cstTextField.gridy = 9;
		tfUsername = new FieldText(10);
		//Disable username-field if editing an employee
		if(objectID != -1)
			tfUsername.setEnabled(false);
		add(tfUsername, cstTextField);

		cstLabel.gridy = 10;
		cstLabel.ipady = 0;
		JLabel lbRole = new JLabel("Rollen");
		add(lbRole, cstLabel);

		cstTextField.gridy = 10;
		cbScheduleManager = new JCheckBox("EinsatzPlaner");
		add(cbScheduleManager, cstTextField);

		cstTextField.gridy = 11;
		cbNetworkPlaner = new JCheckBox("Netzplaner");
		add(cbNetworkPlaner, cstTextField);

		cstTextField.gridy = 12;
		cbBusDriver = new JCheckBox("Busfahrer");
		add(cbBusDriver, cstTextField);

		cstTextField.gridy = 13;
		cbTicketPlaner = new JCheckBox("Fahrkartenplaner");
		add(cbTicketPlaner, cstTextField);

		cstTextField.gridy = 14;
		cbHRManager = new JCheckBox("Personalleiter");
		add(cbHRManager,cstTextField);

		cstLabel.gridy = 15;
		cstLabel.ipadx = 0;
		cstLabel.ipady = 0;
		cstLabel.gridwidth = 2;
		btSave = new JButton("Speichern");
		add(btSave, cstLabel);

		if(objectID != -1)
		{
			cstLabel.gridx = 2;
			cstLabel.gridwidth = 3;
			btPassword = new JButton("Passwort zurücksetzen");
			add(btPassword, cstLabel);
			btPassword.addActionListener(e->
					controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_RESET_PASSWORD));
		}

		cstLabel.gridx = 5;
		cstLabel.gridwidth = 1;
		btCancel = new JButton("Abbrechen");
		add(btCancel, cstLabel);

		//Add events to buttons
		btSave.addActionListener(e ->
				controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_SAVE));
		btCancel.addActionListener(e ->
				controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_CANCEL));

		controllerFormEmployee.insertValuesIntoForm();

		pack();
		setSize(new Dimension(400, 540));
		setLocationRelativeTo(null);
	}

	public String getFirstName()
	{
		return tfFirstName.getText();
	}

	public void setFirstName(String text)
	{
		this.tfFirstName.setText(text);
	}

	public String getLastName()
	{
		return tfName.getText();
	}

	public void setLastName(String text)
	{
		this.tfName.setText(text);
	}

	public String getAddress()
	{
		return tfAddress.getText();
	}

	public void setAddress(String text)
	{
		this.tfAddress.setText(text);
	}

	public String getZipCode()
	{
		return tfZipCode.getText();
	}

	public void setZipCode(String text)
	{
		this.tfZipCode.setText(text);
	}

	public String getCity()
	{
		return tfCity.getText();
	}

	public void setCity(String text)
	{
		this.tfCity.setText(text);
	}

	public Date getBirthDate()
	{
		return tfBirthDate.getDate();
	}

	public void setBirthDate(Date date)
	{
		this.tfBirthDate.setDate(date);
	}

	public String getPhone()
	{
		return tfPhone.getText();
	}

	public void setPhone(String text)
	{
		this.tfPhone.setText(text);
	}

	public void setEMail(String text)
	{
		this.tfMail.setEmail(text);
	}

	public String getEMail()
	{
		return this.tfMail.getEmail();
	}

	public String getNote()
	{
		return tfNote.getText();
	}

	public void setNote(String text)
	{
		this.tfNote.setText(text);
	}

	public String getUsername()
	{
		return tfUsername.getText();
	}

	public void setUsername(String text)
	{
		this.tfUsername.setText(text);
	}

	public boolean getScheduleManager()
	{
		return cbScheduleManager.isSelected();
	}

	public void setScheduleManager(boolean state)
	{
		this.cbScheduleManager.setSelected(state);
	}

	public boolean getNetworkPlaner()
	{
		return cbNetworkPlaner.isSelected();
	}

	public void setNetworkPlaner(boolean state)
	{
		this.cbNetworkPlaner.setSelected(state);
	}

	public boolean getBusDriver()
	{
		return cbBusDriver.isSelected();
	}

	public void setBusDriver(boolean state)
	{
		this.cbBusDriver.setSelected(state);
	}

	public boolean getTicketPlaner()
	{
		return cbTicketPlaner.isSelected();
	}

	public void setTicketPlaner(boolean state)
	{
		this.cbTicketPlaner.setSelected(state);
	}

	public boolean getHRManager()
	{
		return cbHRManager.isSelected();
	}

	public void setHRManager(boolean state)
	{
		this.cbHRManager.setSelected(state);
	}
}
