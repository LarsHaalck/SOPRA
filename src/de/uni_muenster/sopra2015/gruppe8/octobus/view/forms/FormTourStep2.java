package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Jonas on 04.03.2015.
 */

//TODO JTextField und folgen änder

public class FormTourStep2 extends JPanel
{
	private String[] busStops;

	private JScrollPane jspMain;
	private JPanel jpMain;
	private LinkedList<DepartureTime> departureTimes;

	public FormTourStep2()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		jpMain = new JPanel();
//		jpMain.setLayout(new GridLayout(100, 1));

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

		public String getTime()
		{
			return jtfDepartureTime.getText();
		}
	}

	public void fillJpMain(String[] busStops)
	{
		jpMain.removeAll();
		departureTimes = new LinkedList<DepartureTime>();
		int gridRow = 13;
		if (busStops.length > 7)
			gridRow = busStops.length*2 - 1;
		jpMain.setLayout(new GridLayout(gridRow, 1));
		System.out.println(gridRow);
		jpMain.add(new BusStop(busStops[0]));
		for (int i = 1; i < busStops.length; i++)
		{
			DepartureTime dt = new DepartureTime();
			departureTimes.add(dt);
			jpMain.add(dt);
			jpMain.add(new BusStop(busStops[i]));
		}
		revalidate();
		repaint();
	}

	public String[] getDepartureTime()
	{
		String[] times = new String[departureTimes.size()];
		for (int i = 0; i < departureTimes.size(); i++)
			times[i] = departureTimes.get(i).getTime();
		return times;
	}
}
