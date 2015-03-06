package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelTicket;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class TabTicket extends TabTable<TableModelTicket>
{
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;

	private ControllerTabTicket controllerTabTicket;

	public TabTicket()
	{
		super(TableModelTicket.class, true, false);

		controllerTabTicket = new ControllerTabTicket(this);

		setLayout(new BorderLayout(5,5));
		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			controllerTabTicket.buttonPressed(EmitterButton.TAB_TICKET_DELETE);
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			editEntry();
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			controllerTabTicket.buttonPressed(EmitterButton.TAB_TICKET_NEW);
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
		controllerTabTicket.fillTable();
		setVisible(true);

	}

	@Override
	protected void editEntry()
	{
		controllerTabTicket.buttonPressed(EmitterButton.TAB_TICKET_EDIT);
	}
}
