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
	//Static buffered images, so we only have to draw it once
	private static BufferedImage imageNetworkDay;
	private static BufferedImage imageNetworkNight;
	private static double ZOOM = 0.9;
	//Components
	private JRadioButton rdBtnDay;
	private JRadioButton rdBtnNight;
	private JScrollPane scrollPane;
	private JLabel lbDay;
	private JLabel lbNight;
	private ControllerDisplayNetwork controllerDisplayNetwork;

    public DisplayNetwork()
	{
		controllerDisplayNetwork = new ControllerDisplayNetwork(this);

		Tuple<Integer, Integer> size = controllerDisplayNetwork.getMaxSize();

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

		if(getImageNight() == null)
			drawToImage(size, true);

		if(getImageDay() == null)
			drawToImage(size, false);

		lbDay = new JLabel(getImageDay());
		lbNight = new JLabel(getImageNight());

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(16); //increase scroll speed

		panel.add(scrollPane, BorderLayout.CENTER);

	}

	public static ImageIcon getImageDay()
	{
		if(imageNetworkDay == null)
			return null;
		return new ImageIcon(imageNetworkDay);
	}

	public static ImageIcon getImageNight()
	{
		if(imageNetworkNight == null)
			return null;
		return new ImageIcon(imageNetworkNight);
	}

	public void init(int w, int h)
	{

		scrollPane.setPreferredSize(new Dimension(h, w));
		setNetwork(0);
	}

	public void setNetwork(int network)
	{
		if(network == 1)
			scrollPane.setViewportView(lbNight);
		else
			scrollPane.setViewportView(lbDay);

		scrollPane.invalidate();
		scrollPane.revalidate();
		scrollPane.repaint();
	}

	/**
	 * Draw network to buffered image
	 * @param nightline false means day, true night
	 */
	private void drawToImage(Tuple<Integer, Integer> size, boolean nightline)
	{
		if(imageNetworkDay == null && !nightline)
			imageNetworkDay = new BufferedImage((int)(ZOOM*(size.getFirst()+100)),(int)(ZOOM*(size.getSecond()+100)),BufferedImage.TYPE_INT_RGB);

		if(imageNetworkNight == null && nightline)
			imageNetworkNight = new BufferedImage((int)(ZOOM*(size.getFirst()+100)),(int)(ZOOM*(size.getSecond()+100)),BufferedImage.TYPE_INT_RGB);


		//Get Graphics from panel that will contain network-graphics
		Graphics2D g2;
		if(!nightline)
			g2 = imageNetworkDay.createGraphics();
		else
			g2 = imageNetworkNight.createGraphics();

		g2.setColor(Color.WHITE);
		Tuple<Integer, Integer> maxSize = controllerDisplayNetwork.getMaxSize();
		g2.fillRect(0, 0, (int)(ZOOM*(maxSize.getFirst()+100)), (int)(ZOOM*(maxSize.getSecond()+100)));

		g2.setColor(Color.black);

		ControllerDisplayNetwork.DataBusStop[] dataBusStops = controllerDisplayNetwork.getBusStops();
		ControllerDisplayNetwork.DataRoute[] dataRoutes = controllerDisplayNetwork.getRoutes();

		//Draw routes
		for (ControllerDisplayNetwork.DataRoute dataRoute : dataRoutes)
		{
			if (dataRoute.isNightline() != nightline)
				continue;
			for (Quadruple<Integer,Integer,Integer,Integer> step : dataRoute.getSteps())
			{
				g2.drawLine((int)(ZOOM*step.getFirst()), (int)(ZOOM*step.getSecond()), (int)(ZOOM*step.getThird()), (int)(ZOOM*step.getFourth()));
			}
		}

		//Draw bus-stops
		for (ControllerDisplayNetwork.DataBusStop dataBusStop : dataBusStops)
		{
			g2.fillOval((int)(ZOOM*(dataBusStop.getX()-10)), (int)(ZOOM*dataBusStop.getY()-10), (int)(ZOOM*20), (int)(ZOOM*20));
			int textWidth = g2.getFontMetrics().stringWidth(dataBusStop.getName());
			g2.drawString(dataBusStop.getName(), (int)(ZOOM*(dataBusStop.getX() - (textWidth/2))), (int)(ZOOM*dataBusStop.getY()+25));
		}
	}
}
