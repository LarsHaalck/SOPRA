package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.BusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Patricia on 10.03.2015.
 */
public class FormPrintStoppingPoints extends FormGeneral
{
	private BusStop busStop;
	private ArrayList<JCheckBox> stops;
	private JButton btPrint;
	private JButton btCancel;

	public FormPrintStoppingPoints(Frame parent, BusStop busStop)
	{
		super(parent);

		this.busStop = busStop;
		HashSet<StoppingPoint> stoppingPoints = this.busStop.getStoppingPoints();

		setLayout(new GridBagLayout());
		GridBagConstraints cst = new GridBagConstraints();

		cst.gridx = 0;
		cst.gridy = 0;
		cst.gridwidth = 2;
		JLabel lbDescription = new JLabel("Bitte wählen Sie die Haltepunkte zu denen Sie die Abfahrtszeiten ausdrucken wollen");
		add(lbDescription, cst);

		cst.gridy = 1;

		for(StoppingPoint stop: stoppingPoints)
		{
			JCheckBox cbStop = new JCheckBox(stop.getName());
			stops.add(cbStop);
			cst.gridy++;
		}

		cst.gridwidth = 1;
		btPrint = new JButton("Drucken");
		add(btPrint, cst);

		cst.gridx = 1;
		btCancel = new JButton("Abbrechen");
		add(btCancel, cst);

		setTitle("Abfahrtspläne ausdrucken");
	}
}
