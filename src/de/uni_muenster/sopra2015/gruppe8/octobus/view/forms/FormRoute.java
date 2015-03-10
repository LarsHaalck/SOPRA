package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormRoute;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Created by Jonas on 03.03.2015.
 */
public class FormRoute extends FormGeneral
{
	private JPanel cardPanel, buttonPanel;
	private CardLayout cl;
	private Box buttonBox;
	private JButton backButton, nextButton, cancelButton;
	private Container c;
	private int panelCounter, panelMax;
	private FormRouteStep1 step1;
	private FormRouteStep2 step2;

	private ControllerFormRoute controllerFormRoute;

	public FormRoute(Frame parent, int objectId)
	{
		super(parent, "");

		if(objectId == -1)
			setTitle("Neue Linie anlegen");
		else
			setTitle("Linie bearbeiten");

		controllerFormRoute = new ControllerFormRoute(this, objectId);

		c = getContentPane();
		c.setLayout(new BorderLayout());

		initComponents();
		nextButton.requestFocus();
		controllerFormRoute.insertValuesIntoForm();
	}

	public void initComponents()
	{
		cardPanel = new JPanel();
		cardPanel.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		cardPanel.setLayout(new CardLayout());
		step1 = new FormRouteStep1(controllerFormRoute);
		cardPanel.add(step1);
		step2 = new FormRouteStep2(controllerFormRoute);
		cardPanel.add(step2);
		panelMax = 1;
		panelCounter = 0;
		cl = (CardLayout) cardPanel.getLayout();

		backButton = new JButton("Zurück");
		backButton.setEnabled(false);
		nextButton = new JButton("Weiter");
		cancelButton = new JButton("Abbrechen");

		backButton.addActionListener(e -> {
			//backButton_Pressed();
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_BACK);
		});
		nextButton.addActionListener(e -> {
			//nextButton_Pressed();
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_NEXT);
		});
		cancelButton.addActionListener(e -> {
			//dispose();
			controllerFormRoute.buttonPressed(EmitterButton.FORM_ROUTE_CANCEL);
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
		controllerFormRoute.initTableAvailable();
	}

	public FormRouteStep1 getStep1()
	{
		return step1;
	}

	public FormRouteStep2 getStep2()
	{
		return step2;
	}

	public int getPanelMax()
	{
		return panelMax;
	}

	public void setPanelCounter(int panelCounter)
	{
		this.panelCounter = panelCounter;
	}

	public CardLayout getCl()
	{
		return cl;
	}

	public JButton getBackButton()
	{
		return backButton;
	}

	public JButton getNextButton()
	{
		return nextButton;
	}

	public JPanel getCardPanel()
	{
		return cardPanel;
	}

	public int getPanelCounter()
	{
		return panelCounter;
	}
}
