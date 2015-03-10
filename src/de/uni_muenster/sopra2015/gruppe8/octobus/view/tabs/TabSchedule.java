package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelSchedule;

import javax.swing.*;

/**
 * @author Patricia Schinke
 */
public class TabSchedule extends TabTable<TableModelSchedule>
{
	private JButton btnEdit;

	public TabSchedule()
	{
		super(TableModelSchedule.class, true, true);

	}

	@Override
	protected void editEntry()
	{
	}
}
