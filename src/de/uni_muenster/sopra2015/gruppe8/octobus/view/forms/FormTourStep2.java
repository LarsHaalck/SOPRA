package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Jonas on 04.03.2015.
 */

public class FormTourStep2 extends JPanel
{
	private String[] busStops;

	private JScrollPane jspMain;
	private JPanel jpMain, jpRightMain, jpButton, jpTables;
	private LinkedList<DepartureTime> departureTimes;
	private JButton addButton, editButton, deleteButton;
	private JScrollPane jspMo, jspDi, jspMi, jspDo, jspFr, jspSa, jspSo;
	private DefaultTableModel dtmMo, dtmDi, dtmMi, dtmDo, dtmFr, dtmSa, dtmSo;
	private JTable jtMo, jtDi, jtMi, jtDo, jtFr, jtSa, jtSo;

	public FormTourStep2()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		jpMain = new JPanel();

		jspMain = new JScrollPane(jpMain);
		jspMain.setPreferredSize(new Dimension(200, getHeight()));
		add(jspMain, BorderLayout.WEST);

		//---------------------------------

		jpRightMain = new JPanel();
		jpRightMain.setLayout(new BorderLayout());
		jpRightMain.setBorder(new EmptyBorder(new Insets(0, 10, 0, 0)));
		
		jpTables = new JPanel();
		jpTables.setLayout(new BoxLayout(jpTables, BoxLayout.X_AXIS));
		
		dtmMo = new DefaultTableModel(null, new String[] {"Montag"});
		jtMo = new JTable(dtmMo);
		jtMo.setFillsViewportHeight(true);
		jspMo = new JScrollPane(jtMo);
		jpTables.add(jspMo);

		dtmDi = new DefaultTableModel(null, new String[] {"Dienstag"});
		jtDi = new JTable(dtmDi);
		jtDi.setFillsViewportHeight(true);
		jspDi = new JScrollPane(jtDi);
		jpTables.add(jspDi);

		dtmMi = new DefaultTableModel(null, new String[] {"Mittwoch"});
		jtMi = new JTable(dtmMi);
		jtMi.setFillsViewportHeight(true);
		jspMi = new JScrollPane(jtMi);
		jpTables.add(jspMi);

		dtmDo = new DefaultTableModel(null, new String[] {"Donnerstag"});
		jtDo = new JTable(dtmDo);
		jtDo.setFillsViewportHeight(true);
		jspDo = new JScrollPane(jtDo);
		jpTables.add(jspDo);

		dtmFr = new DefaultTableModel(null, new String[] {"Freitag"});
		jtFr = new JTable(dtmFr);
		jtFr.setFillsViewportHeight(true);
		jspFr = new JScrollPane(jtFr);
		jpTables.add(jspFr);

		dtmSa = new DefaultTableModel(null, new String[] {"Samstag"});
		jtSa = new JTable(dtmSa);
		jtSa.setFillsViewportHeight(true);
		jspSa = new JScrollPane(jtSa);
		jpTables.add(jspSa);

		dtmSo = new DefaultTableModel(null, new String[] {"Sonntag"});
		jtSo = new JTable(dtmSo);
		jtSo.setFillsViewportHeight(true);
		jspSo = new JScrollPane(jtSo);
		jpTables.add(jspSo);

		jpRightMain.add(jpTables, BorderLayout.CENTER);
		

		jpButton = new JPanel();
		jpButton.setLayout(new FlowLayout());

		addButton = new JButton("Hinzufügen");
		addButton.addActionListener(e -> {

		});
		jpButton.add(addButton);
		editButton = new JButton("Bearbeiten");
		editButton.addActionListener(e -> {

		});
		jpButton.add(editButton);
		deleteButton = new JButton("Löschen");
		deleteButton.addActionListener(e -> {

		});
		jpButton.add(deleteButton);
		jpRightMain.add(jpButton, BorderLayout.SOUTH);

		add(jpRightMain, BorderLayout.CENTER);

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
		private FieldNumber jtfDepartureTime;

		public DepartureTime()
		{
			setLayout(new FlowLayout());
			lbArrow = new JLabel("↓");
			lbArrow.setHorizontalAlignment(JLabel.CENTER);
			add(lbArrow);
			jtfDepartureTime = new FieldNumber(5, 3);
			add(jtfDepartureTime);
		}

		public int getTime()
		{
			return jtfDepartureTime.getNumber();
		}
	}

	//--------------------------------------
	//------------- setter -----------------
	//--------------------------------------
	
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

	public void setTableData_dtmMo(String[][] rowdata_Mo)
	{
		dtmMo.setDataVector(rowdata_Mo, new String[] {"Montag"});
	}

	public void setTableData_dtmDi(String[][] rowdata_Di)
	{
		dtmDi.setDataVector(rowdata_Di, new String[] {"Dienstag"});
	}

	public void setTableData_dtmMi(String[][] rowdata_Mi)
	{
		dtmMi.setDataVector(rowdata_Mi, new String[] {"Mittwoch"});
	}

	public void setTableData_dtmDo(String[][] rowdata_Do)
	{
		dtmDo.setDataVector(rowdata_Do, new String[] {"Donnerstag"});
	}

	public void setTableData_dtmFr(String[][] rowdata_Fr)
	{
		dtmFr.setDataVector(rowdata_Fr, new String[] {"Freitag"});
	}

	public void setTableData_dtmSa(String[][] rowdata_Sa)
	{
		dtmSa.setDataVector(rowdata_Sa, new String[] {"Samstag"});
	}

	public void setTableData_dtmSo(String[][] rowdata_So)
	{
		dtmSo.setDataVector(rowdata_So, new String[] {"Sonntag"});
	}

	//--------------------------------------
	//------------- getter -----------------
	//--------------------------------------

	public int[] getDepartureTime()
	{
		int[] times = new int[departureTimes.size()];
		for (int i = 0; i < departureTimes.size(); i++)
			times[i] = departureTimes.get(i).getTime();
		return times;
	}

	public Vector<Vector<String>> getTableData_dtmMo()
	{
		return dtmMo.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmDi()
	{
		return dtmDi.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmMi()
	{
		return dtmMi.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmDo()
	{
		return dtmDo.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmFr()
	{
		return dtmFr.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmSa()
	{
		return dtmSa.getDataVector();
	}

	public Vector<Vector<String>> getTableData_dtmSo()
	{
		return dtmSo.getDataVector();
	}
}
