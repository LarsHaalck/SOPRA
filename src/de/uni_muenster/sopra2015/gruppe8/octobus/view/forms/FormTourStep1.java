package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public class FormTourStep1 extends JPanel
{
	private JTable busStopCurrent, busStopAvailable;
	private BasicArrowButton add, delete, up, down;
	private Box boxMain, boxTop, boxButtonLeft, boxButtonRight;
	private DefaultTableModel model_1,model_2;
	private String[][] rowdata;
	private JLabel name, nightLine;
	private JCheckBox nightLineClick;
	private JPanel topPanel;
	private JTextField nameTour;

	public FormTourStep1()
	{
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(new Insets(5, 10, 15, 10)));

		name = new JLabel("Name der Linie");
		nameTour = new JTextField(20);
		nightLineClick = new JCheckBox();
		nightLine = new JLabel("Nachtlinie");

		boxTop = new Box(BoxLayout.X_AXIS);
		boxTop.setBorder(new EmptyBorder(new Insets(5, 10, 10, 10)));

		boxTop.add(name);
		boxTop.add(Box.createHorizontalStrut(10));
		boxTop.add(nameTour);
		boxTop.add(Box.createHorizontalStrut(500));
		boxTop.add(nightLineClick);
		boxTop.add(Box.createHorizontalStrut(5));
		boxTop.add(nightLine);
		boxTop.add(Box.createHorizontalStrut(30));

		add(boxTop, BorderLayout.NORTH);

		up = new BasicArrowButton(BasicArrowButton.NORTH);
		down = new BasicArrowButton(BasicArrowButton.SOUTH);

		boxButtonLeft = new Box(BoxLayout.Y_AXIS);
		boxButtonLeft.add(Box.createHorizontalStrut(90));
		boxButtonLeft.add(Box.createVerticalStrut(140));
		boxButtonLeft.add(up);
		boxButtonLeft.add(Box.createVerticalStrut(30));
		boxButtonLeft.add(down);
		boxButtonLeft.add(Box.createVerticalStrut(140));

		model_1 = new DefaultTableModel(rowdata, new String[] {"Haltestelle"});
		busStopCurrent = new JTable(model_1);
		//TODO Table

		add = new BasicArrowButton(BasicArrowButton.WEST);
		delete = new BasicArrowButton(BasicArrowButton.EAST);

		boxButtonRight = new Box(BoxLayout.Y_AXIS);
		boxButtonRight.add(Box.createHorizontalStrut(100));
		boxButtonRight.add(Box.createVerticalStrut(140));
		boxButtonRight.add(add);
		boxButtonRight.add(Box.createVerticalStrut(30));
		boxButtonRight.add(delete);
		boxButtonRight.add(Box.createVerticalStrut(140));

		model_2 = new DefaultTableModel(rowdata, new String[] {"Haltestelle"});
		busStopAvailable = new JTable(model_2);
		busStopCurrent.setSize(new Dimension(300, 300));
		model_2.addRow(new String[]{"bla"});
		//TODO Table

		boxMain = new Box(BoxLayout.X_AXIS);
		boxMain.add(boxButtonLeft);
		boxMain.add(Box.createHorizontalStrut(60));
		boxMain.add(new JScrollPane(busStopCurrent));
		boxMain.add(boxButtonRight);
		boxMain.add(Box.createHorizontalStrut(60));
		boxMain.add(new JScrollPane(busStopAvailable));


		add(boxMain, BorderLayout.CENTER);




	}
}
