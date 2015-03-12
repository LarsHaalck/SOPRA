package tests;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldDate;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class FieldDateTest
{
	FieldDate fieldDate;

	@Before
	public void setUp() throws Exception
	{
		fieldDate = new FieldDate();
	}

	@Test
	public void testGetDate() throws Exception
	{

		fieldDate.setText("23.01.1994");
		Date result = fieldDate.getDate();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(result);

		assertEquals(1994, calendar.get(Calendar.YEAR));
		assertEquals(0, calendar.get(Calendar.MONTH)); //month are 0 indexed
		assertEquals(23, calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testInvalidDate1() throws Exception
	{
		fieldDate.setText("29.02.1993"); //1993 is now leapyear
		assertNull(fieldDate.getDate());
	}


	@Test
	public void testInvalidDate2() throws Exception
	{
		fieldDate.setText("dskfj");
		assertNull(fieldDate.getDate());
	}


}