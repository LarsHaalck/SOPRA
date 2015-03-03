package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.DefaultExtendedTableModel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Florian on 03.03.2015.
 */
public abstract class TabTable<TM extends ExtendedTableModel> extends JPanel
{
	protected JTable table;
	protected JTextField tfFilter;
	protected JLabel lbFilter = new JLabel("Filter-Text:");
	protected JComboBox<String> cbFilter;
	protected TableRowSorter<TM> sorter;
	private ExtendedTableModel model;

	private boolean isRefineable = true;
	protected int selectedRow = -1;
	private int filterColumn = 0;

	public TabTable(Class<TM> type, boolean isRefineable)
	{
		this.isRefineable = isRefineable;
		//TODO: Check this! Could we get problems?
		try
		{
			model = type.newInstance();
		} catch (Exception e)
		{
			model = new DefaultExtendedTableModel();
		}

		sorter = new TableRowSorter<>((TM)model);
		table = new JTable(model);
		table.setRowSorter(sorter);
		table.setRowHeight(19);
		//Only allow one selection
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//When selection changes, provide user with row numbers for
		//both view and model.
		//TODO: Move this to controller, maybe
		table.getSelectionModel().addListSelectionListener(
				event -> {
					int viewRow = table.getSelectedRow();
					if (viewRow < 0)
					{

					} else
					{
						selectedRow = table.convertRowIndexToModel(viewRow);
					}
				}
		);

		cbFilter = new JComboBox<>(model.getRefineableColumns());
		if(isRefineable)
		{
			cbFilter.addActionListener(e -> {
				filterColumn = model.getColumnIndex((String) cbFilter.getSelectedItem());
				newFilter();
			});
		}


		tfFilter = new JTextField();
		//Whenever filterText changes, invoke newFilter.
		if(isRefineable)
		{
			tfFilter.getDocument().addDocumentListener(
					new DocumentListener()
					{
						public void changedUpdate(DocumentEvent e)
						{
							newFilter();
						}

						public void insertUpdate(DocumentEvent e)
						{
							newFilter();
						}

						public void removeUpdate(DocumentEvent e)
						{
							newFilter();
						}
					});
		}

	}

	/**
	 * Update the row filter regular expression from the expression in
	 * the text box.
	 */
	private void newFilter()
	{
		RowFilter<TM, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try
		{
			rf = RowFilter.regexFilter(tfFilter.getText(), filterColumn);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}
}
