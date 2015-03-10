package de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Route;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.StoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintStoppingPoint;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.time.DayOfWeek;
import java.util.ArrayList;

/**
 * Created by Florian on 07.03.2015.
 */
public class PrintViewStoppingPoint implements Printable
{
	private final int entryXStart = 80;
	private final int entryYStart = 180;
	private Graphics2D graphics2D;
	private Font fontHeader;
	private Font fontHeader2;
	private Font fontNormalHead;
	private Font fontNormal;
	private PrintStoppingPoint data;
	private ArrayList<PrintStoppingPoint.RouteEntry> routes;

	public PrintViewStoppingPoint(PrintStoppingPoint data)
	{
		this.data = data;

		fontHeader = new Font("Serif", Font.BOLD, 40);
		fontHeader2 = new Font("Serif", Font.BOLD, 10);
		fontNormalHead = new Font("Serif", Font.BOLD, 8);
		fontNormal = new Font("Serif", Font.PLAIN, 8);
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException
	{
		graphics2D = (Graphics2D) graphics;

		if (pageIndex >= calcNumPages())
		{
			return NO_SUCH_PAGE;
		}

		routes = data.getRouteEntries();

		for(PrintStoppingPoint.RouteEntry routeEntry: routes)
		{
			drawHeader(pageIndex, routeEntry);
			drawContent(pageIndex,routeEntry);
			pageIndex++;
		}
		return PAGE_EXISTS;
	}

	private void drawHeader(int pageIndex, PrintStoppingPoint.RouteEntry routeEntry)
	{
		graphics2D.setFont(fontHeader);
		graphics2D.drawString("OctoBUS", 140, 110);
		graphics2D.setFont(fontHeader2);
		graphics2D.drawString("Abfahrtszeiten von "+routeEntry.getBusStop().getName()+routeEntry.getStopPoint().getName()+" für Linie "+routeEntry.getRoute().getName(), entryXStart,135);//zB Zeiten von Hbf für Linie 11
	}

	private void drawContent(int pageIndex, PrintStoppingPoint.RouteEntry routeEntry)
	{
		long curX = entryXStart;
		long curY = entryYStart;

		String curDate;

		for (DayOfWeek day:DayOfWeek.values())
		{
			graphics2D.setFont(fontNormalHead);
			if(day == DayOfWeek.MONDAY){curDate = "Mo:";}
			else if(day == DayOfWeek.TUESDAY){curDate = "Di:";}
			else if(day == DayOfWeek.WEDNESDAY){curDate = "Mi:";}
			else if(day == DayOfWeek.THURSDAY){curDate = "Do:";}
			else if(day == DayOfWeek.FRIDAY){curDate = "Fr:";}
			else if(day == DayOfWeek.SATURDAY){curDate = "Sa:";}
			else {curDate = "So:";}
			graphics2D.drawString(curDate, curX, curY);
			graphics2D.setFont(fontNormal);
			curY += 20;
			for (int time : routeEntry.getStartTimes(day))
			{
				String min = time%60+"";
				if(min.length()==1){min = "0"+min;}
				String hour = time/60+"";
				if(hour.length()==1){hour = "0"+hour;}
				curDate = hour + ":" + min;
				graphics2D.drawString(curDate, curX, curY);
				curY += 10;
			}
			curX += 40;
			curY = entryYStart;
		}

		curX += 20;
		graphics2D.setFont(fontNormalHead);
		curDate = "Folgende Haltestellen:";
		graphics2D.drawString(curDate, curX, curY);
		graphics2D.setFont(fontNormal);
		for (String busStop: routeEntry.getNextStops())
		{
			curDate = busStop;
			curY += 20;
			graphics2D.drawString(curDate, curX, curY);
		}

		//TODO: Seitenumbruch bei zu vielen Einträgen

	}

	private int calcNumPages()
	{
		return data.getRouteEntries().size();
	}
}
