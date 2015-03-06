package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.AreaText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class FormEmployee extends FormGeneral
{
	private ControllerFormEmployee controllerFormEmployee;

	/*
	 * at the top of the form is an explanation what you should do
	 */
	private JPanel plExplanation = new JPanel();
	private String strExplanation = "Bitte geben Sie die Daten des Mitarbeiters ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);
	private JPanel plRight = new JPanel();
	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();

	/*
	 * every input has an own label, inputfield and panel
	 */
	private JLabel lbName = new JLabel("Name");
	private JLabel lbFirstName = new JLabel("Vorname");
	private JLabel lbAddress = new JLabel("Adresse");
	private JLabel lbZipCode = new JLabel("PLZ");
	private JLabel lbCity = new JLabel("Ort");
	private JLabel lbBirthDate = new JLabel("Geburtsdatum");
	private JLabel lbPhone = new JLabel("Telefon");
	private JLabel lbMail = new JLabel("Mail-Adresse");
	private JLabel lbNote = new JLabel("Bemerkung");
	private JLabel lbUsername = new JLabel("Benutzername");

	private JTextField tfName = new JTextField();
	private JTextField tfFirstName = new JTextField();
	private JTextField tfAddress = new JTextField();
	private JTextField tfZipCode = new JTextField();
	private JTextField tfCity = new JTextField();
	private JTextField tfBirthDate = new JTextField();
	private JTextField tfPhone = new JTextField();
	private JTextField tfMail = new JTextField();
	private JTextArea tfNote = new AreaText();
	private JTextField tfUsername = new JTextField();

	private JPanel plName = new JPanel();
	private JPanel plFirstName = new JPanel();
	private JPanel plAddress = new JPanel();
	private JPanel plZipCode = new JPanel();
	private JPanel plCity = new JPanel();
	private JPanel plBirthDate = new JPanel();
	private JPanel plPhone = new JPanel();
	private JPanel plMail = new JPanel();
	private JPanel plNote = new JPanel();
	private JPanel plUsername = new JPanel();
	private JPanel plRemarkText = new JPanel();

	/*
	 * we have a panel with checkboxes for  the roles
	 */
	private JLabel lbRole = new JLabel("Rollen:");

	private JCheckBox cbScheduleManager = new JCheckBox("Einsatzplaner");
	private JCheckBox cbNetworkPlaner = new JCheckBox("Netzpaner");
	private JCheckBox cbBusDriver = new JCheckBox("Busfahrer");
	private JCheckBox cbTicketPlaner = new JCheckBox("Fahrartenplaner");
	private JCheckBox cbHRManager = new JCheckBox("Personalleiter");

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

	private int iTextHeight = 25;
	private int iAreaHeight = 50;
	private int iTextWidth = 150;

	public FormEmployee(Frame parent, int objectID)
	{
		//super(parent, "Mitarbeiter verwalten");
		super(parent, "");
		if(objectID == -1)
			setTitle("Mitarbeiter anlegen");
		else
			setTitle("Mitarbeiter ändern");

		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		controllerFormEmployee = new ControllerFormEmployee(this, objectID);

		//Add events to buttons
		btSave.addActionListener(e ->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_CANCEL);
		});

		setLayout(new BorderLayout());
		add(plExplanation, BorderLayout.NORTH);
		add(plMid, BorderLayout.CENTER);
		add(plBottom, BorderLayout.SOUTH);

		plExplanation.setPreferredSize(new Dimension(924, 60));
		plExplanation.setBorder(new EmptyBorder(new Insets(30, 0, 30, 0)));
		plExplanation.add(lbExplanation);

		plMid.setLayout(new BoxLayout(plMid, BoxLayout.X_AXIS));

		plMid.setLayout(new BoxLayout(plMid, BoxLayout.Y_AXIS));
		plMid.add(plName);
		plMid.add(plFirstName);
		plMid.add(plAddress);
		plMid.add(plZipCode);
		plMid.add(plCity);
		plMid.add(plBirthDate);
		plMid.add(plPhone);
		plMid.add(plMail);
		plMid.add(plNote);
		plMid.add(plUsername);

		plName.add(lbName);
		plName.add(tfName);
		plFirstName.add(lbFirstName);
		plFirstName.add(tfFirstName);
		plAddress.add(lbAddress);
		plAddress.add(tfAddress);
		plZipCode.add(lbZipCode);
		plZipCode.add(tfZipCode);
		plCity.add(lbCity);
		plCity.add(tfCity);
		plBirthDate.add(lbBirthDate);
		plBirthDate.add(tfBirthDate);
		plPhone.add(lbPhone);
		plPhone.add(tfPhone);
		plMail.add(lbMail);
		plMail.add(tfMail);
		plNote.add(lbNote);
		plNote.add(plRemarkText);
		plUsername.add(lbUsername);
		plUsername.add(tfUsername);

		JScrollPane scrollPane = new JScrollPane(tfNote);
		plRemarkText.add(scrollPane);
		scrollPane.setPreferredSize(new Dimension(iTextWidth, iAreaHeight));

		/*
		 * we have to set the preferred size for the labels and textfields
		 */
		lbName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbFirstName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfFirstName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbAddress.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfAddress.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbZipCode.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfZipCode.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbCity.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfCity.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbBirthDate.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfBirthDate.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbPhone.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfPhone.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbMail.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfMail.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbNote.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbUsername.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfUsername.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		add(plRight, BorderLayout.EAST);
		plRight.setLayout(new BoxLayout(plRight, BoxLayout.Y_AXIS));
		plRight.add(lbRole);
		lbRole.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbRole.setHorizontalAlignment(JLabel.LEFT);

		plRight.setBorder(new EmptyBorder(new Insets(100, 0, 100, 100)));
		plRight.add(cbScheduleManager);
		plRight.add(cbNetworkPlaner);
		plRight.add(cbBusDriver);
		plRight.add(cbTicketPlaner);
		plRight.add(cbHRManager);

		/*
		 * and we have to set the preferred size for the checkboxes
		 */
		cbScheduleManager.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbNetworkPlaner.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbBusDriver.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbTicketPlaner.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbHRManager.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);

		controllerFormEmployee.insertValuesIntoForm();

		pack();
		System.out.println(tfNote.getFont());
		System.out.println(tfFirstName.getFont());
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

	public String getBirthDate()
	{
		return tfBirthDate.getText();
	}

	public void setBirthDate(String text)
	{
		this.tfBirthDate.setText(text);
	}

	public String getPhone()
	{
		return tfPhone.getText();
	}

	public void setPhone(String text)
	{
		this.tfPhone.setText(text);
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
		this.cbHRManager.isSelected();
	}
}
