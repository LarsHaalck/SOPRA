package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.TupleIntString;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Form to remove an employee from every tour during an duration
 */
public class FormTourResetEmployee extends FormGeneral
{
	private FieldDate tfDateStart;
	private FieldDate tfDateEnd;
	private JComboBox<TupleIntString> cbEmployees;

	public FormTourResetEmployee(Frame parent, int objectId)
	{
		super(parent, "Mitarbeiter zurücksetzen");

		tfDateEnd = new FieldDate();
		tfDateEnd = new FieldDate();
		cbEmployees = new JComboBox<>();
	}

	public void fillEmployees(ArrayList<TupleIntString> employees)
	{
		for (TupleIntString employee : employees)
		{
			cbEmployees.addItem(employee);
		}
	}
}
