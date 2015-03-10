package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldNumber;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Jonas on 04.03.2015.
 */

public class FormRouteStep2 extends JPanel
{
	private String[] busStops;

	private JScrollPane jspMain;
	private JPanel jpMain, jpRightMain, jpButton, jpTables;
	private LinkedList<DepartureTime> departureTimes;
	private JButton addButton, editButton, deleteButton;
	private JScrollPane jspMo, jspDi, jspMi, jspDo, jspFr, jspSa, jspSo;
	private DefaultTableModel dtmMo, dtmDi, dtmMi, dtmDo, dtmFr, dtmSa, dtmSo;
	private JTable jtMo, jtDi, jtMi, jtDo, jtFr, jtSa, jtSo;
	private ControllerFormRoute controllerFormRoute;
	private JTable tableActive;

	public FormRouteStep2(ControllerFormRoute controllerFormRoute)
	{
		this.controllerFormRoute = controllerFormRoute;

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

		//Monday
		dtmMo = new DefaultTableModel(null, new String[] {"Montag"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtMo = new JTable(dtmMo)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtMo.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtMo;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_MONDAY);
			}
		});
		jtMo.setFillsViewportHeight(true);
		jspMo = new JScrollPane(jtMo);
		jpTables.add(jspMo);

		//Tuesday
		dtmDi = new DefaultTableModel(null, new String[] {"Dienstag"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtDi = new JTable(dtmDi)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtDi.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtDi;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_TUESDAY);
			}
		});
		jtDi.setFillsViewportHeight(true);
		jspDi = new JScrollPane(jtDi);
		jpTables.add(jspDi);

		//Wednesday
		dtmMi = new DefaultTableModel(null, new String[] {"Mittwoch"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtMi = new JTable(dtmMi)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtMi.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtMi;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_WEDNESDAY);
			}
		});
		jtMi.setFillsViewportHeight(true);
		jspMi = new JScrollPane(jtMi);
		jpTables.add(jspMi);

		//Thursday
		dtmDo = new DefaultTableModel(null, new String[] {"Donnerstag"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtDo = new JTable(dtmDo)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtDo.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtDo;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_THURSDAY);
			}
		});
		jtDo.setFillsViewportHeight(true);
		jspDo = new JScrollPane(jtDo);
		jpTables.add(jspDo);

		//Friday
		dtmFr = new DefaultTableModel(null, new String[] {"Freitag"})
		{
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtFr = new JTable(dtmFr)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtFr.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtFr;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_FRIDAY);
			}
		});
		jtFr.setFillsViewportHeight(true);
		jspFr = new JScrollPane(jtFr);
		jpTables.add(jspFr);

		//Saturday
		dtmSa = new DefaultTableModel(null, new String[] {"Samstag"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtSa = new JTable(dtmSa)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtSa.addFocusListener(new  FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtSa;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_SATURDAY);
			}
		});
		jtSa.setFillsViewportHeight(true);
		jspSa = new JScrollPane(jtSa);
		jpTables.add(jspSa);

		//Sunday
		dtmSo = new DefaultTableModel(null, new String[] {"Sonntag"})
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;//This causes all cells to be not editable
			}
		};
		jtSo = new JTable(dtmSo)
		{
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		jtSo.addFocusListener(new FocusListener()
		{

			@Override
			public void focusGained(FocusEvent e)
			{
				tableActive = jtSo;
			}

			@Override
			public void focusLost(FocusEvent e)
			{
					controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP2_SUNDAY);
			}
		});
		jtSo.setFillsViewportHeight(true);
		jspSo = new JScrollPane(jtSo);
		jpTables.add(jspSo);

		jpRightMain.add(jpTables, BorderLayout.CENTER);


		jpButton = new JPanel();
		jpButton.setLayout(new FlowLayout());

		addButton = new JButton("Hinzufügen");
		addButton.addActionListener(e -> {
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP2_ADD);
		});
		jpButton.add(addButton);
		editButton = new JButton("Bearbeiten");
		editButton.setFocusable(false);
		editButton.addActionListener(e -> {
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP2_EDIT);
		});
		jpButton.add(editButton);
		deleteButton = new JButton("Löschen");
		deleteButton.setFocusable(false);
		deleteButton.addActionListener(e -> {
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP2_DELETE);
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
			String shortened = name;
			if(shortened.length() > 25)
				shortened = shortened.substring(0,25) + "...";

			lbBusStop = new JLabel(shortened);
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

	public Tuple<Integer, Integer> showEditDialog(int h, int m)
	{
		JPanel p = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Bitte ändern sie den Wert");
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setBorder(new EmptyBorder(new Insets(0,0,10,0)));
		p.add(label, BorderLayout.NORTH);

		JPanel fieldWrapper = new JPanel();
		fieldWrapper.setLayout(new BoxLayout(fieldWrapper, BoxLayout.LINE_AXIS));
		FieldNumber hours = new FieldNumber(3,2,23);
		hours.setHorizontalAlignment(JLabel.CENTER);
		hours.setNumber(h);
		FieldNumber minutes = new FieldNumber(3,2,59);
		minutes.setHorizontalAlignment(JLabel.CENTER);
		minutes.setNumber(m);
		fieldWrapper.add(Box.createHorizontalStrut(90));
		fieldWrapper.add(hours);
		fieldWrapper.add(Box.createHorizontalStrut(10));
		fieldWrapper.add(new Label(":"));
		//fieldWrapper.add(Box.createHorizontalStrut(2));
		fieldWrapper.add(minutes);
		fieldWrapper.add(Box.createHorizontalStrut(100));
		p.add(fieldWrapper, BorderLayout.CENTER);
		int test = JOptionPane.showConfirmDialog(null, p, "Startzeit ändern", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if(test == JOptionPane.OK_OPTION)
		{
			return new Tuple<Integer, Integer>(hours.getNumber(),minutes.getNumber());
		}
		else
		{
			return null;
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
		if(busStops.length > 0)
		{
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

	public JTable getTableActive()
	{
		return tableActive;
	}

	public DayOfWeek getActiveDay()
	{
		String active = tableActive.getColumnName(0);
		DayOfWeek day = null;
		switch(active)
		{
			case "Montag": day = DayOfWeek.MONDAY; break;
			case "Dienstag": day = DayOfWeek.TUESDAY; break;
			case "Mittwoch": day = DayOfWeek.WEDNESDAY; break;
			case "Donnerstag": day = DayOfWeek.THURSDAY; break;
			case "Freitag": day = DayOfWeek.FRIDAY; break;
			case "Samstag": day = DayOfWeek.SATURDAY; break;
			case "Sonntag": day = DayOfWeek.SUNDAY; break;
		}
		return day;
	}

	public int[] getDepartureTime()
	{
		int[] times = new int[departureTimes.size()];
		for (int i = 0; i < departureTimes.size(); i++)
			times[i] = departureTimes.get(i).getTime();
		return times;
	}

	public DefaultTableModel getDtmMo()
	{
		return dtmMo;
	}

	public DefaultTableModel getDtmDi()
	{
		return dtmDi;
	}

	public DefaultTableModel getDtmMi()
	{
		return dtmMi;
	}

	public DefaultTableModel getDtmDo()
	{
		return dtmDo;
	}

	public DefaultTableModel getDtmSa()
	{
		return dtmSa;
	}

	public DefaultTableModel getDtmFr()
	{
		return dtmFr;
	}

	public DefaultTableModel getDtmSo()
	{
		return dtmSo;
	}

	public JTable getJtMo()
	{
		return jtMo;
	}

	public JTable getJtDi()
	{
		return jtDi;
	}

	public JTable getJtDo()
	{
		return jtDo;
	}

	public JTable getJtMi()
	{
		return jtMi;
	}

	public JTable getJtFr()
	{
		return jtFr;
	}

	public JTable getJtSa()
	{
		return jtSa;
	}

	public JTable getJtSo()
	{
		return jtSo;
	}
}
