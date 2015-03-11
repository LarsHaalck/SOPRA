package de.uni_muenster.sopra2015.gruppe8.octobus.view.tabs.table_models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

/**
 * Date wrapper class for german date format which implements Comparator in order to be sorted in Tables
 */
public class TableDate implements Comparator<TableDate>, Comparable<TableDate>
{
	public enum Type
	{
		DATE,
		DATE_TIME,
		TIME
	}


	Date date;

	int day = -1;
	int month = -1;
	int year = -1;
	int hour = -1;
	int minute = -1;

	Calendar calendar;
	Type type;

	/**
	 * constructs TableDate from date object
	 * @param date Date object to be used
	 * @param type see TableData.TYPE (must be one of: Type.DATE, Type.DATE_TIME or Type.TIME)
	 */
	public TableDate(Date date, Type type)
	{
		this.date = date;
		this.type = type;
		calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (type == Type.DATE_TIME || type == Type.DATE)
		{
			this.day = this.calendar.get(Calendar.DAY_OF_MONTH);
			this.month = this.calendar.get(Calendar.MONTH);
			this.year = this.calendar.get(Calendar.YEAR);
		}
		if (type == Type.DATE_TIME || type == Type.TIME)
		{
			this.hour = this.calendar.get(Calendar.HOUR_OF_DAY);
			this.minute = this.calendar.get(Calendar.MINUTE);
		}
	}

	public String toString()
	{
		switch (type)
		{
			case DATE:
				return (new SimpleDateFormat("dd.MM.YYYY", Locale.GERMANY).format(date));
			case DATE_TIME:
				return (new SimpleDateFormat("dd.MM.YYYY HH:mm", Locale.GERMANY).format(date));
			case TIME:
				return (new SimpleDateFormat("HH:mm", Locale.GERMANY).format(date));

		}
		return "";
	}


	@Override
	public int compareTo(TableDate o)
	{
		return compare(this, o);
	}

	@Override
	public int compare(TableDate o1, TableDate o2)
	{
		if (o1.getType() != o2.getType())
		{
			System.out.println("compared two TableDates with different types.");
			return 0;
		}

		switch (this.type)
		{
			case DATE:
				return compareDate(o1, o2);
			case TIME:
				return compareTime(o1, o2);
			case DATE_TIME:
				int compDate = compareDate(o1, o2);
				int compTime = compareTime(o1, o2);
				if (compDate == 0 && compTime == 0)
					return 0;
				else if (compDate < 0)
					return -1;
				else if (compDate > 0)
					return 1;
				else if (compTime < 0)
					return -1;
				else
					return 1;
		}
		return 0;
	}


	/**
	 * compares time of two TableDate objects
	 * @param o1
	 * @param o2
	 * @return  -1 if o1 < o2
	 *          0 if o1 = o2    or
	 *          1 if o1 > o2
	 */
	private int compareTime(TableDate o1, TableDate o2)
	{
		if (o1.getHour() < o2.getHour())
			return -1;
		else if (o1.getHour() > o2.getHour())
			return 1;
		else
		{
			if (o1.getMinute() < o2.getMinute())
				return -1;
			else if (o1.getMinute() > o2.getMinute())
				return 1;
			else return 0;
		}

	}

	/**
	 * compares date (without time) of two TableDate objects
	 * @param o1
	 * @param o2
	 * @return  -1 if o1 < o2
	 *          0 if o1 = o2    or
	 *          1 if o1 > o2
	 */
	private int compareDate(TableDate o1, TableDate o2)
	{
		if (o1.getYear() < o2.getYear())
			return -1;
		else if (o1.getYear() > o2.getYear())
			return 1;
		else
		{
			if (o1.getMonth() < o2.getMonth())
				return -1;
			else if (o1.getMonth() > o2.getMonth())
				return 1;
			else
			{
				if (o1.getDay() < o2.getDay())
					return -1;
				else if (o1.getDay() > o2.getDay())
					return 1;
				else
					return 0;
			}
		}
	}

	public int getDay()
	{
		return day;
	}

	public int getMonth()
	{
		return month;
	}

	public int getYear()
	{
		return year;
	}

	public int getHour()
	{
		return hour;
	}

	public int getMinute()
	{
		return minute;
	}

	public Type getType()
	{
		return type;
	}

}
