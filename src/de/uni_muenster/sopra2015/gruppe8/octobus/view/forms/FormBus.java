package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class FormBus extends FormGeneral
{
	private ControllerFormBus controllerFormBus;

	private String strExplanation = "Bitte geben Sie die Daten des Busses ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);
	private JPanel plExplanation = new JPanel();

	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();
	private JPanel plLeft = new JPanel();
	private JPanel plRight = new JPanel();

	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

	private JLabel lbLicencePlate = new JLabel("Kennzeichen");
	private JLabel lbNumberOfSeats = new JLabel("Anzahl Sitzplätze");
	private JLabel lbStandingRoom = new JLabel("Anzahl Stehplätze");
	private JLabel lbManufacturer = new JLabel("Hersteller");
	private JLabel lbModel = new JLabel("Modell");
	private JLabel lbNextInspectionDue = new JLabel("Nächste Inspektion");
	private JLabel lbArticulatedBus = new JLabel("Bus mit Gelenk");

	private JTextField tfLicencePlate = new JTextField();
	private JTextField tfNumberOfSeats = new JTextField();
	private JTextField tfStandingRoom = new JTextField();
	private JTextField tfManufacturer = new JTextField();
	private JTextField tfModel = new JTextField();
	private JTextField tfNextInspectionDue = new JTextField();
	private JCheckBox cbArticulatedBus = new JCheckBox();

	private JPanel plLicencePlate = new JPanel();
	private JPanel plNumberOfSeats = new JPanel();
	private JPanel plStandingRoom = new JPanel();
	private JPanel plManufacturer = new JPanel();
	private JPanel plModel = new JPanel();
	private JPanel plNextInspectionDue = new JPanel();
	private JPanel plArticulatedBus = new JPanel();

	private int iTextHeight = 25;
	private int iTextWidth = 200;

	public FormBus(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Bus anlegen");
		else
			setTitle("Bus ändern");
		setResizable(false);

		controllerFormBus = new ControllerFormBus(this, objectID);
		plMid.setBorder(new EmptyBorder(new Insets(0, 100, 0, 100)));
		
		btSave.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_SAVE);
		});
		btCancel.addActionListener(e -> {
			controllerFormBus.buttonPressed(EmitterButton.FORM_BUS_CANCEL);
		});

		setLayout(new BorderLayout());
		add(plExplanation, BorderLayout.NORTH);
		add(plMid, BorderLayout.CENTER);
		add(plBottom, BorderLayout.SOUTH);
		add(plRight, BorderLayout.EAST);
		add(plLeft, BorderLayout.WEST);

		plExplanation.setPreferredSize(new Dimension(924, 100));
		plExplanation.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		plExplanation.add(lbExplanation);

		plMid.setLayout(new BoxLayout(plMid, BoxLayout.Y_AXIS));
		plMid.add(plLicencePlate);
		plLicencePlate.setLayout(new FlowLayout());
		plMid.add(plNumberOfSeats);
		plNumberOfSeats.setLayout(new FlowLayout());
		plMid.add(plStandingRoom);
		plStandingRoom.setLayout(new FlowLayout());
		plMid.add(plManufacturer);
		plManufacturer.setLayout(new FlowLayout());
		plMid.add(plModel);
		plModel.setLayout(new FlowLayout());
		plMid.add(plNextInspectionDue);
		plNextInspectionDue.setLayout(new FlowLayout());
		plMid.add(plArticulatedBus);
		plArticulatedBus.setLayout(new FlowLayout());

		plLicencePlate.add(lbLicencePlate);
		plLicencePlate.add(tfLicencePlate);
		plNumberOfSeats.add(lbNumberOfSeats);
		plNumberOfSeats.add(tfNumberOfSeats);
		plStandingRoom.add(lbStandingRoom);
		plStandingRoom.add(tfStandingRoom);
		plManufacturer.add(lbManufacturer);
		plManufacturer.add(tfManufacturer);
		plModel.add(lbModel);
		plModel.add(tfModel);
		plNextInspectionDue.add(lbNextInspectionDue);
		plNextInspectionDue.add(tfNextInspectionDue);
		plArticulatedBus.add(lbArticulatedBus);
		plArticulatedBus.add(cbArticulatedBus);

		lbLicencePlate.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbNumberOfSeats.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbStandingRoom.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbManufacturer.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbModel.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbNextInspectionDue.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbArticulatedBus.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		tfLicencePlate.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfNumberOfSeats.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfStandingRoom.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfManufacturer.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfModel.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfNextInspectionDue.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbArticulatedBus.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);

		controllerFormBus.insertValuesIntoForm();

		pack();
	}

	public String getTfLicencePlate() //nicht mehr wirklich getter
	{
		return tfLicencePlate.getText();
	}

	public String getTfNumberOfSeats()
	{
		return tfNumberOfSeats.getText();
	}

	public String getTfStandingRoom()
	{
		return tfStandingRoom.getText();
	}

	public String getTfManufacturer()
	{
		return tfManufacturer.getText();
	}

	public String getTfModel()
	{
		return tfModel.getText();
	}

	public String getTfNextInspectionDue()
	{
		return tfNextInspectionDue.getText();
	}

	public boolean getCbArticulatedBus()
	{
		return cbArticulatedBus.isSelected();
	}

	public void setTfLicencePlate(String text)
	{
		this.tfLicencePlate.setText(text);
	}

	public void setTfNumberOfSeats(String text)
	{
		this.tfNumberOfSeats.setText(text);
	}

	public void setTfStandingRoom(String text)
	{
		this.tfStandingRoom.setText(text);
	}

	public void setTfManufacturer(String text)
	{
		this.tfManufacturer.setText(text);
	}

	public void setTfModel(String text)
	{
		this.tfModel.setText(text);
	}

	public void setTfNextInspectionDue(String text)
	{
		this.tfNextInspectionDue.setText(text);
	}

	public void setCbArticulatedBus(Boolean state)
	{
		this.cbArticulatedBus.setSelected(state);
	}

}
