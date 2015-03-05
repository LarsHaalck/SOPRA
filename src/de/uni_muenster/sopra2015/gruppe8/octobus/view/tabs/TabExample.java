package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

/**
 * @author Michael Biech
 *         <p>
 *         This code is mostly ripped from http://docs.oracle.com/javase/tutorial/uiswing/examples/components/TableFilterDemoProject/src/components/TableFilterDemo.java,
 *         but has been modified to fit what we need a little more closely.
 *         <p>
 *         This is still terribly mangled, but should give you a basic idea
 *         of how these things might be implemented.
 */
public class TabExample extends JPanel
{

	private boolean DEBUG = false;
	private JTable table;
	private JTextField filterText;
	private JTextField statusText;
	private TableRowSorter<BusTableModel> sorter;

	public TabExample()
	{
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		//Create a table with a sorter.
		BusTableModel model = new BusTableModel();
		sorter = new TableRowSorter<BusTableModel>(model);
		table = new JTable(model);
		table.setRowSorter(sorter);
		table.setPreferredScrollableViewportSize(new Dimension(0, 300));
		table.setFillsViewportHeight(true);
		table.setRowHeight(19);

		//For the purposes of this example, better to have a single
		//selection.
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//When selection changes, provide user with row numbers for
		//both view and model.
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener()
				{
					public void valueChanged(ListSelectionEvent event)
					{
						int viewRow = table.getSelectedRow();
						if (viewRow < 0)
						{
							//Selection got filtered away.
							statusText.setText("");
						} else
						{
							int modelRow =
									table.convertRowIndexToModel(viewRow);
							statusText.setText(
									String.format("Selected Row in view: %d. " +
													"Selected Row in model: %d.",
											viewRow, modelRow));
						}
					}
				}
		);


		//Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		//Create a separate form for filterText and statusText
		JPanel form = new JPanel(new SpringLayout());
		JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
		//form.add(l1);
		filterText = new JTextField();
		//Whenever filterText changes, invoke newFilter.
		filterText.getDocument().addDocumentListener(
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
		//l1.setLabelFor(filterText);
		add(filterText);
		//Add the scroll pane to this panel.
		add(scrollPane);

		JLabel l2 = new JLabel("Status:", SwingConstants.TRAILING);
		//form.add(l2);
		statusText = new JTextField();
		//l2.setLabelFor(statusText);
		add(statusText);

		//add(form);
	}

	/**
	 * Update the row filter regular expression from the expression in
	 * the text box.
	 */
	private void newFilter()
	{
		RowFilter<BusTableModel, Object> rf = null;
		//If current expression doesn't parse, don't update.
		try
		{
			rf = RowFilter.regexFilter(filterText.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e)
		{
			return;
		}
		sorter.setRowFilter(rf);
	}

	/**
	 * Private class for the table model which ought to be slightly different for  most tables.
	 */
	class BusTableModel extends AbstractTableModel
	{

		BusTableModel()
		{
			System.out.println(red.getDescription());
		}
		ImageIcon red = new ImageIcon("res/images/red.png");
		ImageIcon green = new ImageIcon("res/images/green.png");

		private String[] columnNames = {"Name",
				"Kennzeichen",
				"Sitzplätze",
				"Stehplätze",
				"Einsatzfähig"};
		private Object[][] data = {
				{"Kathy", "Smith",
						"Snowboarding", new Integer(5), red},
				{"John", "Doe",
						"Rowing", new Integer(3), red},
				{"Sue", "Black",
						"Knitting", new Integer(2), green},
				{"Jane", "White",
						"Speed reading", new Integer(20), red},
				{"Joe", "Brown",
						"Pool", new Integer(10), green}
		};

		public int getColumnCount()
		{
			return columnNames.length;
		}

		public int getRowCount()
		{
			return data.length;
		}

		public String getColumnName(int col)
		{
			return columnNames[col];
		}

		public Object getValueAt(int row, int col)
		{
			return data[row][col];
		}

		/*
		 * JTable uses this method to determine the default renderer/
		 * editor for each cell.  If we didn't implement this method,
		 * then the last column would contain text ("true"/"false"),
		 * rather than a check box.
		 */
		public Class getColumnClass(int c)
		{
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's
		 * data can change.
		 *
		 * TODO: Does our data change dynamically? I don't think so...
		 */
		public void setValueAt(Object value, int row, int col)
		{
			if (DEBUG)
			{
				System.out.println("Setting value at " + row + "," + col
						+ " to " + value
						+ " (an instance of "
						+ value.getClass() + ")");
			}

			data[row][col] = value;
			fireTableCellUpdated(row, col);

			if (DEBUG)
			{
				System.out.println("New value of data:");
				printDebugData();
			}
		}

		private void printDebugData()
		{
			int numRows = getRowCount();
			int numCols = getColumnCount();

			for (int i = 0; i < numRows; i++)
			{
				System.out.print("    row " + i + ":");
				for (int j = 0; j < numCols; j++)
				{
					System.out.print("  " + data[i][j]);
				}
				System.out.println();
			}
			System.out.println("--------------------------");
		}
	}

}
