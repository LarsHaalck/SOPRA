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
	private Box buttonBox;
	private JButton backButton, nextButton, cancelButton;
	private Container c;
	private int panelCounter;

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

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		initComponents();
		pack();
		nextButton.requestFocus();
	}

	public void initComponents()
	{
		cardPanel = new JPanel();
		cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		cardPanel.setLayout(new CardLayout());
		cardPanel.add(new FormTourStep1());
		cardPanel.add(new FormTourStep2());
		cardPanel.add(new FormTourStep3());

		backButton = new JButton("Zurück");
		backButton.setEnabled(false);
		nextButton = new JButton("Weiter");
		cancelButton = new JButton("Abbrechen");

		CardLayout cl = (CardLayout) cardPanel.getLayout();
		panelCounter = 0;
		backButton.addActionListener(e -> {
			if (panelCounter == 2)
				nextButton.setText("Weiter");
			if (panelCounter == 1)
				setBackButtonEnabled(false);
			cl.previous(cardPanel);
			panelCounter--;
		});
		nextButton.addActionListener(e -> {
			if (panelCounter == 1)
			{
				Dimension button = nextButton.getSize();
				nextButton.setText("Fertig");
			}
			if (panelCounter == 2)
			{
				//TODO lese informationen aus
				dispose();
			}
			setBackButtonEnabled(true);
			cl.next(cardPanel);
			panelCounter++;
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

	public void setBackButtonEnabled(boolean b)
	{
		backButton.setEnabled(b);
	}

	public void setNextButtonEnabled(boolean b)
	{
		nextButton.setEnabled(b);
	}
}
