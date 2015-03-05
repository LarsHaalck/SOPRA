package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 * lenght of textfield
 * needs getter and setter
 */
public class FormBusStop extends FormGeneral
{
	private ControllerFormBusStop controllerFormBusStop;

	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();

	private String strExplanation = "Bitte geben Sie die Daten der Bushaltestelle ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);
	private JPanel plExplanation = new JPanel();

	private JLabel lbName = new JLabel("Name");
	private JLabel lbLocation = new JLabel("Ort");
	private JLabel lbStoppingPoints = new JLabel("Haltepunkte");
	private JLabel lbBarrierFree = new JLabel("Barrierefrei");

	private JTextField tfName = new JTextField();
	private JCheckBox cbBarrierFree = new JCheckBox();

	private JPanel plName = new JPanel();
	private JPanel plLocation = new JPanel();
	private JPanel plStoppingPoints = new JPanel();
	private JPanel plBarrierFree = new JPanel();
	private JPanel plLocationTextFields = new JPanel();

	private JLabel iLocationX = new JLabel("X:");
	private JLabel iLocationY = new JLabel("Y:");

	private JTextField tfLocationX = new JTextField();
	private JTextField tfLocationY = new JTextField();

	private DefaultListModel<String> lmStoppingPoints = new DefaultListModel<String>();
	private JList lStoppingPoints = new JList(lmStoppingPoints);
	private JPanel plListName = new JPanel();
	private JPanel plList = new JPanel();
	private JPanel plListButtons = new JPanel();
	private JButton btListAdd = new JButton("Hinzufügen");
	private JButton btListEdit = new JButton("Bearbeiten");
	private JButton btListDelete = new JButton("Entfernen");

	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

	private int iTextHeight = 25;
	private int iTextWidth = 150;

	public FormBusStop(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Bushaltestelle anlegen");
		else
			setTitle("Bushaltestelle bearbeiten");

		controllerFormBusStop = new ControllerFormBusStop(this, objectID);

		setLayout(new BorderLayout());
		add(plExplanation, BorderLayout.NORTH);
		add(plMid, BorderLayout.CENTER);
		add(plBottom, BorderLayout.SOUTH);

		plExplanation.setPreferredSize(new Dimension(924, 100));
		plExplanation.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		plExplanation.add(lbExplanation);

		btSave.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_CANCEL);
		});

		plMid.setLayout(new BoxLayout(plMid, BoxLayout.Y_AXIS));
		plMid.add(plName);
		plMid.add(plLocation);
		plMid.add(plBarrierFree);
		plMid.add(plStoppingPoints);

		plName.setLayout(new FlowLayout());
		plLocation.setLayout(new FlowLayout());
		plBarrierFree.setLayout(new FlowLayout());

		plName.add(lbName);
		plName.add(tfName);
		plLocation.add(lbLocation);
		plLocation.add(plLocationTextFields);
		plBarrierFree.add(lbBarrierFree);
		plBarrierFree.add(cbBarrierFree);

		lbName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		tfName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbLocation.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		plLocationTextFields.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		lbBarrierFree.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		cbBarrierFree.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		plLocationTextFields.setLayout(new FlowLayout());
		plLocationTextFields.add(iLocationX);
		plLocationTextFields.add(tfLocationX);
		plLocationTextFields.add(iLocationY);
		plLocationTextFields.add(tfLocationY);

		plStoppingPoints.setLayout(new BorderLayout());
		plStoppingPoints.add(plListName, BorderLayout.NORTH);
		plStoppingPoints.add(plList, BorderLayout.CENTER);
		plStoppingPoints.add(plListButtons, BorderLayout.SOUTH);

		plListName.setLayout(new FlowLayout());
		plListName.add(lbStoppingPoints);

		plList.add(new JScrollPane(lStoppingPoints));

		plListButtons.setLayout(new FlowLayout());

		plListButtons.add(btListAdd);
		btListAdd.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_ADD_POINT);
		});

		plListButtons.add(btListEdit);
		btListEdit.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
		});

		plListButtons.add(btListDelete);
		btListDelete.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_DELETE_POINT);
		});

		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);
		pack();
	}

	public String showNewStopPointDialog()
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den Namen eines neuen Haltepunktes ein");
	}

	public String showEditStopPointDialog(String value)
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den neuen Namen des Haltepunktes ein", value);
	}

	public int showDeleteStopPointDialog()
	{
		return JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public int getSelectedStopPoint()
	{
		return lStoppingPoints.getSelectedIndex();
	}

	public String getSelectedStopPointName()
	{
		return (String) lStoppingPoints.getSelectedValue();
	}

	public void addStopPoint(String name)
	{
		lmStoppingPoints.addElement(name);
	}

	public void editStopPoint(int index, String name)
	{
		lmStoppingPoints.setElementAt(name, index);
	}

	public void removeStopPoint(int index)
	{
		lmStoppingPoints.remove(index);
	}
}
