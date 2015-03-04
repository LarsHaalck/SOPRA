package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public abstract class FormGeneral extends JDialog
{
	private static final int HEIGHT = 924;
	private static final int WIDHT = 924;
	public FormGeneral(Frame parent, String name)
	{
		super(parent, name, true);
		setPreferredSize(new Dimension(HEIGHT, WIDHT));
		setLocationRelativeTo(parent);
		setResizable(false);
	}
}
