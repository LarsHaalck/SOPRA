package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplayTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;

/**
 * Used to display available tickets.
 */
public class DisplayTicket extends JPanel
{
	private JPanel plTable;
	private JPanel plTickets;
	private JButton btnBack;
	private ControllerDisplayTicket controller;
	private GridBagConstraints cst;

	public DisplayTicket()
	{
		controller = new ControllerDisplayTicket(this);

		setLayout(new BorderLayout(5,5));
		plTable = new JPanel();
		plTickets = new JPanel();
		JScrollPane scrollPane = new JScrollPane(plTable);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); //increase scroll speed
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
		plTickets.setLayout(new GridBagLayout());
		plTickets.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		cst = new GridBagConstraints();
		controller.fill(); //adds the ticketInformation
		plTable.add(plTickets);
	}

	/**
	 * 	Adds Panel to the plTable with data from the dataBase.
	 * 	Is used in ControllerFormShowTickets.
	 *
	 * @param name of the ticket
	 * @param price of the ticket
	 * @param numPassengers which can drive with the ticket
	 * @param description of the ticket
	 * @param i position of the y-position (for gridBagLayout)
	 */
	public void addPanel(String name, int price, int numPassengers, String description, int i){
		cst.gridy = i;
		if(i>0)
		{
			JPanel plDistance = new JPanel();
			plDistance.setBackground(Color.LIGHT_GRAY);
			plDistance.setPreferredSize(new Dimension(982, 3));
			plTickets.add(plDistance, cst);
		}
		cst.ipadx = 20;
		cst.ipady = 20;
		cst.gridx = 0;
		cst.gridy = i+1;
		cst.gridwidth = 2;
		JLabel lbName = new JLabel(name);
		lbName.setFont(new Font(lbName.getFont().getFontName(), Font.BOLD, 18));
		plTickets.add(lbName, cst);
		cst.gridx = 0;
		cst.gridy = i+2;
		cst.gridwidth = 1;
		cst.weightx = 1;
		cst.anchor = GridBagConstraints.LINE_END;
		JLabel lbPrice = new JLabel("Preis: "+formatPrice(price)+"€");
		lbPrice.setFont(new Font(lbPrice.getFont().getFontName(), Font.PLAIN, 14));
		plTickets.add(lbPrice, cst);
		cst.gridx = 1;
		cst.anchor = GridBagConstraints.LINE_START;
		String strNumPas;
		if(numPassengers == 1)
		{
			strNumPas = numPassengers+" Person";
		}
		else
		{
			strNumPas = numPassengers+" Personen";
		}
		JLabel lbNumPassengers = new JLabel(strNumPas);
		lbNumPassengers.setFont(new Font(lbNumPassengers.getFont().getFontName(), Font.PLAIN, 14));
		plTickets.add(lbNumPassengers, cst);
		cst.gridx = 0;
		cst.gridy = i+3;
		cst.gridwidth = 2;
		cst.anchor = GridBagConstraints.CENTER;
		String[] lines = description.split("\n");
		for(String line: lines)
		{
			JLabel lbDescription = new JLabel(line);
			plTickets.add(lbDescription, cst);
			i++;
			cst.gridy = i+3;
		}
	}

	/**
	 * Formats the price of the ticket and gives it back in a String.
	 *
	 * @param price of the ticket
	 * @return String with the right format of the price
	 */
	private String formatPrice(int price)
	{
		int euro = price/100;
		int cent = price%100;

		String centString = cent+"";
		if(centString.length()==1){centString="0"+centString;}

		return euro+","+centString;
	}
}
