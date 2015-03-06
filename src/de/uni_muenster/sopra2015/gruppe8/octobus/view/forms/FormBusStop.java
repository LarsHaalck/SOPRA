package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;
import javafx.geometry.HPos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Patricia Schinke
 * lenght of textfield
 * needs getter and setter
 */
public class FormBusStop extends FormGeneral
{
	private ControllerFormBusStop controllerFormBusStop;

	private FieldText tfName;
	private JCheckBox cbBarrierFree;

	private FieldNumber tfLocationX;
	private FieldNumber tfLocationY;
	private JButton btListAdd;
	private JButton btListEdit;
	private JButton btListDelete;
	private DefaultListModel<String> lmStoppingPoints = new DefaultListModel<String>();
	private JList lStoppingPoints = new JList(lmStoppingPoints);

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave;
	private JButton btCancel;


	public FormBusStop(Frame parent, int objectID)
	{
		super(parent, "");

		getRootPane().setBorder(new EmptyBorder(10,10,10,10));

		if(objectID == -1)
			setTitle("Bushaltestelle anlegen");
		else
			setTitle("Bushaltestelle bearbeiten");

		controllerFormBusStop = new ControllerFormBusStop(this, objectID);

		setLayout(new GridBagLayout());
		GridBagConstraints cstLabel = new GridBagConstraints();
		GridBagConstraints cstTextField = new GridBagConstraints();

		//general constraints
		cstTextField.fill = GridBagConstraints.HORIZONTAL;
		cstLabel.ipadx = 20;
		cstLabel.ipady = 20;
		cstLabel.anchor = GridBagConstraints.LINE_START;

		cstLabel.gridwidth = 6;
		cstLabel.gridx = 0;
		cstLabel.gridy = 0;
		String strExplanation = "Bitte geben Sie die Daten der Bushaltestelle ein.";
		JLabel lbExplanation = new JLabel(strExplanation);
		add(lbExplanation, cstLabel);

		cstLabel.gridwidth = 1;
		cstLabel.gridx = 0;
		cstLabel.gridy = 1;
		JLabel lbName = new JLabel("Name");
		add(lbName, cstLabel);

		cstTextField.gridwidth = 5;
		cstTextField.gridx = 1;
		cstTextField.gridy = 1;
		tfName = new FieldText();
		add(tfName, cstTextField);

		cstLabel.gridy = 2;
		JLabel lbLocation = new JLabel("Ort");
		add(lbLocation, cstLabel);

		cstTextField.gridy = 2;
		cstTextField.gridwidth=1;
		cstTextField.weightx = 0.1;
		cstTextField.anchor = GridBagConstraints.LINE_START;
		JLabel lbLocationX = new JLabel("X:");
		add(lbLocationX, cstTextField);
		cstTextField.gridx = 2;
		cstTextField.weightx = 1;
		tfLocationX = new FieldNumber(4);
		add(tfLocationX, cstTextField);
		cstTextField.gridx = 3;
		cstTextField.weightx = 0.1;
		JPanel plEmpty = new JPanel();
		add(plEmpty, cstTextField);
		cstTextField.gridx = 4;
		cstTextField.weightx = 0.1;
		cstTextField.anchor = GridBagConstraints.LINE_END;
		JLabel lbLocationY = new JLabel("Y:");
		add(lbLocationY,cstTextField);
		cstTextField.gridx = 5;
		cstTextField.weightx = 1;
		tfLocationY = new FieldNumber(4);
		add(tfLocationY, cstTextField);

		cstTextField.gridwidth = 5;
		cstTextField.gridy = 3;
		cstTextField.gridx = 1;
		cbBarrierFree  = new JCheckBox("Barrierefrei");
		add(cbBarrierFree, cstTextField);

		cstLabel.gridwidth = 6;
		cstLabel.gridx = 0;
		cstLabel.gridy = 4;
		JLabel lbStoppingPoints = new JLabel("Haltepunkte");
		add(lbStoppingPoints, cstLabel);

		cstLabel.gridy = 5;
		cstLabel.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane spListStoppingPoints = new JScrollPane(lStoppingPoints);
		add(spListStoppingPoints, cstLabel);

		cstLabel.gridy = 6;
		JPanel plButtons = new JPanel();
		plButtons.setLayout(new GridBagLayout());
		GridBagConstraints cstButtons = new GridBagConstraints();
		cstButtons.gridx=0;
		cstButtons.gridy=0;
		btListAdd = new JButton("Hinzufügen");
		plButtons.add(btListAdd, cstButtons);
		cstButtons.gridx=1;
		btListEdit = new JButton("Bearbeiten");
		plButtons.add(btListEdit, cstButtons);
		cstButtons.gridx=2;
		btListDelete = new JButton("Entfernen");
		plButtons.add(btListDelete, cstButtons);
		add(plButtons, cstLabel);

		cstLabel.gridy=7;
		cstLabel.ipady=0;
		cstLabel.gridwidth= 6;
		cstLabel.gridheight=1;
		cstLabel.fill = GridBagConstraints.HORIZONTAL;
		JPanel plEndButtons = new JPanel();
		plEndButtons.setLayout(new BorderLayout());
		btSave = new JButton("Speichern");
		plEndButtons.add(btSave, BorderLayout.WEST);
		btCancel = new JButton("Abbrechen");
		plEndButtons.add(btCancel, BorderLayout.EAST);
		add(plEndButtons, cstLabel);

		lStoppingPoints.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
					controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
			}
		});

		btSave.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_CANCEL);
		});

		btListAdd.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_ADD_POINT);
		});

		btListEdit.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
		});

		btListDelete.addActionListener(e -> {
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_DELETE_POINT);
		});

		controllerFormBusStop.insertValuesIntoForm();
		pack();
		setLocationRelativeTo(null);


	}

	public String showNewStoppingPointDialog()
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den Namen eines neuen Haltepunktes ein");
	}

	public String showEditStoppingPointDialog(String value)
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den neuen Namen des Haltepunktes ein", value);
	}

	public int showDeleteStoppingPointDialog()
	{
		return JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public int getSelectedStoppingPoint()
	{
		return lStoppingPoints.getSelectedIndex();
	}

	public String getSelectedStoppingPointName()
	{
		return (String) lStoppingPoints.getSelectedValue();
	}

	public void addStoppingPoint(String name)
	{
		lmStoppingPoints.addElement(name);
	}

	public void editStoppingPoint(int index, String name)
	{
		lmStoppingPoints.setElementAt(name, index);
	}

	public void removeStoppingPoint(int index)
	{
		lmStoppingPoints.remove(index);
	}

	public String getNameBusStop()
	{
		return tfName.getText();
	}

	public void setNameBusStop(String text)
	{
		this.tfName.setText(text);
	}

	public int getLocationX()
	{
		return tfLocationX.getNumber();
	}

	public int getLocationY()
	{
		return tfLocationY.getNumber();
	}

	public void setLocationX(int x)
	{
		tfLocationX.setNumber(x);
	}

	public void setLocationY(int y)
	{
		tfLocationY.setNumber(y);
	}

	public void setLocationBusStop(JPanel plLocation)
	{
		//TODO fehlt noch
	}

	public List getStoppingPoints()
	{
		List list = new List();

		/**
		 * loop which reads the arguments of my list and gives them to a new list
		 */
		for(int i = 0; i<lmStoppingPoints.getSize(); i++)
		{
			list.add(lmStoppingPoints.getElementAt(i));
		}
		return list;
	}

	public boolean getBarrierFree()
	{
		return cbBarrierFree.isSelected();
	}

	public void setBarrierFree(boolean state)
	{
		this.cbBarrierFree.setSelected(state);
	}
}
