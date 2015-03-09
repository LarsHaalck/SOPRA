package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;

/**
 * Controller for the PanelEmployee class.
 */
public class ControllerPanelEmployee extends Controller implements ListenerButton, ListenerUserState
{
	private PanelEmployee panel;
	private Employee employee;
	private ControllerDatabase controllerDatabase;

	public ControllerPanelEmployee(PanelEmployee panel)
	{
		super();
		this.controllerDatabase = ControllerDatabase.getInstance();
		this.panel = panel;
	}

	@Override
	public void buttonPressed(EmitterButton emitter)
	{
		switch (emitter)
		{
			case PANEL_EMPLOYEE_CHANGE_PASSWORD:
				ControllerManager.informWindowOpen(EmitterWindow.FORM_CHANGE_PASSWORD, employee.getId());
				break;
			case PANEL_EMPLOYEE_LOGOUT:
				ControllerManager.informUserStateChanged(EmitterUserState.LOGGED_OUT);
				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton) this);
		ControllerManager.addListener((ListenerUserState) this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton) this);
		ControllerManager.removeListener((ListenerUserState) this);
	}

	@Override
	public void userStateChanged(EmitterUserState emitter)
	{

	}

	@Override
	public void userStateChanged(EmitterUserState emitter, int userId)
	{
		switch(emitter)
		{
			case RIGHTS_CHANGED:
				//Load employee if there isn't one saved, reload employee if userids are equal
				if(employee == null || employee.getId() == userId)
				{
					employee = controllerDatabase.getEmployeeById(userId);
				}
				//Only reset tabs if logged-in-users rights changed
				if(employee.getId() == userId)
				{
					panel.setUsername(employee.getFirstName());
					panel.setTabs(employee.isRole(Role.TICKET_PLANNER), employee.isRole(Role.SCHEDULE_MANAGER),
							employee.isRole(Role.HR_MANAGER), employee.isRole(Role.NETWORK_PLANNER),
							employee.isRole(Role.BUSDRIVER), employee.getId());
				}
				break;
		}
	}
}
