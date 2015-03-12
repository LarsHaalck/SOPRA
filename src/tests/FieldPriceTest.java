package tests;

import de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements.FieldPrice;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldPriceTest
{
	FieldPrice fieldPrice;

	@Before
	public void setUp() throws Exception
	{
		fieldPrice = new FieldPrice();
	}

	@Test
	public void setPrice1() throws Exception
	{
		fieldPrice.setPrice(1);

		assertEquals("0,01", fieldPrice.getText());
	}

	@Test
	public void getPrice1() throws Exception
	{
		fieldPrice.setText(",01");

		assertEquals(1, fieldPrice.getPrice());
	}

	@Test
	public void getPrice2() throws Exception
	{
		fieldPrice.setText("1,");

		assertEquals(100, fieldPrice.getPrice());
	}

	@Test
	public void getPrice3() throws Exception
	{
		fieldPrice.setText("");

		assertEquals(-1, fieldPrice.getPrice());
	}

}