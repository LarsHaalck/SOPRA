package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import javax.swing.*;
import javax.swing.text.Document;

public class FieldPassword extends JPasswordField
{
	private int limit = 32;
	private LimitDocument limitDoc;

	public FieldPassword()
	{
		super();
		limitDoc.setLimit(limit);
	}

	public FieldPassword(int columns)
	{
		super();
		this.setColumns(columns);
		limitDoc.setLimit(limit);
	}

	@Override
	protected Document createDefaultModel()
	{
		limitDoc = new LimitDocument();
		return limitDoc;
	}
}
