package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplayTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author Patricia Schinke
 *         still does not get the Data
 */
public class DisplayTicket extends JPanel
{
	//panel which shall show all tickets should be with scollpane
	private JPanel plTable;
	private JButton btnBack;
	private ControllerDisplayTicket controller;

	public DisplayTicket()
	{
		controller = new ControllerDisplayTicket(this);

		setLayout(new BorderLayout(5,5));
		plTable = new JPanel();
		JScrollPane scrollPane = new JScrollPane(plTable);
		add(scrollPane, BorderLayout.CENTER);

		//all Tickets shown vertical
		plTable.setLayout(new BoxLayout(plTable, BoxLayout.Y_AXIS));

		btnBack = new JButton("Zurück");
		btnBack.addActionListener(e->{
			controller.buttonPressed(EmitterButton.DISPLAY_TICKET_BACK);
		});

		JPanel plButton = new JPanel();
		plButton.setLayout(new BorderLayout());
		plButton.add(btnBack, BorderLayout.EAST);
		add(plButton, BorderLayout.PAGE_END);
		controller.fill();
	}


	//adds Panel to the plTable with data from the dataBase (is used in ControllerFormShowTckets
	public void addPanel(String name, int price, int numPassengers, String description){
		JPanel plNew = new JPanel();
	//noch umranden und vllt mit Farben und anderen schriftgrößen schöner machen
	//	plNew.setBackground(Color.lightGray);
		plNew.setLayout(new GridBagLayout());
		plNew.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		GridBagConstraints cst = new GridBagConstraints();
		cst.ipadx = 20;
		cst.ipady = 20;
		cst.gridx = 0;
		cst.gridy = 0;
		JLabel lbName = new JLabel(name);
		plNew.add(lbName, cst);
		cst.gridx = 1;
		JLabel lbPrice = new JLabel(""+price);
		plNew.add(lbPrice, cst);
		cst.gridx = 2;
		JLabel lbNumPassengers = new JLabel(""+numPassengers);
		plNew.add(lbNumPassengers, cst);
		cst.gridx = 0;
		cst.gridy = 1;
		cst.gridwidth = 3;
		String[] lines = description.split("\n");
		//TODO add every single line with for-each
		JLabel lbDescription = new JLabel(description);
		plNew.add(lbDescription, cst);
		plTable.add(plNew);
	}
}
