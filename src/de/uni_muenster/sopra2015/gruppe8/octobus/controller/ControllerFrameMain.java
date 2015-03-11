package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelPassenger;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayNetworkEmployee;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplaySearchConnection;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.displays.DisplayTicket;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;

import javax.swing.*;
import java.awt.*;

/**
 * Controller for the FrameMain class.
 */
public class ControllerFrameMain extends Controller implements ListenerButton, ListenerUserState, ListenerWindow
{
	private FrameMain frame;
	private ControllerPrint controllerPrint;
	private ControllerGraph controllerGraph;

	public ControllerFrameMain(FrameMain frame)
	{
		super();
		this.frame = frame;
		this.controllerPrint = new ControllerPrint();
		this.controllerGraph = new ControllerGraph();
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case DISPLAY_NETWORK_BACK:
				displayContent(new PanelPassenger());
				break;
		}
	}

	@Override
	public void userStateChanged(EmitterUserState emitter)
	{
		switch (emitter)
		{
			case LOGGED_IN:
				ControllerManager.clearListeners();
				addListeners();
				PanelEmployee newPanelEmployee = new PanelEmployee();
				displayContent(newPanelEmployee);
				break;
			case LOGGED_OUT:
				ControllerManager.clearListeners();
				addListeners();
				PanelPassenger newPanelPassenger = new PanelPassenger();
				displayContent(newPanelPassenger);
				break;
		}
	}

	@Override
	public void userStateChanged(EmitterUserState emitter, int userId)
	{
	}

	@Override
	public void windowOpen(EmitterWindow emitter)
	{
		JDialog f;
		switch (emitter)
		{
			case FORM_LOGIN:
				f = new FormLogin(frame);
				f.setVisible(true);
				break;

			case FORM_BUS_NEW:
				f = new FormBus(frame, -1);
				f.setVisible(true);
				break;

			case FORM_TICKET_NEW:
				f = new FormTicket(frame, -1);
				f.setVisible(true);
				break;

			case FORM_EMPLOYEE_NEW:
				f = new FormEmployee(frame, -1);
				f.setVisible(true);
				break;

			case FORM_BUS_STOP_NEW:
				f = new FormBusStop(frame, -1);
				f.setVisible(true);
				break;

			case FORM_ROUTE_NEW:
				f = new FormRoute(frame, -1);
				f.setVisible(true);
				break;

			case FORM_TOUR_RESET_EMPLOYEE:
				f = new FormTourResetEmployee(frame, -1);
				f.setVisible(true);
				break;
			case FORM_TOUR_RESET_BUS:
				f = new FormTourResetBus(frame, -1);
				f.setVisible(true);
				break;
			case DIALOG_NO_DB:
				JOptionPane.showMessageDialog(frame, "Es konnte keine Datenbank gefunden werden. Bitte schauen Sie für Details in das Benutzerhandbuch.", "Fehler", JOptionPane.ERROR_MESSAGE);
				break;
			case FORM_TOUR_SANITY:
				f = new FormTourSanity(frame);
				f.setVisible(true);
				break;
		}
	}

	@Override
	public void windowOpen(EmitterWindow wd, int objectID)
	{
		JDialog f;

		switch (wd)
		{
			case FORM_BUS_EDIT:
				f = new FormBus(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_EMPLOYEE_EDIT:
				f = new FormEmployee(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_TICKET_EDIT:
				f = new FormTicket(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_BUS_STOP_EDIT:
				f = new FormBusStop(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_BUS_STOP_PRINT:
				f = new FormBusStopPrint(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_ROUTE_EDIT:
				f = new FormRoute(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_CHANGE_PASSWORD:
				f = new FormChangePassword(frame, objectID);
				f.setVisible(true);
				break;
			case FORM_TOUR_EDIT:
				f = new FormTourEdit(frame, objectID);
				f.setVisible(true);
				break;
		}
	}

	@Override
	public void windowClose(EmitterWindow emitter)
	{

	}

	@Override
	public void displaySwitch(EmitterDisplay emitter)
	{
		switch(emitter)
		{
			case DISPLAY_TICKET:
				displayContent(new DisplayTicket());
				break;

			case DISPLAY_NETWORK:
				displayContent(new DisplayNetworkEmployee());
				break;

			case DISPLAY_CONNECTION:
				displayContent(new DisplaySearchConnection());
				break;

			case DISPLAY_MAIN:
				displayContent(new PanelPassenger());
				break;
		}
	}

	/**
	 * Displays a container in the FrameMain.
	 * @param container Container to be displayed.
	 */
	public void displayContent(Container container)
	{
		Container cp = frame.getContentPane();
		cp.removeAll();
		frame.setContentPane(container);
		frame.setVisible(true);
	}


	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
		ControllerManager.addListener((ListenerUserState) this);
		ControllerManager.addListener((ListenerWindow) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerUserState) this);
		ControllerManager.removeListener((ListenerWindow) this);
	}
}
