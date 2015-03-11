package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 11.03.2015.
 */
public class FormTourSanity extends FormGeneral
{
	private JPanel jpButton;
	private JButton jbBack;

	public FormTourSanity(Frame parent)
	{
		super(parent, "Touren in den nächsten zwei Wochen");
		setLayout(new BorderLayout());

		jpButton = new JPanel();
		jpButton.setLayout(new BorderLayout());
		jbBack = new JButton("Zurück");
		jbBack.addActionListener(e -> {

		});
		jpButton.add(new JSeparator(), BorderLayout.NORTH);
		jpButton.add(jbBack, BorderLayout.CENTER);
		add(jpButton, BorderLayout.CENTER);

	}
}
