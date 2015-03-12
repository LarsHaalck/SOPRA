package de.uni_muenster.sopra2015.gruppe8.octobus.controller;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.EmitterPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.ListenerPrint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Quadruple;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintStoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.print.PrintWorkPlan;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views.PrintViewStoppingPoint;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.print_views.PrintViewWorkPlan;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
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
	}

	@Override
	public void printDocument(EmitterPrint emitter, ArrayList<Integer> objectIds)
	{

		switch (emitter)
		{
			//Print stopping-point-plans
			case STOPPING_POINT:
				printStoppingPoints(objectIds);
				break;

		}
	}

	@Override
	public void printDocument(EmitterPrint emitter, int objectId)
	{
		switch (emitter)
		{
			case WORK_PLAN:
				printWorkPlan(objectId);
				break;
		}
	}

	/**
	 * Prints work-plan for given user-id
	 * @param userId
	 * @pre user-id is valid id in employee-table in db, corresponding employee has Bus-Driver-Role
	 * @post print-job was sent if everything was okay
	 */
	private void printWorkPlan(int userId)
	{
		//Get tour and employee-data
		ArrayList<Tour> tours = controllerDatabase.getToursForEmployeeId(userId);
		Employee employee = controllerDatabase.getEmployeeById(userId);

		String name = employee.getName()+", "+employee.getFirstName();
		ArrayList<Quadruple<String, Date, Integer, String>> tourData = new ArrayList<>();

		for (Tour tour : tours)
		{
			//Build strings for printing
			String tourDesc = tour.getRoute().getName() + " (" + tour.getRoute().getStart().getName() + " - " + tour.getRoute().getEnd().getName()+")";
			String busName = tour.getBus() == null ? "" : tour.getBus().getLicencePlate();
			tourData.add(new Quadruple<>(tourDesc, tour.getStartTimestamp(), tour.getRoute().getDuration(), busName));
		}

		//Create new print-job
		PrintWorkPlan printWorkPlan = new PrintWorkPlan(name, tourData);
		//PrintViewWorkPlan is only for managing data to print
		PrintViewWorkPlan printViewWorkPlan = new PrintViewWorkPlan(printWorkPlan);
		Book bookWorkPlan = new Book();
		bookWorkPlan.append(printViewWorkPlan, new PageFormat(), printViewWorkPlan.getNumberOfPages());


		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(bookWorkPlan);

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
	}

	/**
	 * Prints bus-stopping-point-plans
	 * @param objectIds
	 * @pre all ids in objectIds are valid ids in stopping-points-db-table.
	 * @post print-job was sent if everything was okay
	 */
	private void printStoppingPoints(ArrayList<Integer> objectIds)
	{
		Book bookStoppingPoints = new Book();
		int firstPage = 0;

		//Every given id is an unique stopping-point
		for (Integer objectId : objectIds)
		{
			//Create print-data and add them to print-book
			PrintStoppingPoint printStoppingPoint = new PrintStoppingPoint(controllerDatabase.getStoppingPointById(objectId));
			PrintViewStoppingPoint printViewStoppingPoint = new PrintViewStoppingPoint(printStoppingPoint, firstPage);
			bookStoppingPoints.append(printViewStoppingPoint, new PageFormat(), printStoppingPoint.getNumRoutes());
			firstPage += printStoppingPoint.getNumRoutes();
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
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener(this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener(this);
	}
}
