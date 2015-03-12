package de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views;

import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintWorkPlan;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Implements method that is called during print
 */
public class PrintViewWorkPlan implements Printable, Pageable
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
	private final int entryHeight = 60;
	private final int entryXStart = 25;
	private final int entryYStart = 95;

	/**
	 * Inits all needed vars
	 * @param data PrintWorkPlan-Object, contains all data needed for print
	 */
	public PrintViewWorkPlan(PrintWorkPlan data)
	{
		this.data = data;

		fontHeader = new Font("Serif", Font.BOLD, 50);
		fontHeader2 = new Font("Serif", Font.BOLD, 20);
		fontNormal = new Font("Serif", Font.BOLD, 12);


		PageFormat pageFormat = new PageFormat();
		pageHeight = pageFormat.getImageableHeight();
		pageWidth = pageFormat.getImageableWidth();

		calcEntriesPerPage();
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws
	PrinterException {
		graphics2D = (Graphics2D) graphics;
		graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		//Check if there are still pages to print.
		if (pageIndex >= numPages)
		{
			return NO_SUCH_PAGE;
		}

		drawHeader();
		drawContent(pageIndex);

		return PAGE_EXISTS;
	}

	/**
	 * Draw header
	 */
	private void drawHeader()
	{
		graphics2D.setFont(fontHeader);
		graphics2D.drawString("OctoBUS", 20, 40);
		graphics2D.setFont(fontHeader2);
		graphics2D.drawString("Arbeitsplan für "+data.getEmployeeName(), 20, 65);
	}

	/**
	 * Draws content for specific page
	 * @param pageIndex page to print
	 */
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
			//Only print tour if there are some left
			if(i>= tours.size())
				break;

			Quadruple<String, Date, Integer, String> tour = tours.get(i);
			String curDate = date.format(tour.getSecond());
			//Check if to draw new date-string
			if(!curDate.equals(lastDate))
			{
				lastDate = curDate;
				graphics2D.drawString(curDate, curX, curY);
				curY += 20;
			}
			curX += 20;
			//Calculates finish-time
			Date finishedTime = new Date(tour.getSecond().getTime() + (tour.getThird() * 60 * 1000));
			String timeString = hour.format(tour.getSecond()) + " - "+hour.format(finishedTime);
			//Draw tour-details
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

	/**
	 * Calculate how many entries we could have on one page
	 * Calculate how many pages we will have
	 */
	private void calcEntriesPerPage()
	{
		entriesPerPage = ((int)(pageHeight)/entryHeight);
		numPages = data.numTours()/entriesPerPage;
		if(data.numTours()%entriesPerPage != 0)
			numPages++;
	}

	@Override
	public int getNumberOfPages()
	{
		return numPages;
	}

	@Override
	public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException
	{
		return new PageFormat();
	}

	@Override
	public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException
	{
		return this;
	}
}
