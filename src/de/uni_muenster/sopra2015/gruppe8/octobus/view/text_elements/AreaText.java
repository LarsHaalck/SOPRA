package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Created by Lars on 06-Mar-15.
 */
public class AreaText extends JTextArea
{
	private int limit = 2000;

	public AreaText()
	{
		super();
		setFont(new Font("Tahoma", Font.PLAIN, 11));
	}

	@Override
	protected Document createDefaultModel()
	{
		return new LimitDocument();
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
