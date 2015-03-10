package de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintStoppingPoint;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.time.DayOfWeek;

/**
 * Created by Florian on 07.03.2015.
 */
public class PrintViewStoppingPoint implements Printable
{
	private Graphics2D graphics2D;
	private double pageHeight;
	private double pageWidth;
	private Font fontHeader;
	private Font fontHeader2;
	private Font fontNormal;
	private PrintStoppingPoint data;
	private int entriesPerPage;
	private final int entryHeight = 70;
	private final int entryXStart = 60;
	private final int entryYStart = 180;

	private StoppingPoint stop;
	private Route route;

	public PrintViewStoppingPoint(PrintStoppingPoint data)
	{
		this.data = data;

		fontHeader = new Font("Serif", Font.BOLD, 30);
		fontHeader2 = new Font("Serif", Font.BOLD, 10);
		fontNormal = new Font("Serif", Font.BOLD, 12);

		stop = data.getStoppingPoint();
		route = data.getRoute();
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		graphics2D = (Graphics2D) graphics;
		graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		pageHeight = pageFormat.getImageableHeight();
		pageWidth = pageFormat.getImageableWidth();
		if (pageIndex >= 4)
		{
			return NO_SUCH_PAGE;
		}
		drawHeader();
		drawContent(pageIndex);
		return PAGE_EXISTS;
	}

	private void drawHeader()
	{
		graphics2D.setFont(fontHeader);
		graphics2D.drawString("OctoBUS", 150, 60);
		graphics2D.setFont(fontHeader2);
		graphics2D.drawString("Abfahrtszeiten von "+data.getBusStopNameByStoppingPointId(data.getStoppingPoint().getId())+" "+data.getStoppingPoint().getName()+" für Linie "+data.getRoute().getName(), 150,90);//zB Zeiten von Hbf für Linie 11
	}

	private void drawContent(int pageIndex)
	{
		long curX = entryXStart;
		long curY = entryYStart;

		graphics2D.setFont(fontNormal);

		for (int i = pageIndex*entriesPerPage; i<entriesPerPage*(pageIndex + 1); i++)
		{
			for (DayOfWeek day:DayOfWeek.values())
			{
				data = new PrintStoppingPoint(stop, route, day);
				String curDate = day + ":";
				graphics2D.drawString(curDate, curX, curY);
				curY += 20;
				for (int time : data.getDepartureTimes())
				{
					curDate = time / 60 + ":" + time % 60;
					graphics2D.drawString(curDate, curX, curY);
					curY += 20;
				}
				curX += pageWidth/5;
				curY = entryYStart;
			}
		}
	}
}
