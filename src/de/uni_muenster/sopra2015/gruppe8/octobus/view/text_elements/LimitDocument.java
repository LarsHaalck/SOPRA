package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * is used to limit content of text fields (e.g. FieldText)
 */
class LimitDocument extends PlainDocument//package class
{
	private int limit;

	LimitDocument()
	{
		super();
		limit = 200; //default value
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}


	@Override
	public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException
	{
		if (str == null) return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}

}
