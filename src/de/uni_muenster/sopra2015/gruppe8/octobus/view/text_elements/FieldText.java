package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * adjusted JFormattedTextField which can be limited to up to 200 characters
 */
public class FieldText extends JFormattedTextField
{
	private LimitDocument limitDocument;

	/**
	 * calls default constructor of JFormattedTextField
	 */
	public FieldText()
	{
		super();
	}


	/**
	 * calls default constructor of JFormattedTextField and sets limit
	 * @param limit limits maximum number of characters in FieldText. If reached, all user inputs, will be blocked
	 */
	public FieldText(int limit)
	{
		super();
		setLimit(limit);
	}


	/**
	 * calls default constructor of JFormattedTextField, sets column width of the field and sets limit
	 * @param columns numbers of columns to use to calculate preferred width
	 * @param limit maximum number of characters
	 */
	public FieldText(int columns, int limit)
	{
		super();
		this.setColumns(columns);

		if(limit != -1)
			setLimit(limit);
	}

	protected void setLimit(int limit)
	{
		limitDocument.setLimit(limit);
	}

	/**
	 * validates user input in order to prevent sql injections
	 * @return true iff user input does not contain malicious characters
	 */
	public boolean isValidInput()
	{
		//TODO: sql injection prevention
		return true;
	}


	/**
	 * overrides JFormattedTextField getText() by checking if input is malicious before returning
	 * @return text of JFormattedTextField or null if input is not valid
	 */
	@Override
	public String getText()
	{
		if(isValidInput())
			return super.getText();
		else
			return null;
	}



	@Override
	protected Document createDefaultModel()
	{
		this.limitDocument = new LimitDocument();
		return limitDocument;
	}

}
