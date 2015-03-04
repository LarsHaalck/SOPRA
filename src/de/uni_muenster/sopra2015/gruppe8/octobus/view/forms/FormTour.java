package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 03.03.2015.
 */
public class FormTour extends FormGeneral
{
	public FormTour(Frame parent)
	{
		super(parent, "Linie erstellen");

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


		pack();
	}

	public static void main(String[] args)
	{
		FormTour fenster = new FormTour(null);
		fenster.setVisible(true);
	}
}
