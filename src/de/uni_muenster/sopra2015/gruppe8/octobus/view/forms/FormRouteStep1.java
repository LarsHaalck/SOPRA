package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;


import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterTable;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.ExtendedTableModel;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Part of the FormRoute wizard. This is used as the first step.
 */
public class FormRouteStep1 extends JPanel
{
	private JTable busStopCurrent, busStopAvailable;
	private BasicArrowButton add, delete, up, down;
	private Box boxTop;
	private RouteTableModel model_1,model_2;
	private JLabel name;
	private JCheckBox nightLineClick;
	private JPanel bottomPanel, space;
	private FieldText nameTour;
	private JScrollPane t1, t2;
	private ControllerFormRoute controllerFormRoute;

	public FormRouteStep1(ControllerFormRoute controllerFormRoute)
	{
		this.controllerFormRoute = controllerFormRoute;

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		name = new JLabel("Name der Linie: ");
		nameTour = new FieldText(20, -1);
		nightLineClick = new JCheckBox("Nachtlinie");

		boxTop = new Box(BoxLayout.X_AXIS);
		boxTop.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));

		boxTop.add(name);
		boxTop.add(Box.createHorizontalStrut(10));
		boxTop.add(nameTour);
		boxTop.add(Box.createHorizontalStrut(500));
		boxTop.add(nightLineClick);
		boxTop.add(Box.createHorizontalStrut(30));

		add(boxTop, BorderLayout.NORTH);

		//-------------------------------------

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		GridBagConstraints cb = new GridBagConstraints();


		GridBagConstraints cButton = new GridBagConstraints();
		GridBagConstraints cTable = new GridBagConstraints();

		cButton.ipadx = 20;
		cButton.ipady = 20;

		cTable.gridheight = 4;

		up = new BasicArrowButton(BasicArrowButton.NORTH);
		up.addActionListener(e ->
				controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_UP));
		cButton.gridx = 0;
		cButton.gridy = 1;
		cButton.insets = new Insets(130, 0, 5, 50);
		bottomPanel.add(up, cButton);

		down = new BasicArrowButton(BasicArrowButton.SOUTH);
		down.addActionListener(e ->
				controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_DOWN));
		cButton.gridx = 0;
		cButton.gridy = 2;
		cButton.insets = new Insets(5, 0, 5, 50);
		bottomPanel.add(down, cButton);

		model_1 = new RouteTableModel();
		busStopCurrent = new JTable(model_1)
		{
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				JComponent jcomp = (JComponent)comp;
				if (comp == jcomp) {
					jcomp.setToolTipText((String)getValueAt(row, col));
				}
				return comp;
			}
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		busStopCurrent.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		busStopCurrent.removeColumn(busStopCurrent.getColumnModel().getColumn(0));
		busStopCurrent.setFillsViewportHeight(true);
		busStopCurrent.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
				{
					controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_DELETE);
				}
			}
		});
		busStopCurrent.addFocusListener(new FocusAdapter()
		{

			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP1_CURRENT);
			}
		});
		t1 = new JScrollPane(busStopCurrent);
		t1.setPreferredSize(new Dimension(250, 400));
		cTable.gridx = 1;
		cTable.gridy = 0;
		bottomPanel.add(t1, cTable);

		add = new BasicArrowButton(BasicArrowButton.WEST);
		add.addActionListener(e ->
				controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_ADD));
		cButton.gridx = 2;
		cButton.gridy = 1;
		cButton.insets = new Insets(135, 50, 20, 50);
		bottomPanel.add(add, cButton);

		delete = new BasicArrowButton(BasicArrowButton.EAST);
		delete.addActionListener(e ->
				controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_DELETE));
		cButton.gridx = 2;
		cButton.gridy = 2;
		cButton.insets = new Insets(20, 50, 5, 50);
		bottomPanel.add(delete, cButton);

		model_2 = new RouteTableModel();
		busStopAvailable = new JTable(model_2)
		{
			@Override
			public Component prepareRenderer(TableCellRenderer renderer,int row, int col) {
				Component comp = super.prepareRenderer(renderer, row, col);
				JComponent jcomp = (JComponent)comp;
				if (comp == jcomp) {
					jcomp.setToolTipText((String)getValueAt(row, col));
				}
				return comp;
			}

			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend)
			{
				super.changeSelection(rowIndex,columnIndex,true,false);
			}
		};
		busStopAvailable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		busStopAvailable.removeColumn(busStopAvailable.getColumnModel().getColumn(0));
		busStopAvailable.setFillsViewportHeight(true);
		busStopAvailable.addKeyListener(new KeyAdapter()
		{
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_STEP1_ADD);
				}
			}
		});
		busStopAvailable.addFocusListener(new FocusAdapter()
		{
			@Override
			public void focusLost(FocusEvent e)
			{
				controllerFormRoute.tableFocusLost(EmitterTable.FORM_ROUTE_STEP1_AVAILABLE);
			}
		});
		t2 = new JScrollPane(busStopAvailable);
		t2.setPreferredSize(new Dimension(250, 400));
		cTable.gridx = 3;
		cTable.gridy = 0;
		bottomPanel.add(t2, cTable);

		space = new JPanel();
		space.setPreferredSize(new Dimension(40, 10));
		cTable.gridx = 4;
		cTable.gridy = 0;
		bottomPanel.add(space, cTable);

		add(bottomPanel, BorderLayout.CENTER);
	}

	/**
	 * TableModel for tables used in this form.
	 * Sets column name to "Haltestelle".
	 */
	private class RouteTableModel extends ExtendedTableModel
	{
		public RouteTableModel()
		{
			columnNames = new String[]{"Haltestelle"};
		}

		@Override
		public int getFirstSortColumn()
		{
			return 0;
		}

		@Override
		public String[] getRefineableColumns()
		{
			return new String[0];
		}

		@Override
		public Class getColumnClass(int column)
		{
			switch(column)
			{
				case 0:
					return Integer.class;

				case 1:
					return String.class;
			}
			return null;
		}
	}

	/**
	 * Fills table available with data.
	 */
	public void fillTableAvailable(Object[][] data)
	{
		model_2.setData(data);
	}

	/**
	 * Fills table current with data.
	 */
	public void fillTableCurrent(Object[][] data)
	{
		model_1.setData(data);
		revalidate();
		repaint();
		t1.invalidate();
		t1.revalidate();
		t1.repaint();
	}

	//--------------------------------------
	//------------- getter -----------------
	//--------------------------------------

	public JTable getBusStopCurrent()
	{
		return busStopCurrent;
	}

	public JTable getBusStopAvailable()
	{
		return busStopAvailable;
	}

	public RouteTableModel getModel_1()
	{
		return model_1;
	}

	public RouteTableModel getModel_2()
	{
		return model_2;
	}

	public ArrayList<Tuple<Integer, String>> getTableData () {
		int nRow = model_1.getRowCount();
		ArrayList<Tuple<Integer, String>> tableData = new ArrayList<>();
		for (int i = 0 ; i < nRow ; i++)
			tableData.add(new Tuple<Integer, String>((int) model_1.getValueAt(i,0),model_1.getValueAt(i,1).toString()));
		return tableData;
	}

	public String getNameRoute()
	{
		return nameTour.getText();
	}

	public boolean isNightLine()
	{
		return nightLineClick.isSelected();
	}

	//--------------------------------------
	//------------- setter -----------------
	//--------------------------------------

	public void setNightLine(boolean isNightLine)
	{
		nightLineClick.setSelected(isNightLine);
	}

	public void setNameRoute(String setNameTour)
	{
		nameTour.setText(setNameTour);
	}

}
