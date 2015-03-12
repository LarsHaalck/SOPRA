package tests;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldEmail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldEmailTest
{
	FieldEmail fieldEmail;
	@Before
	public void setUp() throws Exception
	{
		fieldEmail = new FieldEmail();
	}

	@Test
	public void testGetEmail1() throws Exception
	{
		fieldEmail.setText("bla@bla.de");
		assertEquals("bla@bla.de", fieldEmail.getEmail());
	}

	@Test
	public void testGetEmail2() throws Exception
	{
		fieldEmail.setText("bla@@bla.de");
		assertNull(null, fieldEmail.getEmail());
	}


}