package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class TabSchedule extends TabTable<TableModelSchedule>
{
	private JButton btnEdit;
	private JButton btnFilter;
	private FieldDate tfDateStart;
	private FieldDate tfDateEnd;
	private JCheckBox cbOnlyUnassigned;
	private ControllerTabSchedule controllerTabSchedule;

	public TabSchedule()
	{
		super(TableModelSchedule.class, true, true);

		controllerTabSchedule = new ControllerTabSchedule();

		setLayout(new BorderLayout(5,5));

		//Add filter
		JPanel plFilter = new JPanel();
		plFilter.setLayout(new BorderLayout(2,2));

		//Data-filter-panel
		JPanel plFilterDate = new JPanel();
		JLabel lbFilterDateFirst = new JLabel("Alle Fahrten zwischen ");
		JLabel lbFilterDateSecond = new JLabel(" und ");
		JLabel lbFilterDateThird = new JLabel(" anzeigen. Für nur einen Tag das zweite Feld leer lassen.");
		tfDateStart = new FieldDate();
		tfDateStart.setPreferredSize(new Dimension(80,20));
		tfDateEnd = new FieldDate();
		tfDateEnd.setPreferredSize(new Dimension(80,20));
		cbOnlyUnassigned = new JCheckBox("Nur freie Fahrten anzeigen");
		btnFilter = new JButton("Anzeigen");
		btnFilter.addActionListener(e->{
			controllerTabSchedule.buttonPressed(EmitterButton.TAB_SCHEDULE_FILTER);
		});
		plFilterDate.add(lbFilterDateFirst);
		plFilterDate.add(tfDateStart);
		plFilterDate.add(lbFilterDateSecond);
		plFilterDate.add(tfDateEnd);
		plFilterDate.add(lbFilterDateThird);
		plFilterDate.add(cbOnlyUnassigned);
		plFilterDate.add(btnFilter);
		plFilter.add(plFilterDate, BorderLayout.PAGE_START);


		//Table-filter-panel
		if(super.isRefineable())
		{
			JPanel plFilterTable = new JPanel();
			plFilterTable.add(lbFilter);
			plFilterTable.add(tfFilter);
			plFilterTable.add(cbFilter);
			plFilter.add(plFilterTable, BorderLayout.PAGE_END);
		}

		add(plFilter, BorderLayout.PAGE_START);

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e->{
			controllerTabSchedule.buttonPressed(EmitterButton.TAB_SCHEDULE_EDIT);
		});

		JPanel plButtons = new JPanel();
		plButtons.add(btnEdit);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		controllerTabSchedule.fillTable();

		setVisible(true);
	}

	@Override
	protected void editEntry()
	{
	}
}
