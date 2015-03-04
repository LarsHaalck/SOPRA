package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jonas on 04.03.2015.
 */
public abstract class FormGeneral extends JDialog
{
	public static final int WIDTH = 924;
	public static final int HEIGHT = 540;

	public FormGeneral(Frame parent)
    {
        this(parent, "");
    }

    public FormGeneral(Frame parent, String name)
    {
        this(parent, name, true);
    }

    public FormGeneral(Frame parent, String name, boolean modal)
	{
		super(parent, name, modal);
		setMySettings();
	}

    public FormGeneral(Dialog parent)
    {
        this(parent, "");
    }

    public FormGeneral(Dialog parent, String name)
    {
        this(parent, name, true);
    }

    public FormGeneral(Dialog parent, String name, boolean modal)
    {
        super(parent, name, modal);
        setMySettings();
    }

    public FormGeneral(Window parent)
    {
        this(parent, "");
    }

    public FormGeneral(Window parent, String name)
    {
        super(parent, name);
        setMySettings();
    }

    private void setMySettings(){
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);
    }
}
