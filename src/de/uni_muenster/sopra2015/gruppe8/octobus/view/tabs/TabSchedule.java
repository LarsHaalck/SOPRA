package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelSchedule;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * @author Patricia Schinke
 */
public class TabSchedule extends TabTable<TableModelSchedule>
{
	private JButton btnEdit;
	private JButton btnFilter;
	private JButton btnResetEmployee;
	private FieldDate tfDateStart;
	private JCheckBox cbOnlyUnassigned;
	private ControllerTabSchedule controllerTabSchedule;

	public TabSchedule()
	{
		super(TableModelSchedule.class, true, true);

		controllerTabSchedule = new ControllerTabSchedule(this);

		setLayout(new BorderLayout(5,5));

		//Add filter
		JPanel plFilter = new JPanel();
		plFilter.setLayout(new BorderLayout(2,2));

		//Data-filter-panel
		JPanel plFilterDate = new JPanel();
		JLabel lbFilterDateFirst = new JLabel("Alle Fahrten vom ");
		JLabel lbFilterDateSecond = new JLabel(" anzeigen.");
		tfDateStart = new FieldDate();
		tfDateStart.setPreferredSize(new Dimension(80,20));
		cbOnlyUnassigned = new JCheckBox("Nur freie Fahrten anzeigen");
		btnFilter = new JButton("Anzeigen");
		btnFilter.addActionListener(e -> {
			controllerTabSchedule.buttonPressed(EmitterButton.TAB_SCHEDULE_FILTER);
		});
		plFilterDate.add(lbFilterDateFirst);
		plFilterDate.add(tfDateStart);
		plFilterDate.add(lbFilterDateSecond);
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

		btnResetEmployee = new JButton("Busfahrer zurücksetzen");
		btnResetEmployee.addActionListener(e->
		{
			controllerTabSchedule.buttonPressed(EmitterButton.TAB_SCHEDULE_RESET_EMPLOYEE);
		});

		JPanel plButtons = new JPanel();
		plButtons.add(btnEdit);
		plButtons.add(btnResetEmployee);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		setVisible(true);
	}

	public Date getDateStart()
	{
		return tfDateStart.getDate();
	}

	public boolean getOnlyUnassigned()
	{
		return cbOnlyUnassigned.isSelected();
	}

	@Override
	protected void editEntry()
	{
		controllerTabSchedule.buttonPressed(EmitterButton.TAB_SCHEDULE_EDIT);
	}
}
