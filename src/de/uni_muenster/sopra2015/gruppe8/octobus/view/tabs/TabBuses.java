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
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;
	public TabBuses()
	{
		super(TableModelBus.class, true, true);

		setLayout(new BorderLayout(5,5));

		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			//Do delete stuff
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			//Do edit stuff
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			//Do new stuff
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
