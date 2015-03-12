package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTourResetEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.TupleIntString;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Form to remove an employee from every tour during an duration
 */
public class FormTourResetEmployee extends FormGeneral
{

	ControllerFormTourResetEmployee controllerFormTourResetEmployee;
	private JPanel jpMain, jpButtonMain, jpButton, jpStart, jpEnd, jpBusDriver;
	private JLabel jlStart, jlEnd, jlBusDriver;
	private FieldDate tfDateStart;
	private FieldDate tfDateEnd;
	private JComboBox<TupleIntString> cbEmployees;
	private JButton jbSave, jbCancel;

	public FormTourResetEmployee(Frame parent, int objectId)
	{
			super(parent, "Mitarbeiter zurücksetzen");

			setLayout(new BorderLayout());

			jpMain = new JPanel();
			jpMain.setLayout(new BoxLayout(jpMain, BoxLayout.Y_AXIS));

			jpStart = new JPanel();
			jpStart.setLayout(new BoxLayout(jpStart, BoxLayout.X_AXIS));
			jpStart.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
			jlStart = new JLabel("Erster Tag: ");
			tfDateStart = new FieldDate();
			jpStart.add(jlStart);
			jpStart.add(Box.createHorizontalStrut(5));
			jpStart.add(tfDateStart);
			jpMain.add(jpStart);

			jpEnd = new JPanel();
			jpEnd.setLayout(new BoxLayout(jpEnd, BoxLayout.X_AXIS));
			jpEnd.setBorder(new EmptyBorder(new Insets(5, 5, 5, 5)));
			jlEnd = new JLabel("Letzter Tag: ");
			tfDateEnd = new FieldDate();
			jpEnd.add(jlEnd);
			jpEnd.add(tfDateEnd);
			jpMain.add(jpEnd);

			jpBusDriver = new JPanel();
			jpBusDriver.setLayout(new BoxLayout(jpBusDriver, BoxLayout.X_AXIS));
			jpBusDriver.setBorder(new EmptyBorder(new Insets(5, 5, 10, 5)));
			jlBusDriver = new JLabel("Busfahrer: ");
			cbEmployees = new JComboBox<TupleIntString>();
			cbEmployees.setMinimumSize(new Dimension(1,1));
			jpBusDriver.add(jlBusDriver);
			jpBusDriver.add(Box.createHorizontalStrut(8));
			jpBusDriver.add(cbEmployees);
			jpMain.add(jpBusDriver);

			add(jpMain);

			jpButtonMain = new JPanel();
			jpButtonMain.setLayout(new BorderLayout());

			jpButton = new JPanel();
			jpButton.setLayout(new FlowLayout());
			jpButton.setBorder(new EmptyBorder(new Insets(5,0,5,0)));
			jbSave = new JButton("Speichern");
			jbCancel = new JButton("Abbrechen");
			jpButton.add(jbSave);
			jpButton.add(jbCancel);

			jpButtonMain.add(new JSeparator(), BorderLayout.NORTH);
			jpButtonMain.add(jpButton, BorderLayout.CENTER);

			add(jpButtonMain, BorderLayout.SOUTH);
			setPreferredSize(new Dimension(220, 169));
			pack();

			jbSave.addActionListener(e ->
					controllerFormTourResetEmployee.buttonPressed(EmitterButton.FORM_TOUR_RESET_EMPLOYEE_SAVE));
			jbCancel.addActionListener(e ->
					controllerFormTourResetEmployee.buttonPressed(EmitterButton.FORM_TOUR_RESET_EMPLOYEE_CANCEL));

			setLocationRelativeTo(null);

			controllerFormTourResetEmployee = new ControllerFormTourResetEmployee(this);
	}

	/**
	 * Fills the combo box with employees
	 * @param employees employees to be added.
	 */
	public void fillEmployees(ArrayList<TupleIntString> employees)
	{
		for (TupleIntString employee : employees)
		{
			cbEmployees.addItem(employee);
		}
	}

	public Date getDateStart()
	{
		return tfDateStart.getDate();
	}

	public Date getDateEnd()
	{
		return tfDateEnd.getDate();
	}

	public TupleIntString getEmployee()
	{
		return (TupleIntString) cbEmployees.getSelectedItem();
	}
}
