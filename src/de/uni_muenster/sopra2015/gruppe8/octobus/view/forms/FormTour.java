package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormRoute;

import javax.smartcardio.Card;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jonas on 03.03.2015.
 */
public class FormTour extends FormGeneral
{
	private JPanel cardPanel, buttonPanel;
	private CardLayout cl;
	private Box buttonBox;
	private JButton backButton, nextButton, cancelButton;
	private Container c;
	private int panelCounter, panelMax;
	private FormTourStep1 step1;
	private FormTourStep2 step2;

	private ControllerFormRoute controllerFormRoute;

	public FormTour(Frame parent, int objectId)
	{
		super(parent, "");

		if(objectId != -1)
			setTitle("Neue Linie anlegen");
		else
			setTitle("Linie bearbeiten");

		controllerFormRoute = new ControllerFormRoute();

		c = getContentPane();
		c.setLayout(new BorderLayout());

		initComponents();
		nextButton.requestFocus();
	}

	public void initComponents()
	{
		cardPanel = new JPanel();
		cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		cardPanel.setLayout(new CardLayout());
		step1 = new FormTourStep1();
		cardPanel.add(step1);
		step2 = new FormTourStep2();
		cardPanel.add(step2);
		panelMax = 1;
		panelCounter = 0;
		cl = (CardLayout) cardPanel.getLayout();

		backButton = new JButton("Zurück");
		backButton.setEnabled(false);
		nextButton = new JButton("Weiter");
		cancelButton = new JButton("Abbrechen");

		backButton.addActionListener(e -> {
			backButton_Pressed();
		});
		nextButton.addActionListener(e -> {

			int zahl = Integer.parseInt(step1.getName());
			String[] test = new String[zahl];
			for (int i = 0; i < zahl; i++)
				test[i] = "Haltestelle" + i;
			step2.fillJpMain(test);

			nextButton_Pressed();
		});
		cancelButton.addActionListener(e -> {
			dispose();
		});

		buttonBox = new Box(BoxLayout.X_AXIS);
		buttonBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));

		buttonBox.add(backButton);
		buttonBox.add(Box.createHorizontalStrut(10));
		buttonBox.add(nextButton);
		buttonBox.add(Box.createHorizontalStrut(30));
		buttonBox.add(cancelButton);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		buttonPanel.add(new JSeparator(), BorderLayout.NORTH);
		buttonPanel.add(buttonBox, BorderLayout.EAST);

		c.add(cardPanel, BorderLayout.CENTER);
		c.add(buttonPanel, BorderLayout.SOUTH);
	}

	public void backButton_Pressed()
	{
		if (panelCounter == 1)
			backButton.setEnabled(false);
		if (panelCounter == panelMax)
			nextButton.setText("Weiter");
		cl.previous(cardPanel);
		panelCounter--;
	}

	public void nextButton_Pressed()
	{
		if (panelCounter == 0)
			backButton.setEnabled(true);
		if (panelCounter == panelMax - 1)
			nextButton.setText("Fertig");
		if (panelCounter == panelMax)
		{
			//TODO lese informationen aus
			dispose();
		}
		cl.next(cardPanel);
		panelCounter++;
	}
}
