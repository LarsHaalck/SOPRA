package de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Created by Florian on 07.03.2015.
 */
public class PrintViewStoppingPoint implements Printable
{
	private Graphics2D graphics2D;

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		return 0;
	}
}
