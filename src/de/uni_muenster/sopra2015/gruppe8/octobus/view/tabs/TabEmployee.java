package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelEmployee;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class TabEmployee extends TabTable<TableModelEmployee>
{
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;

	private ControllerTabEmployee controllerTabEmployee;

	public TabEmployee(int uid)
	{
		super(TableModelEmployee.class, true, true);
		controllerTabEmployee = new ControllerTabEmployee(this, uid);

		setLayout(new BorderLayout(5,5));

		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			controllerTabEmployee.buttonPressed(EmitterButton.TAB_EMPLOYEE_DELETE);
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			editEntry();
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			controllerTabEmployee.buttonPressed(EmitterButton.TAB_EMPLOYEE_NEW);
		});

		if(super.isRefineable())
		{
			JPanel plFilter = new JPanel();
			plFilter.add(lbFilter);
			plFilter.add(tfFilter);
			plFilter.add(cbFilter);
			add(plFilter, BorderLayout.PAGE_START);
		}

		JPanel plButtons = new JPanel();
		plButtons.add(btnNew);
		plButtons.add(btnEdit);
		plButtons.add(btnDelete);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		controllerTabEmployee.fillTable();

		setVisible(true);
	}

	@Override
	protected void editEntry()
	{
		controllerTabEmployee.buttonPressed(EmitterButton.TAB_EMPLOYEE_EDIT);
	}
}