package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * Created by Lars on 05-Mar-15.
 */
public class FieldNumber extends FieldText
{
	private MaskFormatter formatter;

	public FieldNumber()
	{
		super(9);
		formatter = new MaskFormatter();
		try
		{
			formatter.setMask("#########");
		} catch (ParseException e)
		{

		}
		formatter.setValidCharacters("0123456789");
		setFormatterFactory(new DefaultFormatterFactory(formatter));

	}

	public int getNumber()
	{
		return Integer.parseInt(this.getText());
	}

	public void setNumber(int number)
	{
		this.setText(Integer.toString(number));

	}

}
