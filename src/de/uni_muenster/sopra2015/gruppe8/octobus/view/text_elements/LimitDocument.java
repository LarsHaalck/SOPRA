package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Is used to limit content of ComponentText (sub-)classes (e.g. FieldText).
 */
// Package class
class LimitDocument extends PlainDocument
{
	private int limit = 200; //default limit

	LimitDocument()
	{
		super();
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
