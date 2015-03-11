package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Form used for adding and editing bus stops.
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
	private StoppingPointTableModel tmStoppingPoints = new StoppingPointTableModel();
	private JTable tableStoppingPoints = new JTable(tmStoppingPoints);

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave;
	private JButton btCancel;

	private int selectedRow = -1;
	private int selectedID = -1;
    private int stoppingPointsIdCounter = -2;

	/**
	 * Creates a FormBusStop-object.
	 *
	 * @param parent the parent-frame
	 * @param objectID the id of the selected busStop
	 */
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
		JScrollPane spTableStoppingPoints = new JScrollPane(tableStoppingPoints);
		spTableStoppingPoints.setPreferredSize(new Dimension(300, 300));
        add(spTableStoppingPoints, cstLabel);

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

		tableStoppingPoints.removeColumn(tableStoppingPoints.getColumnModel().getColumn(0));
		tableStoppingPoints.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
					controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT);
			}
		});
		tableStoppingPoints.getSelectionModel().addListSelectionListener(e->
		{
			int viewRow = tableStoppingPoints.getSelectedRow();
			if (viewRow < 0)
			{
				selectedRow = -1;
				selectedID = -1;

			} else
			{
				selectedRow = tableStoppingPoints.convertRowIndexToModel(viewRow);
				selectedID = (int) tmStoppingPoints.getValueAt(selectedRow, 0);
			}
		});

		btSave.addActionListener(e ->
				controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_SAVE));
		btCancel.addActionListener(e ->
				controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_CANCEL));

		btListAdd.addActionListener(e ->
				controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_ADD_POINT));

		btListEdit.addActionListener(e ->
				controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_EDIT_POINT));

		btListDelete.addActionListener(e ->
				controllerFormBusStop.buttonPressed(EmitterButton.FORM_BUS_STOP_DELETE_POINT));

		controllerFormBusStop.insertValuesIntoForm();
		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Sets the name of a new stopPoint.
	 *
	 * @return new Name of stopPoint
	 */
	public String showNewStoppingPointDialog()
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den Namen eines neuen Haltepunktes ein");
	}

	/**
	 * Changes the name of a stopPoint..
	 *
	 * @param value a String with the old ame od the stopPoint
	 * @return new name of stopPoint
	 */
	public String showEditStoppingPointDialog(String value)
	{
		return JOptionPane.showInputDialog(null, "Bitte geben Sie den neuen Namen des Haltepunktes ein", value);
	}

	/**
	 * Deletes a stopPoint.
	 *
	 * @return yes or no to delete stopPoint
	 */
	public int showDeleteStoppingPointDialog()
	{
		return JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	public int getSelectedStoppingPoint()
	{
		return selectedID;
	}

	public String getSelectedStoppingPointName()
	{
		return (String) tmStoppingPoints.getValueAt(selectedRow, 1);
	}

	public int getSelectedRow()
	{
		return selectedRow;
	}

	/**
	 * Adds a stopPoint to a busSTop.
	 *
	 * @param name of the new stopPoint
	 */
	public void addStoppingPoint(String name)
	{
		tmStoppingPoints.add(stoppingPointsIdCounter--, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

	/**
	 * Adds a stopPoint to a busStop with id.
	 *
	 * @param id of new stopPoint
	 * @param name of new stopPoint
	 */
	public void addStoppingPoint(int id, String name)
	{
		tmStoppingPoints.add(id, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

	/**
	 * Edits a stopPoint.
	 *
	 * @pre stopPoint is selected
	 * @param index of old stopPoint in table
	 * @param name of new stopPoint
	 */
	public void editStoppingPoint(int index, String name)
	{
		tmStoppingPoints.setElementAt(index, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

	/**
	 * deletes a stopPoint
	 *
	 * @pre stopPoint is selected
	 * @param id of stopPint which will be deleted
	 */
	public void removeStoppingPoint(int id)
	{
        tmStoppingPoints.remove(id);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
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

	public void setLocationX(int x)
	{
		tfLocationX.setNumber(x);
	}

	public int getLocationY()
	{
		return tfLocationY.getNumber();
	}

	public void setLocationY(int y)
	{
		tfLocationY.setNumber(y);
	}

	public ArrayList<StoppingPoint> getStoppingPoints()
	{
		ArrayList<StoppingPoint> list = new ArrayList<>();

		/**
		 * Loop which retrieves the arguments of the current list and puts them into a new list
		 */
		for(int i = 0; i < tmStoppingPoints.getRowCount(); i++)
		{
			list.add(new StoppingPoint((Integer)tmStoppingPoints.getValueAt(i,0), (String) tmStoppingPoints.getValueAt(i,1)));
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

	private class StoppingPointTableModel extends ExtendedTableModel
	{
		public StoppingPointTableModel()
		{
			columnNames = new String[]{"Haltepunkte"};
		}

		@Override
		public int getFirstSortColumn()
		{
			return 1;
		}

		@Override
		public String[] getRefineableColumns()
		{
			return new String[0];
		}

		@Override
		public Class getColumnClass(int column)
		{
			switch(column)
			{
				case 0:
					return Integer.class;

				case 1:
					return String.class;
			}
			return null;
		}

		public void add(int id, String name)
		{
			Object[][] dataNew = new Object[data.length+1][2];
			for(int i=0; i<data.length; i++)
			{
				dataNew[i][0] = data[i][0];
				dataNew[i][1] = data[i][1];
			}
			dataNew[data.length] = new Object[]{id, name};
			setData(dataNew);
		}

		public void setElementAt(int index, String value)
		{
			data[index][1] = value;
			fireTableDataChanged();
			fireTableStructureChanged();
		}

		public void remove(int id)
		{
            // Added this because otherwise clicking the delete button after already
            // having deleted an entry throws and ArrayIndexOutOfBounds exception
            // because the old id is still selected in the background.
            tableStoppingPoints.clearSelection();

			// Create new data array which holds one entry less than previous one
            Object[][] dataNew = new Object[data.length-1][2];
			int curIndex = 0;
            // Copy over stuff that isn't being removed
			for(Object[] obj : data)
			{
                if((int) obj[0] != id)
				{
                    dataNew[curIndex] = obj;
					curIndex++;
				}
			}
			setData(dataNew);
		}
	}
}
