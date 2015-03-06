package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import javax.swing.*;
import java.awt.*;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabBus;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelBus;

/**
 * @author Michael Biech
 */
public class TabBus extends TabTable<TableModelBus>
{
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;

	private ControllerTabBus controllerTabBus;

	public TabBus()
	{
		super(TableModelBus.class, true, true);

		controllerTabBus = new ControllerTabBus(this);

		setLayout(new BorderLayout(5,5));

		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			controllerTabBus.buttonPressed(EmitterButton.TAB_BUS_DELETE);
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			editEntry();
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			controllerTabBus.buttonPressed(EmitterButton.TAB_BUS_NEW);
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

		controllerTabBus.fillTable();
		setVisible(true);
	}

	@Override
	protected void editEntry()
	{
			controllerTabBus.buttonPressed(EmitterButton.TAB_BUS_EDIT);
	}
}
