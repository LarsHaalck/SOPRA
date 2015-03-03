package de.uni_muenster.sopra2015.gruppe8.octobus;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.FrameMain;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.PanelEmployee;

import java.awt.*;

/**
 * Created by Lars on 03-Mar-15.
 */
public class EntryPoint
{
	EntryPoint(String ep)
	{
		FrameMain main = new FrameMain();
		PanelEmployee employ = new PanelEmployee();
		switch (ep)
		{
			case "FrameMain":
				break;
			case "PanelEmployee":
				main.getMainFrame().displayContent(new PanelEmployee());
				break;
			case "TabExample":
				employ.getPaneTabs().setSelectedIndex(0);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabNetwork":
				employ.getPaneTabs().setSelectedIndex(1);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabBuses":
				employ.getPaneTabs().setSelectedIndex(2);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabEmployee":
				employ.getPaneTabs().setSelectedIndex(3);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabBusStop":
				employ.getPaneTabs().setSelectedIndex(4);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabLine":
				employ.getPaneTabs().setSelectedIndex(5);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabTicket":
				employ.getPaneTabs().setSelectedIndex(6);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabWorkPlan":
				employ.getPaneTabs().setSelectedIndex(7);
				main.getMainFrame().displayContent(employ);
				break;
			case "TabSchedule":
				employ.getPaneTabs().setSelectedIndex(8);
				main.getMainFrame().displayContent(employ);
				break;
		}
	}
}
