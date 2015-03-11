package de.uni_muenster.sopra2015.gruppe8.octobus.view.forms;

import javax.swing.*;
import java.awt.*;

/**
 * General class with features all forms inherit.
 */
public abstract class FormGeneral extends JDialog
{
	public final int WIDTH = 924;
	public final int HEIGHT = 540;

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

	//TODO
	/**
	 *
	 */
	private void setMySettings(){
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocationRelativeTo(null);
        setResizable(false);
    }

	/**
	 * Displays an error dialog.
	 * @param error Error to be displayed.
	 */
	public void showErrorForm(String error)
	{
		JOptionPane.showMessageDialog(this, error, "Fehler", JOptionPane.ERROR_MESSAGE);
	}

	public void showInformationForm(String information)
	{
		JOptionPane.showMessageDialog(this, information, "Hinweis", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays a confirmation dialog.
	 * @param string Matter to be confirmed.
	 * @return true on confirmation.
	 */
	public boolean showConfirmDialog(String string)
	{
		return JOptionPane.showConfirmDialog(this, string, "Frage", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
				== JOptionPane.YES_OPTION;
	}

}
