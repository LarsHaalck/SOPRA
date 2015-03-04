package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.listeners.EmitterButton;
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

		controllerTabTicket = new ControllerTabTicket();

		setLayout(new BorderLayout(5,5));
		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			if(JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
					== JOptionPane.YES_OPTION)
			{
				System.out.println("Delete pressed");
				controllerTabTicket.buttonPressed(EmitterButton.TAB_TICKET_DELETE);
			}
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			if(getSelectedID() == -1)
				JOptionPane.showMessageDialog(this, "Um ein Ticket zu bearbeiten, wählen Sie bitte einen Eintrag aus der Tabelle.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
			else
				controllerTabTicket.buttonPressed(EmitterButton.TAB_TICKET_EDIT);
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

		setVisible(true);
	}
}
