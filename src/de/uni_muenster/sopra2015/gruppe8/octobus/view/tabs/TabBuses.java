package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelBus;

/**
 * @author Michael Biech
 */
public class TabBuses extends TabTable<TableModelBus>
{
	public TabBuses()
	{
		super(TableModelBus.class);

		setLayout(new BorderLayout(5,5));


		JPanel plFilter = new JPanel();
		plFilter.add(lbFilter);
		plFilter.add(tfFilter);
		add(plFilter, BorderLayout.PAGE_START);

		add(new JScrollPane(table), BorderLayout.CENTER);

		setVisible(true);
	}


}
