package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabWorkPlan;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelWorkPlan;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 */
public class TabWorkPlan extends TabTable<TableModelWorkPlan>
{
	private ControllerTabWorkPlan controllerTabWorkPlan;

	public TabWorkPlan()
	{
		super(TableModelWorkPlan.class, true, true);
		controllerTabWorkPlan = new ControllerTabWorkPlan(this);

		setLayout(new BorderLayout(5,5));

		if(super.isRefineable())
		{
			JPanel plFilter = new JPanel();
			plFilter.add(lbFilter);
			plFilter.add(tfFilter);
			plFilter.add(cbFilter);
			add(plFilter, BorderLayout.PAGE_START);
		}

		JPanel plButtons = new JPanel();

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		controllerTabWorkPlan.fillTable();

		setVisible(true);
	}
	@Override
	protected void editEntry()
	{

	}
}
