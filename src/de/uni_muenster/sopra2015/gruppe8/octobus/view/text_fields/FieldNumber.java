package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class FieldNumber extends FieldText
{
	private MaskFormatter formatter;

	public FieldNumber(int limit)
	{
		super();
		if(limit >= 9)
			setLimit(9);
		else
			setLimit(limit);
		setFieldMask();
	}

	public FieldNumber()
	{
		super(9);
		setFieldMask();
	}

	private void setFieldMask()
	{
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

	//TODO: setText() getText() Overrides

}
