package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         lenght of textfield
 *         zip an city
 */
public class FormEmployee extends FormGeneral
{
	private ControllerFormEmployee controllerFormEmployee;

	private JPanel explanationPanel = new JPanel();
	private String explanationText = "Bitte geben Sie die Daten des Mitarbeiters ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel rightPanel = new JPanel();
	private JPanel midPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private JLabel firstName = new JLabel("Vorname");
	private JLabel lastName = new JLabel("Name");
	private JLabel address = new JLabel("Adresse");
	private JLabel zip = new JLabel("PLZ");
	private JLabel city = new JLabel("Ort");
	private JLabel date = new JLabel("Geburtsdatum");
	private JLabel phone = new JLabel("Telefon");
	private JLabel mail = new JLabel("E-Mail");
	private JLabel remark = new JLabel("Bemerkung");
	private JLabel username = new JLabel("Benutzername");

	private JTextField firstNameText = new JTextField();
	private JTextField lastNameText = new JTextField();
	private JTextField addressText = new JTextField();
	private JTextField zipText = new JTextField();
	private JTextField cityText = new JTextField();
	private JTextField dateText = new JTextField();
	private JTextField phoneText = new JTextField();
	private JTextField mailText = new JTextField();
	private JTextArea remarkText = new JTextArea();
	private JTextField usernameText = new JTextField();

	private JPanel firstNamePanel = new JPanel();
	private JPanel lastNamePanel = new JPanel();
	private JPanel addressPanel = new JPanel();
	private JPanel zipPanel = new JPanel();
	private JPanel cityPanel = new JPanel();
	private JPanel datePanel = new JPanel();
	private JPanel phonePanel = new JPanel();
	private JPanel mailPanel = new JPanel();
	private JPanel remarkPanel = new JPanel();
	private JPanel usernamePanel = new JPanel();

	private JLabel roleLabel = new JLabel("Rollen:");

	private JLabel schedulemanager = new JLabel("Einsatzplaner");
	private JLabel networkplaner = new JLabel("Netzpaner");
	private JLabel busdriver = new JLabel("Busfahrer");
	private JLabel ticketplaner = new JLabel("Fahrkartenplaner");
	private JLabel hrmanager = new JLabel("Personalleiter");

	private JCheckBox schedulemanagerBox = new JCheckBox();
	private JCheckBox networkplanerBox = new JCheckBox();
	private JCheckBox busdriverBox = new JCheckBox();
	private JCheckBox ticketplanerBox = new JCheckBox();
	private JCheckBox hrmanagerBox = new JCheckBox();

	private JPanel schedulemanagerPanel = new JPanel();
	private JPanel networkplanerPanel = new JPanel();
	private JPanel busdriverPanel = new JPanel();
	private JPanel ticketplanerPanel = new JPanel();
	private JPanel hrmanagerPanel = new JPanel();

	private int textHeight = 25;
	private int areaHeight = 50;
	private int textWidth = 150;
	private int boxWidth = 50;

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
		save.addActionListener(e->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_SAVE);
		});
		cancel.addActionListener(e->
		{
			controllerFormEmployee.buttonPressed(EmitterButton.FORM_EMPLOYEE_CANCEL);
		});

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		explanationPanel.setPreferredSize(new Dimension(924, 60));
		explanationPanel.setBorder(new EmptyBorder(new Insets(30,0,30,0)));
		explanationPanel.add(explanation);

		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));

		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		midPanel.add(lastNamePanel);
		midPanel.add(firstNamePanel);
		midPanel.add(addressPanel);
		midPanel.add(zipPanel);
		midPanel.add(cityPanel);
		midPanel.add(datePanel);
		midPanel.add(phonePanel);
		midPanel.add(mailPanel);
		midPanel.add(remarkPanel);
		midPanel.add(usernamePanel);

		lastNamePanel.add(lastName);
		lastNamePanel.add(lastNameText);
		firstNamePanel.add(firstName);
		firstNamePanel.add(firstNameText);
		addressPanel.add(address);
		addressPanel.add(addressText);
		zipPanel.add(zip);
		zipPanel.add(zipText);
		cityPanel.add(city);
		cityPanel.add(cityText);
		datePanel.add(date);
		datePanel.add(dateText);
		phonePanel.add(phone);
		phonePanel.add(phoneText);
		mailPanel.add(mail);
		mailPanel.add(mailText);
		remarkPanel.add(remark);
		remarkPanel.add(remarkText);
		usernamePanel.add(username);
		usernamePanel.add(usernameText);

		remarkPanel.add(new JScrollPane(remarkText));

		lastName.setPreferredSize(new Dimension(textWidth, textHeight));
		lastNameText.setPreferredSize(new Dimension(textWidth, textHeight));
		firstName.setPreferredSize(new Dimension(textWidth, textHeight));
		firstNameText.setPreferredSize(new Dimension(textWidth, textHeight));
		address.setPreferredSize(new Dimension(textWidth, textHeight));
		addressText.setPreferredSize(new Dimension(textWidth, textHeight));
		zip.setPreferredSize(new Dimension(textWidth, textHeight));
		zipText.setPreferredSize(new Dimension(textWidth, textHeight));
		city.setPreferredSize(new Dimension(textWidth, textHeight));
		cityText.setPreferredSize(new Dimension(textWidth, textHeight));
		date.setPreferredSize(new Dimension(textWidth, textHeight));
		dateText.setPreferredSize(new Dimension(textWidth, textHeight));
		phone.setPreferredSize(new Dimension(textWidth, textHeight));
		phoneText.setPreferredSize(new Dimension(textWidth, textHeight));
		mail.setPreferredSize(new Dimension(textWidth, textHeight));
		mailText.setPreferredSize(new Dimension(textWidth, textHeight));
		remark.setPreferredSize(new Dimension(textWidth, textHeight));
		remarkText.setPreferredSize(new Dimension(textWidth, areaHeight));
		username.setPreferredSize(new Dimension(textWidth, textHeight));
		usernameText.setPreferredSize(new Dimension(textWidth, textHeight));

		add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(roleLabel);
		rightPanel.setBorder(new EmptyBorder(new Insets(0, 50, 0, 50)));

		Border border = rightPanel.getBorder();
		Border myBorder = new LineBorder(Color.black, 1);
		rightPanel.setBorder(new CompoundBorder(border, myBorder));
		rightPanel.add(schedulemanagerPanel);
		rightPanel.add(networkplanerPanel);
		rightPanel.add(busdriverPanel);
		rightPanel.add(ticketplanerPanel);
		rightPanel.add(hrmanagerPanel);

		schedulemanagerPanel.add(schedulemanager);
		schedulemanagerPanel.add(schedulemanagerBox);
		networkplanerPanel.add(networkplaner);
		networkplanerPanel.add(networkplanerBox);
		busdriverPanel.add(busdriver);
		busdriverPanel.add(busdriverBox);
		ticketplanerPanel.add(ticketplaner);
		ticketplanerPanel.add(ticketplanerBox);
		hrmanagerPanel.add(hrmanager);
		hrmanagerPanel.add(hrmanagerBox);

		schedulemanager.setPreferredSize(new Dimension(textWidth, textHeight));
		schedulemanagerBox.setPreferredSize(new Dimension(boxWidth, textHeight));
		networkplaner.setPreferredSize(new Dimension(textWidth, textHeight));
		networkplanerBox.setPreferredSize(new Dimension(boxWidth, textHeight));
		busdriver.setPreferredSize(new Dimension(textWidth, textHeight));
		busdriverBox.setPreferredSize(new Dimension(boxWidth, textHeight));
		ticketplaner.setPreferredSize(new Dimension(textWidth, textHeight));
		ticketplanerBox.setPreferredSize(new Dimension(boxWidth, textHeight));
		hrmanager.setPreferredSize(new Dimension(textWidth, textHeight));
		hrmanagerBox.setPreferredSize(new Dimension(boxWidth, textHeight));

		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		bottomPanel.setPreferredSize(new Dimension(924, 100));
		bottomPanel.add(cancel, BorderLayout.WEST);
		bottomPanel.add(save, BorderLayout.EAST);

		controllerFormEmployee.insertValuesIntoForm();

		pack();
	}

	public String getFirstNameText()
	{
		return firstNameText.getText();
	}

	public void setFirstNameText(String text)
	{
		this.firstNameText.setText(text);
	}

	public String getLastNameText()
	{
		return lastNameText.getText();
	}

	public void setLastNameText(String text)
	{
		this.lastNameText.setText(text);
	}

	public String getAddressText()
	{
		return addressText.getText();
	}

	public void setAddressText(String text)
	{
		this.addressText.setText(text);
	}

	public String getZipText()
	{
		return zipText.getText();
	}

	public void setZipText(String text)
	{
		this.zipText.setText(text);
	}

	public String getCityText()
	{
		return cityText.getText();
	}

	public void setCityText(String text)
	{
		this.cityText.setText(text);
	}

	public String  getDateText()
	{
		return dateText.getText();
	}

	public void setDateText(String text)
	{
		this.dateText.setText(text);
	}

	public String getPhoneText()
	{
		return phoneText.getText();
	}

	public void setPhoneText(String text)
	{
		this.phoneText.setText(text);
	}

	public String getRemarkText()
	{
		return remarkText.getText();
	}

	public void setRemarkText(String text)
	{
		this.remarkText.setText(text);
	}

	public String getUsernameText()
	{
		return usernameText.getText();
	}

	public void setUsernameText(String text)
	{
		this.usernameText.setText(text);
	}

	public boolean getSchedulemanagerBox()
	{
		return schedulemanagerBox.isSelected();
	}

	public void setSchedulemanagerBox(boolean state)
	{
		this.schedulemanagerBox.setSelected(state);
	}

	public boolean getNetworkplanerBox()
	{
		return networkplanerBox.isSelected();
	}

	public void setNetworkplanerBox(boolean state)
	{
		this.networkplanerBox.setSelected(state);
	}

	public boolean getBusdriverBox()
	{
		return busdriverBox.isSelected();
	}

	public void setBusdriverBox(boolean state)
	{
		this.busdriverBox.setSelected(state);
	}

	public boolean getTicketplanerBox()
	{
		return ticketplanerBox.isSelected();
	}

	public void setTicketplanerBox(boolean state)
	{
		this.ticketplanerBox.setSelected(state);
	}

	public boolean getHrmanagerBox()
	{
		return hrmanagerBox.isSelected();
	}

	public void setHrmanagerBox(boolean state)
	{
		this.hrmanagerBox.isSelected();
	}
}
