package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public class FormTourStep1 extends JPanel
{
	private JTable busStopCurrent, busStopAvailable;
	private JButton add, delete, up, down;
	private Box boxMain, boxTop;
	private DefaultTableModel model;
	private String[][] rowdata;
	private JLabel name, nightLine;
	private JCheckBox nightLineClick;

	public FormTourStep1()
	{
		setLayout(new BorderLayout());
		boxTop = new Box(BoxLayout.X_AXIS);
		
		boxMain = new Box(BoxLayout.X_AXIS);
		boxMain.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));

	}
}
