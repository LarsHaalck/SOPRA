package de.uni_muenster.sopra2015.gruppe8.octobus.view.displays;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.display.ControllerDisplayNetwork;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterButton;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tuple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by Steen Sziegaud on 04.03.2015.
 */
public class DisplayNetwork extends JPanel
{
	//Components
	private JRadioButton rdBtnDay;
	private JRadioButton rdBtnNight;
	private JScrollPane scrollPane;
	private JLabel lbDay;
	private JLabel lbNight;
	private ControllerDisplayNetwork controllerDisplayNetwork;
	private BufferedImage imageNetworkDay;
	private BufferedImage imageNetworkNight;
    public DisplayNetwork()
	{
		controllerDisplayNetwork = new ControllerDisplayNetwork(this);

		//Get max-size
		Tuple<Integer, Integer> size = controllerDisplayNetwork.getMaxSize();

		imageNetworkDay = new BufferedImage(size.getFirst()+100,size.getSecond()+100,BufferedImage.TYPE_INT_RGB);
		imageNetworkNight = new BufferedImage(size.getFirst()+100, size.getSecond()+100, BufferedImage.TYPE_INT_RGB);

		//0 for day
		drawToImage(0);
		//1 for day
		drawToImage(1);

		JRadioButton rdBtnDay = new JRadioButton("Tag");
		rdBtnDay.setMnemonic(KeyEvent.VK_T);
		rdBtnDay.addActionListener(e->
		{
			controllerDisplayNetwork.buttonPressed(EmitterButton.DISPLAY_NETWORK_DAY);
		});
		rdBtnDay.setSelected(true);

		JRadioButton rdBtnNight = new JRadioButton("Nacht");
		rdBtnNight.setMnemonic(KeyEvent.VK_N);
		rdBtnNight.addActionListener(e->
		{
			controllerDisplayNetwork.buttonPressed(EmitterButton.DISPLAY_NETWORK_NIGHT);
		});
		rdBtnNight.setSelected(false);

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdBtnDay);
		btnGroup.add(rdBtnNight);

		add(rdBtnDay, BorderLayout.NORTH);
		add(rdBtnNight, BorderLayout.NORTH);

		lbDay = new JLabel(new ImageIcon(imageNetworkDay));
		lbNight = new JLabel(new ImageIcon(imageNetworkNight));

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(size.getFirst()+100, size.getSecond()+100));
		scrollPane.add(lbDay);
		//add(lbDay, BorderLayout.CENTER);
		add(scrollPane, BorderLayout.CENTER);
    }

    private void initComponents()
    {
    }

	public ImageIcon getNetwork()
	{
		return new ImageIcon(imageNetworkDay);
	}

	/**
	 * Draw network to buffered image
	 * @param type 0 means day, 1 means night
	 */
	private void drawToImage(int type)
	{
		//Get Graphics from panel that will contain network-graphics
		Graphics2D g2 = imageNetworkDay.createGraphics();

		g2.setColor(Color.WHITE);
		Tuple<Integer, Integer> maxSize = controllerDisplayNetwork.getMaxSize();
		g2.fillRect(0, 0, maxSize.getFirst()+100, maxSize.getSecond()+100);

		g2.setColor(Color.black);

		ControllerDisplayNetwork.DataBusStop[] dataBusStops = controllerDisplayNetwork.getBusStops();
		ControllerDisplayNetwork.DataRoute[] dataRoutes = controllerDisplayNetwork.getRoutes();

		//Draw routes
		for (ControllerDisplayNetwork.DataRoute dataRoute : dataRoutes)
		{
			if (dataRoute.isNightline() && type != 0)
				continue;
			for (Quadruple<Integer,Integer,Integer,Integer> step : dataRoute.getSteps())
			{
				g2.drawLine(step.getFirst(), step.getSecond(), step.getThird(), step.getFourth());
			}
		}

		//Draw bus-stops
		for (ControllerDisplayNetwork.DataBusStop dataBusStop : dataBusStops)
		{
			g2.fillOval(dataBusStop.getX()-10, dataBusStop.getY()-10, 20, 20);
			int textWidth = g2.getFontMetrics().stringWidth(dataBusStop.getName());
			g2.drawString(dataBusStop.getName(), dataBusStop.getX() - (textWidth/2), dataBusStop.getY()+25);
		}
	}
}
