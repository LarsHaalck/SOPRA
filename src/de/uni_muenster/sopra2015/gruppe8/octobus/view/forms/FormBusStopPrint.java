package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.form.ControllerFormBusStopPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Patricia on 10.03.2015.
 */
public class FormBusStopPrint extends FormGeneral
{
	private ArrayList<Tuple<JCheckBox, Integer>> stops = new ArrayList<>();
	private JButton btPrint;
	private JButton btCancel;
	private ControllerFormBusStopPrint controllerFormBusStopPrint;

	//gets the selected BusStop
	public FormBusStopPrint(Frame parent, int objectId)
	{
		super(parent);

		controllerFormBusStopPrint = new ControllerFormBusStopPrint(this, objectId);

		ArrayList<StoppingPoint> stoppingPoints = controllerFormBusStopPrint.getStoppingPoints();

		setLayout(new GridBagLayout());
		GridBagConstraints cst = new GridBagConstraints();

		cst.gridx = 0;
		cst.gridy = 0;
		cst.gridwidth = 2;
		JLabel lbDescription = new JLabel("Bitte wählen Sie die Haltepunkte zu denen Sie die Abfahrtszeiten ausdrucken wollen.");
		add(lbDescription, cst);

		cst.gridy = 1;
		cst.anchor = GridBagConstraints.LINE_START;
		for (StoppingPoint stop : stoppingPoints)
		{
			JCheckBox cbStop = new JCheckBox(stop.getName());
			Tuple<JCheckBox, Integer> myStop = new Tuple<JCheckBox, Integer>(cbStop, stop.getId());
			stops.add(myStop);
			add(cbStop, cst);
			cst.gridy++;
		}

		cst.gridwidth = 1;
		btPrint = new JButton("Drucken");
		btPrint.addActionListener(e->
		{
			controllerFormBusStopPrint.buttonPressed(EmitterButton.FORM_BUS_STOP_PRINT_PRINT);
		});
		add(btPrint, cst);

		cst.gridx = 1;
		cst.anchor = GridBagConstraints.LINE_END;
		btCancel = new JButton("Abbrechen");
		btCancel.addActionListener(e->
		{
			controllerFormBusStopPrint.buttonPressed(EmitterButton.FORM_BUS_STOP_PRINT_CANCEL);
		});
		add(btCancel, cst);

		setTitle("Abfahrtspläne ausdrucken");
		pack();
		setLocationRelativeTo(null);
	}
	public ArrayList<Tuple<JCheckBox, Integer>> getStops()
	{
		return stops;
	}
}
