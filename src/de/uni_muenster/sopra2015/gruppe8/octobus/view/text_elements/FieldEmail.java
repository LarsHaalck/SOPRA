package de.uni_muenster.sopra2015.gruppe8.octobus.view.text_elements;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Pattern;

public class FieldEmail extends FieldText
{
	public FieldEmail()
	{
		super();
	}

	private boolean isValidEmail(String email)
	{
		if(email == null)
			return false;
		if(super.isValidInput())
		{
			//rfc 5322 regex pattern for email adresses
			String pattern = "^([\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+[\\.]?)+[\\!#\\$%&'\\*\\+\\/\\=?\\^`\\{\\|\\}~a-zA-Z0-9_-]+@{1}((([0-9A-Za-z_-]+)([\\.]{1}[0-9A-Za-z_-]+)*\\.{1}([A-Za-z]){1,6})|(([0-9]{1,3}[\\.]{1}){3}([0-9]{1,3}){1}))$";
			if (Pattern.compile(pattern).matcher(email).matches())
				return true;
		}
		return false;
	}

	public String getEmail()
	{
		String input = this.getText();
		if(isValidEmail(input))
			return input;
		else
			return null;
	}

	public void setEmail(String email)
	{
		if(isValidEmail(email))
			this.setText(email);
		else
			this.setText("");
	}

}
