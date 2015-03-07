package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormShowTickets;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Patricia on 06.03.2015.
 */
public class FormShowTickets extends JDialog
{
	//panel which shall show all tickets should be with scollpane
	private JPanel plTable;
	private ControllerFormShowTickets controller;

	public FormShowTickets()
	{
		controller = new ControllerFormShowTickets(this);
		plTable = new JPanel();

		JScrollPane scrollPane = new JScrollPane(plTable);
		add(scrollPane);

		//all Tickets shown vertical
		plTable.setLayout(new BoxLayout(plTable, BoxLayout.Y_AXIS));
	}

	public void fillData()
	{
		controller.fill();
	}

	//adds Panel to the plTable with data from the dataBase (is used in ControllerFormShowTckets
	public void addPanel(String name, int price, int numPassengers, String description){
		JPanel plNew = new JPanel();
		plNew.setLayout(new GridBagLayout());
		GridBagConstraints cst = new GridBagConstraints();
		cst.ipadx = 20;
		cst.ipady = 20;
		cst.gridx = 0;
		cst.gridy = 0;
		JLabel lbName = new JLabel(name);
		add(lbName, cst);
		cst.gridx = 1;
		JLabel lbPrice = new JLabel(""+price);
		add(lbPrice, cst);
		cst.gridx = 2;
		JLabel lbNumPassengers = new JLabel(""+numPassengers);
		add(lbNumPassengers, cst);
		cst.gridx = 1;
		cst.gridy = 0;
		cst.gridwidth = 3;
		JLabel lbDescription = new JLabel(description);
		add(lbDescription, cst);
		plTable.add(plNew);
	}
}
