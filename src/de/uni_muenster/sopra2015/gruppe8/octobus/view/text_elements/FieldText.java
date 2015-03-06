package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.util.regex.Pattern;

public class FieldText extends JFormattedTextField
{

	private int limit;

	public FieldText()
	{
		limit = 200;

	}
	public FieldText(int limit)
	{
		super();
		this.limit = limit;
	}

	public FieldText(int width, int limit)
	{
		super();
		this.setColumns(width);
		if(limit == -1)
			this.limit = 200;
		else
			this.limit = limit;
	}

	public FieldText(int limit, JFormattedTextField.AbstractFormatter formatter)
	{
		super(formatter);
		this.limit = limit;
	}


	@Override
	protected Document createDefaultModel()
	{
		return new LimitDocument();
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	@Override
	public String getText()
	{
		if(isValidInput())
			return super.getText();
		else
			return "";
	}

	public boolean isValidInput()
	{
		//TODO: sql injection prevention
		return true;
	}

	private class LimitDocument extends PlainDocument
	{
		@Override
		public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException
		{
			if (str == null) return;

			if ((getLength() + str.length()) <= limit) {
				super.insertString(offset, str, attr);
			}
		}
	}
}
