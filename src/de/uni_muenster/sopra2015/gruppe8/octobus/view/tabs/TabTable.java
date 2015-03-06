package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.DefaultExtendedTableModel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Abstract class that creates stuff we need for every tab with a table
 * TM is TableModel that should be use
 */
public abstract class TabTable<TM extends ExtendedTableModel> extends JPanel
{
	protected JTable table;
	protected JTextField tfFilter;
	protected JLabel lbFilter = new JLabel("Filter-Text:");
	protected JComboBox<String> cbFilter;
	protected TableRowSorter<TM> sorter;
	protected int selectedRow = -1;
	protected int selectedID = -1;
	private TM model;
	private boolean isRefineable = true;
	private boolean enableMultiFilter = false;
	private int filterColumn = 0;
	public TabTable(Class<TM> type, boolean isRefineable, boolean enableMultifilter)
	{
		this.isRefineable = isRefineable;
		this.enableMultiFilter = enableMultifilter;
		try
		{
			model = type.newInstance();
		} catch (Exception e)
		{
			model = (TM) new DefaultExtendedTableModel();
		}

		sorter = new TableRowSorter<>((TM)model);
		table = new JTable((TM)model);
		table.setFillsViewportHeight(true);

		table.removeColumn(table.getColumnModel().getColumn(0));

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
						selectedRow = -1;
						selectedID = -1;

					} else
					{
						selectedRow = table.convertRowIndexToModel(viewRow);
						selectedID = (int) model.getValueAt(selectedRow, 0);
					}
				}
		);
		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount() == 2)
					editEntry();
			}
		});

		cbFilter = new JComboBox<>(((TM)model).getRefineableColumns());

		if(isRefineable)
		{

			if(enableMultifilter) //multi-filter should only work, if filters are generally enabled
			{
				cbFilter.addItem("alle");
				cbFilter.setSelectedIndex(((TM)model).getRefineableColumns().length);
			}

			cbFilter.addActionListener(e -> {
				filterColumn = ((TM)model).getColumnIndex((String) cbFilter.getSelectedItem());
				newFilter();
			});

		}

		tfFilter = new JTextField(20);

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

	public boolean isRefineable()
	{
		return isRefineable;
	}

	public boolean isEnableMultiFilter()
	{
		return enableMultiFilter;
	}

	public int getSelectedID()
	{
		return this.selectedID;
	}

	/**
	 * Update the row filter regular expression from the expression in
	 * the text box.
	 */
	private void newFilter()
	{
		if(model.getRowCount() <= 0)
			return;

		RowFilter<TM, Object> rf = null;

		if(enableMultiFilter && ((String)cbFilter.getSelectedItem()).equals("alle"))
		{
			int[] ids = ((TM)model).getRefineableColumnsIDs();
			ArrayList<RowFilter<TM, Object>> filters = new ArrayList<>(ids.length); //one filter for every column
			for(int i = 0; i < ids.length; i++)
			{
				try
				{
					filters.add(RowFilter.regexFilter("(?i)" + tfFilter.getText(), ids[i]));
				} catch (java.util.regex.PatternSyntaxException e)
				{
					return;
				}
			}

			rf = RowFilter.orFilter(filters);
		}
		else
		{
			//If current expression doesn't parse, don't update.
			try
			{
				rf = RowFilter.regexFilter("(?i)" + tfFilter.getText(), filterColumn);
			} catch (java.util.regex.PatternSyntaxException e)
			{
				return;
			}

		}
		sorter.setRowFilter(rf);
	}

	protected abstract void editEntry();

	public void fillTable(Object[][] data)
	{
		model.setData(data);
		table.revalidate();
		table.repaint();
	}
}
