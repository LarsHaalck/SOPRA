package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
	private StoppingPointTableModel tmStoppingPoints = new StoppingPointTableModel();
	private JTable tableStoppingPoints = new JTable(tmStoppingPoints);

	/*
	 * the buttons for save and cancel
	 */
	private JButton btSave;
	private JButton btCancel;
	//private RowSorter<StoppingPointTableModel> sorter;

	private int selectedRow = -1;
	private int selectedID = -1;
    private int stoppingPointsIdCounter = -2;


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
        // TODO: An die Swing-Experten: Können wir das Panel bitte ein bisschen weniger hoch machen? GitHub issue #27.
		JScrollPane spListStoppingPoints = new JScrollPane(tableStoppingPoints);
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

		//sorter = new TableRowSorter<>(tmStoppingPoints);
		tableStoppingPoints.removeColumn(tableStoppingPoints.getColumnModel().getColumn(0));
		//tableStoppingPoints.setRowSorter(sorter);
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

	public void addStoppingPoint(String name)
	{
		tmStoppingPoints.add(stoppingPointsIdCounter--, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

	public void addStoppingPoint(int id, String name)
	{
		tmStoppingPoints.add(id, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

	public void editStoppingPoint(int index, String name)
	{
		tmStoppingPoints.setElementAt(index, name);
		tableStoppingPoints.revalidate();
		tableStoppingPoints.repaint();
	}

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
