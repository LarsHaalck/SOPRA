package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         lenght of textfield
 */
public class FormEmployee extends FormGeneral
{
	private ControllerFormEmployee controllerFormEmployee;

	private JPanel plExplanation = new JPanel();
	private String strExplanation = "Bitte geben Sie die Daten des Mitarbeiters ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);
	private JPanel plRight = new JPanel();
	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();

	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

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
	private JTextArea tfNote = new JTextArea();
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

	private JLabel lbRole = new JLabel("Rollen:");

	private JLabel lbScheduleManager = new JLabel("Einsatzplaner");
	private JLabel lbNetworkPlaner = new JLabel("Netzpaner");
	private JLabel lbBusDriver = new JLabel("Busfahrer");
	private JLabel lbTicketPlaner = new JLabel("Fahrkartenplaner");
	private JLabel lbHRManager = new JLabel("Personalleiter");

	private JCheckBox cbScheduleManager = new JCheckBox();
	private JCheckBox cbNetworkPlaner = new JCheckBox();
	private JCheckBox cbBusDriver = new JCheckBox();
	private JCheckBox cbTicketPlaner = new JCheckBox();
	private JCheckBox cbHRManager = new JCheckBox();

	private JPanel plScheduleManager = new JPanel();
	private JPanel plNetworkPlaner = new JPanel();
	private JPanel plBusDriver = new JPanel();
	private JPanel plTicketPlaner = new JPanel();
	private JPanel plHRManager = new JPanel();

	private int iTextHeight = 25;
	private int iAreaHeight = 50;
	private int iTextWidth = 150;
	private int iBoxWidth = 50;

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
		plRight.setBorder(new EmptyBorder(new Insets(0, 50, 0, 50)));

		Border border = plRight.getBorder();
		Border myBorder = new LineBorder(Color.black, 1);
		plRight.setBorder(new CompoundBorder(border, myBorder));
		plRight.add(plScheduleManager);
		plRight.add(plNetworkPlaner);
		plRight.add(plBusDriver);
		plRight.add(plTicketPlaner);
		plRight.add(plHRManager);

		plScheduleManager.add(lbScheduleManager);
		plScheduleManager.add(cbScheduleManager);
		plNetworkPlaner.add(lbNetworkPlaner);
		plNetworkPlaner.add(cbNetworkPlaner);
		plBusDriver.add(lbBusDriver);
		plBusDriver.add(cbBusDriver);
		plTicketPlaner.add(lbTicketPlaner);
		plTicketPlaner.add(cbTicketPlaner);
		plHRManager.add(lbHRManager);
		plHRManager.add(cbHRManager);

		lbScheduleManager.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbScheduleManager.setPreferredSize(new Dimension(iBoxWidth, iTextHeight));
		lbNetworkPlaner.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbNetworkPlaner.setPreferredSize(new Dimension(iBoxWidth, iTextHeight));
		lbBusDriver.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbBusDriver.setPreferredSize(new Dimension(iBoxWidth, iTextHeight));
		lbTicketPlaner.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbTicketPlaner.setPreferredSize(new Dimension(iBoxWidth, iTextHeight));
		lbHRManager.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbHRManager.setPreferredSize(new Dimension(iBoxWidth, iTextHeight));

		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);

		controllerFormEmployee.insertValuesIntoForm();

		pack();
	}

	public String getTfFirstName()
	{
		return tfFirstName.getText();
	}

	public void setTfFirstName(String text)
	{
		this.tfFirstName.setText(text);
	}

	public String getTfName()
	{
		return tfName.getText();
	}

	public void setTfName(String text)
	{
		this.tfName.setText(text);
	}

	public String getTfAddress()
	{
		return tfAddress.getText();
	}

	public void setTfAddress(String text)
	{
		this.tfAddress.setText(text);
	}

	public String getTfZipCode()
	{
		return tfZipCode.getText();
	}

	public void setTfZipCode(String text)
	{
		this.tfZipCode.setText(text);
	}

	public String getTfCity()
	{
		return tfCity.getText();
	}

	public void setTfCity(String text)
	{
		this.tfCity.setText(text);
	}

	public String getTfBirthDate()
	{
		return tfBirthDate.getText();
	}

	public void setTfBirthDate(String text)
	{
		this.tfBirthDate.setText(text);
	}

	public String getTfPhone()
	{
		return tfPhone.getText();
	}

	public void setTfPhone(String text)
	{
		this.tfPhone.setText(text);
	}

	public String getTfNote()
	{
		return tfNote.getText();
	}

	public void setTfNote(String text)
	{
		this.tfNote.setText(text);
	}

	public String getTfUsername()
	{
		return tfUsername.getText();
	}

	public void setTfUsername(String text)
	{
		this.tfUsername.setText(text);
	}

	public boolean getCbScheduleManager()
	{
		return cbScheduleManager.isSelected();
	}

	public void setCbScheduleManager(boolean state)
	{
		this.cbScheduleManager.setSelected(state);
	}

	public boolean getCbNetworkPlaner()
	{
		return cbNetworkPlaner.isSelected();
	}

	public void setCbNetworkPlaner(boolean state)
	{
		this.cbNetworkPlaner.setSelected(state);
	}

	public boolean getCbBusDriver()
	{
		return cbBusDriver.isSelected();
	}

	public void setCbBusDriver(boolean state)
	{
		this.cbBusDriver.setSelected(state);
	}

	public boolean getCbTicketPlaner()
	{
		return cbTicketPlaner.isSelected();
	}

	public void setCbTicketPlaner(boolean state)
	{
		this.cbTicketPlaner.setSelected(state);
	}

	public boolean getCbHRManager()
	{
		return cbHRManager.isSelected();
	}

	public void setCbHRManager(boolean state)
	{
		this.cbHRManager.isSelected();
	}
}
