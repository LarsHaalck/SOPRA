package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
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

	private JPanel plMid = new JPanel();
	private JPanel plBottom = new JPanel();
	private JPanel plUpperMid = new JPanel();
	private JPanel plEmpty = new JPanel();

	/*
	 * at the top of the form is an explanation what you should do
	 */
	private String strExplanation = "Bitte geben Sie die Daten der Bushaltestelle ein.";
	private JLabel lbExplanation = new JLabel(strExplanation);
	private JPanel plExplanation = new JPanel();

	/*
	 * every input has an own label, inputfield and panel
	 */
	private JLabel lbName = new JLabel("Name");
	private JLabel lbLocation = new JLabel("Ort");
	private JLabel lbStoppingPoints = new JLabel("Haltepunkte");

	private JTextField tfName = new JTextField();
	private JCheckBox cbBarrierFree = new JCheckBox("Barrierefrei");

	private JPanel plName = new JPanel();
	private JPanel plLocation = new JPanel();
	private JPanel plStoppingPoints = new JPanel();
	private JPanel plBarrierFree = new JPanel();
	private JPanel plLocationTextFields = new JPanel();

	/*
	 * location has two textfields
	 */
	private JLabel lbLocationX = new JLabel("X:");
	private JLabel lbLocationY = new JLabel("Y:");

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

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave = new JButton("Speichern");
	private JButton btCancel = new JButton("Abbrechen");

	private int iTextHeight = 25;
	private int iTextWidth = 150;
	private int iSmallTextWidth = 55;
	private int iSmallTextHeight = 20;

	public FormBusStop(Frame parent, int objectID)
	{
		super(parent, "");
		if(objectID == -1)
			setTitle("Bushaltestelle anlegen");
		else
			setTitle("Bushaltestelle bearbeiten");

		controllerFormBusStop = new ControllerFormBusStop(this, objectID);

		setLayout(new BorderLayout());

		//Panel with title
		plExplanation.setPreferredSize(new Dimension(924, 100));
		plExplanation.setBorder(new EmptyBorder(new Insets(40, 0, 40, 0)));
		plExplanation.add(lbExplanation);

		//Panel containing input form and stopping points
		plMid.setLayout(new BoxLayout(plMid, BoxLayout.Y_AXIS));

		//Panel containing input form
		plUpperMid.setLayout(new GridBagLayout());
		GridBagConstraints cstLabel = new GridBagConstraints();
		GridBagConstraints cstTextfield = new GridBagConstraints();

		cstLabel.gridx=0;
		cstLabel.gridy=0;
		lbName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		plUpperMid.add(lbName,cstLabel);

		cstTextfield.gridx=1;
		cstTextfield.gridy=0;
		tfName.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		plUpperMid.add(tfName, cstTextfield);

		cstLabel.gridy=1;
		lbLocation.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		plUpperMid.add(lbLocation, cstLabel);

		cstTextfield.gridy=1;
		plLocationTextFields.setPreferredSize(new Dimension(iTextWidth, iTextHeight));

		tfLocationX.setPreferredSize(new Dimension(iSmallTextWidth, iSmallTextHeight));
		tfLocationY.setPreferredSize(new Dimension(iSmallTextWidth, iSmallTextHeight));

		plLocationTextFields.add(lbLocationX);
		plLocationTextFields.add(tfLocationX);
		plLocationTextFields.add(lbLocationY);
		plLocationTextFields.add(tfLocationY);

		plUpperMid.add(plLocationTextFields, cstTextfield);

		cstTextfield.gridy=2;
		cbBarrierFree.setPreferredSize(new Dimension(iTextWidth, iTextHeight));
		plUpperMid.add(cbBarrierFree, cstTextfield);

		//Panel for stopping points
		plStoppingPoints.setLayout(new BorderLayout());

		plListName.setLayout(new FlowLayout());
		plListName.add(lbStoppingPoints);

		lStoppingPoints.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2)
					controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
			}
		});

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

		plStoppingPoints.add(plListName, BorderLayout.NORTH);
		plStoppingPoints.add(plList, BorderLayout.CENTER);
		plStoppingPoints.add(plListButtons, BorderLayout.SOUTH);

		plMid.add(plUpperMid);
		plMid.add(plStoppingPoints);

		//Panel for save/cancel buttons
		plBottom.setLayout(new BorderLayout());
		plBottom.setBorder(new EmptyBorder(new Insets(30, 60, 30, 60)));
		plBottom.setPreferredSize(new Dimension(924, 100));
		btSave.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_SAVE);
		});
		btCancel.addActionListener(e ->
		{
			controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_CANCEL);
		});
		plBottom.add(btCancel, BorderLayout.WEST);
		plBottom.add(btSave, BorderLayout.EAST);

		add(plExplanation, BorderLayout.NORTH);
		add(plMid, BorderLayout.CENTER);
		add(plBottom, BorderLayout.SOUTH);

		pack();
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
		return Integer.parseInt(tfLocationX.getText());
	}

	public int getLocationY()
	{
		return Integer.parseInt(tfLocationY.getText());
	}

	public void setLocationX(int x)
	{
		tfLocationX.setText(""+x);
	}

	public void setLocationY(int y)
	{
		tfLocationY.setText(""+y);
	}

	public void setLocationBusStop(JPanel plLocation)
	{
		this.plLocation = plLocation;
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
