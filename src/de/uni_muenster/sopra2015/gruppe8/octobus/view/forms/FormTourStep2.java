package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public class FormTourStep2 extends JPanel
{
	private JScrollPane jspMain;
	private JPanel jpMain;

	public FormTourStep2()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(100, 1)); //100 muss noch verändert werden

		jpMain.add(new BusStop("Haltestelle1"));
		jpMain.add(new DepartureTime());
		jpMain.add(new BusStop("Haltestelle1"));


		jspMain = new JScrollPane(jpMain);
		jspMain.setPreferredSize(new Dimension(200, getHeight()));
		add(jspMain, BorderLayout.WEST);
	}

	private class BusStop extends JPanel
	{
		private Border border;
		private JLabel lbBusStop;

		public BusStop(String name)
		{
			setLayout(new FlowLayout());
			border = BorderFactory.createLineBorder(Color.GRAY);
			lbBusStop = new JLabel(name);
			lbBusStop.setHorizontalAlignment(JLabel.CENTER);
			add(lbBusStop);
		}
	}

	private class DepartureTime extends JPanel
	{
		private JLabel lbArrow;
		private JTextField jtfDepartureTime;

		public DepartureTime()
		{
			setLayout(new FlowLayout());
			lbArrow = new JLabel("↓");
			lbArrow.setHorizontalAlignment(JLabel.CENTER);
			add(lbArrow);
			jtfDepartureTime = new JTextField(5);
			add(jtfDepartureTime);
		}
	}
}
