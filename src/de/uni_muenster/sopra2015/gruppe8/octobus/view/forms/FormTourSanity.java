package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormTourSanity;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * creates the Dialog who shows the occupation of the next two weeks
 */
public class FormTourSanity extends FormGeneral
{
	private JPanel jpButton, jpButtonMain, jpMain, jpTop;
	private JButton jbBack;
	private JScrollPane jspMain;
	private ControllerFormTourSanity controllerFormTourSanity;

	public FormTourSanity(Frame parent)
	{
		super(parent, "Touren (2 Wochen)");

		controllerFormTourSanity = new ControllerFormTourSanity(this);

		setLayout(new BorderLayout());

		jpMain = new JPanel();
		jpMain.setLayout(new GridLayout(1,1));

		jspMain = new JScrollPane(jpMain);
		jspMain.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

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

		jpTop = new JPanel();
		jpTop.setLayout(new BorderLayout());
		JLabel edit = new JLabel("Datum kann mit Doppelklick bearbeitet werden!");
		edit.setBorder(new EmptyBorder(new Insets(5,0,0,0)));
		edit.setHorizontalAlignment(JLabel.CENTER);
		jpTop.add(edit, BorderLayout.NORTH);
		jpTop.add(jspMain, BorderLayout.CENTER);

		add(jpTop, BorderLayout.CENTER);
		add(jpButtonMain, BorderLayout.SOUTH);

		controllerFormTourSanity.fillForm();
		setPreferredSize(new Dimension(310, 300));
		pack();

		setLocationRelativeTo(null);
	}

	/**
	 * creates a panel which contains a Date and the current status of schedule
	 */
	private class FormattedLabel extends JPanel
	{
		private JLabel jlDate, jlStatus;
		private JPanel panel;

		public FormattedLabel(String date, Integer sanity, int day)
		{
			setLayout(new BorderLayout());
			jlDate = new JLabel(date);
			jlDate.addMouseListener(new MouseAdapter()
			{
				private Font orginal;

				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount() == 2)
					{
						controllerFormTourSanity.setDate(day);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					orginal = jlDate.getFont();
					jlDate.setFont(jlDate.getFont().deriveFont(Font.BOLD));
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					jlDate.setFont(orginal);
				}

			});
			jlStatus = new JLabel(new ImageIcon("res/images/green.png"));
			if (sanity == -1 || sanity > 0)
				jlStatus.setIcon(new ImageIcon("res/images/red.png"));
			add(jlDate, BorderLayout.CENTER);

			panel = new JPanel();
			panel.setLayout(new BorderLayout());
			panel.setPreferredSize(new Dimension(150, 30));
			panel.add(jlStatus, BorderLayout.WEST);
			switch (sanity)
			{
				case 0:
					JLabel label1 = new JLabel("   fertig");
					label1.setHorizontalAlignment(JLabel.LEFT);
					panel.add(label1, BorderLayout.CENTER);
					break;
				case -1:
					JLabel label2 = new JLabel("   komplett ungeplant");
					label2.setHorizontalAlignment(JLabel.LEFT);
					panel.add(label2, BorderLayout.CENTER);
					break;
				default:
					JLabel label3 = new JLabel( "  " + sanity + " Fahrten ungeplant");
					label3.setHorizontalAlignment(JLabel.LEFT);
					panel.add(label3,BorderLayout.CENTER);
					break;
			}
			add(panel, BorderLayout.EAST);
		}
	}

	/**
	 * fills the JScrollPane with FormattedLabels
 	 * @param sanityInfo is a Tuple out of the Date (String) and a Integer (0 = finished / -1 and x = not finished)
	 */
	public void setSanityInfo(ArrayList<Tuple<String, Integer>> sanityInfo)
	{
		jpMain.setLayout(new GridLayout(sanityInfo.size(), 1));
		int i = 0;
		for (Tuple<String, Integer> stringIntegerTuple : sanityInfo)
		{
			jpMain.add(new FormattedLabel(stringIntegerTuple.getFirst(), stringIntegerTuple.getSecond(), i++));
		}
		revalidate();
		repaint();
	}
}
