package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldEmail extends FieldText
{
	public FieldEmail()
	{
		super();
		this.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e) //ignore some cases to prevent SQL-injections
			{
				char c = e.getKeyChar();
				if (c == '\'' || c == '=')
				{
					e.consume();  // ignore event
				}
			}
		});
	}

	private boolean isValidEmail()
	{
		String pattern = "^(.+)@(.+)$";
		if (Pattern.compile(pattern).matcher(this.getText()).matches())
			return true;
		else
			return false;
	}

	public String getEmail()
	{
		if(isValidEmail())
			return this.getText();
		else
			return null;
	}

	public void setEmail(String email)
	{
		this.setText(email);
	}
}
