package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTourSanity;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jonas on 11.03.2015.
 */
public class FormTourSanity extends FormGeneral
{
	private JPanel jpButton, jpButtonMain, jpMain;
	private JButton jbBack;
	private JScrollPane jspMain;
	private ControllerFormTourSanity controllerFormTourSanity;

	public FormTourSanity(Frame parent)
	{
		super(parent, "Touren in den nächsten zwei Wochen");

		controllerFormTourSanity = new ControllerFormTourSanity(this);

		setLayout(new BorderLayout());

		jspMain = new JScrollPane();
		jspMain.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(1, 1));
		jspMain.add(jpMain);

		add(jspMain, BorderLayout.CENTER);

		jpButtonMain = new JPanel();
		jpButtonMain.setLayout(new BorderLayout());

		jpButton = new JPanel();
		jpButton.setLayout(new FlowLayout());
		jbBack = new JButton("Zurück");
		jbBack.addActionListener(e -> {
			controllerFormTourSanity.buttonPressed(EmitterButton.FORM_TOUR_SANITY_BACK);
		});
		jpButton.add(jbBack);

		jpButtonMain.add(new JSeparator(), BorderLayout.NORTH);
		jpButtonMain.add(jpButton, BorderLayout.CENTER);
		add(jpButtonMain, BorderLayout.SOUTH);

		controllerFormTourSanity.fillForm();

		pack();

	}

	private class FormattedLabel extends JPanel
	{
		private JLabel jlDate, jlStatus;

		public FormattedLabel(String date, Integer sanity)
		{
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			setBorder(new EmptyBorder(new Insets(5, 0, 5, 0)));
			jlDate = new JLabel(date);
			jlStatus = new JLabel(new ImageIcon("res/images/green.png") + " fertig");
			if (sanity == -1)
				jlStatus.setText(new ImageIcon("res/images/red.png") + " ungeplant");
			if (sanity > 0)
				jlStatus.setText(new ImageIcon("res/images/red.png") + " " + sanity + " Fahrten ungeplant");

			add(jlDate);
			add(Box.createHorizontalStrut(10));
			add(jlStatus);
		}
	}

	public void setSanityInfo(ArrayList<Tuple<String, Integer>> sanityInfo)
	{
		jpMain.setLayout(new GridLayout(sanityInfo.size(), 1));
		for (Tuple<String, Integer> stringIntegerTuple : sanityInfo)
		{
			jpMain.add(new FormattedLabel(stringIntegerTuple.getFirst(), stringIntegerTuple.getSecond()));
		}
		revalidate();
		repaint();
	}
}
