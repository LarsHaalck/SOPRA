package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * @author Patricia Schinke
 * lenght of textfield
 */
public class FormBusStop extends JDialog
{
	private JPanel mid = new JPanel();
	private JPanel bottom = new JPanel();

	private String explanationText = "Bitte geben Sie die Daten der Bushaltestelle ein.";
	private JLabel explanation = new JLabel(explanationText);
	private JPanel explanationPanel = new JPanel();

	private JLabel name = new JLabel("Name");
	private JLabel location = new JLabel("Ort");
	private JLabel stoppingPoints = new JLabel("?"); //hm how do I give them to the form?
	private JLabel barrierFree = new JLabel("Barrierefrei");

	private JTextField nameText = new JTextField();
	private JTextField locationText = new JTextField();
	private JTextField stoppingPointsText = new JTextField(); //hm how do I give them to the form?
	private JToggleButton barrierFreeText = new JToggleButton();

	private JButton save = new JButton("Speichern");
	private JButton cancel = new JButton("Abbrechen");

	public FormBusStop()
	{
		setResizable(false);

		setLayout(new BorderLayout());
		add(explanationPanel, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

		explanationPanel.add(explanation);

		mid.setLayout(new GridLayout(4,2));
		mid.add(name);
		mid.add(nameText);
		mid.add(location);
		mid.add(locationText);
		mid.add(stoppingPoints);
		mid.add(stoppingPointsText);
		mid.add(barrierFree);
		mid.add(barrierFreeText);

		bottom.setLayout(new FlowLayout());
		bottom.add(save);
		bottom.add(cancel);
	}
}
