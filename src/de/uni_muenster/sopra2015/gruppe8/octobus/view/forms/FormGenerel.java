package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public abstract class FormGenerel extends JDialog
{
	public FormGenerel(Frame parent, String name)
	{
		super(parent, name, true);
		setPreferredSize(new Dimension(924, 540));
		setLocationRelativeTo(parent);
		setResizable(false);
	}
}
