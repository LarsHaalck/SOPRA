package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTourEdit;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * Created by Florian on 10.03.2015.
 */
public class FormTourEdit extends FormGeneral
{
	private ControllerFormTourEdit controllerFormTourEdit;

	private JButton btnCancel;
	private JButton btnSave;
	private JLabel lbTourDesc;
	private JLabel lbTourTime;
	private JTable tbBuses;
	private JTable tbBusDriver;
	private TableModelTourData tmBuses;
	private TableModelTourData tmBusDriver;

	private int selectedBusRow;
	private int selectedBusId;
	private int selectedBusDriverRow;
	private int selectedBusDriverId;

	public FormTourEdit(Frame parent, int objectId)
	{
		super(parent);

		controllerFormTourEdit = new ControllerFormTourEdit(this, objectId);

		setLayout(new BorderLayout(5,10));

		//Description of form
		JPanel plTourDesc = new JPanel();
		plTourDesc.setLayout(new BorderLayout());
		JLabel lbTourDesc = new JLabel("Beschreibung");
		JLabel lbTourTime = new JLabel("Zeit");
		plTourDesc.add(lbTourDesc, BorderLayout.NORTH);
		plTourDesc.add(lbTourTime, BorderLayout.SOUTH);

		//Tables
		JPanel plContent = new JPanel();
		plContent.setLayout(new GridLayout(1,2,10,10));

		tmBuses = new TableModelTourData("Bus");
		tbBuses = new JTable(tmBuses);
		tbBuses.removeColumn(tbBuses.getColumnModel().getColumn(0));
		tbBuses.getSelectionModel().addListSelectionListener(e -> {
			int viewRow = tbBuses.getSelectedRow();
			if (viewRow < 0)
			{
				selectedBusRow = -1;
				selectedBusId = -1;

			} else
			{
				selectedBusRow = tbBuses.convertRowIndexToModel(viewRow);
				selectedBusId = (int) tmBuses.getValueAt(selectedBusRow, 0);
			}
		});
		tmBusDriver = new TableModelTourData("Fahrer");
		tbBusDriver = new JTable(tmBusDriver);
		tbBusDriver.removeColumn(tbBusDriver.getColumnModel().getColumn(0));
		tbBusDriver.getSelectionModel().addListSelectionListener(e -> {
			int viewRow = tbBusDriver.getSelectedRow();
			if (viewRow < 0)
			{
				selectedBusDriverRow = -1;
				selectedBusDriverId = -1;

			} else
			{
				selectedBusDriverRow = tbBusDriver.convertRowIndexToModel(viewRow);
				selectedBusDriverId = (int) tbBusDriver.getValueAt(selectedBusDriverRow, 0);
			}
		});
		plContent.add(new JScrollPane(tbBuses));
		plContent.add(new JScrollPane(tbBusDriver));

		//Buttons
		JPanel plButtons = new JPanel();
		plButtons.setLayout(new BorderLayout(5,5));
		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(e-> {
			controllerFormTourEdit.buttonPressed(EmitterButton.FORM_TOUR_EDIT_CANCEL);
		});
		btnSave = new JButton("Speichern");
		btnSave.addActionListener(e-> {
			controllerFormTourEdit.buttonPressed(EmitterButton.FORM_TOUR_EDIT_SAVE);
		});
		plButtons.add(btnSave, BorderLayout.WEST);
		plButtons.add(btnCancel, BorderLayout.EAST);

		add(plTourDesc, BorderLayout.NORTH);
		add(plContent, BorderLayout.CENTER);
		add(plButtons, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5,5,5,5));

		controllerFormTourEdit.fillForm();
	}

	public int getSelectedBus()
	{
		return selectedBusId;
	}

	public int getSelectedBusDriver()
	{
		return selectedBusDriverId;
	}

	public void setSelectedBus(int id)
	{
		int i = 0;
		for(i=0; i<tmBuses.getRowCount(); i++)
		{
			if((int)tmBuses.getValueAt(i,0) == id)
				break;
		}
		tbBuses.getSelectionModel().setSelectionInterval(i,i);
	}

	public void setSelectedBusDriver(int id)
	{
		int i = 0;
		for(i=0; i<tmBusDriver.getRowCount(); i++)
		{
			if((int)tmBusDriver.getValueAt(i,0) == id)
				break;
		}
		tbBusDriver.getSelectionModel().setSelectionInterval(i,i);
	}

	private class TableModelTourData extends ExtendedTableModel
	{
		public TableModelTourData(String name)
		{
			columnNames = new String[]{name};
		}

		@Override
		public int getFirstSortColumn()
		{
			return 0;
		}

		@Override
		public String[] getRefineableColumns()
		{
			return new String[0];
		}
	}
}
