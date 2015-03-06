package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplayNetwork;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.forms.FormGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Steen Sziegaud on 04.03.2015.
 */
public class DisplayNetwork extends JPanel
{
	//Components
	private JButton dayNightSwitch;
	private JPanel networkDisplay;
	private ControllerDisplayNetwork controllerDisplayNetwork;
	private BufferedImage bi;
    public DisplayNetwork()
	{
		controllerDisplayNetwork = new ControllerDisplayNetwork(this);
		setPreferredSize(new Dimension(5000,5000));
		bi = new BufferedImage(5000,5000,BufferedImage.TYPE_INT_RGB);
		draw();
    }

    private void initComponents()
    {
        dayNightSwitch = new JButton();
        networkDisplay = new JPanel();



        networkDisplay.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    }

	public ImageIcon getNetwork()
	{
		return new ImageIcon(bi);
	}

	private void draw()
	{
		//Get Graphics from panel that will contain network-graphics
		Graphics2D g2 = bi.createGraphics();

		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, 5000, 5000);

		g2.setColor(Color.black);

		ControllerDisplayNetwork.DataBusStop[] dataBusStops = controllerDisplayNetwork.getBusStops();
		ControllerDisplayNetwork.DataRoute[] dataRoutes = controllerDisplayNetwork.getRoutes();

		for (ControllerDisplayNetwork.DataRoute dataRoute : dataRoutes)
		{
			for (Quadruple<Integer,Integer,Integer,Integer> step : dataRoute.getSteps())
			{
				if(g2 == null || step == null || step.getFirst() == null || step.getSecond() == null || step.getThird() == null || step.getFourth() == null)
					continue;
				g2.drawLine(step.getFirst(), step.getSecond(), step.getThird(), step.getFourth());
			}
		}

		for (ControllerDisplayNetwork.DataBusStop dataBusStop : dataBusStops)
		{
			if(g2 == null)
				continue;
			g2.drawOval(dataBusStop.getX(), dataBusStop.getY(), 20, 20);
		}
	}
}
