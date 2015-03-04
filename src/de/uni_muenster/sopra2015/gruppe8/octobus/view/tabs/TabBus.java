package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import javax.swing.*;
import java.awt.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelBus;

/**
 * @author Michael Biech
 */
public class TabBus extends TabTable<TableModelBus>
{
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;

	public TabBus()
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
