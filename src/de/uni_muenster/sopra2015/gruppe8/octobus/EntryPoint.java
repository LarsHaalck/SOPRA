package de.uni_muenster.sopra2015.gruppe8.octobus;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;

/**
 * Created by Lars on 03-Mar-15.
 */
public class EntryPoint
{
	public EntryPoint(String ep)
	{
		FrameMain frameMain = new FrameMain();
		PanelEmployee panelEmployee = new PanelEmployee();

		switch (ep)
		{
			case "FrameMain":
				break;
			case "PanelEmployee":
				frameMain.getControllerFrameMain().displayContent(new PanelEmployee());
				break;
			case "TabExample":
				panelEmployee.getPaneTabs().setSelectedIndex(0);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabNetwork":
				panelEmployee.getPaneTabs().setSelectedIndex(1);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabBuses":
				panelEmployee.getPaneTabs().setSelectedIndex(2);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabEmployee":
				panelEmployee.getPaneTabs().setSelectedIndex(3);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabBusStop":
				panelEmployee.getPaneTabs().setSelectedIndex(4);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabLine":
				panelEmployee.getPaneTabs().setSelectedIndex(5);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabTicket":
				panelEmployee.getPaneTabs().setSelectedIndex(6);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabWorkPlan":
				panelEmployee.getPaneTabs().setSelectedIndex(7);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
			case "TabSchedule":
				panelEmployee.getPaneTabs().setSelectedIndex(8);
				frameMain.getControllerFrameMain().displayContent(panelEmployee);
				break;
		}
	}
}
