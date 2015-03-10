package de.uni_muenster.sopra2015.gruppe8.octobus.controller.tab;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.Controller;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerManager;
import de.uni_muenster.sopra2015.gruppe8.octobus.controller.listeners.*;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Tour;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.TabWorkPlan;
import de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models.TableDate;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lars on 02-Mar-15.
 */
public class ControllerTabWorkPlan extends Controller implements ListenerButton
{
	private ControllerDatabase controllerDatabase;
	private TabWorkPlan tabWorkPlan;
	private int userId;

	public ControllerTabWorkPlan(TabWorkPlan tabWorkPlan, int userId)
	{
		super();
		controllerDatabase = ControllerDatabase.getInstance();
		this.userId = userId;
		this.tabWorkPlan = tabWorkPlan;
	}

	@Override
	protected void addListeners()
	{
		ControllerManager.addListener((ListenerButton)this);
	}

	@Override
	protected void removeListeners()
	{
		ControllerManager.removeListener((ListenerButton)this);
	}

	private void exportToIcal()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HHmmss");
		ArrayList<Tour> tours = controllerDatabase.getUserTours(userId);
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("iCal-Datei", "ical"));
		int returnVal = fc.showSaveDialog(null);
		if(returnVal == JFileChooser.CANCEL_OPTION)
			return;
		try {
			FileWriter fileWriter = new FileWriter(fc.getSelectedFile());
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write("BEGIN:VCALENDAR");
			bw.newLine();
			bw.write("VERSION:2.0");
			bw.newLine();
			bw.write("BEGIN:VTIMEZONE");
			bw.newLine();
			bw.write("TZID:Europe/Berlin");
			bw.newLine();
			bw.write("X-LIC-LOCATION:Europe/Berlin");
			bw.newLine();
			bw.write("BEGIN:DAYLIGHT");
			bw.newLine();
			bw.write("TZOFFSETFROM:+0100");
			bw.newLine();
			bw.write("TZOFFSETTO:+0200");
			bw.newLine();
			bw.write("TZNAME:CEST");
			bw.newLine();
			bw.write("DTSTART:19700329T020000");
			bw.newLine();
			bw.write("END:DAYLIGHT");
			bw.newLine();
			bw.write("BEGIN:STANDARD");
			bw.newLine();
			bw.write("TZOFFSETFROM:+0200");
			bw.newLine();
			bw.write("TZOFFSETTO:+0100");
			bw.newLine();
			bw.write("TZNAME:CET");
			bw.newLine();
			bw.write("DTSTART:19701025T030000");
			bw.newLine();
			bw.write("END:STANDARD");
			bw.newLine();
			bw.write("END:VTIMEZONE");
			bw.newLine();
			for(Tour tour : tours)
			{
				Date start = tour.getStartTimestamp();

				bw.write("BEGIN:VEVENT");
				bw.newLine();
				bw.write("LOCATION:" + tour.getRoute().getStart().getName());
				bw.newLine();
				bw.write("SUMMARY:" + tour.getRoute().getName());
				bw.newLine();
				bw.write("DESCRIPTION:" + tour.getRoute().getName() + " von " + tour.getRoute().getStart().getName() + " bis " + tour.getRoute().getEnd().getName() + " - Bus: " + tour.getBus().getLicencePlate());
				bw.newLine();
				bw.write("CLASS:PRIVATE");
				bw.newLine();
				bw.write("DTSTART;TZID=Europe/Berlin:" + dateFormat.format(start) + "T" + timeFormat.format(start));
				bw.newLine();
				Date end = new Date();
				end.setTime(start.getTime()+tour.getRoute().getDuration()*60*1000);
				bw.write("DTEND;TZID=Europe/Berlin:" + dateFormat.format(end)+"T"+timeFormat.format(end));
				bw.newLine();
				bw.write("DTSTAMP:20150309T170741");
				bw.newLine();
				bw.write("END:VEVENT");
				bw.newLine();
			}
			bw.write("END:VCALENDAR");

			bw.close();
			fileWriter.close();
		}
		catch (Exception e)
		{
			tabWorkPlan.showMessageDialog("iCal-Datei konnte nicht erstellt werden.");
		}
	}

	@Override
	public void buttonPressed(EmitterButton btn)
	{
		switch (btn)
		{
			case TAB_WORK_PLAN_EXPORT:
				exportToIcal();
				break;

			case TAB_WORK_PLAN_PRINT:
				ControllerManager.informPrintRequested(EmitterPrint.WORK_PLAN, userId);
				break;
		}
	}

	public void fillTable()
	{
		ArrayList<Tour> tours = controllerDatabase.getUserTours(userId);
		Object[][] data = new Object[tours.size()][7];
		for(int i=0; i<tours.size(); i++)
		{
			Tour tour = tours.get(i);
			data[i][0] = tour.getId();
			data[i][1] = new TableDate(tour.getStartTimestamp(), TableDate.Type.DATE_TIME);
			data[i][2] = tour.getRoute().getName();
			data[i][3] = tour.getRoute().getStart().getName();
			data[i][4] = tour.getRoute().getEnd().getName();
			data[i][5] = tour.getRoute().getDuration();
			data[i][6] = tour.getBus().getLicencePlate();
		}
		tabWorkPlan.fillTable(data);
		tabWorkPlan.enableButtons(data.length > 0);
	}
}
