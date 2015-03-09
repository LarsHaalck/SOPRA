package de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintWorkPlan;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Florian on 06.03.2015.
 */
public class PrintViewWorkPlan implements Printable
{
	private Graphics2D graphics2D;
	private Font fontHeader;
	private Font fontHeader2;
	private Font fontNormal;
	private double pageHeight;
	private double pageWidth;
	private PrintWorkPlan data;
	private int entriesPerPage;
	private int numPages;
	private final int entryHeight = 70;
	private final int entryXStart = 60;
	private final int entryYStart = 180;

	public PrintViewWorkPlan(PrintWorkPlan data)
	{
		this.data = data;

		fontHeader = new Font("Serif", Font.BOLD, 50);
		fontHeader2 = new Font("Serif", Font.BOLD, 20);
		fontNormal = new Font("Serif", Font.BOLD, 12);
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws
	PrinterException {
		graphics2D = (Graphics2D) graphics;
		graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		pageHeight = pageFormat.getImageableHeight();
		pageWidth = pageFormat.getImageableWidth();

		calcEntriesPerPage();

		if (pageIndex >= numPages)
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
		graphics2D.drawString("OctoBUS", 140, 110);
		graphics2D.setFont(fontHeader2);
		graphics2D.drawString("Arbeitsplan für "+data.getEmployeeName(), 140,135);
	}

	private void drawContent(int pageIndex)
	{
		SimpleDateFormat date = new SimpleDateFormat("E, d. MMM YYYY", Locale.GERMANY);
		SimpleDateFormat hour = new SimpleDateFormat("HH:mm");

		String lastDate = "";

		long curX = entryXStart;
		long curY = entryYStart;

		graphics2D.setFont(fontNormal);

		ArrayList<Quadruple<String, Date, Integer, String>> tours = data.getTours();
		for (int i = pageIndex*entriesPerPage; i<entriesPerPage*(pageIndex + 1); i++)
		{
			if(i>= tours.size())
				break;

			Quadruple<String, Date, Integer, String> tour = tours.get(i);
			String curDate = date.format(tour.getSecond());
			if(!curDate.equals(lastDate))
			{
				lastDate = curDate;
				graphics2D.drawString(curDate, curX, curY);
				curY += 20;
			}
			curX += 20;
			Date finishedTime = new Date(tour.getSecond().getTime() + (tour.getThird() * 60 * 1000));
			String timeString = hour.format(tour.getSecond()) + " - "+hour.format(finishedTime);
			graphics2D.drawString(timeString,curX, curY);
			curX += 70;
			graphics2D.drawString(tour.getFirst(),curX, curY);
			curX = entryXStart + 20;
			curY += 20;
			graphics2D.drawString("Bus: "+tour.getFourth(), curX, curY);
			curX = entryXStart;
			curY += 30;
		}
	}

	private void calcEntriesPerPage()
	{
		entriesPerPage = ((int)(pageHeight)/entryHeight)-2;
		numPages = data.numTours()/entriesPerPage;
		if(data.numTours()%entriesPerPage != 0)
			numPages++;
	}

	private void drawLines()
	{
		Font f = new Font("Serif", Font.PLAIN, 10);
		graphics2D.setFont(f);
		graphics2D.setColor(Color.black);
		for(double i=0; i<pageHeight; i+=20)
		{
			graphics2D.drawLine(0, (int) i, (int) pageWidth, (int) i);
			String s = (int)i+"";
			graphics2D.drawString(s, 1, 1+(int)i);
			for (double j = 0; j < pageWidth; j += 20)
			{
				s = (int)j+"";
				graphics2D.drawString(s, (int)j+1, 11);
				graphics2D.drawLine((int) j, 0, (int) j, (int) pageHeight);
			}
		}
	}
}
