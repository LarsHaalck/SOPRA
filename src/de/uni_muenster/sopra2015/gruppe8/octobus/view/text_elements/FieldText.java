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

	private int limit = 200; //default value
	private LimitDocument limitDoc;

	public FieldText()
	{
		super();
		limitDoc.setLimit(200);

	}
	public FieldText(int limit)
	{
		super();
		this.limit = limit;
		limitDoc.setLimit(limit);
	}

	public FieldText(int width, int limit)
	{
		super();
		this.setColumns(width);
		if(limit == -1)
			this.limit = 200;
		else
			this.limit = limit;
		limitDoc.setLimit(limit);
	}



	protected void setLimit(int limit)
	{
		this.limit = limit;
		limitDoc.setLimit(limit);
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


	@Override
	protected Document createDefaultModel()
	{
		this.limitDoc = new LimitDocument();
		return limitDoc;
	}

}
