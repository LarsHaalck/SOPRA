package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Jonas on 04.03.2015.
 */
public class FormTourStep1 extends JPanel
{
	private JTable busStopCurrent, busStopAvailable;
	private BasicArrowButton add, delete, up, down;
	private Box boxMain, boxTop;
	private DefaultTableModel model_1,model_2;
	private String[][] rowdata_busStopCurrent, rowdata_busStopAvailable;
	private JLabel name;
	private JCheckBox nightLineClick;
	private JPanel bottomPanel, space;
	private FieldText nameTour;
	private JScrollPane t1, t2;

	public FormTourStep1()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		name = new JLabel("Name der Linie");
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
		cButton.gridx = 0;
		cButton.gridy = 1;
		cButton.insets = new Insets(130, 0, 5, 50);
		bottomPanel.add(up, cButton);

		down = new BasicArrowButton(BasicArrowButton.SOUTH);
		cButton.gridx = 0;
		cButton.gridy = 2;
		cButton.insets = new Insets(5, 0, 5, 50);
		bottomPanel.add(down, cButton);

		model_1 = new DefaultTableModel(rowdata_busStopCurrent, new String[] {"Haltestelle"});
		busStopCurrent = new JTable(model_1);
		busStopCurrent.setFillsViewportHeight(true);
		t1 = new JScrollPane(busStopCurrent);
		t1.setPreferredSize(new Dimension(250, 400));
		cTable.gridx = 1;
		cTable.gridy = 0;
		bottomPanel.add(t1, cTable);

		add = new BasicArrowButton(BasicArrowButton.WEST);
		cButton.gridx = 2;
		cButton.gridy = 1;
		cButton.insets = new Insets(135, 50, 20, 50);
		bottomPanel.add(add, cButton);

		delete = new BasicArrowButton(BasicArrowButton.EAST);
		cButton.gridx = 2;
		cButton.gridy = 2;
		cButton.insets = new Insets(20, 50, 5, 50);
		bottomPanel.add(delete, cButton);

		model_2 = new DefaultTableModel(rowdata_busStopAvailable, new String[] {"Haltestelle"});
		busStopAvailable = new JTable(model_2);
		busStopAvailable.setFillsViewportHeight(true);
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

	public Vector<Vector<String>> getTableData() //nochmal angucken
	{
		return model_1.getDataVector();
	}

	public String getNameValue()
	{
		System.out.println(nameTour.getText());
		return nameTour.getText();
	}

	public boolean isNightLine()
	{
		return nightLineClick.isSelected();
	}
}
