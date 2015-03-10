package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTourEdit;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
		tmBusDriver = new TableModelTourData("Fahrer");
		tbBusDriver = new JTable(tmBusDriver);
		tbBusDriver.removeColumn(tbBusDriver.getColumnModel().getColumn(0));
		plContent.add(new JScrollPane(tbBuses));
		plContent.add(new JScrollPane(tbBusDriver));

		//Buttons
		JPanel plButtons = new JPanel();
		plButtons.setLayout(new BorderLayout(5,5));
		btnCancel = new JButton("Abbrechen");
		btnSave = new JButton("Speichern");
		plButtons.add(btnSave, BorderLayout.WEST);
		plButtons.add(btnCancel, BorderLayout.EAST);

		add(plTourDesc, BorderLayout.NORTH);
		add(plContent, BorderLayout.CENTER);
		add(plButtons, BorderLayout.SOUTH);

		getRootPane().setBorder(new EmptyBorder(5,5,5,5));
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
