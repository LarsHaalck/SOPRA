package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplayTicket;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Patricia Schinke
 *         still does not get the Data
 */
public class DisplayTicket extends JPanel
{
	private ControllerDisplayTicket controllerDisplayTicket;

	private JTable ticketTable;
	private DefaultTableModel tableModel = new DefaultTableModel();
	private String[] columnNames = {"Name", "Preis", "Anzahl Personen", "Beschreibung"};

	public DisplayTicket()
	{
		controllerDisplayTicket = new ControllerDisplayTicket(this);

		ticketTable = new JTable(tableModel);
		add(new JScrollPane(ticketTable));
		tableModel.setColumnIdentifiers(columnNames);
		ticketTable.setRowSorter(new TableRowSorter<DefaultTableModel>(tableModel));
		ticketTable.getRowSorter().toggleSortOrder(1); //sorts by price
	}
}
