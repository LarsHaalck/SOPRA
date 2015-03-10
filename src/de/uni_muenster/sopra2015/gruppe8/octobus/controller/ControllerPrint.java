package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.RoutesRecord;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintStoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintWorkPlan;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views.PrintViewStoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views.PrintViewWorkPlan;

import java.awt.*;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controller will handle every print-request
 */
public class ControllerPrint extends Controller implements ListenerPrint
{
	private ControllerDatabase controllerDatabase;
	public ControllerPrint()
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();

		//controllerDatabase.createTours(new Date((long)1425966900*1000), new Date((long)1425981300*1000));
	}

	@Override
	public void printDocument(EmitterPrint emitter, ArrayList<Integer> objectIds)
	{

		switch (emitter)
		{
			case STOPPING_POINT:
				Book bookStoppingPoints = new Book();

				for (Integer objectId : objectIds)
				{
					PrintStoppingPoint printStoppingPoint = new PrintStoppingPoint(controllerDatabase.getStoppingPointById(objectId));
					PrintViewStoppingPoint printViewStoppingPoint = new PrintViewStoppingPoint(printStoppingPoint);
					bookStoppingPoints.append(printViewStoppingPoint, new PageFormat());
					//TODO: zweites Book überschreibt das erste
				}

				PrinterJob job = PrinterJob.getPrinterJob();
				job.setPageable(bookStoppingPoints);

				//Show dialog to user, select printer
				boolean doPrint = job.printDialog();
				if (doPrint)
				{
					try
					{
						job.print();
					} catch (Exception e)
					{
						System.out.println(e.getMessage());
						System.out.println("Don't print");
					}
				}
				break;

		}
	}

	@Override
	public void printDocument(EmitterPrint emitter, int objectId)
	{
		switch (emitter)
		{
			case WORK_PLAN:
				//TODO Maybe show a little message to user, prepare print or se

				if(objectId <= 0)
					break;

				//Get tour and employee-data
				ArrayList<Tour> tours = controllerDatabase.getUserTours(objectId);
				Employee employee = controllerDatabase.getEmployeeById(objectId);

				String name = employee.getName()+", "+employee.getFirstName();
				ArrayList<Quadruple<String, Date, Integer, String>> tourData = new ArrayList<>();

				for (Tour tour : tours)
				{
					//Build strings for printing
					String tourDesc = tour.getRoute().getName() + " (" + tour.getRoute().getStart().getName() + " - " + tour.getRoute().getEnd().getName()+")";
					String busName = tour.getBus().getLicencePlate();
					tourData.add(new Quadruple<>(tourDesc, tour.getStartTimestamp(), tour.getRoute().getDuration(), busName));
				}

				//Create new print-job
				PrintWorkPlan printWorkPlan = new PrintWorkPlan(name, tourData);
				
				PrinterJob job = PrinterJob.getPrinterJob();
				//PrintViewWorkPlan is only for managing data to print
				PrintViewWorkPlan printViewWorkPlan = new PrintViewWorkPlan(printWorkPlan);
				job.setPrintable(printViewWorkPlan);

				//Show dialog to user, select printer
				boolean doPrint = job.printDialog();
				if(doPrint)
				{
					try{
						job.print();
					}
					catch(Exception e)
					{
						System.out.println(e.getMessage());
						System.out.println("Don't print");
					}
				}

				//TODO Show error

				break;
		}
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerPrint)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerPrint)this);
	}
}
