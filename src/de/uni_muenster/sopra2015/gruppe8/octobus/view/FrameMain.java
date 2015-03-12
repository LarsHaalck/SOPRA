package de.uni_muenster.sopra2015.gruppe8.octobus.view;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerFrameMain;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;

/**
 * Handles the overall application and takes care of changing the main
 * content panel depending on whether a user is logged in or not, as
 * well as handling the options available to a passenger.
 */
public class FrameMain extends JFrame
{
	private ControllerFrameMain controllerFrameMain;

	public FrameMain()
	{
		super("OctoBUS");

		controllerFrameMain = new ControllerFrameMain(this);

		////////////////////////////////////////////////
		// Anything below this line is just GUI stuff //
		////////////////////////////////////////////////

		// Set cross-platform Java Look and Feel (also called "Metal")
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}

		PanelPassenger panelPassenger = new PanelPassenger();

		controllerFrameMain.displayContent(panelPassenger);

		setResizable(false);
		// Make sure we don't lose any space to window decorations
		getContentPane().setPreferredSize(new Dimension(1024, 640));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);


		ImageIcon icon16 = new ImageIcon(this.getClass().getResource("/images/icon_16.png"));
		ImageIcon icon32 = new ImageIcon(this.getClass().getResource("/images/icon_32.png"));
		ImageIcon icon64 = new ImageIcon(this.getClass().getResource("/images/icon_64.png"));
		ImageIcon icon128 = new ImageIcon(this.getClass().getResource("/images/icon_128.png"));



		ArrayList<Image> icons = new ArrayList<>();
		icons.add(icon16.getImage());
		icons.add(icon32.getImage());
		icons.add(icon64.getImage());
		icons.add(icon128.getImage());
		setIconImages(icons);

		pack();
		// Center frame on screen (as per http://stackoverflow.com/a/2442614/2010258)
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/*
	TODO: delete before deploy, only for DEBUGGING purposes
	 */
	public ControllerFrameMain getControllerFrameMain()
	{
		return this.controllerFrameMain;
	}

}
