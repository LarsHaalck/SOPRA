package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_fields;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldEmail extends FieldText
{
	public FieldEmail()
	{
		super();
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
