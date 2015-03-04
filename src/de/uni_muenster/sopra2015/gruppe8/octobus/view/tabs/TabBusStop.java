package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab.ControllerTabBusStop;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableModelBusStop;

import javax.swing.*;
import java.awt.*;

/**f
 * @author Patricia Schinke
 */
public class TabBusStop extends TabTable<TableModelBusStop>
{
	private ControllerTabBusStop controllerTabBusStop;
	private JButton btnDelete;
	private JButton btnEdit;
	private JButton btnNew;


	public TabBusStop()
	{
		super(TableModelBusStop.class, true, false);

		controllerTabBusStop = new ControllerTabBusStop(this);

		setLayout(new BorderLayout(5,5));

		btnDelete = new JButton("Löschen");
		btnDelete.addActionListener(e -> {
			if(getSelectedID()!=-1 && JOptionPane.showConfirmDialog(this, "Wirklich löschen?", "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
					== JOptionPane.YES_OPTION)
			{
				System.out.println("Delete pressed");
				controllerTabBusStop.buttonPressed(EmitterButton.TAB_BUS_STOP_DELETE);
			}
		});

		btnEdit = new JButton("Bearbeiten");
		btnEdit.addActionListener(e-> {
			if(getSelectedID() == -1)
				JOptionPane.showMessageDialog(this, "Um eine Haltestelle zu bearbeiten, wählen Sie bitte einen Eintrag aus der Tabelle.", "Hinweis", JOptionPane.INFORMATION_MESSAGE);
			else
				controllerTabBusStop.buttonPressed(EmitterButton.TAB_BUS_STOP_EDIT);
		});

		btnNew = new JButton("Neu");
		btnNew.addActionListener(e-> {
			controllerTabBusStop.buttonPressed(EmitterButton.TAB_BUS_STOP_NEW);
		});

		if(super.isRefineable())
		{
			JPanel plFilter = new JPanel();
			plFilter.add(lbFilter);
			plFilter.add(tfFilter);
			plFilter.add(cbFilter);
			add(plFilter, BorderLayout.PAGE_START);
		}

		JPanel plButtons = new JPanel();
		plButtons.add(btnNew);
		plButtons.add(btnEdit);
		plButtons.add(btnDelete);

		add(new JScrollPane(table), BorderLayout.CENTER);
		add(plButtons, BorderLayout.PAGE_END);

		setVisible(true);
	}
}
