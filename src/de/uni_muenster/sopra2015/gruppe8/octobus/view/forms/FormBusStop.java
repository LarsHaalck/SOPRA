package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Patricia Schinke
 * lenght of textfield
 * needs getter and setter
 */
public class FormBusStop extends FormGeneral
{
	private ControllerFormBusStop controllerFormBusStop;

	private JPanel mid = new JPanel();
	private JPanel bottom = new JPanel();

	private String explanationText = "Bitte geben Sie die Daten der Bushaltestelle ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JLabel name = new JLabel("Name");
	private JLabel location = new JLabel("Ort");
	private JLabel stoppingPoints = new JLabel("Haltepunkte");
	private JLabel barrierFree = new JLabel("Barrierefrei");

	private JTextField nameText = new JTextField();
	private JCheckBox barrierFreeText = new JCheckBox();

	private JPanel namePanel = new JPanel();
	private JPanel locationPanel = new JPanel();
	private JPanel stoppingPointsPanel = new JPanel();
	private JPanel barrierFreePanel = new JPanel();
	private JPanel locationText = new JPanel();

	private JLabel intX = new JLabel("X:");
	private JLabel intY = new JLabel("Y:");

	private JTextField intXText = new JTextField();
	private JTextField intYText = new JTextField();

	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList list = new JList(listModel);
	private JPanel listName = new JPanel();
	private JPanel listPanel = new JPanel();
	private JPanel listButtons = new JPanel();
	private JButton listAdd = new JButton("Hinzufügen");
	private JButton listEdit = new JButton("Bearbeiten");
	private JButton listDelete = new JButton("Entfernen");

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	private int textHeight = 25;
	private int textWidth = 150;

	public FormBusStop(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Bushaltestelle anlegen");
		else
			setTitle("Bushaltestelle bearbeiten");

		controllerFormBusStop = new ControllerFormBusStop(this, objectID);

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		explanationPanel.setPreferredSize(new Dimension(924, 100));
		explanationPanel.setBorder(new EmptyBorder(new Insets(40,0,40,0)));
		explanationPanel.add(explanation);

		save.addActionListener(e->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_SAVE);
		});
		cancel.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_CANCEL);
		});

		mid.setLayout(new BoxLayout(mid, BoxLayout.Y_AXIS));
		mid.add(namePanel);
		mid.add(locationPanel);
		mid.add(barrierFreePanel);
		mid.add(stoppingPointsPanel);

		namePanel.setLayout(new FlowLayout());
		locationPanel.setLayout(new FlowLayout());
		barrierFreePanel.setLayout(new FlowLayout());

		namePanel.add(name);
		namePanel.add(nameText);
		locationPanel.add(location);
		locationPanel.add(locationText);
		barrierFreePanel.add(barrierFree);
		barrierFreePanel.add(barrierFreeText);

		name.setPreferredSize(new Dimension(textWidth, textHeight));
		nameText.setPreferredSize(new Dimension(textWidth, textHeight));
		location.setPreferredSize(new Dimension(textWidth, textHeight));
		locationText.setPreferredSize(new Dimension(textWidth, textHeight));
		barrierFree.setPreferredSize(new Dimension(textWidth, textHeight));
		barrierFreeText.setPreferredSize(new Dimension(textWidth, textHeight));

		locationText.setLayout(new FlowLayout());
		locationText.add(intX);
		locationText.add(intXText);
		locationText.add(intY);
		locationText.add(intYText);

		stoppingPointsPanel.setLayout(new BorderLayout());
		stoppingPointsPanel.add(listName, BorderLayout.NORTH);
		stoppingPointsPanel.add(listPanel, BorderLayout.CENTER);
		stoppingPointsPanel.add(listButtons, BorderLayout.SOUTH);

		listName.setLayout(new FlowLayout());
		listName.add(stoppingPoints);

		listPanel.add(new JScrollPane(list));

		listButtons.setLayout(new FlowLayout());

		listButtons.add(listAdd);
		listAdd.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_ADD_POINT);
		});

		listButtons.add(listEdit);
		listEdit.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
		});

		listButtons.add(listDelete);
		listDelete.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_DELETE_POINT);
		});

		bottom.setLayout(new BorderLayout());
		bottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		bottom.setPreferredSize(new Dimension(924, 100));
		bottom.add(cancel, BorderLayout.WEST);
		bottom.add(save, BorderLayout.EAST);
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
		return list.getSelectedIndex();
	}

	public String getSelectedStopPointName()
	{
		return (String)list.getSelectedValue();
	}

	public void addStopPoint(String name)
	{
		listModel.addElement(name);
	}

	public void editStopPoint(int index, String name)
	{
		listModel.setElementAt(name, index);
	}

	public void removeStopPoint(int index)
	{
		listModel.remove(index);
	}
}
