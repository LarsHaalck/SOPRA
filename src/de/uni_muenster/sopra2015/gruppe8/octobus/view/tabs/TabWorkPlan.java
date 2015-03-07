package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
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

	private JButton btnPrint, btnExport;

	public TabWorkPlan(int userId)
	{
		super(TableModelWorkPlan.class, true, true);
		controllerTabWorkPlan = new ControllerTabWorkPlan(this, userId);

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
		btnPrint = new JButton("Ausdrucken");
		btnPrint.addActionListener(e->{
			controllerTabWorkPlan.buttonPressed(EmitterButton.TAB_WORK_PLAN_PRINT);
		});
		btnExport = new JButton("Export");
		btnExport.addActionListener(e->{
			controllerTabWorkPlan.buttonPressed((EmitterButton.TAB_WORK_PLAN_EXPORT));
		});

		plButtons.add(btnPrint);
		plButtons.add(btnExport);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		controllerTabWorkPlan.fillTable();

		setVisible(true);
	}

	public void enableButtons(boolean enable)
	{
		btnExport.setEnabled(enable);
		btnPrint.setEnabled(enable);
	}

	@Override
	protected void editEntry()
	{

	}
}
