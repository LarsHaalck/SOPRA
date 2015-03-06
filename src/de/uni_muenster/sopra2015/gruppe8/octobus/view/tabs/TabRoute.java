package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelRoute;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class TabRoute extends TabTable<TableModelRoute>
{
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;

	private ControllerTabRoute controllerTabRoute;

	public TabRoute()
	{
		super(TableModelRoute.class, true, true);

		controllerTabRoute = new ControllerTabRoute(this);

		setLayout(new BorderLayout(5,5));

		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			controllerTabRoute.buttonPressed(EmitterButton.TAB_ROUTE_DELETE);
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			editEntry();
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			controllerTabRoute.buttonPressed(EmitterButton.TAB_ROUTE_NEW);
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

		controllerTabRoute.fillTable();

		setVisible(true);
	}

	@Override
	protected void editEntry()
	{
		controllerTabRoute.buttonPressed(EmitterButton.TAB_ROUTE_EDIT);
	}
}
